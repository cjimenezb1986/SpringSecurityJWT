package com.sistema.blog.services;

import com.sistema.blog.dtos.PublicacionDTO;
import com.sistema.blog.dtos.PublicacionRespuesta;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.models.Publicacion;
import com.sistema.blog.repositories.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {

        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);

        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordernarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordernarPor).ascending():Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina,sort);

        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);

        List<Publicacion> listaPublicaciones = publicaciones.getContent();

        List<PublicacionDTO> contenido = listaPublicaciones.stream()
                .map(publicacion -> mapearDTO(publicacion))
                .collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeropagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepository
                                .findById(id)
                                .orElseThrow(()-> new ResourceNotFoundException("Publicacion","id",id));

        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id",id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);

        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id",id));
        publicacionRepository.delete(publicacion);
    }

    private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
    }

    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);

        return publicacion;
    }
}
