package com.distribuida.servicios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.distribuida.clases.Album;

@ApplicationScoped
public class ServicioAlbumImpl implements ServicioAlbumI {

	@Inject
	private DataSource source;
	
	@Override
	public List<Album> listar() {
		List<Album> listar = new ArrayList<>();
		Connection connection = null;
		try {
			// Iniciamos la conexión
			connection = source.getConnection();
			// Realizamos la consulta a la base de datos
			PreparedStatement statement = connection.prepareStatement("select * from Album");
			// Ejecutamos la consulta
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Album album = new Album();
				album.setId(resultSet.getInt(1));
				album.setTitle(resultSet.getString(2));
				album.setRelease_date(resultSet.getDate(3));
				album.setSinger_id(resultSet.getInt(4));
				album.setVersion(resultSet.getInt(5));
				listar.add(album);
			}
			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Problemas en la conexion");
		}
//		 return result;
		return listar;
	}

	@Override
	public Album buscar(int idAlbum) {
		Connection connection = null;
		Album album = new Album();
		try {
			// Iniciamos la conexión
			connection = source.getConnection();
			// Realizamos la sentencia para buscar un objeto album
			PreparedStatement statement = connection.prepareStatement("select * from album where id = ?");
			// Ejecutamos la consulta
			statement.setInt(1, idAlbum);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				album.setId(resultSet.getInt(1));
				album.setTitle(resultSet.getString(2));
				album.setRelease_date(resultSet.getDate(3));
				album.setSinger_id(resultSet.getInt(4));
				album.setVersion(resultSet.getInt(5));
			}
			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Problemas en la conexion");
		}
		return album;
	}

	@Override
	public void crear(Album album) {
		Connection connection = null;
		try {
			// Iniciamos la conexión
			connection = source.getConnection();
			// Realizamos la sentencia para crear un objeto album
			PreparedStatement statement = connection.prepareStatement("insert into album VALUES (?,?,?,?)");
			statement.setInt(1, album.getId());
			statement.setString(2, album.getTitle());
			statement.setDate(3, (Date) album.getRelease_date());
			statement.setInt(4, album.getSinger_id());
			statement.setInt(5, album.getVersion());
			// Ejecutamos la consulta
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void editar(Album album) {
		Connection connection = null;
		try {
			// Iniciamos la conexión
			connection = source.getConnection();
			// Realizamos la sentencia para modificar en la base de datos
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE album set title =?, release_date =? version =? where singer_id =?");
			statement.setInt(1, album.getId());
			statement.setString(2, album.getTitle());
			statement.setDate(3, (Date) album.getRelease_date());
			statement.setInt(4, album.getSinger_id());
			statement.setInt(5, album.getVersion());
			// Ejecutamos la consulta
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void borrar(int idAlbum) {
		Connection connection = null;
		try {
			// Iniciamos la conexión
			connection = source.getConnection();
			// Realizamos la sentencia para borrar un objeto album
			PreparedStatement statement = connection.prepareStatement("delete from album where id =?");
			// Ejecutamos la consulta
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
