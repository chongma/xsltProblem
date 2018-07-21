package test.rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import test.application.BookService;

@Path("/open")
@Produces(MediaType.APPLICATION_XML)
public class OpenRS {

	@Inject
	private BookService bookService;

	@Path("/test1")
	@GET
	public Response test1() {
		bookService.test1();
		return Response.ok().build();
	}

	@Path("/test2")
	@GET
	public Response test2() {
		bookService.test2();
		return Response.ok().build();
	}

}
