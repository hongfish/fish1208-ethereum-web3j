package com.fish1208.ipfs;

public class FileByte {
    private String filename;
    private byte[] data;

    public FileByte() {
    }

    public FileByte(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
