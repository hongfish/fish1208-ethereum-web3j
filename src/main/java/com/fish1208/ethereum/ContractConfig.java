//package com.fish1208.ethereum;
//
//import com.fish1208.contract.HelloWorld;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.admin.Admin;
//import org.web3j.tx.RawTransactionManager;
//import org.web3j.tx.TransactionManager;
//import org.web3j.tx.gas.DefaultGasProvider;
//import org.web3j.tx.response.QueuingTransactionReceiptProcessor;
//import org.web3j.tx.response.TransactionReceiptProcessor;
//
//@Slf4j
//@Configuration
//@ConfigurationProperties(prefix = "contract-address")
//public class ContractConfig {
//
//    private String helloWorld;
//
//    @Autowired
//    private Credentials credentials;
//
//    @Autowired
//    private Admin admin;
//
//    @Bean
//    public HelloWorld loadHelloWorld(){
//        int attemptsPerTxHash = 30; //尝试轮询次数
//        long frequency = 1000; //轮询的时间周期
//        TransactionReceiptProcessor processor = new QueuingTransactionReceiptProcessor(admin, new ETHCallback(), attemptsPerTxHash, frequency);
//        TransactionManager manager = new RawTransactionManager(admin, credentials, 128, processor);
//        HelloWorld helloWorld2 = HelloWorld.load(helloWorld, admin, manager, new DefaultGasProvider());
//
//        return HelloWorld.load(helloWorld, admin, credentials, new DefaultGasProvider());
//    }
//
//    public String getHelloWorld() {
//        return helloWorld;
//    }
//
//    public void setHelloWorld(String helloWorld) {
//        this.helloWorld = helloWorld;
//    }
//}
