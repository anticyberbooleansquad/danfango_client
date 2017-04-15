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
import java.util.Calendar;
import javax.xml.stream.XMLStreamException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;

/**
 *
 * @author johnlegutko
 */
public class MovieAgency {

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

    public static ArrayList<JSONObject> parseImdbHTML(Document doc) throws IOException, XMLStreamException {
        ArrayList<String> hrefs = new ArrayList();
        ArrayList<String> imdbids = new ArrayList();
        ArrayList<JSONObject> movies = new ArrayList();
        ArrayList<String> crew = new ArrayList();

        Elements movieTitles = doc.select("div.col-title > span > span > a");

        for (Element e : movieTitles) {
            hrefs.add(e.attr("href"));
        }

        for (String s : hrefs) {
            String id = s.substring(7, 16);
            imdbids.add(id);
        }

        //System.out.println(Arrays.toString(imdbids.toArray()));
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
                        String[] actors = jsonObj.get("Actors").toString().split(", ");
                        String[] directors = jsonObj.get("Director").toString().split(", ");
                        String[] writers = jsonObj.get("Writer").toString().split(", ");

                        for (String actor : actors) {
                            crew.add(actor.replace(" ", "_"));
                        }

//                        for (String director : directors) {
//                            crew.add(director.replace(" ", "_"));
//                        }

//                        for (String writer : writers) {
//                            crew.add(writer.replace(" ", "_"));
//                        }

                    }
                }

            }
        }

        parseWikipediaHTML(crew);
        return movies;

    }

    public static void parseWikipediaHTML(ArrayList<String> crewNames) throws IOException, XMLStreamException {

        Document doc;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        
        for (String crewName : crewNames) {

            System.out.println("CREWNAME: " + crewName);
            String wikipedia = "https://en.wikipedia.org/wiki/" + crewName;

            Connection.Response response = Jsoup.connect(wikipedia)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .ignoreHttpErrors(true)
                    .execute();

            int statusCode = response.statusCode();
//            System.out.println("CODE: " + statusCode);

            if (statusCode != 404) {
                doc = Jsoup.connect(wikipedia).get();
                Element content = doc.getElementById("mw-content-text");
               System.out.println("CONTNET:" + content);

                Elements nameclass = content.getElementsByClass("fn");
                Elements bdayclass = content.getElementsByClass("bday");
                Elements ageclass = content.getElementsByClass("noprint");

                if ((nameclass != null && !nameclass.toString().equals("")) && !bdayclass.toString().equals("") && !ageclass.toString().equals("")) {
                    String name = nameclass.first().text();
                    //String name = doc.select("div.heading-holder >h1").first().text();
                    String bday = bdayclass.text();
                    String bdayYear = bday.substring(0, 4);
                    int ageNum = year-Integer.parseInt(bdayYear);
                    String age = Integer.toString(ageNum);
                    String bio = doc.select("div#mw-content-text> p").first().text();

                    ClientXMLGenerator gen = new ClientXMLGenerator();
                    gen.genCrewXMLFile(name, bday, age, bio);
                }

            }
        }

    }

}
