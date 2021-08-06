package cl.everis.cliente.service;


import cl.everis.cliente.dto.UsuarioRequestDTO;
import cl.everis.cliente.dto.UsuarioResponseDTO;
import cl.everis.cliente.exception.ErrorException;
import cl.everis.cliente.model.Usuario;
import cl.everis.cliente.repository.UsuarioRepository;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;


import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UsuarioServiceImpl implements UsuarioService {


    private UsuarioRepository usuarioRepository;

    @Value("${message.emailerror}")
    private String mensajeEmailError;

    @Value(("${message.rutError}"))
    private String mensajeRutError;

    @Value(("${message.datoExistente}"))
    private String mensajeDatoExistente;

    @Value(("${message.peticionInvalida}"))
    private String mensajePeticionInvalida;

    @Value(("${message.recurso}"))
    private String mensajeRecurso;

        public UsuarioServiceImpl(UsuarioRepository usuarioRepository){
            this.usuarioRepository = usuarioRepository;
        }

    //@Autowired
    //private MensajeErrorConfiguration mensajeErrorConfiguration;

    @Override
    public List<UsuarioResponseDTO> findAll() {


            List<Usuario> usuarios = usuarioRepository.findAll();



        //COMO FUNCIONA UN STREAM
        //el punto adelante
        return usuarios.stream().
                map(usuario -> UsuarioResponseDTO.builder()
                        .uuid(usuario.getUuid())
                        .fechaCreacion(usuario.getFechaCreacion())
                        .fechaActualizacion(usuario.getFechaActualizacion())
                        .estado(usuario.isEstado())
                        .rut(usuario.getRut())
                        .nombre(usuario.getNombre())
                        .email(usuario.getEmail()).build())
                .collect(Collectors.toList());
    }


    @Override
    public UsuarioResponseDTO findById(String id) {
        Optional<Usuario> oUsuario = usuarioRepository.findById(id);


        if (!oUsuario.isPresent()) {
            throw new ErrorException(HttpStatus.NOT_FOUND, mensajeRecurso);
        }


        return UsuarioResponseDTO.builder()
                .uuid(oUsuario.get().getUuid())
                .rut(oUsuario.get().getRut())
                .nombre(oUsuario.get().getNombre())
                .fechaCreacion(oUsuario.get().getFechaCreacion())
                .fechaActualizacion(oUsuario.get().getFechaActualizacion())
                .estado(oUsuario.get().isEstado())
                .email(oUsuario.get().getEmail()).build();
    }



    @Override
    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO) {
        LocalDateTime fecha = LocalDateTime.now();

        List<Usuario> usuarios = usuarioRepository.findByEmailOrRut(usuarioRequestDTO.getEmail(),usuarioRequestDTO.getRut());

        for (int i=0;i < usuarios.size(); i++) {
            if (!usuarios.isEmpty()) {
                if (usuarios.get(i).getEmail().equals(usuarioRequestDTO.getEmail())) {
                    throw new ErrorException(HttpStatus.BAD_REQUEST, mensajeEmailError);
                }
                if (usuarios.get(i).getRut().equals(usuarioRequestDTO.getRut())) {
                    throw new ErrorException(HttpStatus.BAD_REQUEST, mensajeRutError);
                }
            }
        }


        Usuario usuario = Usuario.builder()
                .uuid(UUID.randomUUID().toString())
                .token(UUID.randomUUID().toString())
                .rut(usuarioRequestDTO.getRut())
                .email(usuarioRequestDTO.getEmail())
                .nombre(usuarioRequestDTO.getNombre())
                .pwd(usuarioRequestDTO.getPwd())
                .estado(true)
                .fechaCreacion(fecha)
                .fechaActualizacion(fecha)
                .build();



        usuarioRepository.save(usuario);




        return UsuarioResponseDTO.builder().uuid(usuario.getUuid())
                .rut(usuario.getRut())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .fechaCreacion(usuario.getFechaCreacion())
                .estado(usuario.isEstado())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .build();


    }

    @Override
    public UsuarioResponseDTO update(String id, UsuarioRequestDTO usuarioRequestDTO) {
        //habilitado o deshabilitado
        Optional<Usuario> oUsuario = usuarioRepository.findById(id);

        if (!oUsuario.isPresent()) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, mensajeDatoExistente);
        }
        //rut, nombre correctos
        if (!oUsuario.get().getRut().equals(usuarioRequestDTO.getRut())
                || (!oUsuario.get().getNombre().equals(usuarioRequestDTO.getNombre()))) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, mensajePeticionInvalida);
        }
        Usuario usuario = oUsuario.get();
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setPwd(usuarioRequestDTO.getPwd());
        usuario.setFechaActualizacion(LocalDateTime.now());
        usuario.setEstado(usuarioRequestDTO.isEstado());

            usuarioRepository.save(usuario);



        return UsuarioResponseDTO.builder()
                .uuid(usuario.getUuid())
                .rut(usuario.getRut())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .estado(usuario.isEstado())
                .build();


    }


}
