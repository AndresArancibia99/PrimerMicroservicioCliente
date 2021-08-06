package cl.everis.cliente.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties (prefix = "message")
@Configuration
public class MensajeErrorConfiguration {
    private String emailError;
    private String rutError;
    private String datoExistente;
    private String peticionInvalida;
    private String recurso;
}
