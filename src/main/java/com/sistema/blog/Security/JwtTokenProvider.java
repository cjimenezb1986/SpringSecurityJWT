package com.sistema.blog.Security;

import com.sistema.blog.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date fechaActual= new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();

        return token;
    }

    public String obtenerUsernameDelJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Firma no valida");

        }catch (MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
        }catch (UnsupportedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
        }catch (IllegalArgumentException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"La cadena de Claims JWT esta vacia");
        }
    }

}
