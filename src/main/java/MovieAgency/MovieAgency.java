/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MovieAgency;

import XMLGenerator.ClientXMLGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.*;
import java.io.*;
import javax.xml.stream.XMLStreamException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author johnlegutko
 */
public class MovieAgency {
    
    private static ArrayList<String> crewMembers;

    public ArrayList<JSONObject> getMovies() throws IOException, XMLStreamException {
        Document doc;
        ArrayList<JSONObject> movies;
        String year2016 = "http://www.imdb.com/search/title?year=2016,2016&title_type=feature&sort=moviemeter,asc&view=simple";
        String year2017 = "http://www.imdb.com/search/title?year=2017,2017&title_type=feature&sort=moviemeter,asc&view=simple";
        String year2018 = "http://www.imdb.com/search/title?year=2018,2018&title_type=feature&sort=moviemeter,asc&view=simple";

        doc = Jsoup.connect(year2016).get();
        movies = parseImdbHTML(doc);
        doc = Jsoup.connect(year2017).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2018).get();
        movies.addAll(parseImdbHTML(doc));

        return movies;

    }
    
    /**
     * This method takes in an imdb popular movie page doc and uses the omdb api to return 
     * a list of movie JsonObjects
     * @param doc
     * @return
     * @throws IOException
     * @throws XMLStreamException 
     */
    public static ArrayList<JSONObject> parseImdbHTML(Document doc) throws IOException, XMLStreamException {
        ArrayList<String> hrefs = new ArrayList();
        ArrayList<String> imdbids = new ArrayList();
        ArrayList<JSONObject> movies = new ArrayList();
        

        Elements movieTitles = doc.select("div.col-title > span > span > a");

        for (Element e : movieTitles) {
            hrefs.add(e.attr("href"));
        }

        for (String s : hrefs) {
            String id = s.substring(7, 16);
            imdbids.add(id);
        }

        // at this point we have all of the imdb movie ids in the list 
        // now need to look them up using omdbapi 
        
        for (String id : imdbids) {
            URL omdb = new URL("http://www.omdbapi.com/?i=" + id);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(omdb.openStream()))) {
                String inputLine = in.readLine();
                JSONObject jsonObj = null;
                if (inputLine != null) {
                    //System.out.println(inputLine);
                    jsonObj = new JSONObject(inputLine);

                    if (!jsonObj.isNull("Title")) {
                        movies.add(jsonObj);
                    }
                }

            }
        }
        return movies;
    }
    
}