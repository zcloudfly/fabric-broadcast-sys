package com.broadcast.app.controller;

import com.broadcast.app.service.IpfsService;
import com.broadcast.app.util.Result;
import com.broadcast.app.util.ResultUtil;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
@RestController
@RequestMapping("/ipfs")
public class IpfsController {

    @Resource
    private IpfsService service;

    @RequestMapping("/getfiles")
    public Result getfiles(String hash){
        try {
            List<MerkleNode> files = service.getfiles(hash);
            return ResultUtil.success(files);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
    }
    @RequestMapping("/getfile")
    @ResponseBody
    @CrossOrigin
    public  void download(String hash, String filename, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName="+ URLEncoder.encode(filename, "UTF-8"));

        byte[] buff = service.download(hash);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
       // byte[] buff =new byte[1024];
        int index=0;
        //4、执行 写出操作
        out.write(buff, 0, buff.length);
        out.flush();
        out.close();

    }

}
