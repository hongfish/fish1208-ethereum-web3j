package com.fish1208.ipfs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ipfs-service")
public class IPFSConfig {

    private String node;

    @Bean(name="ipfsCluster")
    public IPFSCluster setIpfsCluster() {
        IPFSCluster ipfs = new IPFSCluster();
        String[] nodes = node.split(";");
        for(String n : nodes){
            String[] ip = n.split(":");
            IPFSPeer peer = new IPFSPeer(ip[0], Integer.parseInt(ip[1]));
            ipfs.addPeer(peer);
        }
        return ipfs;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
