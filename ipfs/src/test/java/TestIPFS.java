import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 注意：查询和下载可以是其他节点数据，添加删除必须是本节点数据
 */
public class TestIPFS {
    //实例化ipfs节点对象
    private final IPFS ipfs = new IPFS(new MultiAddress("/ip4/192.168.91.138/tcp/5001"));
    /**
     * 获取节点文件夹中文件列表
     */
    @Test
    public void getfiles()throws IOException{
        //文件夹hash
        String hash="QmR8CCp1D237t5HWJUH8pwiDH4K2gkZxnn9JpesdiTuV1U";
        Multihash filePointer = Multihash.fromBase58(hash);
        List<MerkleNode> ls = ipfs.ls(filePointer);
        for(MerkleNode node:ls){
            System.out.println(node.toJSONString());
        }
    }
    /**
     * 添加文件
     * @throws IOException
     */
    @Test
    public void add() throws IOException {
       //上传文件
       NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(new File("D:/文件.jpg"));
       MerkleNode result = ipfs.add(savefile).get(0);
       System.out.println(result.toJSONString());//返回结果里面获取保存文件的唯一hash，基于文件内容的 hash
    }

    /**
     * 文件下载并保存
     * @throws IOException
     */
    @Test
    public  void download() throws IOException {
        //文件hash
        String hash="QmcAMXwHfxkQVQaSyTqG72fNbpthhLNNPRo1zzf6W9KwUf";
        //保存的文件路径
        String filename="D:/vv.txt";
        Multihash filePointer = Multihash.fromBase58(hash);
        byte[] data = ipfs.cat(filePointer);
        if(data != null){
            File file  = new File(filename);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }

    /**
     * 文件删除
     * @throws IOException
     */
    @Test
    public void delete() throws IOException {
        //文件hash
        String hash="QmcAMXwHfxkQVQaSyTqG72fNbpthhLNNPRo1zzf6W9KwUf";
        Multihash filePointer = Multihash.fromBase58(hash);
        List<Multihash> rm = ipfs.pin.rm(filePointer);
        System.out.println(rm.get(0));//返回结果文件内容的 hash
    }

    /**
     * 添加新节点
     */
    @Test
    public void addPeers()throws  IOException{
        MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        List<MultiAddress> peers = ipfs.bootstrap.add(multiAddress);
        System.out.println(peers.get(0).toString());
    }

    /**
     * 删除节点
     * @throws IOException
     */
    @Test
    public void rmPeers()throws IOException{
        MultiAddress multiAddress = new MultiAddress("/ip4/192.168.91.140/tcp/4001/ipfs/QmQJPAqVaURJbSnFLaiA65Zm6fYePFTR7AkWKT2LANp7X5");
        List<MultiAddress> rm = ipfs.bootstrap.rm(multiAddress);
        System.out.println(rm.get(0).toString());
    }
}
