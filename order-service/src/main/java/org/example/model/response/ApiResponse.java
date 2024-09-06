package org.example.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.awt.datatransfer.Clipboard;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse <T>{
    private String message;
    private T payload;
    private HttpStatus status;
    private LocalDateTime localdatetime;
}
