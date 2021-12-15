package com.fish1208.ethereum.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

//import org.web3j.protocol.Web3j;

@Configuration
@ConfigurationProperties(prefix = "ethereum-service")
public class ChainConfig {

    private String url;

//    @Bean("ethWeb3j")
//    public Web3j initWeb3j() {
//        return Web3j.build(new HttpService(url));//实例化web3j对象;
//    }

    /**
     * admin继承web3j，所以只需实例化admin对象
     * @return
     */
    @Bean
    public Admin initAdmin(){
        return Admin.build(new HttpService(url));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
