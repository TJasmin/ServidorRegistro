package com.distribuida.manejador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.distribuida.clases.Album;
import com.distribuida.servicios.ServicioAlbumI;

@Named("albumM")
@SessionScoped
public class AlbumManejador implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private ServicioAlbumI servicio;
	private List<Album> listaAlbums;
	private Album album;
	

	public ServicioAlbumI getServicio() {
		return servicio;
	}
	public void setServicio(ServicioAlbumI servicio) {
		this.servicio = servicio;
	}
	public List<Album> getListaSingers() {
		return listaAlbums;
	}
	public void setListaSingers(List<Album> listaSingers) {
		this.listaAlbums = listaSingers;
	}
	public Album getSinger() {
		return album;
	}
	public void setSinger(Album singer) {
		this.album = singer;
	}
	public String viewSingers(){
		listaAlbums = servicio.listar();
		return "/albumsViews/listarAlbums?faces-redirect-true";
	}
	public List<Album> listarSing(){
		return servicio.listar();
	}
	
	@PostConstruct
	public void init() {
		album = new Album();
		listaAlbums = servicio.listar();
	}
}
