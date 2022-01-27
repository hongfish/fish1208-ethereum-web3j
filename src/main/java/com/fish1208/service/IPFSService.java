package com.fish1208.service;

import com.fish1208.ipfs.FileByte;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public interface IPFSService {

    public String upload(File f);

    public String upload(String filename ,byte[] bytes);

    public File download(String hash);

    public FileByte downloadBytes(String hash);

}
