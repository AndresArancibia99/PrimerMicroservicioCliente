package cl.everis.cliente.repository;

import cl.everis.cliente.dto.UsuarioResponseDTO;
import cl.everis.cliente.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, String> {
    //Extend JPA

    List<Usuario> findByEmail(String email);
    List<Usuario> findByRut(String rut);
    //request
    List<Usuario> findByEmailOrRut(String email, String rut);
}
