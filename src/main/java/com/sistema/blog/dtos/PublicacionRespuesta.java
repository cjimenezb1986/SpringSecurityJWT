package com.sistema.blog.dtos;

import java.util.List;

public class PublicacionRespuesta {
    private List<PublicacionDTO>contenido;
    private int numeropagina;
    private int medidaPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public PublicacionRespuesta() {
    }

    public List<PublicacionDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionDTO> contenido) {
        this.contenido = contenido;
    }

    public int getNumeropagina() {
        return numeropagina;
    }

    public void setNumeropagina(int numeropagina) {
        this.numeropagina = numeropagina;
    }

    public int getMedidaPagina() {
        return medidaPagina;
    }

    public void setMedidaPagina(int medidaPagina) {
        this.medidaPagina = medidaPagina;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}
