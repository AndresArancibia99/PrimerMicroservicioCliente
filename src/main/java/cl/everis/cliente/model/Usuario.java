package cl.everis.cliente.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {



    @Id
    @Column(name= "UUID")
    private String uuid;

    @Column(name="token")
    private String token;

    @Column(name= "rut")
    private String rut;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "pwd", length = 40)
    private String pwd;

    @Column(name = "email", length=50)
    @Email(message = "El email ingresado no es valido")
    private String email;

    @Column(name= "estado", length = 50)
    private boolean estado;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;


    String regex = "z.*t?";


}

