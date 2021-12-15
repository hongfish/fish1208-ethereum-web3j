package com.fish1208.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WatcherLog {
    private String coinName;
    private Long lastSyncHeight;
    private Date lastSyncTime;
}
