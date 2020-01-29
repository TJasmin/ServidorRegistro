package com.distribuida.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.distribuida.clases.Album;
import com.distribuida.servicios.ServicioAlbumI;

@Path("/albums")
@ApplicationScoped
public class AlbumRest {
	
	@Inject
	private ServicioAlbumI servicio;
	
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Album> listar() {
		return servicio.listar();
	}

	@GET
	@Path("/{idAlbum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Album buscar(@PathParam("idAlbum") int idAlbum) {
		return servicio.buscar(idAlbum);
	}
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public void crear(Album album) {
		servicio.crear(album);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public void editar(Album album) {
		servicio.editar(album);
	}

	@DELETE
	@Path("/{idAlbum}")
	public void borrar(@PathParam("idAlbum") int idAlbum) {
		servicio.borrar(idAlbum);
	}
}
