package com.fish1208.service.impl;

import com.fish1208.ipfs.FileByte;
import com.fish1208.ipfs.IPFSCluster;
import com.fish1208.service.IPFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class IPFSServiceImpl implements IPFSService {

    private final String dir = "private_dir";

    @Autowired
    private IPFSCluster ipfsCluster;

    @Override
    public String upload(File f) {
        try {
            return ipfsCluster.uploadDir(dir,f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String upload(String filename, byte[] bytes) {
        try {
            return ipfsCluster.uploadDir(dir,filename,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public File download(String hash) {
        try {
            return ipfsCluster.downloadDir(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileByte downloadBytes(String hash) {
        try {
            return ipfsCluster.downloadDirBytes(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
