package pl.edu.agh.kis.soa.resources;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by krzysiek on 5/24/15.
 */
public class Client {
    public static void main(String[] args)
    {
        try {
            BufferedReader rd;
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            HttpGet getStudentRequest = new HttpGet("http://localhost:8080/lab2-web/rest/get/5");
            HttpResponse response = null;
            response = client.execute(getStudentRequest);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

            HttpPost post = new HttpPost("http://localhost:8080/lab2-web/rest/setStudent");

            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("id", "6"));
            params.add(new BasicNameValuePair("firstName", "Krzysiek"));
            params.add(new BasicNameValuePair("lastName", "Misiak"));
            post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            response = client.execute(post);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println("PARAMS "+ params);
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

            getStudentRequest = new HttpGet("http://localhost:8080/lab2-web/rest/get/6");
            response = client.execute(getStudentRequest);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
