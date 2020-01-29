package com.distribuida.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;

@ApplicationPath("/")
@ApplicationScoped
public class RestApplication extends Application {

	@Inject
	@ConfigProperty(name = "server.port")
	private Integer puerto;

	@Inject
	@ConfigProperty(name = "consul.port", defaultValue = "localhost")
	private String consulHost;

	@Inject
	@ConfigProperty(name = "consult,port", defaultValue = "8500")
	private Integer consulPort;

	private String ID = UUID.randomUUID().toString();

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(SingerRest.class);
		return ret;
	}

	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		System.out.println("inicializando");

		ConsulClient client = new ConsulClient(); // consulHost, consulPort
		NewService newService = new NewService();
		newService.setId(ID);
		newService.setName("singer");
		newService.setAddress("127.0.0.1"); // donde se esta ejecutando la maquina
		newService.setPort(puerto);
		
		// quita servidores caidos
		client.agentServiceRegister(newService);
//el proposito es variar el puerto y no la direccion 

	}

	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
		System.out.println("Finalizando");

		ConsulClient client = new ConsulClient();
		client.agentServiceDeregister(ID);

	}
}
