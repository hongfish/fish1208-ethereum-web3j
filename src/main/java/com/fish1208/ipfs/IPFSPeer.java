package com.fish1208.ipfs;


import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ipfs 节点
 */
@Slf4j
public class IPFSPeer {
    private String ip;
    private int port;
    private IPFS client;

    public IPFSPeer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            this.client = new IPFS("/ip4/"+ ip + "/tcp/"+ port);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public boolean check() {
        try {
            this.client.id();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private String uploadDir(String dirname,List<NamedStreamable> children) throws IOException {
        NamedStreamable dir = new NamedStreamable.DirWrapper(dirname, children);
        List<MerkleNode> add = this.client.add(dir);
        MerkleNode res = null;
        //传一个文件返回两个node
        if (add.size() == 2 && Integer.parseInt(add.get(0).largeSize.get()) > Integer.parseInt(add.get(1).largeSize.get())){
            res = add.get(0);
        }else {
            res = add.get(1);
        }
        return  res.hash.toBase58();
    }

    public String uploadDir(String dirname,File filename) throws IOException {
        List<NamedStreamable> children = new ArrayList<NamedStreamable>();
        NamedStreamable file = new NamedStreamable.FileWrapper(filename);
        children.add(file);
        return uploadDir(dirname,children);
    }

    public String uploadDir(String dirname,String filename, byte[] bytes) throws IOException {
        List<NamedStreamable> children = new ArrayList<NamedStreamable>();
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(filename,bytes);
        children.add(file);
        return uploadDir(dirname,children);
    }

    public String upload(File f) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(f);
        MerkleNode res = this.client.add(file).get(0);
        return  res.hash.toBase58();
    }

    public String upload(String filename, byte[] bytes) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(filename,bytes);
        MerkleNode res = this.client.add(file).get(0);
        return  res.hash.toBase58();
    }

    public File downloadDir(String hash) throws IOException {
        List<MerkleNode> ls = this.client.ls(Multihash.fromBase58(hash));
        String filename = null;
        String filehash = null;
        if (ls.size() == 1) {
            MerkleNode merkleNode = ls.get(0).links.get(0);
            filename = merkleNode.name.get();
            filehash = merkleNode.hash.toBase58();
        }
        byte[] fileContents = this.client.cat(Multihash.fromBase58(filehash));

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = new File(filename);
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(fileContents);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return file;
    }

    public FileByte downloadDirBytes(String hash) throws IOException {
        List<MerkleNode> ls = this.client.ls(Multihash.fromBase58(hash));
        String filename = null;
        String filehash = null;
        if (ls.size() == 1) {
            MerkleNode merkleNode = ls.get(0).links.get(0);
            filename = merkleNode.name.get();
            filehash = merkleNode.hash.toBase58();
        }
        byte[] fileContents = this.client.cat(Multihash.fromBase58(filehash));
        return new FileByte(filename,fileContents);
    }

    public byte[] download(String base58) throws IOException {
        Multihash filePointer = Multihash.fromBase58(base58);
        byte[] fileContents = this.client.cat(filePointer);
        return fileContents;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
