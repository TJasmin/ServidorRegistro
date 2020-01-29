package com.distribuida.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.distribuida.clases.Album;
import com.distribuida.clases.Singer;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/servicios")
public class ProducerServicios {
	private static final String URL_SINGER = "http://localhost:7001/singers";
	private static final String URL_ALBUM = "http://localhost:7002/albums";
	private static final String NAME_SINGER = "singer";
	private static final String NAME_ALBUM = "album";

	private ObjectMapper mapper = new ObjectMapper();
	private HttpClient client = HttpClient.newHttpClient();
	private HttpResponse<String> response;

	ConsulClient clientConsulClient = new ConsulClient("127.0.0.1");
	Response<Map<String, Service>> ss = clientConsulClient.getAgentServices();
	Map<String, Service> services = ss.getValue();

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Singer> listar() {
		List<Singer> lista = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_SINGER)).GET().build();
		try {
			response = client.send(request, BodyHandlers.ofString());
			lista = mapper.readValue(response.body(), new TypeReference<List<Singer>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Service> lista1 = services.values().stream().filter(s -> NAME_SINGER.equals(s.getService()))
				.collect(Collectors.toList());
		if (lista1.isEmpty()) {
			System.err.println("No existe ningun servicio registrado con el nombre " + NAME_SINGER);
		}
		for (Service s : lista1) {
			String url = String.format("http:// %s:%d/listar", s.getAddress(), s.getPort());
			System.out.println(s.getId() + "-->" + url);
		}

		// vamos a ver cuales estan disponibles
		HealthServicesRequest request1 = HealthServicesRequest.newBuilder().setPassing(true)
				.setQueryParams(QueryParams.DEFAULT).build();
		Response<List<HealthService>> healthyServices = clientConsulClient.getHealthServices(NAME_SINGER, request1);

		List<HealthService> datos = healthyServices.getValue();

		// consulta datos
		datos.stream().map(s -> s.getService())
				.map(s -> String.format("%s--> http:// %s:%d/listar", s.getId(), s.getAddress(), s.getPort()))
				.forEach(System.out::println);

		return lista;
	}

	@GET
	@Path("/listar/{idCantante}")
	@Produces(MediaType.APPLICATION_JSON)
	public Singer buscar(@PathParam("idCantante") int idCantante) {
		Singer singer = null;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_SINGER + "/" + idCantante)).GET().build();
		try {
			response = client.send(request, BodyHandlers.ofString());
			singer = mapper.readValue(response.body(), Singer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Service> lista1 = services.values().stream().filter(s -> NAME_SINGER.equals(s.getService()))
				.collect(Collectors.toList());
		if (lista1.isEmpty()) {
			System.err.println("No existe ningun servicio registrado con el nombre " + NAME_SINGER);
		}
		for (Service s : lista1) {
			String url = String.format("http:// %s:%d/listar" + "idCantante", s.getAddress(), s.getPort());
			System.out.println(s.getId() + "-->" + url);
		}

		// vamos a ver cuales estan disponibles
		HealthServicesRequest request1 = HealthServicesRequest.newBuilder().setPassing(true)
				.setQueryParams(QueryParams.DEFAULT).build();
		Response<List<HealthService>> healthyServices = clientConsulClient.getHealthServices(NAME_SINGER, request1);

		List<HealthService> datos = healthyServices.getValue();

		// consulta datos
		datos.stream().map(s -> s.getService()).map(
				s -> String.format("%s--> http:// %s:%d/listar" + "idCantante", s.getId(), s.getAddress(), s.getPort()))
				.forEach(System.out::println);
		return singer;
	}

	@GET
	@Path("/listarAlbums")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Album> listarAlbum() {
		List<Album> listarAlbums = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_ALBUM)).GET().build();
		try {
			response = client.send(request, BodyHandlers.ofString());
			listarAlbums = mapper.readValue(response.body(), new TypeReference<List<Album>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Service> lista1 = services.values().stream().filter(s -> NAME_ALBUM.equals(s.getService()))
				.collect(Collectors.toList());
		if (lista1.isEmpty()) {
			System.err.println("No existe ningun servicio registrado con el nombre " + NAME_ALBUM);
		}
		for (Service s : lista1) {
			String url = String.format("http:// %s:%d/listarAlbums", s.getAddress(), s.getPort());
			System.out.println(s.getId() + "-->" + url);
		}

		// vamos a ver cuales estan disponibles
		HealthServicesRequest request1 = HealthServicesRequest.newBuilder().setPassing(true)
				.setQueryParams(QueryParams.DEFAULT).build();
		Response<List<HealthService>> healthyServices = clientConsulClient.getHealthServices(NAME_ALBUM, request1);

		List<HealthService> datos = healthyServices.getValue();

		// consulta datos
		datos.stream().map(s -> s.getService())
				.map(s -> String.format("%s--> http:// %s:%d/listarAlbums", s.getId(), s.getAddress(), s.getPort()))
				.forEach(System.out::println);
		return listarAlbums;
	}

	@GET
	@Path("/listarAlbums/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Album buscarAlbum(@PathParam("id") int id) {
		Album album = null;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_ALBUM + "/" + id)).GET().build();
		try {
			response = client.send(request, BodyHandlers.ofString());
			album = mapper.readValue(response.body(), Album.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Service> lista1 = services.values().stream().filter(s -> NAME_ALBUM.equals(s.getService()))
				.collect(Collectors.toList());
		if (lista1.isEmpty()) {
			System.err.println("No existe ningun servicio registrado con el nombre " + NAME_ALBUM);
		}
		for (Service s : lista1) {
			String url = String.format("http:// %s:%d/listarAlbums" + "/" + id, s.getAddress(), s.getPort());
			System.out.println(s.getId() + "-->" + url);
		}

		// vamos a ver cuales estan disponibles
		HealthServicesRequest request1 = HealthServicesRequest.newBuilder().setPassing(true)
				.setQueryParams(QueryParams.DEFAULT).build();
		Response<List<HealthService>> healthyServices = clientConsulClient.getHealthServices(NAME_ALBUM, request1);

		List<HealthService> datos = healthyServices.getValue();

		// consulta datos
		datos.stream().map(s -> s.getService()).map(s -> String.format("%s--> http:// %s:%d/listarAlbums" + "/" + id,
				s.getId(), s.getAddress(), s.getPort())).forEach(System.out::println);
		return album;
	}

}
