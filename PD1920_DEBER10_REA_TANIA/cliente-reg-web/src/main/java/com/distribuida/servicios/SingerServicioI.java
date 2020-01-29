package com.distribuida.servicios;

import java.util.List;

import com.distribuida.clases.Singer;

public interface SingerServicioI {

	List<Singer> listar();

	Singer buscar(int id);

	void crear(Singer cantante);

	void editar(int idCantante);

	void borrar(int idCantante);
}
