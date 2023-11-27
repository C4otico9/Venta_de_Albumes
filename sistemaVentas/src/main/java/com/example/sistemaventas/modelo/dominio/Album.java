package com.example.sistemaventas.modelo.dominio;

import java.util.Date;

public class Album extends Genero {
    private Integer idAlbum;
    private String codigoAlbum;
    private String nombreAlbum;
    private String artistaAlbum;
    private String generoAlbum;
    private Double precioAlbum;
    private Date fechaCreacionAlbum;

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getCodigoAlbum() {
        return codigoAlbum;
    }

    public void setCodigoAlbum(String codigoAlbum) {
        this.codigoAlbum = codigoAlbum;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getArtistaAlbum() {
        return artistaAlbum;
    }

    public void setArtistaAlbum(String artistaAlbum) {
        this.artistaAlbum = artistaAlbum;
    }

    public String getGeneroAlbum() {
        return generoAlbum;
    }

    public void setGeneroAlbum(String generoAlbum) {
        this.generoAlbum = generoAlbum;
    }

    public Double getPrecioAlbum() {
        return precioAlbum;
    }

    public void setPrecioAlbum(Double precioAlbum) {
        this.precioAlbum = precioAlbum;
    }

    public Date getFechaCreacionAlbum() {
        return fechaCreacionAlbum;
    }

    public void setFechaCreacionAlbum(Date fechaCreacionAlbum) {
        this.fechaCreacionAlbum = fechaCreacionAlbum;
    }
}
