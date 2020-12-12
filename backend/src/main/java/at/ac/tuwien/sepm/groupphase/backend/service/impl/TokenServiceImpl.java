package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;

    public TokenServiceImpl(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public String getEmailFromHeader(String authHeader) {
        byte[] signingKey = securityProperties.getJwtSecret().getBytes();
        Jws<Claims> jws = Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(authHeader.split(" ")[1]);
        return jws.getBody().getSubject();
    }
}
