/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CrewAgency;

import XMLGenerator.ClientXMLGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.xml.stream.XMLStreamException;
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
    
    public CrewAgency(){
        actors = new ArrayList();
    }
    
    public ArrayList<Actor> getActors(){
        return actors;
    }
    
        public void updateActorsByMovie(JSONObject movie) throws IOException, XMLStreamException{
            
            String movieId = movie.get("imdbID").toString();
            
            ArrayList<String> crewMemberNames = new ArrayList();
            String[] actorNames = movie.get("Actors").toString().split(", ");
            String directorName = movie.get("Director").toString();
            String writerName = movie.get("Writer").toString();
           
            crewMemberNames.addAll(Arrays.asList(actorNames));
           //  crewMemberNames.add(directorName);
           //  crewMemberNames.add(writerName);
            
            for(String name: crewMemberNames){
                Actor actor = getActorByName(name);
                // if the actor doesn't exist yet, we must create the java object, pull wikipedia info, and add it to the list
                if (actor == null){
                    actor = new Actor();
                    actor.setName(name);
                    // TODO -  will have to add a try catch at this line later rather than just throwing the exceptions above
                    updateActorWikipedia(actor);
                    actor.getMovieIds().add(movieId);
                    actors.add(actor);
                }
                // if actor already exists in the database, simply add the movieId to his list of movies
                else{
                    actor.getMovieIds().add(movieId);
                }
            }
        }
        
        public Actor getActorByName(String name){
            for(Actor a: actors){
                if(a.getName() != null && a.getName().equals(name)){
                    return a;
                }
            }
            return null;
        }
        
        public ArrayList<Actor> getActorsByMovieId(String movieId){
            ArrayList<Actor> actors = new ArrayList();
            for(Actor a: actors){
                if(a.getMovieIds().contains(movieId)){
                    actors.add(a);
                }
            }
           return actors;
        }
        
        public static void updateActorWikipedia(Actor actor) throws IOException, XMLStreamException {

        Document doc;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String actorName  = actor.getName();
        actorName = actorName.replace(" ", "_");
            
            System.out.println("CREWNAME: "+ actorName);
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
                   int ageNum = year-Integer.parseInt(bdayYear);
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
