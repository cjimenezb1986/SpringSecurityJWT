package com.sistema.blog.services;

import com.sistema.blog.dtos.ComentarioDTO;
import com.sistema.blog.exceptions.BlogAppException;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.models.Comentario;
import com.sistema.blog.models.Publicacion;
import com.sistema.blog.repositories.ComentarioRepository;
import com.sistema.blog.repositories.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntity(comentarioDTO);

        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("Publicacion","id",publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);

        return  mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
       List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);

       return comentarios.stream()
               .map(comentario -> mapearDTO(comentario))
               .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id",publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()->new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId ,ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id",publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()->new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comentarioRepository.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id",publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()->new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }

        comentarioRepository.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;

    }

    private Comentario mapearEntity(ComentarioDTO comentarioDTO){
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        return comentario;
    }
}

