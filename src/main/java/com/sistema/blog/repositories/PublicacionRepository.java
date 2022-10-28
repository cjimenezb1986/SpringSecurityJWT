package com.sistema.blog.repositories;

import com.sistema.blog.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository <Publicacion, Long>{
}
