package com.cloudchaptask.common.config;

import com.cloudchaptask.dto.response.ErrorResponse;
import com.cloudchaptask.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. DTO 입력값 검증 실패 시 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        // 1. ERROR 로그 및 스택트레이스 출력
        log.error("[ERROR - LOG] 입력값 검증 실패", e);

        // 2. DTO에 작성했던 예외 메세지 가져오기
        String message = e.getBindingResult() // 입력값 검증 결과 가져오기
                .getFieldErrors()// 검증에 실패한 필드 목록 가져오기
                .get(0) // 첫 번째 오류 가져오기
                .getDefaultMessage(); // DTO에 작성한 메세지 가져오기

        // 3. 에러 응답 객체 생성
        ErrorResponse errorResponse = new ErrorResponse(400, message);

        // 4. 클라이언트에게 에러 응답 반환
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException e) {

        // 1. ERROR 로그 및 스택트레이스 출력
        log.error("[ERROR - LOG] 서비스 처리 실패", e);

        // 2. 발생한 예외 객체에서 상태 코드와 메세지 가져오기
        HttpStatus status = e.getStatus();
        String message = e.getMessage();

        // 3. 에러 응답 객체 생성
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);

        // 4. 클라이언트에게 에러 응답 반환
        return new ResponseEntity<>(errorResponse, status);


    }

    // 3. JSON 데이터 변환 실패 시 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(
            HttpMessageNotReadableException e) {

        // 1. ERROR 로그 및 스택트레이스 출력
        log.error("[ERROR - LOG] 요청 데이터 변환 실패", e);

        // 2. 에러 응답 객체 생성
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "요청 데이터의 형식 또는 타입이 올바르지 않습니다."
        );

        // 3. 클라이언트에게 에러 응답 반환
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
