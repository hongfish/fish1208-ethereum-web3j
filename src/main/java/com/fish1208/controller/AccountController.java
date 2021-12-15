package com.fish1208.controller;

import com.fish1208.common.response.Result;
import com.fish1208.entity.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private Admin admin;

    /**
     * 创建外部地址
     *
     * @param dto 密码（建议同一个平台的地址使用一个相同的，且复杂度较高的密码）
     * @return 地址hash
     * @throws Exception
     */
    @PostMapping(value = "/create")
    public Result<?> create(@RequestBody AccountDTO dto) throws Exception {
        Request<?, NewAccountIdentifier> request = admin.personalNewAccount(dto.getPassword());
        NewAccountIdentifier result = request.send();
        return Result.data(result.getAccountId());
    }

    /**
     * 查询账户余额
     * @param address
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/balanceOf")
    public Result<?> getBalanceOf(@RequestParam String address) throws Exception {
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = admin.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        //格式转化 wei-ether
        String balanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
        return Result.data(balanceETH);
    }

    /**
     * 解锁账户，发送交易前需要对账户进行解锁
     *
     * @param address  地址
     * @param password 密码
     * @param duration 解锁有效时间，单位秒
     * @return
     * @throws Exception
     */
    private Boolean unlock(String address, String password, Integer duration) throws Exception {
        Request<?, PersonalUnlockAccount> request = admin.personalUnlockAccount(address, password, new BigInteger(String.valueOf(duration)));
        PersonalUnlockAccount account = request.send();
        return account.accountUnlocked();
    }

}