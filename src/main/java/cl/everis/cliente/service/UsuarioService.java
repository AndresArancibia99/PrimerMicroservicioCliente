package cl.everis.cliente.service;



import cl.everis.cliente.dto.UsuarioRequestDTO;
import cl.everis.cliente.dto.UsuarioResponseDTO;
import cl.everis.cliente.model.Usuario;

import java.util.List;


public interface UsuarioService {
    public List<UsuarioResponseDTO> findAll();
    public UsuarioResponseDTO findById(String id);
    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO);
    public UsuarioResponseDTO update(String id, UsuarioRequestDTO usuarioRequestDTO);
    //public UsuarioResponseDTO findByEstado(boolean estado);




}
