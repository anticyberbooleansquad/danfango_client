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
        MovieAgency ms = new MovieAgency();
        ArrayList<JSONObject> movies = ms.getMovies();
        generator.genMovieXMLFile(movies);
        
        
        ///////////// ACTORS ///////////////
        CrewAgency as = new CrewAgency();
        for(JSONObject movie: movies){
            as.updateActorsByMovie(movie);
        }
       
        // now that we have updated/created all of the actor objects in the "database" that we're interested in, let's retrieve them
        ArrayList<Actor> actors = new ArrayList();
        for(JSONObject movie: movies){
            String movieId = movie.get("imdbId").toString();
            Actor actor = as.getActorByMovieId(movieId);
            actors.add(actor);
        }
        // call the xml method here VVVV
       
        
        ///////////// THEATRES  ///////////////
        
        
        ///////////// SHOWINGS ///////////////
        
        
        //TheatreAgency ts = new TheatreAgency();
        //ArrayList<JSONObject> theatres = ts.getTheatres();
        //ArrayList<JSONObject> showings = ts.getShowingsForTheatre(formattedDate);

        //generator.genTheatreXMLFile(theatres);
        //generator.genShowingXMLFile(showings);
        //System.out.println(Arrays.toString(showings.toArray()));
        

    }

}
