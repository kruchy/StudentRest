package pl.edu.agh.kis.soa.resources;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import pl.edu.agh.kis.soa.resources.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysiek on 5/24/15.
 */
public class Client {
    public static void main(String[] args) {
        try {
            BufferedReader rd;
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            HttpGet getStudentRequest = new HttpGet("http://localhost:8080/lab2-web/rest/get/5");
            HttpResponse response = null;
            response = client.execute(getStudentRequest);
            printResponse(response);

            HttpPost post = new HttpPost("http://localhost:8080/lab2-web/rest/addStudent");

            Student s = new Student();
            s.setAlbumNo("6");
            s.setFirstName("Krzysiek");
            s.setLastName("Misiak");
//            params.add(new BasicNameValuePair("id", "6"));
//            params.add(new BasicNameValuePair("firstName", "Krzysiek"));
//            params.add(new BasicNameValuePair("lastName", "Misiak"));
            Gson gson = new Gson();
            StringEntity se = new StringEntity(gson.toJson(s));
            se.setContentType("application/json");
            post.setEntity(se);
            response = client.execute(post);
            printResponse(response);



            post = new HttpPost("http://localhost:8080//lab2-web/rest/authorize");
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("parameter1","login"));
            params.add(new BasicNameValuePair("parameter2","haslo"));
            se = new StringEntity(gson.toJson(params));
            se.setContentType("application/json");
            post.setEntity(se);
            response = client.execute(post);
            printResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printResponse(HttpResponse response) {
        String line = "";
        BufferedReader rd;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
