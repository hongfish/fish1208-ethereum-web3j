package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EthereumWeb3jApplication {

    public static void main(String[] args) {
        SpringApplication.run(EthereumWeb3jApplication.class, args);
    }

}
