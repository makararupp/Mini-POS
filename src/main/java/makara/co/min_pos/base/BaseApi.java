package makara.co.min_pos.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseApi<T>(
       Boolean status,
       Integer code,
       String message,
       LocalDateTime timeStamp,
       T data
) {
}
