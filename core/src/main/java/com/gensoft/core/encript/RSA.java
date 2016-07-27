package com.gensoft.core.encript;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.HashMap;
//import java.util.Map;

import javax.crypto.Cipher;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @version 1.0
 * @date 2012-4-26
 */
public class RSA {


    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    //private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    //private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

//    /**
//     * <p>
//     * 生成密钥对(公钥和私钥)
//     * </p>
//     *
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> genKeyPair() throws Exception {
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGen.initialize(1024);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        Map<String, Object> keyMap = new HashMap<String, Object>(2);
//        keyMap.put(PUBLIC_KEY, publicKey);
//        keyMap.put(PRIVATE_KEY, privateKey);
//        return keyMap;
//    }
//
//    /**
//     * <p>
//     * 用私钥对信息生成数字签名
//     * </p>
//     *
//     * @param data       已加密数据
//     * @param privateKey 私钥(BASE64编码)
//     * @return
//     * @throws Exception
//     */
//    public static String sign(byte[] data, String privateKey) throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature.initSign(privateK);
//        signature.update(data);
//        byte[] cipher = Base64.getEncoder().encode(signature.sign());
//        return new String(cipher, "utf-8");
//    }
//
//    /**
//     * <p>
//     * 校验数字签名
//     * </p>
//     *
//     * @param data      已加密数据
//     * @param publicKey 公钥(BASE64编码)
//     * @param sign      数字签名
//     * @return
//     * @throws Exception
//     */
//    public static boolean verify(byte[] data, String publicKey, String sign)
//            throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        PublicKey publicK = keyFactory.generatePublic(keySpec);
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature.initVerify(publicK);
//        signature.update(data);
//        return signature.verify(Base64.getDecoder().decode(sign));
//    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param hex 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String hex, String privateKey)
            throws Exception {
        byte[] encryptedData = hexToBytes(hex);
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "utf-8");
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param text 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String text, String publicKey)
            throws Exception {
        byte[] encryptedData = hexToBytes(text);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "utf-8");
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param text      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String text, String publicKey)
            throws Exception {
        byte[] data = text.getBytes("utf-8");
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return bytesToHex(encryptedData);
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param text       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String text, String privateKey)
            throws Exception {
        byte[] data = text.getBytes("utf-8");
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return bytesToHex(encryptedData);
    }

//    /**
//     * <p>
//     * 获取私钥
//     * </p>
//     *
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
//    public static String getPrivateKey(Map<String, Object> keyMap)
//            throws Exception {
//        Key key = (Key) keyMap.get(PRIVATE_KEY);
//        byte[] cipher = Base64.getEncoder().encode(key.getEncoded());
//        return new String(cipher, "utf-8");
//    }
//
//    /**
//     * <p>
//     * 获取公钥
//     * </p>
//     *
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
//    public static String getPublicKey(Map<String, Object> keyMap)
//            throws Exception {
//        Key key = (Key) keyMap.get(PUBLIC_KEY);
//        byte[] cipher = Base64.getEncoder().encode(key.getEncoded());
//        return new String(cipher, "utf-8");
//    }

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();


    private static String bytesToHex(byte[] bytes) {
        int len = bytes.length;
        char[] hexChars = new char[len * 2];
        for ( int j = 0; j < len; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] hexToBytes(String hexString){
        char[] hexChars = hexString.toCharArray();
        int len = hexChars.length/2;
        byte[] bytes = new byte[len];
        for(int i =0;i<len;i++) {
            String str = new String(hexChars, i * 2, 2);
            int num = Integer.parseInt(str, 16);
            bytes[i] = (byte) (num & 0xFF);
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        //Map<String, Object> keyMap = RSA.genKeyPair();
        String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY2xu7xM6uuDhQFF29/KPLP5KHpSGNl1kuPpDMjGC7riuJUsfn2dhxjoytgPdh2mqaeOE1gjTHPUNUQgSuPEz3MDRjuO/kUbtmmTiq3UomiFH7Y9fLTXLfYN4+NhOuM1VEUq1E45cxsooFOLl3FeLd6WrXuj4elsvC8iIb2VHh2wIDAQAB";//getPublicKey(keyMap);
        String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJjbG7vEzq64OFAUXb38o8s/koelIY2XWS4+kMyMYLuuK4lSx+fZ2HGOjK2A92Haapp44TWCNMc9Q1RCBK48TPcwNGO47+RRu2aZOKrdSiaIUftj18tNct9g3j42E64zVURSrUTjlzGyigU4uXcV4t3pate6Ph6Wy8LyIhvZUeHbAgMBAAECgYA4Xbv5Xta0eTxS01/KGmqwHW5EVPFtjB0Xv3yX4UAelVh6mCsdZSi6n01jAca3r9pVwjOSDsS1n/K/FGUzdwsOHisBz8mYDtvG82FwZyv1Iye4uVhHNU0yXkRU5cbwv5N3Jsuji+6NyrGXYkpZhdZHYPH8LY8l9g7f7wVrJFUYiQJBAMpvPaLMdnK3kXDZBif71uOi7v82F71CS+/7t1u4YS/+HFwhYK/WPJD+gBZboQaABa+hO9DpwJw201XCkUH30j8CQQDBTXLVqlUGn7BYc27ukU7r+dNL3YOU44QDQD2s97CPaKtNaTIjm+gDqzae2OLNK/BlgDWJC/VvS/YQI9EJelFlAkBcsY0+k3MVWlr9mlKIUnf2ajAXqnQ6VDYjYFZ4aDgQWsIvqZT1TxsSpYaPNkvIhyyTXDfglHh+z1f76AjNdSXPAkABW1XPAh8OqNhIBqdkrUhsh5hgE/H0fZRToYPNTv/CQe3R4Uck0NdUD0OW4dbVsdVCISA/EgJrAZF8k4PIGT+xAkEAtqh5DpvTI3Yxj2gYYy3C6ce5OBZ2hn2yPOA4WyjNDGejeuUPXZfqU8Eshqj6shRjRubPIJk2P/lQiBd7nzmqcA==";//getPrivateKey(keyMap);
//        System.out.println(" publickey: " + publickey);
//        System.out.println(" privatekey: " + privatekey);
        String text = "username+passwordlonglonglonglonglong+deviceid15length";
        System.out.println(" text: " + text);
        String cipher = RSA.encryptByPublicKey(text, publickey);
        System.out.println(" cipher: " + cipher);
        String backText = RSA.decryptByPrivateKey(cipher, privatekey);
        System.out.println(" backText: " + backText);
    }
}
