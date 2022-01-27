package com.fish1208.controller;

import com.fish1208.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eth")
public class ETHController {

    @Autowired
    private Admin admin;

    /**
     * 获取所有账户地址
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/accounts")
    public Result<?> accounts() throws Exception {
        Request<?, EthAccounts> request = admin.ethAccounts();//获取accounts
        EthAccounts response = request.send();//提交请求并获取响应
        List<String> accounts = response.getAccounts();//获取结果
        return Result.data(accounts);
    }

    /**
     * 获取当前区块高度
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getCurrentBlockNumber")
    public Result<?> getCurrentBlockNumber() throws Exception{
        Request<?, EthBlockNumber> request = admin.ethBlockNumber();
        return Result.data(request.send().getBlockNumber());
    }

    /**
     * 根据hash值获取交易
     *
     * @param hash
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getTransactionByHash")
    public Result<?> getTransactionByHash(@RequestParam String hash) throws Exception {
        Request<?, EthTransaction> request = admin.ethGetTransactionByHash(hash);
        return Result.data(request.send());
    }

    /**
     * 根据blockNumber获取区块信息
     *
     * @param blockNumber 根据区块编号
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getBlockEthBlock")
    public Result<?> getBlockEthBlock(@RequestParam Integer blockNumber) throws Exception {
        DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(blockNumber);
        Request<?, EthBlock> request = admin.ethGetBlockByNumber(defaultBlockParameter, true);
        EthBlock ethBlock = request.send();
        return Result.data(ethBlock);
    }

    /**
     * 指定地址发送交易所需nonce获取
     *
     * @param address 待发送交易地址
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getNonce")
    public Result<?> getNonce(@RequestParam String address) throws Exception {
        Request<?, EthGetTransactionCount> request = admin.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST);
        return Result.data(request.send().getTransactionCount());
    }

    /**
     * 获取节点版本
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getVersion")
    public Result<?> getVersion() throws Exception {
        Web3ClientVersion version = admin.web3ClientVersion().send();
        return Result.data(version.getWeb3ClientVersion());
    }

}
