package com.dianxin.imessage.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author faberxu
 */
public class RSAKeyStore implements InitializingBean {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    private static Logger log = LoggerFactory.getLogger(RSAKeyStore.class);
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PrivateKey basPrivateKey;
    private String charset = "GBK";

    @Value("${private.key.path}")
    private String privateKeyPath;

    @Value("${public.key.path}")
    private String publicKeyPath;

    @Value("${base.public.key.path}")
    private String basePrivateKeyPath;


    private void init() {
       /* publicKey = genRSAPubKeyFromPem("F:/document/data/rsa_public_key.pem");
        privateKey = genRSAPriKeyFromPem("F:/document/data/rsa_private_key.pem");*/
        publicKey = genRSAPubKeyFromPem(publicKeyPath);
        privateKey = genRSAPriKeyFromPem(privateKeyPath);
        if (StringUtils.isNotBlank(basePrivateKeyPath)) {
            basPrivateKey = genRSAPriKeyFromPem(basePrivateKeyPath);
        }
        if (StringUtils.isNotBlank(basePrivateKeyPath)) {
        	basPrivateKey = genRSAPriKeyFromPem(basePrivateKeyPath);
        }
    }

    /**
     * 对字符串加密
     *
     * @param str
     * @return
     */
    public String encrypt(String str) {
        try {
            return encrypt(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            log.error("加密异常", e);
            return null;
        }
    }

    public String encrypt(byte[] obj) {
        return encrypt(publicKey, obj);
    }

    /**
     * 解密
     *
     * @param base64String
     * @return
     */
    public String decrypt(String base64String) {
        return decrypt(base64String, charset);
    }

    public String decrypt(String base64String, String charset) {
        return decrypt(privateKey, Base64.decodeBase64(base64String), charset);
    }

    /**
     * Encrypt String.
     *
     * @return byte[]
     */
    public String encrypt(PublicKey publicKey, byte[] obj) {
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
                // ENCRYPT_MODE :
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return Base64.encodeBase64String(cipher.doFinal(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * Basic decrypt method
     *
     * @return byte[]
     */
    public String decrypt(PrivateKey privateKey, byte[] obj,
                          String charset) {
        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
                // DECRYPT_MODE :
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return new String(cipher.doFinal(obj), charset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public PublicKey genRSAPublicKey(String base64PubKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(base64PubKey.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception e) {
            log.warn("获取公钥失败", e);
            return null;
        }
    }

    /**
     * 通过base64私钥串生成私钥对象
     *
     * @param base64PriKey
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public PrivateKey genRSAPrivateKey(String base64PriKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(base64PriKey.getBytes());
            PKCS8EncodedKeySpec privatePKCS8 = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(privatePKCS8);
            return privateKey;
        } catch (Exception e) {
            log.warn("生成私钥对象失败", e);
            return null;
        }
    }

    public PrivateKey genRSAPriKeyFromPem(String pemFile) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        PEMReader reader = null;
        try {
            fis = new FileInputStream(pemFile);
            bis = new BufferedInputStream(fis);
            byte[] keyBytes = new byte[bis.available()];
            bis.read(keyBytes);
            bis.close();
            Security.addProvider(new BouncyCastleProvider());
            ByteArrayInputStream bais = new ByteArrayInputStream(keyBytes);
            reader = new PEMReader(new InputStreamReader(bais),
                    new PasswordFinder() {
                        @Override
                        public char[] getPassword() {
                            return "".toCharArray();
                        }
                    });
            KeyPair keyPair = (KeyPair) reader.readObject();
            reader.close();
            PrivateKey prik = keyPair.getPrivate();
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            KeySpec keySpec2 = new PKCS8EncodedKeySpec(prik.getEncoded());
            return keyFactory.generatePrivate(keySpec2);
        } catch (Exception e) {
            log.warn("生成私钥异常", e);
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                }
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
        }
        return null;
    }

    public PublicKey genRSAPubKeyFromPem(String pemFile) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        PEMReader reader = null;
        try {
            fis = new FileInputStream(pemFile);
            bis = new BufferedInputStream(fis);
            byte[] keyBytes = new byte[bis.available()];
            bis.read(keyBytes);
            bis.close();
            Security.addProvider(new BouncyCastleProvider());
            ByteArrayInputStream bais = new ByteArrayInputStream(keyBytes);
            reader = new PEMReader(new InputStreamReader(bais),
                    new PasswordFinder() {
                        @Override
                        public char[] getPassword() {
                            return "".toCharArray();
                        }
                    });
            PublicKey pubk = (PublicKey) reader.readObject();
            reader.close();

            KeySpec keySpec = new X509EncodedKeySpec(pubk.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.warn("生成公钥异常", e);
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                }
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
        }
        return null;
    }

    public String sign(String data) throws UnsupportedEncodingException {
        return sign(data.getBytes(charset), privateKey);
    }

    public String signBAS(String data) {
        try {
            return sign(data.getBytes(charset), basPrivateKey);
        } catch (UnsupportedEncodingException e) {
            //
            log.warn("签名bas异常", e);
        }
        return null;
    }

    public String sign(byte[] data) {
        return sign(data, privateKey);
    }

    public static String sign(byte[] data, PrivateKey priKey) {
        //
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            log.error("签名异常", e);
            return null;
        }
    }

    public boolean verify(String data, String sign) throws Exception {
        return verify(data.getBytes(charset), publicKey, sign);
    }

    public boolean verify(byte[] data, String sign) throws Exception {
        return verify(data, publicKey, sign);
    }

    public static boolean verify(byte[] data, PublicKey pubKey, String sign)
            throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    public static void main(String[] args) throws Exception {
        // genRSAPublicKeyByPem("C:\\Users\\jessiegao\\Downloads\\rsa_public_key.pem");
        // genRSAPrivateKeyByPem("C:\\Users\\jessiegao\\Downloads\\rsa_private_key.pem");
       /* RSAKeyStore rsa = new RSAKeyStore(
                "C:\\Users\\jessiegao\\Downloads\\rsa_public_key.pem",
                "C:\\Users\\jessiegao\\Downloads\\rsa_private_key.pem");
        String s = "Jessie|ߵߵߵ|212��";
        System.out.println(s);
        // System.out.println(rsa.privateKey);
        // String sign = rsa.sign(s.getBytes("GBK"), rsa.privateKey);
        String sign = rsa.sign(s);
        System.out.println(sign);
        // System.out.println(rsa.verify(s.getBytes("GBK"), rsa.publicKey,
        // sign));
        System.out.println(rsa.verify(s, sign));
        // byte[] bs = rsa.encrypt2Byte(rsa.publicKey, s.getBytes("GBK"));
        // System.out.println(new String(bs, "GBK"));
        String e = rsa.encrypt(s);
        System.out.println("-------------------");
        System.out.println(e);
        System.out.println("-------------------");
        String d = rsa.decrypt(e);
        System.out.println(d);*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}