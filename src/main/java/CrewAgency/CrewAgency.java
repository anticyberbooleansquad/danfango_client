/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrewAgency;

import static MovieAgency.MovieAgency.mergeJSONObjects;
import XMLGenerator.ClientXMLGenerator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.xml.stream.XMLStreamException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class CrewAgency {

    private ArrayList<Actor> actors;
    private ArrayList<String> imdbids;

    public CrewAgency() {
        actors = new ArrayList();
        imdbids = new ArrayList();
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<String> getImdbids() {
        return imdbids;
    }

    public void getActorIMBDIdsByMovie(JSONObject movie) throws IOException, XMLStreamException, InterruptedException {
        Document doc;
        String id = movie.get("imdb_id").toString();
        String moviePage = "http://www.imdb.com/title/" + id + "/?ref_=adv_li_tt";

        System.out.println("AFTER CREATE LINK for ID: " + id);

        doc = Jsoup.connect(moviePage).get();
        ArrayList<String> actorIds = parseImdbHTML(doc);
        System.out.println(Arrays.toString(actorIds.toArray()));

        System.out.println("AFTER PARSE HTML");

        for (String s : actorIds) {
            imdbids.add(s);
        }

    }

    public ArrayList<String> parseImdbHTML(Document doc) throws IOException, XMLStreamException, InterruptedException {
        ArrayList<String> hrefs = new ArrayList();
        ArrayList<String> actorIMDBIds = new ArrayList();

        Elements actorIds = doc.select("div#titleCast > table.cast_list > tbody > tr >td > a");
        System.out.println("ELEMENT: " + actorIds);

        for (Element e : actorIds) {
            System.out.println("HREF: " + e.attr("href"));
            hrefs.add(e.attr("href"));
        }

        for (String s : hrefs) {
            String id = s.substring(6, 15);
            System.out.println("ID: " + id);
            actorIMDBIds.add(id);
        }

        return actorIMDBIds;
    }

    public String getTMDBIds(String imdbid) throws IOException {

        String tmdbid = null;
        URL tmdbActor = new URL("https://api.themoviedb.org/3/find/" + imdbid + "?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US&external_source=imdb_id");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbActor.openStream()))) {
            String inputLine = in.readLine();
            JSONObject jsonObj = null;
            if (inputLine != null) {
                jsonObj = new JSONObject(inputLine);
                JSONArray personResults = jsonObj.getJSONArray("person_results");

                if (personResults.length() != 0) {
                    JSONObject personInfo = (JSONObject) personResults.get(0);
                    tmdbid = personInfo.get("id").toString();
                }
            }

        }
        return tmdbid;

    }

    public JSONObject getActorDetails(String tmdbid) throws IOException {

        JSONObject actor = null;
        URL tmdbActor = new URL("https://api.themoviedb.org/3/person/" + tmdbid + "?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbActor.openStream()))) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                actor = new JSONObject(inputLine);
            }

        }
        return actor;

    }

    public void updateActorsByMovie(JSONObject movie) throws IOException, XMLStreamException {

        String movieId = movie.get("imdbID").toString();

        ArrayList<String> crewMemberNames = new ArrayList();
        String[] actorNames = movie.get("Actors").toString().split(", ");
        String directorName = movie.get("Director").toString();
        String writerName = movie.get("Writer").toString();

        crewMemberNames.addAll(Arrays.asList(actorNames));
        //  crewMemberNames.add(directorName);
        //  crewMemberNames.add(writerName);

        for (String name : crewMemberNames) {
            Actor actor = getActorByName(name);
            // if the actor doesn't exist yet, we must create the java object, pull wikipedia info, and add it to the list
            if (actor == null) {
                actor = new Actor();
                actor.setName(name);
                // TODO -  will have to add a try catch at this line later rather than just throwing the exceptions above
                updateActorWikipedia(actor);
                actor.getMovieIds().add(movieId);
                actors.add(actor);
            } // if actor already exists in the database, simply add the movieId to his list of movies
            else {
                actor.getMovieIds().add(movieId);
            }
        }
    }

    public Actor getActorByName(String name) {
        for (Actor a : actors) {
            if (a.getName() != null && a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Actor> getActorsByMovieId(String movieId) {
        ArrayList<Actor> actors = new ArrayList();
        for (Actor a : actors) {
            if (a.getMovieIds().contains(movieId)) {
                actors.add(a);
            }
        }
        return actors;
    }

    public static void updateActorWikipedia(Actor actor) throws IOException, XMLStreamException {

        Document doc;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String actorName = actor.getName();
        actorName = actorName.replace(" ", "_");

        //System.out.println("CREWNAME: " + actorName);
        String wikipedia = "https://en.wikipedia.org/wiki/" + actorName;

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
            Elements nameclass = content.getElementsByClass("fn");
            Elements bdayclass = content.getElementsByClass("bday");
            Elements ageclass = content.getElementsByClass("noprint");

            if ((nameclass != null && !nameclass.toString().equals("")) && !bdayclass.toString().equals("") && !ageclass.toString().equals("")) {
                String name = nameclass.first().text();
                //String name = doc.select("div.heading-holder >h1").first().text();
                String bday = bdayclass.text();
                String bdayYear = bday.substring(0, 4);
                int ageNum = year - Integer.parseInt(bdayYear);
                String age = Integer.toString(ageNum);
                String bio = doc.select("div#mw-content-text> p").first().text();

                // update the actor object;
                actor.setBirthDate(bday);
                actor.setBiography(bio);
                actor.setAge(age);
            }

        }
    }
}
