package com.hcl.sprbootdemo.security;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, java.io.IOException {
		
		logger.info("!!!!!!!!!!!!!!!!!!!!!!!JWT Token Generation Stage entered!!!!!!!!!!!!!!!!!");
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (null != authentication) {
	            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
	            String jwt = Jwts.builder().setIssuer("ExampleXYZ").setSubject("JWT Token")
	                    .claim("username", authentication.getName())
	                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
	                    .setIssuedAt(new Date())
	                    .setExpiration(new Date((new Date()).getTime() + 300000000))
	                    .signWith(key).compact();
	            logger.info("!!!!!!!! Generated Token is below ************\n"+jwt);
	            response.setHeader(SecurityConstants.JWT_HEADER, jwt);	           
	        }
	        filterChain.doFilter(request, response);		
	}

        @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/api/users/login");
	    }
        /*
         * Filter named here will run once /login is hit
         */

	    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
	        Set<String> authoritiesSet = new HashSet<>();
	        for (GrantedAuthority authority : collection) {
	            authoritiesSet.add(authority.getAuthority());
	        }
	        return String.join(",", authoritiesSet);
	    }
}