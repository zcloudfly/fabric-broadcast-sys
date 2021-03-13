package com.broadcast.app.util;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.impl.GatewayImpl;
import org.hyperledger.fabric.gateway.impl.identity.GatewayUser;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.identity.X509Enrollment;
import org.mockito.Mockito;

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

public class FabricUtil {

    private Network network;
    private static final Path NETWORK_CONFIG_PATH = Paths.get("fabric\\src", "main", "resources", "crypto-config/connection.json");
    private static final Path credentialPath = Paths.get("fabric\\src", "main", "resources", "crypto-config",
            "peerOrganizations", "org1.example.com", "users", "Admin@org1.example.com", "msp");

    /**
     * 获取网关
     * @return
     */
    public static Gateway geteWay() {
        Gateway gateway=null;
        X509Certificate certificate = null;
        PrivateKey privateKey = null;
        try {
            //使用org1中的user1初始化一个网关wallet账户用于连接网络
            Wallet wallet = Wallets.newInMemoryWallet();
            Path certificatePath = credentialPath.resolve(Paths.get("signcerts", "Admin@org1.example.com-cert.pem"));
            certificate = readX509Certificate(certificatePath);

            Path privateKeyPath = credentialPath.resolve(Paths.get("keystore", "priv_sk"));
            privateKey = getPrivateKey(privateKeyPath);

            wallet.put("admin", Identities.newX509Identity("Org1MSP", certificate, privateKey));

            //根据connection-org1.json 获取Fabric网络连接对象
            GatewayImpl.Builder builder = (GatewayImpl.Builder) Gateway.createBuilder();

            builder.identity(wallet, "admin").networkConfig(NETWORK_CONFIG_PATH);
            //连接网关
            gateway = builder.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gateway;

    }

    /**
     * 获取合约
     * @param gateway
     * @param channel
     * @param contract
     * @return
     */
    public static Contract contact(Gateway gateway,String channel,String contract ){
        //获取mychannel通道
        Network network = gateway.getNetwork(channel);
        //获取合约对象
        return network.getContract(contract);
    }

    public static HFClient newMockClient() throws Exception{
        X509Credentials credentials = new X509Credentials();
        Path privateKeyPath = credentialPath.resolve(Paths.get("keystore", "priv_sk"));

        Path certificatePath = credentialPath.resolve(Paths.get("signcerts", "Admin@org1.example.com-cert.pem"));

        Enrollment enrollment = new X509Enrollment(getPrivateKey(privateKeyPath),  new String(readX509Certificate(certificatePath).getSignature()));
        User user = new GatewayUser("user", "msp1", enrollment);

        HFClient mockClient = Mockito.mock(HFClient.class);
        Mockito.when(mockClient.getUserContext()).thenReturn(user);
        Mockito.when(mockClient.newTransactionProposalRequest()).thenReturn(TransactionProposalRequest.newInstance(user));
        Mockito.when(mockClient.newQueryProposalRequest()).thenReturn(QueryByChaincodeRequest.newInstance(user));

        return mockClient;
    }

    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
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