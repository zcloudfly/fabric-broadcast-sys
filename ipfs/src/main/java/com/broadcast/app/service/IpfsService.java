package com.broadcast.app.service;


import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Component
public class IpfsService {

   /* @Value("${ipfs.server.url}")
    private String ipfsUrl;
    @Value("${file.temp.dir}")
    private String fileUrl;*/

    private final static String  fileDir=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private  final static String rootPath = "fileTmp"+File.separator+fileDir+File.separator;
    //实例化ipfs节点对象
    private final IPFS ipfs = new IPFS(new MultiAddress("/ip4/192.168.91.138/tcp/5001"));
    /**
     * 获取节点文件夹中文件列表
     */

    public List<MerkleNode> getfiles(String hash)throws IOException {
        //文件夹hash
        Multihash filePointer = Multihash.fromBase58(hash);
        return ipfs.ls(filePointer);
        /*for(MerkleNode node:ls){
            System.out.println(node.toJSONString());
        }*/
    }
    /**
     * 添加文件
     * @throws IOException
     */

    public String add(String fileDir) throws IOException {
        //上传文件
       String path= rootPath+fileDir;
        NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(new File(path));
        MerkleNode result = ipfs.add(savefile).get(0);
        return result.toJSONString();//返回结果里面获取保存文件的唯一hash，基于文件内容的 hash
    }

    /**
     * 文件下载并保存
     * @throws IOException
     */

    public  byte[] download(String hash) throws IOException {
        //文件hash
        //String hash="QmcAMXwHfxkQVQaSyTqG72fNbpthhLNNPRo1zzf6W9KwUf";
        //保存的文件路径
       // String filename="D:/vv.txt";
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
     return data;
    }

    /**
     * 文件删除
     * @throws IOException
     */

    public List<Multihash> delete(String hash) throws IOException {
        //文件hash
        //String hash="QmcAMXwHfxkQVQaSyTqG72fNbpthhLNNPRo1zzf6W9KwUf";
        Multihash filePointer = Multihash.fromBase58(hash);
        return ipfs.pin.rm(filePointer);
       // System.out.println(rm.get(0));//返回结果文件内容的 hash
    }

    /**
     * 添加新节点
     */

    public void addPeers(String peer)throws  IOException{
        //MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        MultiAddress multiAddress = new MultiAddress(peer);
        List<MultiAddress> peers = ipfs.bootstrap.add(multiAddress);
        System.out.println(peers.get(0).toString());
    }

    /**
     * 删除节点
     * @throws IOException
     */
    public void rmPeers()throws IOException{
        MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        List<MultiAddress> rm = ipfs.bootstrap.rm(multiAddress);
        System.out.println(rm.get(0).toString());
    }
}
