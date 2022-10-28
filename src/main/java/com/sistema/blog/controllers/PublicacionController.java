package com.sistema.blog.controllers;

import com.sistema.blog.dtos.PublicacionDTO;
import com.sistema.blog.dtos.PublicacionRespuesta;
import com.sistema.blog.services.PublicacionService;
import com.sistema.blog.utils.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping()
    public PublicacionRespuesta listarPublicaciones(
            @RequestParam(value = "pageNumber", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO,required = false) int numeroDePagina,
            @RequestParam(value = "pageSize",defaultValue =AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO,required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue =AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue =AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sorDir){
        return publicacionService.obtenerTodasLasPublicaciones(numeroDePagina,medidaDePagina,ordernarPor,sorDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") Long id){

        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){

            return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@RequestBody PublicacionDTO publicacionDTO, @PathVariable(name = "id") Long id){
        PublicacionDTO publicacionRespuesta = publicacionService.actualizarPublicacion(publicacionDTO,id);

        return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion (@PathVariable(name = "id") Long id){
        publicacionService.eliminarPublicacion(id);

        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
    }
}
