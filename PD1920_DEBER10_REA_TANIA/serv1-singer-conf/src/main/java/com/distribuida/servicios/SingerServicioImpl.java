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

import com.distribuida.clases.Singer;

@ApplicationScoped
public class SingerServicioImpl implements SingerServicioI{
	
	@Inject 
	private DataSource source;

	@Override
	public List<Singer> listar() {
		List<Singer> listar = new ArrayList<>();
		Connection connection = null;
		try {
			connection = source.getConnection();
			// Realizamos la consulta a la base de datos
			PreparedStatement statement = connection.prepareStatement("select * from singer");
			// Ejecutamos la consulta
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Singer cantante = new Singer();
				// Vamos obteniendo los atributos
				cantante.setId(resultSet.getInt(1));
				cantante.setFirst_name(resultSet.getString(2));
				cantante.setLast_name(resultSet.getString(3));
				cantante.setBirth_date(resultSet.getDate(4));
				cantante.setVersion(resultSet.getInt(5));
				listar.add(cantante);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Problemas en la conexion");
		}
//		 return result
		return listar;
	}

	@Override
	public Singer buscar(int id) {
		Connection connection = null;
		Singer cantante = new Singer();
		try {
//			//Iniciamos la conexión
			connection = source.getConnection();
//			//Creamos sentencia a la base de datos por el identificador de la clase singer
			PreparedStatement statement = connection
					.prepareStatement("select * from Singer where id = ?");
//			//ejecutamos la sentencia
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				cantante.setId(resultSet.getInt(1));
				cantante.setFirst_name(resultSet.getString(2));
				cantante.setLast_name(resultSet.getString(3));
				cantante.setBirth_date(resultSet.getDate(4));
				cantante.setVersion(resultSet.getInt(5));
			}
			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Problemas en la conexion");
		}
		return cantante;
	}
	@Override
	public void crear(Singer cantante) {
		Connection connection = null;
		try {
//			//Iniciamos la conexión
			connection = source.getConnection();
//			//Creamos sentencia a la base de datos
			PreparedStatement statement = connection.prepareStatement("insert into singer VALUES (?,?,?,?)");
			statement.setInt(1, cantante.getId());
			statement.setString(2, cantante.getFirst_name());
			statement.setString(3, cantante.getLast_name());
			statement.setDate(4, (Date) cantante.getBirth_date());
			statement.setInt(5, cantante.getVersion());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	@Override
	public void borrar(int idCantante) {
		Connection connection = null;
		Singer cantante = new Singer();
		try {
//			//Iniciamos la conexión
			connection = source.getConnection();
//			//Creamos sentencia para eliminar un registro de la base de datos
			PreparedStatement statement = connection.prepareStatement("delete from singer where id = ?");
			statement.setInt(1, cantante.getId());
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	@Override
	public void editar(int idCantante) {
		Connection connection = null;
		Singer cantante = new Singer();
		try {
//			//Iniciamos la conexión
			connection = source.getConnection();
//			//Creamos sentencia para modificar datos en la base de datos
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE singer set first_name = ?, last_name = ?, birth_date = ? version =? where id = ?");
			statement.setInt(1, cantante.getId());
			statement.setString(2, cantante.getFirst_name());
			statement.setString(3, cantante.getLast_name());
			statement.setDate(4, (Date) cantante.getBirth_date());
			statement.setInt(5, cantante.getVersion());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
