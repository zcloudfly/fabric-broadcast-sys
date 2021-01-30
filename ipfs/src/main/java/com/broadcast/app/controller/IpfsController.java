package com.broadcast.app.controller;

import com.broadcast.app.service.IpfsService;
import com.broadcast.app.util.Result;
import com.broadcast.app.util.ResultUtil;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

}
