package com.girgin.ramazan.springsecurityoauth2publickey.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    String jwtVerificationKey;

    public String getJwtVerificationKey() {
        return jwtVerificationKey;
    }

    public void setJwtVerificationKey(String jwtVerificationKey) {
        this.jwtVerificationKey = jwtVerificationKey;
    }
}
