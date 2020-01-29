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

import com.distribuida.clases.Singer;
import com.distribuida.servicios.SingerServicioI;

@Path("/singers")
@ApplicationScoped
public class SingerRest {

	@Inject
	private SingerServicioI servicio;
	
	@GET
	//@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Singer> listarSingers() {
		return servicio.listar();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Singer buscar(@PathParam("id") int id) {
		return servicio.buscar(id);
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void crear(Singer cantante) {
		servicio.crear(cantante);
	}

	@PUT
	@Path("/{idCantante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editar(@PathParam("idCantante") int idCantante) {
		servicio.editar(idCantante);
	}

	@DELETE
	// @Path("/eliminar/{idCantante}")
	@Path("/{idCantante}")
	public void eliminar(@PathParam("idCantante") int idCantante) {
		servicio.borrar(idCantante);
	}
}
