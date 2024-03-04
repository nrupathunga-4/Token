package com.example.tokenstorage.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;

    public String extractUserName(String token)
    {
     return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims,T>claimsResolver)
    {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token)
    {
      return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String email)
    {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,email);
    }
    private String createToken(Map<String,Object> claims,String email)
    {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + cookieExpiry+1000L);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuedAt(expiryDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignInKey() {
        byte [] key = Decoders.BASE64.decode("357638792F423F4428472B4B6250655368566D597133743677397A2443264629");
        return Keys.hmacShaKeyFor(key);
    }
}
