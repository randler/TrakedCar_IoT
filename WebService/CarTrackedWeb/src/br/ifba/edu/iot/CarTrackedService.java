package br.ifba.edu.iot;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")
public class CarTrackedService {
	
	
	@GET
	@Produces("text/plain")
	public String helloWorld(){
		return "Hello";
	}

}
