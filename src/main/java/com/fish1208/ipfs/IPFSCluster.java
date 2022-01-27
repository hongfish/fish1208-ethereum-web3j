package com.fish1208.ipfs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ipfs 集群
 */
public class IPFSCluster {

    private ArrayList<IPFSPeer> peers;

    public IPFSCluster() {
        this.peers = new ArrayList<IPFSPeer>();
    }

    public ArrayList<IPFSPeer> getPeers() {
        return peers;
    }

    public void addPeer(IPFSPeer peer) {
        this.peers.add(peer);
    }

    private IPFSPeer getPeer() {
        return RoundRobin.getPeer(this);
    }


    public String upload(File f) throws IOException {
        return  this.getPeer().upload(f);
    }

    public String upload(String filename,byte[] bytes) throws IOException {
        return  this.getPeer().upload(filename,bytes);
    }

    public String uploadDir(String dir,File f) throws IOException {
        return  this.getPeer().uploadDir(dir,f);
    }

    public File downloadDir(String hash) throws IOException {
        return  this.getPeer().downloadDir(hash);
    }

    public FileByte downloadDirBytes(String hash) throws IOException {
        return  this.getPeer().downloadDirBytes(hash);
    }

    public byte[] download(String hash) throws IOException {
        return  this.getPeer().download(hash);
    }

    public String uploadDir(String dir, String filename, byte[] bytes) throws IOException {
        return  this.getPeer().uploadDir(dir,filename,bytes);
    }
}
