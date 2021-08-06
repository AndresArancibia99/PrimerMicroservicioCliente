package cl.everis.cliente.dto;


import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequestDTO implements Serializable {
//definiciones de javax.validation.constraints
    @NotBlank
    private String rut;
    @NotBlank
    private String nombre;
    @Email(message = "El email ingresado no es valido")
    @NotBlank
    private String email;
    @NotNull

    //@Pattern(regexp = "^(?=.*\\d).{6,15}$", message = "contraseña invalida")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$", message = "debe contener almenos una mayuscula, letras minusculas, dos numeros y 5 caracteres")
    private String pwd;

    private boolean estado;

    private String token;
}
