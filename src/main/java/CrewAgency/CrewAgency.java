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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
    private ArrayList<String> tmdbids;

    public CrewAgency() {
        actors = new ArrayList();
        imdbids = new ArrayList();
        tmdbids = new ArrayList();
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<String> getImdbids() {
        return imdbids;
    }

    public ArrayList<String> getTmdbids() {
        return tmdbids;
    }

    public void updateActorsByMovie(JSONObject movie) throws IOException, XMLStreamException, InterruptedException {
        Document doc;
        String movieId = movie.get("imdb_id").toString();
        String moviePage = "http://www.imdb.com/title/" + movieId + "/?ref_=adv_li_tt";

        //System.out.println("AFTER CREATE LINK for ID: " + id);
        doc = Jsoup.connect(moviePage).get();
        ArrayList<String> actorIdsTemp = parseImdbHTML(doc);
        HashSet<String> actorIds = new HashSet();
        for (String s : actorIdsTemp) {
            actorIds.add(s);
        }
        //System.out.println(movie.get("title") + ": Actors " + Arrays.toString(actorIds.toArray()));

        Iterator<String> it = actorIds.iterator();
        System.out.println("HASH SIZE: " + actorIds.size());

        while (it.hasNext()) {
            String imdbActorId = it.next();
            Thread.sleep(200);
            //System.out.println("IMDB: " + imdbActorId);
            imdbids.add(imdbActorId);
            String tmdbActorId = getTMDBId(imdbActorId);
            //System.out.println("TMDB: " + tmdbActorId);
            tmdbids.add(tmdbActorId);
            // lets see if there is actor in actors "database" with this id already
            // if there is simply retrieve this object and add this movieId to that actor object
            // if there isn't we must:
            // 1. create the actor
            // 2. add this movieId to that actor object
            // 3. add this actor object to the actors "database"
            if (tmdbActorId != null) {
                Actor actor = getActorByImdbId(imdbActorId);
                if (actor == null) {
                    actor = new Actor();
                    actor.setImdbId(imdbActorId);
                    actor.setTmdbId(tmdbActorId);
                    updateActorTMDB(actor);
                    actor.getMovieIds().add(movieId);
                    actors.add(actor);
                } else {
                    actor.getMovieIds().add(movieId);
                }
            }
        }

    }

    public ArrayList<String> parseImdbHTML(Document doc) throws IOException, XMLStreamException, InterruptedException {
        ArrayList<String> hrefs = new ArrayList();
        ArrayList<String> actorIMDBIds = new ArrayList();
        Elements actorIds = doc.select("div#titleCast > table.cast_list > tbody > tr >td > a");
        for (Element e : actorIds) {
            hrefs.add(e.attr("href"));
        }
        for (String s : hrefs) {
            String id = s.substring(6, 15);
            actorIMDBIds.add(id);
        }
        return actorIMDBIds;
    }

    public String getTMDBId(String imdbid) throws IOException, InterruptedException {

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

    public void updateActorTMDB(Actor actor) throws IOException, InterruptedException {
        System.out.println("ACTORID: "+ actor.getTmdbId());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Thread.sleep(500);
        String tmdbId = actor.getTmdbId();
        JSONObject jsonActor = null;
        URL tmdbActor = new URL("https://api.themoviedb.org/3/person/" + tmdbId + "?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbActor.openStream()))) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                jsonActor = new JSONObject(inputLine);

             
              
                if (!jsonActor.isNull("name")) {
                    String name = jsonActor.getString("name");
                    actor.setName(name);

                }
                if (!jsonActor.isNull("birthday")) {
                    String birthdate = jsonActor.getString("birthday");
                    String bdayYear = birthdate;
                    if (bdayYear.length() > 0) {
                        bdayYear = bdayYear.substring(0, 4);
                        int ageNum = year - Integer.parseInt(bdayYear);
                        String age = Integer.toString(ageNum);
                        actor.setAge(age);
                    }
                    actor.setBirthDate(birthdate);

                }
                if (!jsonActor.isNull("biography")) {
                    String biography = jsonActor.getString("biography");
                    actor.setBiography(biography);

                }
                if (!jsonActor.isNull("profile_path")) {
                    String profile_path = jsonActor.getString("profile_path");
                    actor.setPoster(profile_path);

                }

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

    public Actor getActorByImdbId(String id) {
        for (Actor a : actors) {
            if (a.getImdbId() != null && a.getImdbId().equals(id)) {
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
