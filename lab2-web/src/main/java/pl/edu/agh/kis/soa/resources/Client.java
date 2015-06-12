package pl.edu.agh.kis.soa.resources;

import com.google.gson.Gson;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import pl.edu.agh.kis.soa.Student;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by krzysiek on 5/24/15.
 */

class HttpException extends Exception
{

    private String m ;
    public HttpException(String msg)
    {
        m = msg;
    }
    public void printError()
    {
        System.out.println(m);
    }


}



public class Client {


    public static void main(String[] args) {
        try {

            ClientRequest request = new ClientRequest(
                    "http://localhost:8080/lab2-web/rest/get/5");
            request.accept("application/json");
            ClientResponse<String> response = request.get(String.class);
            printResponse(response);



            request = new ClientRequest("http://localhost:8080/lab2-web/rest/addStudent");
            request.accept("application/json");
            Student s = new Student();
            Gson gson = new Gson();
            s.setAlbumNo(5);
            s.setFirstName("Krzysiek");
            s.setLastName("Misiak");
            request.body("application/json", gson.toJson(s).toString());
            response = request.post(String.class);
            handleError(response);
            printResponse(response);

            request = new ClientRequest("http://localhost:8080/lab2-web/rest/authorize");
            request.accept("application/json");
            request.queryParameter("login", "login");
            request.queryParameter("password", "haslo");
            response = request.post(String.class);

            handleError(response);
            printResponse(response);

            request = new ClientRequest("http://localhost:8080/lab2-web/rest/hello");
            request.queryParameter("id","5");
            response = request.post(String.class);

            handleError(response);
            printResponse(response);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (HttpException e)
        {
            e.printError();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printResponse(ClientResponse<String> response) {
        String line = "";
        BufferedReader rd;
        try {
            rd = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleError(ClientResponse<String> response) throws HttpException {
                if(response.getResponseStatus() != Response.Status.OK ) throw new HttpException(response.getResponseStatus().getReasonPhrase());
    }

}
