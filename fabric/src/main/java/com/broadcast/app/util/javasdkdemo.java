package com.broadcast.app.util;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ChannelConfiguration;
import org.hyperledger.fabric.sdk.HFClient;

;import java.io.File;

public class javasdkdemo {

    public static void main(String[] args) throws Exception {

        HFClient client = FabricUtil.newMockClient();
        //ChannelConfiguration channelConfiguration = new ChannelConfiguration("");
       // Channel newChannel = client.newChannel(name, anOrderer, channelConfiguration, client.getChannelConfigurationSignature(channelConfiguration, peerAdmin));


    }

}