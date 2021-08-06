package cl.everis.cliente.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

//DATA TRANSFER OBJECT

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO implements Serializable {

    private String uuid;
    private String rut;
    private String nombre;
    private String email;
    private LocalDateTime fechaCreacion;
    private boolean estado;
    private LocalDateTime fechaActualizacion;

}
