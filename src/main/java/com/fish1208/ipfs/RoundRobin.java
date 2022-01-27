package com.fish1208.ipfs;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * 轮询
 */
@Slf4j
public class RoundRobin {
    private static Integer pos = 0;

    public static Set<String> deadPeer = new HashSet<String>();
    //轮询
    public static IPFSPeer getPeer(IPFSCluster ipfsCluster) {

        int peerNum = ipfsCluster.getPeers().size();

        // 取得Ip地址List
        IPFSPeer peer = null;
        synchronized (pos) {
            if (pos >= peerNum){
                pos = 0;
            }
            peer = ipfsCluster.getPeers().get(pos);
            pos ++;
            if(!peer.check()){

                if (deadPeer.size() == peerNum){
                    log.info("all ipfs peers down");
                    return null;
                }

                log.info("ipfs peer down "+peer.getIp()+":"+peer.getPort());
                deadPeer.add(peer.getIp()+":"+peer.getPort());
                return getPeer(ipfsCluster);
            }
            deadPeer.remove(peer.getIp()+":"+peer.getPort());

        }
        log.info("use ipfs peer "+peer.getIp()+":"+peer.getPort());
        return peer;
    }
}
