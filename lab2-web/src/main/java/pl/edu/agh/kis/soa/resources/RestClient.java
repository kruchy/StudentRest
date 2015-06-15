package pl.edu.agh.kis.soa.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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



public class RestClient {


    public static void main(String[] args) {

            Client client = Client.create();
            WebResource webResource;
            ClientResponse response;
            webResource = client.resource("http://localhost:8080/lab2-web/rest/rest/get/15");
            response = webResource.get(ClientResponse.class);
            printResponse(response);

            webResource = client.resource("http://localhost:8080/lab2-web/rest/rest/addStudent/Andrzej/Golota");
            webResource.post();


            webResource = client.resource("http://localhost:8080/lab2-web/rest/rest/delStudent/5");
            webResource.queryParam("id","5");
            webResource.delete();


    }

    public static void printResponse(ClientResponse response) {
        String line = "";
        System.out.println(response.getEntity(String.class));
    }

    public static void handleError(ClientResponse response) throws HttpException {
                if(response.getStatus() != 200) throw new HttpException(response.toString());
    }

}
