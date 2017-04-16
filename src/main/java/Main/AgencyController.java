/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import CrewAgency.CrewAgency;
import CrewAgency.Actor;
import MovieAgency.MoviePush;
import MovieAgency.MovieAgency;
import TheatreAgency.TheatreAgency;
import XMLGenerator.ClientXMLGenerator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import org.json.JSONObject;

/**
 *
 * @author johnlegutko
 */
public class AgencyController {

    public static void main(String[] args) throws IOException, XMLStreamException, InterruptedException {
        System.setProperty("http.agent", "Chrome");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = format1.format(cal.getTime());
        System.out.println("TODAYS DATE: " + formattedDate);

        ClientXMLGenerator generator = new ClientXMLGenerator();

        ///////////// MOVIES ///////////////
//        MovieAgency ms = new MovieAgency();
//        ArrayList<JSONObject> movies = ms.getMovies();
        //generator.genMovieXMLFile(movies);

        ///////////// ACTORS ///////////////
//        CrewAgency as = new CrewAgency();
//        for (JSONObject movie : movies) {
//            as.updateActorsByMovie(movie);
//        }
//        ArrayList<Actor> ourDatabaseActors = as.getActors();
//        System.out.println(Arrays.toString(ourDatabaseActors.toArray()));
//
//        HashSet<Actor> actors = new HashSet();
//        for (Actor a : ourDatabaseActors) {
//            actors.add(a);
//            System.out.println("Added actor: " + a.getName());
//        }

        //generator.genCrewXMLFile(actors);

        ///////////// THEATRES  ///////////////
        TheatreAgency ts = new TheatreAgency();
        ArrayList<JSONObject> theatres = ts.getTheatres();
        ///////////// SHOWINGS ///////////////
        ArrayList<JSONObject> showings = ts.getShowingsForTheatres(formattedDate);
        generator.genTheatreXMLFile(theatres);
        generator.genShowingXMLFile(showings);
        //System.out.println(Arrays.toString(showings.toArray()));

    }

}
