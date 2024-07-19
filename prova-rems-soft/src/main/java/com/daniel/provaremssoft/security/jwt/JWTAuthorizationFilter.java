package com.daniel.provaremssoft.security.jwt;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final String secret;
    private final Set<String> excludedUrls;

    public JWTAuthorizationFilter(String secret) {
        this.secret = secret;
        this.excludedUrls = new HashSet<>();
        excludedUrls.add("/api/login");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!excludedUrls.contains(request.getRequestURI())) {
            String jwtToken = request.getHeader(HEADER);

            if (jwtToken != null && jwtToken.startsWith(PREFIX)) {
                jwtToken = jwtToken.replace(PREFIX, "");
                try {
                    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();

                    if (claims.getSubject() != null) {
                        setUpSpringAuthentication(claims);
                    } else {
                        SecurityContextHolder.clearContext();
                    }

                } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
                    LOGGER.error("JWT Error: {}", e.getMessage());  // Logando o erro
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                    return;
                }
            } else {
                // Não há token JWT no request, então apenas continue a cadeia de filtros.
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private void setUpSpringAuthentication(Claims claims) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
