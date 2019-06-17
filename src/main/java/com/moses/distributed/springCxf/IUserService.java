package com.moses.distributed.springCxf;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@WebService
@Path(value="/users/")
public interface IUserService {
	
	@GET
	@Path("/")	//http://ip:port/users		http://localhost:8080/distributed/ws/users
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<User> getUsers();
	
	@DELETE
	@Path("{id}")		//header: Accept -> application/xml
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response delete(int id);
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	User getUser(int id);
	
	@POST
	@Path("add")
	Response insert(User user);
	
	@PUT
	@Path("update")
	Response update(User user);
}
