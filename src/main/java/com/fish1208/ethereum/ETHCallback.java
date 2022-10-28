package com.fish1208.ethereum;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.response.Callback;

import java.util.List;

/**
 * 回调函数，获取交易明细
 */
@Slf4j
public class ETHCallback implements Callback {

    @Override
    public void accept(TransactionReceipt receipt) {
        log.info("交易明细: {}",  JSON.toJSONString(receipt));
//        List<Log> logList = receipt.getLogs();
//        if(CollUtil.isNotEmpty(logList)){
//            for (Log logInfo : logList) {
//                log.info("log data: {}", logInfo.getData());
//            }
//        }
    }

    @Override
    public void exception(Exception e) {
        log.error("交易失败, err: {}", e);
    }
}
