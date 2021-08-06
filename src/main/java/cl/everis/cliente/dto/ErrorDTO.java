package cl.everis.cliente.dto;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ErrorDTO implements Serializable {

    private final String statusCode;
    private final String message;

    public ErrorDTO(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
