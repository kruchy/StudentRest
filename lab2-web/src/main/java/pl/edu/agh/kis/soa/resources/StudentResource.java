package pl.edu.agh.kis.soa.resources;

import pl.edu.agh.kis.soa.Student;
import pl.edu.agh.kis.soa.StudentDaoInt;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.logging.Logger;

/**
 * Klasa wystawiająca interfejs REST.
 * @author teacher
 *
 */
@Path("rest")
@Stateless
public class StudentResource {

    @EJB
    private StudentDaoInt studentDao;
	private static final Logger logger = Logger.getLogger("StudentResource");
	private static final String PATH_PDF = "/home/krzysiek/rest.pdf";
    private static final String PATH_PNG = "/home/krzysiek/test.png";
    public StudentResource()
    {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("primary");
//        em = emf.createEntityManager();
    }






    @POST
    @Path("setStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setStudent(@QueryParam("id") int albumNo, @QueryParam("name")String firstName,@QueryParam("lastName") String lastName) {

        Student s = studentDao.getStudent(albumNo);
        if (s == null) {
            s = new Student();
            s.setFirstName(firstName);
            s.setLastName(lastName);
            studentDao.saveStudent(s);
            return Response.ok(s, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
//        if(students.add(student) ) return Response.ok(true, MediaType.APPLICATION_JSON).build();
//        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@PathParam("id") int albumNo) {

//        Student s = getS(albumNo);
		Student s;
        s = studentDao.getStudent(albumNo);
        if(s != null) {
            return Response.ok(s,MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}

    @POST
    @Path("addStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(Student s)//@QueryParam("firstName")String firstName,@QueryParam("lastName") String lastName)
    {
//        Student s = new Student();
//        s.setFirstName(firstName);
//        s.setLastName(lastName);
        studentDao.saveStudent(s);
//        return Response.ok(s, MediaType.APPLICATION_JSON).build();
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("delStudent/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delStudent(@PathParam("id")int albumNo)
    {

        return Response.ok(studentDao.deleteStudent(albumNo),MediaType.APPLICATION_JSON).build();

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
            session.setAttribute("user_login",login);
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        }
        else
        {
            session.setAttribute("user_login",null);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@QueryParam("id") int id, @Context HttpServletRequest request)
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
