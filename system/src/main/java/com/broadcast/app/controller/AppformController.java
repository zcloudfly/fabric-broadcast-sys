package com.broadcast.app.controller;

import com.alibaba.fastjson.JSON;
import com.broadcast.app.controller.vo.AppformVo;
import com.broadcast.app.entity.*;
import com.broadcast.app.service.*;
import com.broadcast.app.util.FabricUtil;
import com.broadcast.app.util.Result;
import com.broadcast.app.util.ResultUtil;
import io.ipfs.api.MerkleNode;

import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (Appform)表控制层
 *
 * @author makejava
 * @since 2021-01-29 15:14:23
 */
@RestController
@RequestMapping("appform")
@CrossOrigin
public class AppformController {
    /**
     * 服务对象
     */
    @Resource
    private AppformService appformService;
    @Resource
    private IpfsService ipfsService;
    @Resource
    private AttachService attachService;
    @Resource
    private FabricService fabricService;
    @Resource
    private OrgService orgService;
    @Resource
    private  ClienttermService clienttermService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Result selectOne( String id) {
        Appform appform = this.appformService.queryById(id);
        Attach attach = new Attach();
        attach.setAppformid(id);
        List<Attach> files = this.attachService.queryAll(attach);
        appform.setFiles(files);

        return ResultUtil.success(appform);
    }

    @GetMapping("deletetOne")
    public Result deletetOne( String id) {
        this.appformService.deleteById(id);
        return ResultUtil.success();
    }


    @PostMapping("insert")
    public Object insertAppForm(@RequestBody   Appform sendForm ){
       // String fid= UUID.randomUUID().toString().replace("-","");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fid = format.format(new Date());
        sendForm.setId(fid);
        sendForm.setSts("0");
        sendForm.setCreatetime(new Date());
      try {
          List<Attach> files = sendForm.getFiles();
          for (int j = 0; j < files.size(); j++) {

              String json = ipfsService.add(files.get(j).getFilename());
              MerkleNode merkleNode = JSON.parseObject(json, MerkleNode.class);
              String hash = merkleNode.hash.toString();
              //插入附件表
              Attach attach = new Attach();
              attach.setFilename(files.get(j).getFilename());
              attach.setFilesize(files.get(j).getFilesize());
              attach.setId(UUID.randomUUID().toString().replace("-",""));
              attach.setHashid(hash);
              attach.setAppformid(fid);
              attachService.insert(attach);

          }
          int i = appformService.insert(sendForm);
        //  pushPlstToBlock(sendForm);
          if (i > 0) {
              return ResultUtil.success(sendForm);
          } else {
              return ResultUtil.error("提交失败");
          }
      }catch (Exception e){
          e.printStackTrace();
          return ResultUtil.error(e.getMessage());
      }

    }



    /**
     * 生成机构范围下的所有客户端的信息列表，并发布到区块链上
     * @param sendForm
     */
    @PostMapping("pushPlstToBlock")
   public Object pushPlstToBlock(@RequestBody Appform sendForm){
       try {
           sendForm = appformService.queryById(sendForm.getId());
           sendForm.setSts("1");
           this.appformService.update(sendForm);
           String distOrg = sendForm.getDistorgid();
           List<OrgTree> orgTrees = orgService.queryById(distOrg);
           //获取客户端
           List<Clientterm> clients = getClients(orgTrees.get(0));
           //生成播放data
           Attach attach = new Attach();
           attach.setAppformid(sendForm.getId());
           List<Attach> attaches = attachService.queryAll(attach);
           PlayList playList = null;
           List<ListData> listData = new ArrayList<>();
           ListData data =null;
           for (Attach att : attaches) {
               data= new ListData();
               data.setName(att.getFilename());
               data.setHashid(att.getHashid());
               data.setStaopdate(sendForm.getEndtime());
               data.setStartdate(sendForm.getStarttime());
               data.setType(sendForm.getInfotype());
               listData.add(data);
           }
           //push到blockchain
           Gateway gateway = FabricUtil.geteWay();
           //Contract contact = FabricUtil.contact(gateway, "mychannel", "play1_2_1");
           for (Clientterm cli : clients) {
               String re=fabricService.invoke(gateway, "mychannel", "play1_2_1",
                       cli.getClientid(),JSON.toJSON(listData).toString());
               System.out.println(re);
           }

           return ResultUtil.success("提交成功");
       }catch (Exception e){
           e.printStackTrace();
           return ResultUtil.error(e.getMessage());

       }
   }

    /**
     * 根据机构范围获取所有客户端
     * @param orgTree
     * @return
     */
   public List<Clientterm> getClients(OrgTree orgTree){
       List<Clientterm> clis = new ArrayList<>();
       Clientterm cli=new Clientterm();
       cli.setOrgid(orgTree.getId());
       List<Clientterm> clientterms = clienttermService.queryAll(cli);
       if(clientterms.size()>0) {
           clis.addAll(clientterms);
       }

       List<OrgTree> children = orgTree.getChildren();
       for (OrgTree org:children) {
           clis.addAll(getClients(org));
       }
       return clis;
   }

   @PostMapping("findFormByWhere")
    public Result findFormByWhere(@RequestBody AppformVo vo){
       int total = this.appformService.querytotal(vo);
       vo.setTotal(total);
       int pageNum=vo.getCurrentPage();
       int pageSize=vo.getPagesize();
       int offset=vo.getCurrentPage() > 0 ? (pageNum - 1) * pageSize : 0;
       vo.setOffset(offset);
       vo.setLimit( offset + pageSize * (pageNum > 0 ? 1 : 0));
       List<Appform> appform=this.appformService.queryAll(vo);
        return ResultUtil.successPage(total,appform);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}