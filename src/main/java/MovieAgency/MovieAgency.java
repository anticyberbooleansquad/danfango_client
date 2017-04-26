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
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;

/**
 *
 * @author johnlegutko
 */
public class MovieAgency {

    public ArrayList<JSONObject> getMovies() throws IOException, XMLStreamException, InterruptedException {
        Document doc;
        ArrayList<JSONObject> movies;
        String year2000 = "http://www.imdb.com/search/title?year=2000,2000&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2001 = "http://www.imdb.com/search/title?year=2001,2001&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2002 = "http://www.imdb.com/search/title?year=2002,2002&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2003 = "http://www.imdb.com/search/title?year=2003,2003&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2004 = "http://www.imdb.com/search/title?year=2004,2004&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2005 = "http://www.imdb.com/search/title?year=2005,2005&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2006 = "http://www.imdb.com/search/title?year=2006,2006&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2007 = "http://www.imdb.com/search/title?year=2007,2007&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2008 = "http://www.imdb.com/search/title?year=2008,2008&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2009 = "http://www.imdb.com/search/title?year=2009,2009&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2010 = "http://www.imdb.com/search/title?year=2010,2010&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2011 = "http://www.imdb.com/search/title?year=2011,2011&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2012 = "http://www.imdb.com/search/title?year=2012,2012title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2013 = "http://www.imdb.com/search/title?year=2013,2013&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";      
        String year2014 = "http://www.imdb.com/search/title?year=2014,2014&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2015 = "http://www.imdb.com/search/title?year=2015,2015&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2016 = "http://www.imdb.com/search/title?year=2016,2016&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2017_1 = "http://www.imdb.com/search/title?year=2017,2017&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";
        String year2017_2 = "http://www.imdb.com/search/title?year=2017,2017&title_type=feature&sort=moviemeter,asc&page=2&view=simple&ref_=adv_prv";
        String year2017_3 = "http://www.imdb.com/search/title?year=2017,2017&title_type=feature&sort=moviemeter,asc&page=3&view=simple&ref_=adv_prv";
        String year2018 = "http://www.imdb.com/search/title?year=2018,2018&title_type=feature&sort=moviemeter,asc&page=1&view=simple&ref_=adv_prv";

        doc = Jsoup.connect(year2000).get();
        movies = parseImdbHTML(doc);
        doc = Jsoup.connect(year2001).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2002).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2003).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2004).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2005).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2006).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2007).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2008).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2009).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2010).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2011).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2012).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2013).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2014).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2015).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2016).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2017_1).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2017_2).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2017_3).get();
        movies.addAll(parseImdbHTML(doc));
        doc = Jsoup.connect(year2018).get();
        movies.addAll(parseImdbHTML(doc));
        
        return movies;
        
        // IMAGE LINK https://image.tmdb.org/t/p/w500 + poster.jpg

    }

    /**
     * This method takes in an imdb popular movie page doc and uses the omdb api
     * to return a list of movie JsonObjects
     *
     * @param doc
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    public ArrayList<JSONObject> parseImdbHTML(Document doc) throws IOException, XMLStreamException, InterruptedException {
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
            Thread.sleep(1000);

            String tmdbid = getTMDBID(id);

            if (tmdbid != null) {
                JSONObject movie = getMovieDetails(tmdbid);
                JSONObject trailers = getMovieTrailers(tmdbid);
                JSONObject omdbInfo = getOMDBMovieInfo(id);
                JSONObject merge1 = mergeJSONObjects(movie, trailers);
                JSONObject merge2 = mergeJSONObjects(merge1, omdbInfo);
                movies.add(merge2);
                System.out.println(merge2.get("title"));
            }
        }

        System.out.println(Arrays.toString(movies.toArray()));

        return movies;
    }

    public String getTMDBID(String imdbid) throws IOException {

        String tmdbid = null;
        URL tmdbMovie = new URL("https://api.themoviedb.org/3/find/" + imdbid + "?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US&external_source=imdb_id");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbMovie.openStream()))) {
            String inputLine = in.readLine();
            JSONObject jsonObj = null;
            if (inputLine != null) {
                jsonObj = new JSONObject(inputLine);
                JSONArray movieResults = jsonObj.getJSONArray("movie_results");

                if (movieResults.length() != 0) {
                    JSONObject movieInfo = (JSONObject) movieResults.get(0);
                    tmdbid = movieInfo.get("id").toString();
                }
            }

        }
        return tmdbid;

    }

    public JSONObject getMovieDetails(String tmdbid) throws IOException {

        JSONObject movie = null;
        URL tmdbMovie = new URL("https://api.themoviedb.org/3/movie/" + tmdbid + "?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbMovie.openStream()))) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                movie = new JSONObject(inputLine);
            }

        }
        return movie;

    }

    public JSONObject getMovieTrailers(String tmdbid) throws IOException {

        JSONObject trailers = null;
        URL tmdbMovie = new URL("https://api.themoviedb.org/3/movie/" + tmdbid + "/videos?api_key=0910d4231d712cc65022c17dc1da4a68&language=en-US");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tmdbMovie.openStream()))) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                trailers = new JSONObject(inputLine);
            }

        }
        return trailers;

    }

    public JSONObject getOMDBMovieInfo(String imdbid) throws IOException {

        JSONObject movie = null;
        String actors = "";
        String genre = "";
        String rated = "";
        String director = "";
        String writer = "";

        URL omdb = new URL("http://www.omdbapi.com/?i=" + imdbid);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(omdb.openStream()))) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                movie = new JSONObject(inputLine);

                if (!movie.isNull("Title")) {
                    actors = movie.get("Actors").toString();
                    genre = movie.get("Genre").toString();
                    rated = movie.get("Rated").toString();
                    director = movie.get("Director").toString();
                    writer = movie.get("Writer").toString();
                }

            }

        }
        

        JSONObject movieInfo = new JSONObject();
        movieInfo.put("actors", actors);
        movieInfo.put("genre", genre);
        movieInfo.put("rated", rated);
        movieInfo.put("director", director);
        movieInfo.put("writer", writer);

        return movieInfo;

    }

    public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON = new JSONObject();
        try {
            mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
            for (String crunchifyKey : JSONObject.getNames(json2)) {
                mergedJSON.put(crunchifyKey, json2.get(crunchifyKey));
            }

        } catch (JSONException e) {
            throw new RuntimeException("JSON Exception" + e);
        }
        return mergedJSON;
    }
}
