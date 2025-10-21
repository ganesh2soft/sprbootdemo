package com.hcl.sprbootdemo.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public class JWTValidateFilter extends OncePerRequestFilter {	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, java.io.IOException {
	
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		logger.info("Received jwt inside JWTValidateFilter        "+jwt);
		if (null != jwt) {

			try {
			
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				/*
				 * String 'jwt' contains Bearer <jwttoken>
				 * So, using Substring operation , First 7 characters have to be removed. Remaining
				 * String will be named as 'jwtsub' and it will be parsed to ParseClaimsJws function 		
				 */
				String jwtsub=jwt.substring(7);
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtsub).getBody();
				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");
				logger.info("______________________AT JwtValidate _ Inside if ________usernmae is __"+username);
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
						AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				logger.info("_________________Auth Object______________"+auth);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} 
			catch (BadCredentialsException e) {
				throw new BadCredentialsException("Error at Token");
			}	
			
			catch (Exception e) {
				e.printStackTrace();
				logger.info("**********Excecption details shown**************"+e);
			}
		}
		else {
			logger.info("________________ELSE Block entered________Means NO TOKEN supplied");
		}
		
		logger.info("Confirming JWT valid filter leaving");
		filterChain.doFilter(request, response);

	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		logger.info("Shouldnotfilter inside validatefiler");
		return request.getServletPath().equals("/api/users/login");
	}
}

	