package com.c9Pay.userservice.encryption;

import com.c9Pay.userservice.data.dto.auth.CertificateForm;
import com.c9Pay.userservice.data.dto.auth.CertificateResponse;
import com.c9Pay.userservice.data.dto.auth.ServiceInfo;
import com.c9Pay.userservice.web.client.AuthClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Base64;

import static com.c9Pay.userservice.encryption.RsaAlgorithm.generateKey;
import static com.c9Pay.userservice.encryption.RsaAlgorithm.getEncryptedData;

@Slf4j
@Component
@RequiredArgsConstructor
public class RsaUtils {


    @Value("${rsa.public-key}")
    private String publicKey;

    @Value("${rsa.private-key}")
    private String privateKey;

    private final AuthClient authClient;

    private CertificateResponse certificate;

    private KeyPair keyPair;
    @PostConstruct
    public void createKeyPair(){
        try{
            keyPair = generateKey(publicKey, privateKey);
            String endpoint = "user-service/internal/auth";
            log.debug("end-point: {}",endpoint);
            CertificateForm certificateForm = new CertificateForm(publicKey,
                    new ServiceInfo("user-service",endpoint));
            certificate =  authClient.getCertificate(certificateForm).getBody();
            log.info("public key: {}", keyPair.getPublic());
            log.info("private key: {}", keyPair.getPrivate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String encryption(String serialNumber){
        try {
            return Arrays.toString(getEncryptedData(serialNumber, keyPair.getPrivate()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CertificateResponse getCertificate() {
        return this.certificate;
    }

}
