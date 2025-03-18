package com.github.platatomasz.imagecatalogue.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    
    // FIXME It's not secure to hold secret here
    private static final String SECRET_KEY = "7a072478dd53b5f6ae31e7d62a8a7cd30e5878c9f323f109712da6180bb33562";
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    // Check if token is valid and is asigned to this exact user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private boolean isTokenExpired(String token) {        
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 minutes
                .signWith(getSignInKey())
                .compact();
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Extracts claims from a JWT
    private Claims extractAllClaims(String token) {
        // This had to be changed because I'm using newer version of Jwts(0.12.6)
        return Jwts.parser()
                .verifyWith(getSignInKey()) // Secret that was used to sign this JWT
                .build()
                .parseSignedClaims(token)	
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	public JwtData getJwtFromAuthorizationHeader(String authorizationHeader) {
		final String BEARER_PREFIX = "Bearer ";
		String jwtToken = authorizationHeader.substring(BEARER_PREFIX.length());
		
		return new JwtData(extractUsername(jwtToken));
	}

}