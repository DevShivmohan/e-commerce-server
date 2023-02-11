package com.ecommerce.user.util;
import com.ecommerce.user.constants.ApiConstants;
import com.ecommerce.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "E-commerce$%shivmohan@!^FtVgf";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ApiConstants.TOKEN_TYPE, ApiConstants.ACCESS_TOKEN);
        claims.put(ApiConstants.NAME,user.getName());
        claims.put(ApiConstants.USER_NAME,user.getName());
        return createAccessToken(claims, user.getUsername());
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ApiConstants.TOKEN_TYPE, ApiConstants.REFRESH_TOKEN);
        claims.put(ApiConstants.NAME,user.getName());
        claims.put(ApiConstants.USER_NAME,user.getName());
        return createRefreshToken(claims, user.getUsername());
    }

    private String createAccessToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24*1))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24*7))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isValidAccessToken(String token) {
        if(token==null || isTokenExpired(token))
            return false;
        var claims= extractAllClaims(token);
        return claims.get(ApiConstants.TOKEN_TYPE).equals(ApiConstants.ACCESS_TOKEN);
    }

    public boolean isValidRefreshToken(String token) {
        if(token==null || isTokenExpired(token))
            return false;
        var claims= extractAllClaims(token);
        return claims.get(ApiConstants.TOKEN_TYPE).equals(ApiConstants.REFRESH_TOKEN);
    }
}
