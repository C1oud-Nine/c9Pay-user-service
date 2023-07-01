package com.c9Pay.userservice.certificate;


import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.Signature;

public interface KeyAlgorithm {
     KeyPair createKeyPair();
     Signature getSignature();
     Cipher getCipher();
     int getMaxEncodingSize();
}
