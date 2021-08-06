package cl.everis.clientes.service

import cl.everis.cliente.configuration.MensajeErrorConfiguration
import cl.everis.cliente.dto.UsuarioRequestDTO
import cl.everis.cliente.dto.UsuarioResponseDTO
import cl.everis.cliente.exception.ErrorException
import cl.everis.cliente.model.Usuario
import cl.everis.cliente.repository.UsuarioRepository
import cl.everis.cliente.service.UsuarioService
import cl.everis.cliente.service.UsuarioServiceImpl
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.time.LocalDateTime

class ServiceUsuarioSpec extends Specification{
    UsuarioService usuarioService
    @Autowired
    UsuarioRepository usuarioRepository

    Usuario usuario
    Usuario usuariouno;
    Usuario usuariodos;
    List<Usuario> usuarios = new ArrayList<>()


    def setup(){
        this.usuarioRepository = Stub(UsuarioRepository.class)
        this.usuarioService = new UsuarioServiceImpl(this.usuarioRepository)
        this.usuarioService.@mensajeEmailError = "Problema Email"
        this.usuarioService.@mensajeRutError = "Problema Rut"
        this.usuarioService.@mensajeDatoExistente = "Problema Dato Existente"
        this.usuarioService.@mensajePeticionInvalida = "Problema Peticion Invalida"
        this.usuarioService.@mensajeRecurso = "Recurso no encontrado"



        this.usuario = Usuario.builder()
                        .uuid("123")
                        .rut("11111")
                        .nombre("andres")
                        .pwd("aaa")
                        .email("asd@gmail.com")
                        .build()

    }

    def "Listar un Usuario OK"(){
        given:"cargar los datos"
        this.usuarioRepository.findById("123") >> Optional.of(this.usuario)
        String id = "123"

        when:"listar el usuario"
        UsuarioResponseDTO respuesta = this.usuarioService.findById(id)

        then:"Respuesta"
        usuario.getRut() == respuesta.getRut()
        usuario.getEmail() == respuesta.getEmail()
        usuario.getNombre() == respuesta.getNombre()
    }

    def "Listar un Usuario NotFound"(){
        given:"cargar los datos"
        this.usuarioRepository.findById("123") >> Optional.empty()
        String id = "123"
        when:"Asignar"
        this.usuarioService.findById(id)
        then:"Respuesta"
        ErrorException error = thrown()
        error.getHttpStatus() == HttpStatus.NOT_FOUND
        error.getMessage() == "Recurso no encontrado"

    }

    def "Listar Usuarios"(){
        given:"datos"
        this.usuariouno = Usuario.builder()
                .uuid("123")
                .rut("11111")
                .nombre("andres")
                .pwd("aaa")
                .email("asd@gmail.com")
                .build()

        this.usuariodos = Usuario.builder()
                .uuid("124")
                .rut("11111")
                .nombre("andres")
                .pwd("aaa")
                .email("asd@gmail.com")
                .build()
        List<Usuario> usuarios = new ArrayList<>()
            usuarios.add(usuariouno)
            usuarios.add(usuariodos)
        this.usuarioRepository.findAll() >> usuarios
        when:"Listar Usuarios"
        List <UsuarioResponseDTO> resp = this.usuarioService.findAll()
        then:"resultado"
        usuarios.size() == 2
        usuarios.get(0).getUuid().equals("123")
        usuarios.get(1).getUuid().equals("124")
    }

    def "Listar Usuarios Vacios"(){
        given: "datos"
        List<Usuario> usuarios = new ArrayList<>()
        this.usuarioRepository.findAll() >> usuarios

        when: "Listar Usuarios"
        List <UsuarioResponseDTO> resp = this.usuarioService.findAll()

        then:"Respuesta"
        resp.size() == 0;

    }



    def "Insertar Usuarios" (){
        given:"datos"
        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .rut("11111")
                .nombre("andres")
                .email("asd@gmail.com")
                .build()


        when:"Asignar"
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.save(usuarioRequestDTO)

        then:"comparar"
        usuarioResponseDTO.getRut() == usuarioRequestDTO.getRut();
        usuarioResponseDTO.getNombre() == usuarioRequestDTO.getNombre();
        usuarioResponseDTO.getEmail() == usuarioRequestDTO.getEmail();

    }

    def "Insertar Usuario Error Email"(){
        given:"datos"
        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .rut("11111")
                .nombre("andres")
                .email("asd@gmail.com")
                .build()

        Usuario usuario = Usuario.builder()
                .rut("11112")
                .nombre("aaa")
                .email("asd@gmail.com")
                .build()

        List<Usuario> usuarios = new ArrayList<>()
        usuarios.add(usuario)

        // _ = variable
        this.usuarioRepository.findByEmailOrRut(_, _) >> usuarios

        when:"Ingresar usuario"
        this.usuarioService.save(usuarioRequestDTO)

        then:"respuesta"
        ErrorException error = thrown()
        error.getHttpStatus() == HttpStatus.BAD_REQUEST
        error.getMessage() ==  "Problema Email"


    }


    def "Insertar Usuario Error Rut"(){
        given:"datos"
        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .rut("11111")
                .nombre("andres")
                .email("asd@gmail.com")
                .build()

        Usuario usuario = Usuario.builder()
                .rut("11111")
                .nombre("aaa")
                .email("asdd@gmail.com")
                .build()

        List<Usuario> usuarios = new ArrayList<>()
        usuarios.add(usuario)

        // _ = variable
        this.usuarioRepository.findByEmailOrRut(_, _) >> usuarios

        when:"Ingresar usuario"
        this.usuarioService.save(usuarioRequestDTO)

        then:"respuesta"
        ErrorException error = thrown()
        error.getHttpStatus() == HttpStatus.BAD_REQUEST
        error.getMessage() ==  "Problema Rut"


    }

    def "Modificar Usuario"(){
        given:"Datos"
        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .rut("1-9")
                .nombre("matias")
                .email("asdf@gmail.com")
                .build()

        Usuario usuario = Usuario.builder()
                .rut("1-9")
                .nombre("matias")
                .email("asd@gmail.com")
                .build()

        this.usuarioRepository.findById(_) >> Optional.of(usuario)
        this.usuarioRepository.save(usuario)
        when:"Modificar Usuario"
        this.usuarioService.update("1-9", usuarioRequestDTO)
        then:"respuesta"
        usuarioRequestDTO.getEmail().equals("asdf@gmail.com")
        usuarioRequestDTO.getNombre().equals("matias")
        usuarioRequestDTO.getRut().equals("1-9")
    }

    def "Modificar Usuario Error Peticion Invalida"(){
        given:"Datos"
        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .rut("1-9")
                .nombre("matias")
                .email("asdf@gmail.com")
                .build()

        Usuario usuario = Usuario.builder()
                .rut("222")
                .nombre("andres")
                .email("asd@gmail.com")
                .build()

        this.usuarioRepository.findById(_) >> Optional.of(usuario)
        this.usuarioRepository.save(usuario)
        when:"Modificar Usuario"
        this.usuarioService.update("1-9", usuarioRequestDTO)
        then:"respuesta"
        ErrorException error = thrown()
        error.getHttpStatus() == HttpStatus.BAD_REQUEST
        error.getMessage() ==  "Problema Peticion Invalida"
    }


}
