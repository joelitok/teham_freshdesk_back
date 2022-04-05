package com.ticket.ticket.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.ticket.data.CustomFields;
import com.ticket.ticket.data.MyData;

@CrossOrigin("*")
@RestController
public class TicketController{

    
    	public List<JSONObject> response;
        //get tickets 
    	@RequestMapping(value ="/tickets" , method = RequestMethod.GET,  produces="application/json")
        public ResponseEntity<MyData> filterTickets(String apiToken, String apiEndpoint) throws JSONException, IOException, URISyntaxException {

             apiToken ="YzT7GcwChrQGTHZRTTAG";
             apiEndpoint ="https://prototypthesis.freshdesk.com";
            
        
        
        final HttpClientBuilder hcBuilder = HttpClientBuilder.create();
        final RequestBuilder reqBuilder = RequestBuilder.get();
        final RequestConfig.Builder rcBuilder = RequestConfig.custom();
        
        
        // URL object from API endpoint:
        URL url = new URL(apiEndpoint + "/api/v2/tickets");
        final String urlHost = url.getHost();
        final int urlPort = url.getPort();
        final String urlProtocol = url.getProtocol();
        reqBuilder.setUri(url.toURI());
       
        
        // Authentication:
        List<String> authPrefs = new ArrayList<>();
        authPrefs.add(AuthSchemes.BASIC);
        rcBuilder.setTargetPreferredAuthSchemes(authPrefs);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(urlHost, urlPort, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(apiToken, "X"));
        hcBuilder.setDefaultCredentialsProvider(credsProvider);
        AuthCache authCache = new BasicAuthCache();
        AuthSchemeBase authScheme = new BasicScheme();
        authCache.put(new HttpHost(urlHost, urlPort, urlProtocol), authScheme);
        HttpClientContext hccContext = HttpClientContext.create();
        hccContext.setAuthCache(authCache);
        
        HttpEntity entity = new StringEntity("", ContentType.APPLICATION_JSON.withCharset(Charset.forName("utf-8")));
        reqBuilder.setEntity(entity);
        
        // Execute:
        RequestConfig rc = rcBuilder.build();
        reqBuilder.setConfig(rc);
        
        HttpClient hc = hcBuilder.build();
        HttpUriRequest req = reqBuilder.build();
        HttpResponse response = hc.execute(req, hccContext);
        
        // Print out:
        HttpEntity body = response.getEntity();
        InputStream is = body.getContent();
        is.toString();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=br.readLine())!=null) {
            sb.append(line);
        }
        
        int response_status = response.getStatusLine().getStatusCode();
        String response_body = sb.toString();
        JSONArray response_json = new JSONArray(response_body);
 
        
        
        List<JSONObject> list = new ArrayList();
        for (int i = 0; i < response_json.length();list.add(response_json.getJSONObject(i++)));
        
        String   liste =  list.toString();
        
        MyData data = new MyData(null, liste);
 
        
        
        System.out.println("Response Status: "+ response_status);
        System.out.println("Body:\n");
        System.out.println(response_body);
       
      
        if(response_status > 400) {
            System.out.println("X-Request-Id: " + response.getFirstHeader("x-request-id").getValue());
        }
        else if(response_status==201){ 
               //For creation response_status is 201 where are as for other actions it is 200
                System.out.println("Ticket Creation Successfull");
              
                System.out.println("Location : " + response.getFirstHeader("location").getValue());
                return  new ResponseEntity<MyData>(data,  HttpStatus.CREATED);

        }
              return  new ResponseEntity<MyData>(data, HttpStatus.CREATED);
 
    }






    @RequestMapping(value ="/tickets/{id}" , method = RequestMethod.PUT, produces="application/json" )
    	 public int createTicket(String apiToken, String apiEndpoint, @PathVariable Long id, @RequestBody CustomFields results) throws  JSONException, IOException, URISyntaxException {
    		
    		  apiToken ="YzT7GcwChrQGTHZRTTAG";
              apiEndpoint ="https://prototypthesis.freshdesk.com";
              
              
            final HttpClientBuilder hcBuilder = HttpClientBuilder.create();
            final RequestBuilder reqBuilder = RequestBuilder.put();
            final RequestConfig.Builder rcBuilder = RequestConfig.custom();
            
            // URL object from API endpoint:
            URL url = new URL(apiEndpoint + "/api/v2/tickets/"+id);
            final String urlHost = url.getHost();
            final int urlPort = url.getPort();
            final String urlProtocol = url.getProtocol();
            reqBuilder.setUri(url.toURI());
            
            // Authentication:
            List<String> authPrefs = new ArrayList<>();
            authPrefs.add(AuthSchemes.BASIC);
            rcBuilder.setTargetPreferredAuthSchemes(authPrefs);
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(urlHost, urlPort, AuthScope.ANY_REALM),
                    new UsernamePasswordCredentials(apiToken, "X"));
            hcBuilder.setDefaultCredentialsProvider(credsProvider);
            AuthCache authCache = new BasicAuthCache();
            AuthSchemeBase authScheme = new BasicScheme();
            authCache.put(new HttpHost(urlHost, urlPort, urlProtocol), authScheme);
            HttpClientContext hccContext = HttpClientContext.create();
            hccContext.setAuthCache(authCache);
          
            
            // Body:
           // final String jsonBody = "{\"custom_fields\": {\"cf_diagnose\": \"Un diagnostique\",\"cf_kommentaren\": \"Un comment\" }}";  
    final String jsonBody = "{\"custom_fields\": {\"cf_diagnose\": \""+results.getCf_diagnose()+"\",\"cf_kommentaren\": \""+results.getCf_kommentaren()+"\" }}";
    HttpEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON.withCharset(Charset.forName("utf-8")));
            reqBuilder.setEntity(entity);
            
            // Execute:
            RequestConfig rc = rcBuilder.build();
            reqBuilder.setConfig(rc);
            
            HttpClient hc = hcBuilder.build();
            HttpUriRequest req = reqBuilder.build();
            HttpResponse response = hc.execute(req, hccContext);
            
            // Print out:
            HttpEntity body = response.getEntity();
            InputStream is = body.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            int response_status = response.getStatusLine().getStatusCode();
            String response_body = sb.toString();

            System.out.println("Response Status: "+ response_status);
            System.out.println("Body:\n");
            System.out.println(response_body);
            if(response_status > 400) {
                System.out.println("X-Request-Id: " + response.getFirstHeader("x-request-id").getValue());
            }
            else if(response_status==201){ 
                //For creation response_status is 201 where are as for other actions it is 200
                try{
                    System.out.println("Ticket Creation Successfull");
                    //Creating JSONObject for the response string
                    JSONObject response_json = new JSONObject(sb.toString());
                    System.out.println("Ticket ID: " + response_json.get("id"));
                    System.out.println("Location : " + response.getFirstHeader("location").getValue());
                }
                catch(JSONException e){
                    System.out.println("Error in JSON Parsing\n :"+ e);
                }
            }
            return response_status;
        }






