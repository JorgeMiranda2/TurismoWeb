package com.turismo.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {


    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(),user);
    }

    private  String getToken(Map<String,Object> kvHashMap, UserDetails user) {
            return Jwts.builder()
                    .setClaims(kvHashMap)
                    .setSubject(user.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TOKEN))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();

    }

    private Key getKey() {
        byte[]  keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = getAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Claims getAllClaims(String token){
        System.out.println("claims: "+ Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody());
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }


    private Date getExpiration(String token){
        return  getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
