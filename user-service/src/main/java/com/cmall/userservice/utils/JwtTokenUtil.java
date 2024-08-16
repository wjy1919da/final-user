package com.cmall.userservice.utils;

import com.cmall.userservice.exception.AccountAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public JwtTokenUtil() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 生成符合要求的密钥
        this.jwtSecret = Base64.getEncoder().encodeToString(key.getEncoded()); // 将密钥编码为Base64字符串
    }

    /**
     * generate token
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Expected CustomUserDetails instance.");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // getUsername() 返回的是用户的电子邮件

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(email)  // 使用邮箱作为JWT的subject
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    /**
     * get username from the token
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * validate JWT token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