//get tickets infos to json format
@RequestMapping(value ="/ticketsinfos" , method = RequestMethod.GET,  produces="application/json")
public String ticketsinfos(String apiToken, String apiEndpoint) throws JSONException, IOException, URISyntaxException {

     apiToken ="YzT7GcwChrQGTHZRTTAG";
     apiEndpoint ="https://prototypthesis.freshdesk.com";
    


final HttpClientBuilder hcBuilder = HttpClientBuilder.create();
final RequestBuilder reqBuilder = RequestBuilder.get();
final RequestConfig.Builder rcBuilder = RequestConfig.custom();


// URL object from API endpoint:
URL url = new URL(apiEndpoint + "/api/v2/tickets");
final String urlHost = url.getHost();
final int urlPort = url.getPort();
final String urlProtocol = url.getProtocol();
reqBuilder.setUri(url.toURI());


// Authentication:
List<String> authPrefs = new ArrayList<>();
authPrefs.add(AuthSchemes.BASIC);
rcBuilder.setTargetPreferredAuthSchemes(authPrefs);
CredentialsProvider credsProvider = new BasicCredentialsProvider();
credsProvider.setCredentials(
        new AuthScope(urlHost, urlPort, AuthScope.ANY_REALM),
        new UsernamePasswordCredentials(apiToken, "X"));
hcBuilder.setDefaultCredentialsProvider(credsProvider);
AuthCache authCache = new BasicAuthCache();
AuthSchemeBase authScheme = new BasicScheme();
authCache.put(new HttpHost(urlHost, urlPort, urlProtocol), authScheme);
HttpClientContext hccContext = HttpClientContext.create();
hccContext.setAuthCache(authCache);

HttpEntity entity = new StringEntity("", ContentType.APPLICATION_JSON.withCharset(Charset.forName("utf-8")));
reqBuilder.setEntity(entity);

// Execute:
RequestConfig rc = rcBuilder.build();
reqBuilder.setConfig(rc);

HttpClient hc = hcBuilder.build();
HttpUriRequest req = reqBuilder.build();
HttpResponse response = hc.execute(req, hccContext);

// Print out:
HttpEntity body = response.getEntity();
InputStream is = body.getContent();
is.toString();
BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));
String line;
StringBuilder sb = new StringBuilder();
while((line=br.readLine())!=null) {
    sb.append(line);
}

int response_status = response.getStatusLine().getStatusCode();
String response_body = sb.toString();
JSONArray response_json = new JSONArray(response_body);



System.out.println("Response Status: "+ response_status);
System.out.println("Body:\n");
System.out.println(response_body);


if(response_status > 400) {
    System.out.println("X-Request-Id: " + response.getFirstHeader("x-request-id").getValue());
}
else if(response_status==201){ 
       //For creation response_status is 201 where are as for other actions it is 200
        System.out.println("Ticket Creation Successfull");
      
        System.out.println("Location : " + response.getFirstHeader("location").getValue());
        return  response_body;

}
      return  response_body;

}





}
