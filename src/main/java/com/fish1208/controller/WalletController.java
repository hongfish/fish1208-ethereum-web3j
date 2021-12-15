package com.fish1208.controller;

import com.fish1208.common.response.Result;
import com.fish1208.entity.dto.WalletDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private Admin admin;

    private static String KEY_STORE_PATH = "wallet/keystore";

    static {
        File file = new File(KEY_STORE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 创建钱包
     *
     * @param dto
     * @return 地址hash
     * @throws Exception
     */
    @PostMapping(value = "/create")
    public Result<?> create(@RequestBody WalletDTO dto) throws Exception {
        log.info("====>  Generate new wallet file for ETH.");

        String fileName = WalletUtils.generateNewWalletFile(dto.getPassword(), new File(KEY_STORE_PATH), true);
        Credentials credentials = WalletUtils.loadCredentials(dto.getPassword(), KEY_STORE_PATH + "/" + fileName);
        String address = credentials.getAddress();
//        accountService.saveOne(account, fileName, address);
        return Result.data(address);
    }

    /**
     * 加载钱包
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/load")
    public Result<?> load(@RequestBody WalletDTO dto) throws Exception {
        String walletFilePath = KEY_STORE_PATH + "/" + dto.getWalletFileName();
        Credentials credentials = WalletUtils.loadCredentials(dto.getPassword(), walletFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
        Map<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("publicKey", publicKey);
        result.put("privateKey", privateKey);
        return Result.data(result);
    }

    /**
     * 钱包转账
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/sendFunds")
    public Result<?> sendFunds(@RequestBody WalletDTO dto) throws Exception {
        String walletFilePath = KEY_STORE_PATH + "/" + dto.getWalletFileName();
        Credentials credentials = WalletUtils.loadCredentials(dto.getPassword(), walletFilePath);
        TransactionReceipt send = Transfer.sendFunds(admin, credentials, dto.getAddressTo(), dto.getFunds(), Convert.Unit.FINNEY).send();
        return Result.data(send);
    }

}