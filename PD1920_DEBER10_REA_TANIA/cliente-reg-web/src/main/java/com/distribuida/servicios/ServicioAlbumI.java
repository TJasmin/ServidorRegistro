package com.distribuida.servicios;

import java.util.List;

import com.distribuida.clases.Album;

public interface ServicioAlbumI {

	List<Album> listar();

	Album buscar(int idAlbum);

	void crear(Album album);

	void editar(Album album);

	void borrar(int idAlbum);
}
