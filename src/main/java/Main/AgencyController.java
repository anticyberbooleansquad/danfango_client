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
import org.json.JSONArray;
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
        MovieAgency ms = new MovieAgency();
        ArrayList<JSONObject> movies = ms.getMovies();
        System.out.println(Arrays.toString(movies.toArray()));

        System.out.println("STARTING MOVIE PARSE");
        generator.genMovieXMLFile(movies);
//        ///////////// ACTORS ///////////////
        CrewAgency as = new CrewAgency();
        for (JSONObject movie : movies) {
            System.out.println("GETTING ACTORS FOR: "+ movie.get("title"));
            as.updateActorsByMovie(movie);
        }
        ArrayList<Actor> ourDatabaseActors = as.getActors();
        HashSet<Actor> actors = new HashSet();
        for (Actor a : ourDatabaseActors) {
            actors.add(a);
            System.out.println("Added actor: " + a.getName());
        }
        System.out.println("STARTING CREW PARSE");
        generator.genCrewXMLFile(actors);





//        
//        ///////////// THEATRES  ///////////////
//        TheatreAgency ts = new TheatreAgency();
//        ArrayList<JSONObject> theatres = ts.getTheatres();
//        System.out.println("STARTING THEATRE PARSE");
//        generator.genTheatreXMLFile(theatres);
//    
//        ///////////// SHOWINGS ///////////////
//        ArrayList<JSONArray> theatreShowingsList = ts.getShowingsForTheatres(formattedDate);
//        System.out.println("STARTING SHOWING PARSE");
//        generator.genShowingXMLFile(theatreShowingsList);
//         //System.out.println(Arrays.toString(theatreShowingsList.toArray()));
    }
    
}
