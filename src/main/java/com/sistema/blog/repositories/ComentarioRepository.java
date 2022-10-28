package com.sistema.blog.repositories;

import com.sistema.blog.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository <Comentario, Long> {

    public List<Comentario>findByPublicacionId(Long publicacionId);
}
