package edu.fbansept.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    public String generateJwt(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256,"azerty")
                .compact();

    }

    public Claims getData(String jwt) {

        return Jwts.parser()
                .setSigningKey("azerty")
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValide (String jwt) {

        try {
            Claims donnees = getData(jwt);
        } catch (SignatureException e) {

            return false;
        }

        return true;
    }

}
