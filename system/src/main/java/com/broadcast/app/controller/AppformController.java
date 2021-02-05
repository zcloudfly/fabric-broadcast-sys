package com.broadcast.app.controller;

import com.alibaba.fastjson.JSON;
import com.broadcast.app.entity.Appform;
import com.broadcast.app.entity.Attach;
import com.broadcast.app.service.AppformService;
import com.broadcast.app.service.AttachService;
import com.broadcast.app.service.IpfsService;
import com.broadcast.app.util.ResultUtil;
import io.ipfs.api.MerkleNode;
import io.ipfs.multihash.Multihash;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Appform selectOne(String id) {
        return this.appformService.queryById(id);
    }


    @PostMapping("insert")
    public Object insertAppForm(@RequestBody   Appform sendForm ){
        String fid= UUID.randomUUID().toString().replace("-","");
        sendForm.setId(fid);
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
          if (i > 0) {
              return ResultUtil.success("提交成功");
          } else {
              return ResultUtil.error("提交失败");
          }
      }catch (Exception e){
          return ResultUtil.error(e.getMessage());
      }

    }

   /* @RequestMapping("insert")
    public Object commitDataToBlockChain(@RequestBody   Appform sendForm ){
        Appform appform = appformService.queryById(sendForm.getId());



    }*/

}