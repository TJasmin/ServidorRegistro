package com.distribuida.manejador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.distribuida.clases.Singer;
import com.distribuida.servicios.SingerServicioI;

@Named("singerM")
@SessionScoped
public class SingerManejador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private SingerServicioI servicio;
	private List<Singer> listaSingers;
	private Singer singer;
	

	public SingerServicioI getServicio() {
		return servicio;
	}
	public void setServicio(SingerServicioI servicio) {
		this.servicio = servicio;
	}
	public List<Singer> getListaSingers() {
		return listaSingers;
	}
	public void setListaSingers(List<Singer> listaSingers) {
		this.listaSingers = listaSingers;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public String viewSingers(){
		listaSingers = servicio.listar();
		return "/cantantesViews/listarCantante?faces-redirect-true";
	}
	public List<Singer> listarSing(){
		return servicio.listar();
	}
	
	@PostConstruct
	public void init() {
		singer = new Singer();
		listaSingers = servicio.listar();
	}

}
