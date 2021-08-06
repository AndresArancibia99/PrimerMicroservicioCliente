package cl.everis.cliente.controller;

import cl.everis.cliente.dto.UsuarioRequestDTO;
import cl.everis.cliente.dto.UsuarioResponseDTO;
import cl.everis.cliente.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioservice;


    //CrearUsuario
    @PostMapping
    public ResponseEntity<?> crear(@Validated @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuarioRequestDTO));
    }


    //Leer Usuario
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> leer(@Validated @PathVariable(value = "id") String userId) {
        return new ResponseEntity<>(usuarioservice.findById(userId), HttpStatus.OK);
    }


    //Actualizar Usuario
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@RequestBody UsuarioRequestDTO usuarioRequestDTO, @PathVariable(value = "id") String userId) {

        return new ResponseEntity<UsuarioResponseDTO>(usuarioservice.update(userId, usuarioRequestDTO), HttpStatus.OK);
    }


    //Ver todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> leertodos() {
        return new ResponseEntity<>(usuarioservice.findAll(), HttpStatus.OK);

//firma es lo que le vas a mandar para consumirlo


    }
}

