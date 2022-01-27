package com.fish1208.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


@Slf4j
public class SolidityGeneratorTest extends BaseTest{

    @Test
    public void compileSolFilesToJava() throws IOException {
        /**
         * 使用在线remix生成bin、abi文件
         * https://remix.ethereum.org/
         */
        String binFile;
        String abiFile;
        String tempDirPath = new File("src/test/java/").getAbsolutePath();
        String packageName = "com.fish1208.temp";
        String contract = "HelloWorld";
        abiFile = "src/test/resources/solidity/" + contract + ".abi";
        binFile = "src/test/resources/solidity/" + contract + ".bin";
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-a", abiFile,
                "-b", binFile,
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0]));
    }

}
