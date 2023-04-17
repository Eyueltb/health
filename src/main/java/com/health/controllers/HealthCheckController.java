package com.health.controllers;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.OK;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/healthcheck")
    public ResponseEntity<HealthCheckResponse> healthCheck(@RequestParam(required = true) String format) {
        HealthCheckResponse response = new HealthCheckResponse();
        switch (format) {
            case "short":
                response.setStatus("OK");
                break;
            case "full":
                response.setStatus("OK");
                response.setCurrentTime(now(UTC));
                break;
            default:
                return ResponseEntity.status(BAD_REQUEST).body(null);
        }
        //Responses should have Content-Type header with appropriate value (application/json).
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response, headers, OK);
    }

    @PutMapping(value = "/healthcheck")
    public void healthCheckPut() {
        throw new MethodNotAllowedException(UseExceptionType.METHOD_NOT_ALLOWED.getErrorMessage());
    }

    @PostMapping(value = "/healthcheck")
    public void healthCheckPost() {
        throw new MethodNotAllowedException(UseExceptionType.METHOD_NOT_ALLOWED.getErrorMessage());
    }

    @DeleteMapping(value = "/healthcheck")
    public void healthCheckDelete() { throw new MethodNotAllowedException(UseExceptionType.METHOD_NOT_ALLOWED.getErrorMessage()); }

    public enum UseExceptionType {
        METHOD_NOT_ALLOWED("Method is not allowed");
        private final String errorMessage;

        UseExceptionType(String errorMessage) { this.errorMessage = errorMessage;  }
        public String getErrorMessage() {  return errorMessage;   }
    }

    //@ResponseStatus(METHOD_NOT_ALLOWED)
    static class MethodNotAllowedException extends RuntimeException {

        public MethodNotAllowedException(String message) {
            super(message);
            System.out.println("MethodNotAllowedException: " + message);
        }
    }

    @Data
    static class HealthCheckResponse {
        private String status;
        private ZonedDateTime currentTime;

        @JsonInclude(NON_NULL) //it will remove the row currentTime only if value is null
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        public ZonedDateTime getCurrentTime() {
            return currentTime;
        }
    }

}

