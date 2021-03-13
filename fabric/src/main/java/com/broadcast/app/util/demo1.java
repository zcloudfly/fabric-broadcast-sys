
package com.broadcast.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.impl.GatewayImpl;
import org.hyperledger.fabric.sdk.Peer;

import javax.json.Json;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

/**
 * @author lps
 * @title: demo1
 * @projectName fabricdemo
 * @description: fabric2.0 java SDK使用
 * @date 2020/2/2218:10
 */

public class demo1 {
   // private Gateway gateway;
  //  private Network network;
    private static final Path NETWORK_CONFIG_PATH = Paths.get(".\\fabric\\src", "main", "resources", "crypto-config/connection.json");
    private static final Path credentialPath = Paths.get(".\\fabric\\src", "main","resources", "crypto-config",
            "peerOrganizations", "org1.example.com", "users", "User1@org1.example.com", "msp");

    public static void main(String[] args) {

        X509Certificate certificate = null;
        PrivateKey privateKey = null;
        Gateway gateway = null;
        try {
            //使用org1中的user1初始化一个网关wallet账户用于连接网络
            Wallet wallet = Wallets.newInMemoryWallet();
            Path certificatePath = credentialPath.resolve(Paths.get("signcerts", "User1@org1.example.com-cert.pem"));
            certificate = readX509Certificate(certificatePath);

            Path privateKeyPath = credentialPath.resolve(Paths.get("keystore", "priv_sk"));
            privateKey = getPrivateKey(privateKeyPath);

            wallet.put("user",Identities.newX509Identity("Org1MSP",certificate,privateKey));

            //根据connection-org1.json 获取Fabric网络连接对象
            GatewayImpl.Builder builder = (GatewayImpl.Builder) Gateway.createBuilder();

            builder.identity(wallet, "user").networkConfig(NETWORK_CONFIG_PATH);
            //连接网关
            gateway = builder.connect();
            //获取mychannel通道
            Network network = gateway.getNetwork("mychannel");
            //获取合约对象
            Contract contract = network.getContract("play1_2_1");
           // Contract contract = network.getContract("broadcast2_1");
            //查询合约对象evaluateTransaction
       // byte[] queryAResultBefore = contract.evaluateTransaction("query","444");
       //   System.out.println("交易前："+new String(queryAResultBefore, StandardCharsets.UTF_8));

          // 创建并且提交交易


            //char[] chars = JSON.toJSONString(list).toCharArray();
           // System.out.println(s);
          //  System.out.println();
//           Collection<Peer> peers = network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER));
//            byte[] invokeResult = contract.createTransaction("Invoke")
//                    .setEndorsingPeers(peers)
//                    .submit("444",new String(chars));
//           System.out.println(new String(invokeResult, StandardCharsets.UTF_8));

            //查询合约对象evaluateTransaction
           byte[] queryAResultAfter = contract.evaluateTransaction("query","444");
            String s = new String(queryAResultAfter, StandardCharsets.UTF_8);
            Object parse = JSON.parse(s);

            System.out.println("交易后："+parse);
            System.out.println("交易后："+s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        //String url="src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\signcerts\\Admin@org1.example.com-cert.pem";
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
