package com.dianxin.imessage.common.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.StringReader;
import java.security.*;

/**
 * ==============================================================================
 * Copyright (c) 2015 by www.dianxin.com, All rights reserved.
 * ==============================================================================
 * This software is the confidential and proprietary information of
 * tencent.com, Inc. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with dianxin.com, Inc.
 * ------------------------------------------------------------------------------
 * <p/>
 * Author: faberxu
 * Date: 2015/12/18
 * Description:
 * Nothing.
 * Function List:
 * 1. Nothing.
 * History:
 * 1. Nothing.
 * ==============================================================================
 */
public class SignUtil {

    private static Logger log = LoggerFactory.getLogger(SignUtil.class);
    private static PrivateKey privateKey = null;

    public static PrivateKey getPrivateKey(String keypath) {
        if (privateKey != null)
            return privateKey;
        log.debug("初始化签名");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            FileReader fileReader = new FileReader(keypath);
            char[] cbuf = new char[40960];
            fileReader.read(cbuf, 0, 40960);
            PEMReader reader = new PEMReader(new StringReader(new String(cbuf)));
            KeyPair keyPair = (KeyPair) reader.readObject();
            privateKey = keyPair.getPrivate();
            reader.close();
        } catch (Exception e) {
            log.warn("加载签名失败", e);
        }
        return privateKey;
    }

    public static String sign(String src, String keypath) {
        log.debug(src);
        try {
            String signiture = sign(src, getPrivateKey(keypath));
            log.debug(signiture);
            return signiture;
        } catch (Exception e) {
            log.warn("生成签名失败", e);
        }
        return null;
    }

    public static String sign(String src, PrivateKey privateKey) throws Exception {
        Signature rsa = Signature.getInstance("SHA1WithRSA");
        rsa.initSign(privateKey);
        rsa.update(src.getBytes());
        byte[] sign = rsa.sign();
        return new String(Base64.encodeBase64(sign));
    }

    public static boolean verifySign(String src, String sign, PublicKey publicKey) {
        try {
            Signature rsa = Signature.getInstance("SHA1WithRSA");

            rsa.initVerify(publicKey);
            rsa.update(src.getBytes());
            return rsa.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
