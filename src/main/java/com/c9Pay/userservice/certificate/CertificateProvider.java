package com.c9Pay.userservice.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateProvider {
    private KeyPair keyPair;
    private final KeyAlgorithm keyAlgorithm;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void createKeyPair(){
        keyPair = keyAlgorithm.createKeyPair();
    }

    public Optional<Certificate> getCertificate(Object obj){
        try{
            byte[] json = objectMapper.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8);
            if(json.length > keyAlgorithm.getMaxEncodingSize()) return Optional.empty();

            Cipher cipher = keyAlgorithm.getCipher();
            Signature signature = keyAlgorithm.getSignature();

            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
            signature.initSign(keyPair.getPrivate());

            //ciphertext
            byte[] encoded = cipher.doFinal(json);

            String certificate = new String(Base64.getEncoder().encode(encoded),
                    StandardCharsets.UTF_8);
            //signatiure
            signature.update(encoded);
            String sign = new String(Base64.getEncoder().encode(signature.sign()));

            return Optional.of(new Certificate(certificate, sign));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
