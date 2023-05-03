package com.dopamines.backend.account.security;

import com.dopamines.backend.account.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.dopamines.backend.account.security.JwtConstants.JWT_SECRET;
import static com.dopamines.backend.account.security.JwtConstants.TOKEN_HEADER_PREFIX;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String authrizationHeader = request.getHeader(AUTHORIZATION);

        // 로그인, 리프레시 요청이라면 토큰 검사하지 않음
        if (servletPath.equals("/account/login")
                || servletPath.equals("/account/refresh")
                || servletPath.equals("/oauth")
                || servletPath.equals("/account/signup")
                || servletPath.equals("/account/oauth")
        ) {
            filterChain.doFilter(request, response);
            System.out.println("====================================");
            System.out.println(request.getParameter("code"));
            System.out.println(request.getParameter("email"));
            System.out.println(request.getParameter("kakao_id"));
        } else if (authrizationHeader == null || !authrizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            // 토큰값이 없거나 정상적이지 않다면 400 오류
            log.info("CustomAuthorizationFilter : JWT Token이 존재하지 않습니다.");
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(400, "JWT Token이 존재하지 않습니다.");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        } else {
            try {
                // Access Token만 꺼내옴
                String accessToken = authrizationHeader.substring(TOKEN_HEADER_PREFIX.length());

                // === Access Token 검증 === //
                JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(JWT_SECRET.getBytes()).build();
                Jws<Claims> claimsJws = jwtParser.parseClaimsJws(accessToken);

                // === Access Token 내 Claim에서 Authorities 꺼내 Authentication 객체 생성 & SecurityContext에 저장 === //
                List<String> strAuthorities = claimsJws.getBody().get("roles", List.class);
                List<SimpleGrantedAuthority> authorities = strAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                String username = claimsJws.getBody().getSubject();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);
            } catch (ExpiredJwtException e) {
                log.info("CustomAuthorizationFilter : Access Token이 만료되었습니다.");
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(401, "Access Token이 만료되었습니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            } catch (Exception e) {
                log.info("CustomAuthorizationFilter : JWT 토큰이 잘못되었습니다. message : {}", e.getMessage());
                response.setStatus(SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            }
        }
    }
}
