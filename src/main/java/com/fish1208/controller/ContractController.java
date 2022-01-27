package com.fish1208.controller;

import com.fish1208.common.response.Result;
import com.fish1208.contract.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private HelloWorld queryHelloWorld;

    @Autowired
    private HelloWorld invokeHelloWorld;

    @RequestMapping(value = "/helloWorld/get", method = RequestMethod.GET)
    public Result<?> get() throws Exception {
        if (queryHelloWorld != null) {
            log.info("HelloWorld address is: {}", queryHelloWorld.getContractAddress());
            String result = queryHelloWorld.get().send();
            return Result.data(result);
        }
        return Result.fail("HelloWorld合约的queryHelloWorld对象获取为空");
    }

    @RequestMapping(value = "/helloWorld/set", method = RequestMethod.POST)
    public Result<?> set(@RequestBody Map<String,Object> param) throws Exception {
        if (invokeHelloWorld != null) {
            log.info("HelloWorld address is: {}", invokeHelloWorld.getContractAddress());
            String n = (String)param.get("n");
            try {
                invokeHelloWorld.set(n).sendAsync();
                return Result.success("执行HelloWorld合约的set(string)方法成功");
            } catch (Exception e) {
                log.error("HelloWorld set() e: {}", e);
                return Result.fail("执行HelloWorld合约的set(string)方法失败");
            }
        }
        return Result.fail("HelloWorld合约的invokeHelloWorld对象获取为空");
    }
}
