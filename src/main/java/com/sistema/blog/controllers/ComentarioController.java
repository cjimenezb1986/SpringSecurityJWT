package com.sistema.blog.controllers;

import com.sistema.blog.dtos.ComentarioDTO;
import com.sistema.blog.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacion(@PathVariable(value = "publicacionId") Long publicacionId){

        return comentarioService.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value ="publicacionId" ) Long publicacionId, @PathVariable(value = "id") Long comentarioId){
        ComentarioDTO comentarioDTO = comentarioService.obtenerComentarioPorId(publicacionId,comentarioId);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }


    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioService.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value ="publicacionId" ) Long publicacionId, @PathVariable(value = "id") Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO comentarioActualizado= comentarioService.actualizarComentario(publicacionId,comentarioId,comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarCometario(@PathVariable(value ="publicacionId" ) Long publicacionId, @PathVariable(value = "id") Long comentarioId){
        comentarioService.eliminarComentario(publicacionId,comentarioId);

        return new ResponseEntity<>("Comentario Eliminado con exito", HttpStatus.OK);
    }
}
