/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import MovieAgency.MoviePush;
import MovieAgency.MovieAgency;
import TheatreAgency.TheatreAgency;
import XMLGenerator.ClientXMLGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.json.JSONObject;

/**
 *
 * @author johnlegutko
 */
public class AgencyController {

    public static void main(String[] args) throws IOException, XMLStreamException {
        System.setProperty("http.agent", "Chrome");

        ClientXMLGenerator generator = new ClientXMLGenerator();

        MovieAgency ms = new MovieAgency();
        ArrayList<JSONObject> movies = ms.getMovies();
        generator.genMovieXMLFile(movies);

        TheatreAgency ts = new TheatreAgency();
        ArrayList<JSONObject> theatres = ts.getTheatres();
        ArrayList<JSONObject> showings = ts.getShowingsForTheatre();

        generator.genTheatreXMLFile(theatres);
        //generator.genShowingXMLFile(showings);
        System.out.println(Arrays.toString(showings.toArray()));

    }

}
