package pl.edu.agh.kis.soa.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.edu.agh.kis.soa.resources.model.Student;
import sun.net.www.http.HttpClient;

/**
 * Klasa wystawiająca interfejs REST.
 * @author teacher
 *
 */
@Path("rest")
@Stateless
public class StudentResource {

	private static final Logger logger = Logger.getLogger("StudentResource");
	private static final String PATH_PDF = "/Network/Servers/labserver.local/Volumes/Data/NetUsers/2013.2n.krzysztof.misiak/Downloads/rest.pdf";
    private static final String PATH_PNG = "/Network/Servers/labserver.local/Volumes/Data/NetUsers/2013.2n.krzysztof.misiak/Downloads/test.png";



	@GET
	@Path("getStudent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@QueryParam("id") String albumNo) {
		Student s = new Student();
		s.setAlbumNo(albumNo);
		s.setFirstName("Jan");
		s.setLastName("Nowak");
		List<String> subjects = new ArrayList<String>();
		subjects.add("Bazy danych");
		subjects.add("SOA");
		s.setSubjects(subjects);
		return Response.ok(s,MediaType.APPLICATION_JSON).build();
	}

    @PUT
    @Path("setStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setStudent(@QueryParam("id") String albumNo, @QueryParam("name")String firstName,@QueryParam("lastName") String lastName)
    {

        Student s =  new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setAlbumNo(albumNo);
        return Response.ok(s,MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Path("getpdf")
    @Produces("application/pdf")
    public Response getPdf() {

        File file = new File(PATH_PDF);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=rest.pdf");
        return response.build();

    }

    @GET
    @Path("getpng")
    @Produces("application/pdf")
    public Response getPng() {

        File file = new File(PATH_PNG);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=test.png");
        return response.build();

    }

}
