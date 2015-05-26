package pl.edu.agh.kis.soa.resources;

import pl.edu.agh.kis.soa.resources.model.Student;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Klasa wystawiająca interfejs REST.
 * @author teacher
 *
 */
@Path("rest")
@Stateless
public class StudentResource {

	private static final Logger logger = Logger.getLogger("StudentResource");
	private static final String PATH_PDF = "/home/krzysiek/rest.pdf";
    private static final String PATH_PNG = "/home/krzysiek/test.png";
    private static ArrayList<Student> students = new ArrayList<Student>();
    public StudentResource()
    {
        Student s = new Student();
        s.setAlbumNo("5");
		s.setFirstName("Jan");
		s.setLastName("Nowak");
		List<String> subjects = new ArrayList<String>();
		subjects.add("Bazy danych");
		subjects.add("SOA");
		s.setSubjects(subjects);
        students.add(s);
    }

    private Student getS(String albumNo)
    {
        for(Student s:students)
        {
            if(s.getAlbumNo().equals(albumNo)) return s;
        }
        return null;
    }
    private boolean remS(String albumNo)
    {
        for(Student s:students)
        {
            if(s.getAlbumNo().equals(albumNo))
            {
                students.remove(s);
                return true;
            }
        }
        return false;
    }




	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("id") String albumNo) {

        Student s = getS(albumNo);
//		Student s = new Student();
//
        if(s != null) {
            return Response.ok(s,MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
	}

    @POST
    @Path("setStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setStudent(Student student)//@QueryParam("id") String albumNo, @QueryParam("name")String firstName,@QueryParam("lastName") String lastName)
    {
        if(students.add(student) ) return Response.ok(true, MediaType.APPLICATION_JSON).build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    @PUT
    @Path("delStudent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delStudent(Student student) //@QueryParam("id") String albumNo,@QueryParam("name") String firstName,@QueryParam("lastName") String lastName, @QueryParam("subjects") String[] subjects )
    {
        Student s = getS(student.getAlbumNo());
        if(students.remove(s)) return Response.ok(true,MediaType.APPLICATION_JSON).build();
        else return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("addStudent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student)//@QueryParam("id") String albumNo,@QueryParam("firstName")String firstName, @QueryParam("lastName")String lastName) //, @QueryParam("subjects") String[] subjects )
    {
        if(getS(student.getAlbumNo()) != null) {
            return Response.ok("Student already exists", MediaType.APPLICATION_JSON).build();
        }
        students.add(student);
        return Response.ok(student,MediaType.APPLICATION_JSON).build();
//        s.setSubjects(Arrays.asList(subjects));
//        if(students.add(s)) return Response.ok(Response.Status.CREATED).build();
//        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
    @Produces("application/png")
    public Response getPng() {

        File file = new File(PATH_PNG);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=test.png");
        return response.build();

    }



    private boolean verifyPassword(String login,String pass)
    {
        return pass != null && pass.equals("haslo");

    }

    @POST
    @Path("authorize")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@QueryParam("login") String login,@QueryParam("password")String password,@Context HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session == null)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if(verifyPassword(login, password))
        {
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        }
        else
        {
            session.setAttribute("user_login",null);
            return Response.ok(login + " " + password,MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@QueryParam("id") String id, @Context HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session == null)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
        String login = (String) session.getAttribute("user_login");
        if(login == null)
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(getStudent(id),MediaType.APPLICATION_JSON).build();
    }



}
