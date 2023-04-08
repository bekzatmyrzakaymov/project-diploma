package com.diploma.project.model.oauth.jwt;

import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.oauth.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("MegaLargeSigningSecretKeyForDemoApplicationMegaLargeSigningSecretKeyForDemoApplication")
    private String jwtSecret;

    @Value("86400000")
    private int jwtExpirationMs;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired(required = false)
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }


    public String createToken(String username, Role roles) {
        User user = userRepository.findByEmailAndStatus(username, EUserStatus.ACTIVE).orElseThrow(() -> new UsernameNotFoundException("Username is not found"));

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles.getName());
        claims.put("id", user.getId());

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException {
//        try {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }

        return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("Token is expired");
////            throw new JwtAuthenticationException("JWT token is expired or invalid");

    }
}
