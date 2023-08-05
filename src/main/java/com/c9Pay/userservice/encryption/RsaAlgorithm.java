package com.c9Pay.userservice.encryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.c9Pay.userservice.constant.RsaConstant.ALGORITHM;
import static com.c9Pay.userservice.constant.RsaConstant.HASH;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class RsaAlgorithm {

    public static KeyPair generateKey(String publicKey, String privateKey) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey pri = factory.generatePrivate(privateKeySpec);
        PublicKey pub = factory.generatePublic(publicKeySpec);
        return new KeyPair(pub, pri);
    }

    public static byte[] getEncryptedData(String plaintext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(ENCRYPT_MODE, privateKey);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String getDecryptedData(byte[] encryptedData, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(DECRYPT_MODE,privateKey);
        return new String(cipher.doFinal(encryptedData));
    }

    public static byte[] getSignature(String data, PrivateKey privateKey)throws Exception{
        Signature signature = Signature.getInstance(HASH);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    public static boolean verify(String data, byte[] signature, PublicKey publicKey) throws  Exception{
        Signature verifySign = Signature.getInstance(HASH);
        verifySign.initVerify(publicKey);
        verifySign.update(data.getBytes());
        return verifySign.verify(signature);
    }

    public static String bytesToHex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}