/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheatreAgency;

import XMLGenerator.ClientXMLGenerator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author johnlegutko
 */
public class TheatreAgency {
    private ArrayList<String> theatreIds;
    
    public TheatreAgency(){
        theatreIds = new ArrayList();
    }
    
    /**
     * Return list of JSONObject theatres using an API with radius of 100 of Lindenhurst, NY, 11757. 
     * @return
     * @throws IOException
     * @throws XMLStreamException 
     */
    public ArrayList<JSONObject> getTheatres() throws IOException, XMLStreamException {
        ArrayList<JSONObject> theatres = new ArrayList();
       
        URL theatreAPI = new URL("http://data.tmsapi.com/v1.1/theatres?zip=11757&radius=5&units=mi&numTheatres=1000&api_key=7k72q6prdt4z44t764r3jw7t");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(theatreAPI.openStream()))) {
            String inputLine = in.readLine();
            JSONArray theatresJSON = null;
            if (inputLine != null) {
                theatresJSON = new JSONArray(inputLine);

                for (int i = 0; i < theatresJSON.length(); i++) {
                    JSONObject jsonObj = theatresJSON.getJSONObject(i);
                    theatres.add(jsonObj);
                    String theatreId = jsonObj.get("theatreId").toString();
                    theatreIds.add(theatreId);
                }
                System.out.println(Arrays.toString(theatres.toArray()));
                System.out.println(Arrays.toString(theatreIds.toArray()));
            }
        }
        return theatres;
    }


    public ArrayList<JSONObject> getShowingsForTheatres(String date) throws IOException, XMLStreamException, InterruptedException {

        ArrayList<JSONObject> movies = new ArrayList();

//        theatreIds = new ArrayList<String>();
//        theatreIds.add("2935");
//        theatreIds.add("7587");
//        theatreIds.add("9692");
//        theatreIds.add("5981");
        for (String id : theatreIds) {
            Thread.sleep(500);

            URL theatreAPI = new URL("https://data.tmsapi.com/v1.1/theatres/" + id + "/showings?startDate=" + date + "&api_key=7k72q6prdt4z44t764r3jw7t");
            HttpURLConnection connection = (HttpURLConnection) theatreAPI.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            System.out.println("CODE for " + id + " " + code);

            if (code != 403) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(theatreAPI.openStream()))) {
                    String inputLine = in.readLine();
                    JSONArray jsonArray = null;
                    if (inputLine != null) {
                        System.out.println("Theatre " + id + " " + inputLine);
                        jsonArray = new JSONArray(inputLine);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            movies.add(jsonObj);
                        }

                    }

                }
            }
        }

        return movies;

    }

}
