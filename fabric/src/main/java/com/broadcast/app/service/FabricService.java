package com.broadcast.app.service;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.EnumSet;
@Component
public class FabricService {

    /**
     * 查询
     * @param contract
     * @param args
     * @return
     */
    public String query(Contract contract,String... args)throws Exception{
            //查询合约对象evaluateTransaction
        byte[] queryAResultBefore = contract.evaluateTransaction("query", args);
        String result=new String(queryAResultBefore, StandardCharsets.UTF_8);

        return result;
    }

    /**
     * 提交
     * @param gateway
     * @param mychannel
     * @param mycontract
     * @param args
     * @return
     */
    public String invoke(Gateway gateway,String mychannel,String mycontract,String... args)throws Exception{

            Network network = gateway.getNetwork(mychannel);
            //获取合约对象
            Contract contract = network.getContract(mycontract);
            Collection<Peer> peers = network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER));
            byte[] invokeResult = contract.createTransaction("invoke")
                    .setEndorsingPeers(peers)
                    .submit(args);
            return new String(invokeResult, StandardCharsets.UTF_8);
    }

    public String test(){
        return "jj";
    }

}
