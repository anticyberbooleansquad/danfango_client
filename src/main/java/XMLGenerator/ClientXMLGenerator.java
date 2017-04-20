/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLGenerator;

/**
 *
 * @author johnlegutko
 */
import CrewAgency.Actor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientXMLGenerator {

    public void genMovieXMLFile(ArrayList<JSONObject> movies) throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

        xMLStreamWriter.writeStartDocument();
        xMLStreamWriter.writeStartElement("movies"); //start outer movies

        for (JSONObject movie : movies) {

            xMLStreamWriter.writeStartElement("movie");

            xMLStreamWriter.writeStartElement("title");
            xMLStreamWriter.writeCharacters(movie.get("title").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("rated");
            xMLStreamWriter.writeCharacters(movie.get("rated").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("released");
            xMLStreamWriter.writeCharacters(movie.get("release_date").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("tmbdID");
            xMLStreamWriter.writeCharacters(movie.get("id").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("imdbID");
            xMLStreamWriter.writeCharacters(movie.get("imdb_id").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("imdbRating");
            xMLStreamWriter.writeCharacters(movie.get("vote_average").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("genre");
            xMLStreamWriter.writeCharacters(movie.get("genre").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("plot");
            xMLStreamWriter.writeCharacters(movie.get("overview").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("poster");
            xMLStreamWriter.writeCharacters(movie.get("poster_path").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("backdrop");
            xMLStreamWriter.writeCharacters(movie.get("backdrop_path").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("runtime");
            xMLStreamWriter.writeCharacters(movie.get("runtime").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("actors");
            xMLStreamWriter.writeCharacters(movie.get("actors").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("director");
            xMLStreamWriter.writeCharacters(movie.get("director").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("writer");
            xMLStreamWriter.writeCharacters(movie.get("writer").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("trailers");

            JSONArray trailers = movie.getJSONArray("results");
            for (int i = 0; i < trailers.length(); i++) {
                JSONObject video = (JSONObject) trailers.get(i);

                xMLStreamWriter.writeStartElement("trailer");

                xMLStreamWriter.writeStartElement("id");
                xMLStreamWriter.writeCharacters(video.getString("id"));
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeStartElement("key");
                xMLStreamWriter.writeCharacters(video.getString("key"));
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeEndElement();

            }
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeEndElement();

        }

        xMLStreamWriter.writeEndElement(); //end outer movies

        xMLStreamWriter.writeEndDocument();
        xMLStreamWriter.flush();
        xMLStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();
        stringWriter.close();
        System.out.println(xmlString);

        FileWriter fw = new FileWriter("movieAgency3.xml");
        fw.write(xmlString);
        fw.close();

    }

    public void genTheatreXMLFile(ArrayList<JSONObject> theatres) throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

        xMLStreamWriter.writeStartDocument();
        xMLStreamWriter.writeStartElement("theatres"); //start outer movies

        for (JSONObject theatre : theatres) {

            xMLStreamWriter.writeStartElement("theatre");

            xMLStreamWriter.writeStartElement("agencyID");
            xMLStreamWriter.writeCharacters(theatre.get("theatreId").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("name");
            xMLStreamWriter.writeCharacters(theatre.get("name").toString());
            xMLStreamWriter.writeEndElement();

            JSONObject locationObj = theatre.getJSONObject("location");
            JSONObject addressObj = locationObj.getJSONObject("address");

            xMLStreamWriter.writeStartElement("address");
            xMLStreamWriter.writeCharacters(addressObj.get("street").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("city");
            xMLStreamWriter.writeCharacters(addressObj.get("city").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("state");
            xMLStreamWriter.writeCharacters(addressObj.get("state").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("zipcode");
            xMLStreamWriter.writeCharacters(addressObj.get("postalCode").toString());
            xMLStreamWriter.writeEndElement();

            ///////////
            xMLStreamWriter.writeEndElement();

        }

        xMLStreamWriter.writeEndElement(); //end outer theatres

        xMLStreamWriter.writeEndDocument();
        xMLStreamWriter.flush();
        xMLStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();
        stringWriter.close();
        System.out.println(xmlString);

        FileWriter fw = new FileWriter("theatreAgency.xml");
        fw.write(xmlString);
        fw.close();

    }

    /**
     * This method creates a single theatre-showing section on the showings xml
     *
     * @param movies
     * @throws XMLStreamException
     * @throws IOException
     */
    public void genShowingXMLFile(ArrayList<JSONArray> theatreShowingsList) throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

        xMLStreamWriter.writeStartDocument();
        xMLStreamWriter.writeStartElement("showings"); //start outer showings

        for (int i = 0; i < theatreShowingsList.size(); i++) {
            JSONArray theatreShowings = theatreShowingsList.get(i);

            for (int j = 0; j < theatreShowings.length(); j++) {

                JSONObject movie = (JSONObject) theatreShowings.get(j);
                String moviename = movie.get("title").toString();
                //int releaseYear = (int) movie.get("releaseYear");
                //String releaseDate = movie.get("releaseDate").toString();

                JSONArray showtimes = movie.getJSONArray("showtimes");

                if (j == 0) {
                    JSONObject showing = (JSONObject) showtimes.get(0);
                    JSONObject theatreObj = showing.getJSONObject("theatre");
                    String theatreId = theatreObj.get("id").toString();
                    String theatreName = theatreObj.get("name").toString();

                    xMLStreamWriter.writeStartElement("theatre");
                    xMLStreamWriter.writeAttribute("id", theatreId);
                    xMLStreamWriter.writeAttribute("name", theatreName);
                }

                for (int k = 0; k < showtimes.length(); k++) {
                    xMLStreamWriter.writeStartElement("showtime");

                    xMLStreamWriter.writeStartElement("moviename");
                    xMLStreamWriter.writeCharacters(moviename);
                    xMLStreamWriter.writeEndElement();

                    xMLStreamWriter.writeStartElement("datetime");
                    JSONObject datetime = (JSONObject) showtimes.get(k);
                    xMLStreamWriter.writeCharacters(datetime.get("dateTime").toString());
                    xMLStreamWriter.writeEndElement();

                    xMLStreamWriter.writeEndElement(); //close showtime    
                }
                // next moving on to next movie object
            }
            //close with theatre tag because looped through all movies and listed all showtimes for each movie
            xMLStreamWriter.writeEndElement(); //close theatre

        }
        xMLStreamWriter.writeEndElement(); //close showings

        xMLStreamWriter.writeEndDocument();
        xMLStreamWriter.flush();
        xMLStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();
        stringWriter.close();
        System.out.println(xmlString);

        FileWriter fw = new FileWriter("showingAgency.xml");
        fw.write(xmlString);
        fw.close();

    }

    public void genCrewXMLFile(HashSet<Actor> actors) throws XMLStreamException, IOException {
        Iterator iter = actors.iterator();

        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

        xMLStreamWriter.writeStartDocument();
        xMLStreamWriter.writeStartElement("actors"); //start outer movies

        while (iter.hasNext()) {
            Actor actor = (Actor) iter.next();
            String name = actor.getName();
            String dob = actor.getBirthDate();
            String age = actor.getAge();
            String bio = actor.getBiography();
            String poster = actor.getPoster();

            ArrayList<String> imdbIDs = actor.getMovieIds();

            xMLStreamWriter.writeStartElement("actor");

            xMLStreamWriter.writeStartElement("name");
            xMLStreamWriter.writeCharacters(name);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("birthday");
            xMLStreamWriter.writeCharacters(dob);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("age");
            xMLStreamWriter.writeCharacters(age);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("biography");
            xMLStreamWriter.writeCharacters(bio);
            xMLStreamWriter.writeEndElement();
            
            xMLStreamWriter.writeStartElement("poster");
            xMLStreamWriter.writeCharacters(poster);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("movies");
            for (String id : imdbIDs) {
                xMLStreamWriter.writeStartElement("movie");
                xMLStreamWriter.writeCharacters(id);
                xMLStreamWriter.writeEndElement();
            }
            xMLStreamWriter.writeEndElement();

            ///////////
            xMLStreamWriter.writeEndElement();
        }
        xMLStreamWriter.writeEndElement(); //end outer actors

        xMLStreamWriter.writeEndDocument();
        xMLStreamWriter.flush();
        xMLStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();
        stringWriter.close();
        System.out.println(xmlString);

        FileWriter fw = new FileWriter("actorAgency2.xml");
        fw.write(xmlString);
        fw.close();

    }
}
