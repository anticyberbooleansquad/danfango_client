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
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
            xMLStreamWriter.writeCharacters(movie.get("Title").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("year");
            xMLStreamWriter.writeCharacters(movie.get("Year").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("rated");
            xMLStreamWriter.writeCharacters(movie.get("Rated").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("released");
            xMLStreamWriter.writeCharacters(movie.get("Released").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("imdbID");
            xMLStreamWriter.writeCharacters(movie.get("imdbID").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("imdbRating");
            xMLStreamWriter.writeCharacters(movie.get("imdbRating").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("genre");
            xMLStreamWriter.writeCharacters(movie.get("Genre").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("plot");
            xMLStreamWriter.writeCharacters(movie.get("Plot").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("poster");
            xMLStreamWriter.writeCharacters(movie.get("Poster").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("runtime");
            xMLStreamWriter.writeCharacters(movie.get("Runtime").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("actors");
            xMLStreamWriter.writeCharacters(movie.get("Actors").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("director");
            xMLStreamWriter.writeCharacters(movie.get("Director").toString());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("writer");
            xMLStreamWriter.writeCharacters(movie.get("Writer").toString());
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

        FileWriter fw = new FileWriter("movieAgency.xml");
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

    public void genShowingXMLFile(ArrayList<JSONObject> movies) throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

        xMLStreamWriter.writeStartDocument();
        xMLStreamWriter.writeStartElement("showings"); //start outer showings
        
        for (int i = 0; i < movies.size(); i++) {
            JSONObject movie = movies.get(i);

            String moviename = movie.get("title").toString();
            int releaseYear = (int) movie.get("releaseYear");
            String releaseDate = movie.get("releaseDate").toString();

            JSONArray showtimes = movie.getJSONArray("showtimes");
            JSONObject showing = (JSONObject) showtimes.get(0);
            JSONObject theatreObj = showing.getJSONObject("theatre");

            String theatreId = theatreObj.get("id").toString();
            String theatreName = theatreObj.get("name").toString();

            if (i == 0) {
                xMLStreamWriter.writeStartElement("theatre");
                xMLStreamWriter.writeAttribute("id", theatreId);
                xMLStreamWriter.writeAttribute("name", theatreName);
            }

            for (int j = 0; j < showtimes.length(); j++) {
                xMLStreamWriter.writeStartElement("showing");

                xMLStreamWriter.writeStartElement("moviename");
                xMLStreamWriter.writeCharacters(moviename);
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeStartElement("datetime");
                JSONObject showtime = (JSONObject) showtimes.get(j);

                xMLStreamWriter.writeCharacters(showtime.get("dateTime").toString());
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeEndElement(); //end single showing

            }

            xMLStreamWriter.writeEndElement(); //end single theatre

        }

        xMLStreamWriter.writeEndElement(); //end outer showings

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

}
