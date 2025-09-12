package makara.co.min_pos.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseError<T>(
        Boolean status,
        Integer code,
        String message,
        LocalDateTime timeStamp,
        T errors
){
}
