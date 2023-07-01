package com.c9Pay.userservice.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Optional;

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

            byte[] encoded = cipher.doFinal(json);

            String certificate = new String(Base64.getEncoder().encode(encoded),
                    StandardCharsets.UTF_8);

            signature.update(encoded);
            String sign = new String(Base64.getEncoder().encode(signature.sign()));

            return Optional.of(new Certificate(certificate, sign));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Optional<T> decrypt(Certificate encoded, Class<T> type){
        Cipher cipher = keyAlgorithm.getCipher();
        Signature signature = keyAlgorithm.getSignature();

        try{
            byte[] messageEncoded = Base64.getDecoder().decode(encoded.getCertificate().getBytes(StandardCharsets.UTF_8));

            signature.initVerify(keyPair.getPublic());
            signature.update(messageEncoded);
            byte[] sign = Base64.getDecoder().decode(encoded.getSign());

            if(signature.verify(sign)){
                cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());

                String json = new String(cipher.doFinal(messageEncoded), StandardCharsets.UTF_8);
                T obj = objectMapper.readValue(json, type);
                return Optional.of(obj);
            }
            else{
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey getPublicKey(){ return keyPair.getPublic();}
}
