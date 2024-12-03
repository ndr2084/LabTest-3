package restful.examples;
 
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
 
@Path("/hello")
public class HelloWorldService {
 
	@GET
	@Path("/query")
	public Response getMsg(
			@QueryParam("firstName") String firstName, 
			@QueryParam("lastName") String lastName,
			@DefaultValue("2019") @QueryParam("year") int year) {
 
		String output = "Jersey says : Hello " + firstName + " " 
						+ lastName + " from " + year;
 
		return Response.status(200).entity(output).build();
 
	}
 
}