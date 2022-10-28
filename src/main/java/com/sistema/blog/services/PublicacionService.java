package com.sistema.blog.services;

import com.sistema.blog.dtos.PublicacionDTO;
import com.sistema.blog.dtos.PublicacionRespuesta;

public interface PublicacionService {
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO,Long id);

    public void eliminarPublicacion(Long id);
}
