package com.fish1208.ethereum;

import com.fish1208.contract.HelloWorld;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.QueuingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "ethereum-service")
public class ChainConfig {

    private String nodeUrl;

    private String coinbaseFile;

    private String coinbasePassword;

    private Long chainId;

    private Integer attemptsCount;

    private Long frequency;

    private Map<String, String> contractAddress;

    /**
     * admin继承web3j，所以只需实例化admin对象
     * @return
     */
    @Bean
    public Admin initAdmin(){
        return Admin.build(new HttpService(nodeUrl));
    }

    @Bean
    public Credentials initCredentials() throws Exception{
        return WalletUtils.loadCredentials(coinbasePassword, coinbaseFile);
    }

    @Bean(value = "queryHelloWorld")
    public HelloWorld loadQueryHelloWorld(Admin admin, Credentials credentials){
        return HelloWorld.load(contractAddress.get("helloWorld"), admin, credentials, new DefaultGasProvider());
    }

    @Bean(value = "invokeHelloWorld")
    public HelloWorld loadInvokeHelloWorld(Admin admin, Credentials credentials){
        //交易明细处理程序：通过内部队列管理和控制待处理的程序，周期性地判断交易明细是否就绪，倘若已就绪，则会通过回调（Call Back）方式通知相关程序进行后续的处理
        TransactionReceiptProcessor processor = new QueuingTransactionReceiptProcessor(admin, new ETHCallback(), attemptsCount, frequency);
        //处理程序管理组件
        TransactionManager manager = new RawTransactionManager(admin, credentials, chainId, processor);
        return HelloWorld.load(contractAddress.get("helloWorld"), admin, manager, new DefaultGasProvider());
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getCoinbaseFile() {
        return coinbaseFile;
    }

    public void setCoinbaseFile(String coinbaseFile) {
        this.coinbaseFile = coinbaseFile;
    }

    public String getCoinbasePassword() {
        return coinbasePassword;
    }

    public void setCoinbasePassword(String coinbasePassword) {
        this.coinbasePassword = coinbasePassword;
    }

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public Integer getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(Integer attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Map<String, String> getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(Map<String, String> contractAddress) {
        this.contractAddress = contractAddress;
    }
}
