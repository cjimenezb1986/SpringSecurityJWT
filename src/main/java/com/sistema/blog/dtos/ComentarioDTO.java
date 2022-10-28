package com.sistema.blog.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ComentarioDTO {

    private Long id;

    @NotEmpty(message = "El Nombre no debe ser vacio o nulo")
    private String nombre;

    @NotEmpty(message = "El email no debe ser vacio o nulo")
    @Email
    private String email;

    @NotEmpty (message = "El cuerpo no debe ser vaco o nulo")
    @Size(min = 10, message = "El cuerpo del comentario debe tener almenos 10 caracteres")
    private String cuerpo;

    public ComentarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
