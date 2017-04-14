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

    public void genXMLFile() throws IOException, XMLStreamException {

        ArrayList<JSONObject> theatres = getTheatres();
        ArrayList<JSONObject> showings = getShowingsForTheatre();


        ClientXMLGenerator generator = new ClientXMLGenerator();
        generator.genTheatreXMLFile(theatres);
        generator.genShowingXMLFile(showings);
    }

    public ArrayList<JSONObject> getTheatres() throws IOException, XMLStreamException {
        ArrayList<JSONObject> theatres = new ArrayList();
        ArrayList<String> theatreIds = new ArrayList();

        URL theatreAPI = new URL("http://data.tmsapi.com/v1.1/theatres?zip=11757&radius=100&units=mi&numTheatres=1000&api_key=7k72q6prdt4z44t764r3jw7t");
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
                // at this point have theatres full 
                //System.out.println(Arrays.toString(theatres.toArray()));
                //System.out.println(Arrays.toString(theatreIds.toArray()));

            }

        }

        return theatres;

    }

    public ArrayList<String> getTheatreIds() throws IOException, XMLStreamException {
        ArrayList<JSONObject> theatres = new ArrayList();
        ArrayList<String> theatreIds = new ArrayList();

        URL theatreAPI = new URL("http://data.tmsapi.com/v1.1/theatres?zip=11757&radius=100&units=mi&numTheatres=1000&api_key=7k72q6prdt4z44t764r3jw7t");
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
                // at this point have theatres full 
                //System.out.println(Arrays.toString(theatres.toArray()));
                //System.out.println(Arrays.toString(theatreIds.toArray()));

            }

        }

        return theatreIds;

    }

    public ArrayList<JSONObject> getShowingsForTheatre() throws IOException, XMLStreamException {

        ArrayList<String> theatreIds = getTheatreIds();
        ArrayList<JSONObject> showings = new ArrayList();

        for (String id : theatreIds) {
            URL theatreAPI = new URL("http://data.tmsapi.com/v1.1/theatres/" + id + "/showings?startDate=2017-04-14&numDays=7&imageSize=Md&imageText=true&api_key=7k72q6prdt4z44t764r3jw7t");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(theatreAPI.openStream()))) {
                String inputLine = in.readLine();
                JSONArray showingsJSON = null;
                if (inputLine != null) {
                    System.out.println(inputLine);
                    showingsJSON = new JSONArray(inputLine);

                    if (!showingsJSON.isNull(0)) {
                        for (int i = 0; i < showingsJSON.length(); i++) {
                            JSONObject jsonObj = showingsJSON.getJSONObject(i);
                            showings.add(jsonObj);
                        }

                    }

                }

            }
        }

        return showings;

    }

}
