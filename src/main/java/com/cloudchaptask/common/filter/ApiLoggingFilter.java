package com.cloudchaptask.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 클라이언트가 요청한 HTTP Method 가져오기
        String method = request.getMethod();
        // 2. 클라이언트가 요청한 URL 가져오기
        String uri = request.getRequestURI();

        // 3. 요청받은 API 정보를 INFO 레벨로 로그 출력
        log.info("[API - LOG] {} {}", method, uri);

        // 4. 다음 필터 또는 요청 처리 단계로 요청 전달
        filterChain.doFilter(request, response);
    }
}
