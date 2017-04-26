/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrewAgency;

import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Actor {

    private String name;
    private String birthDate;
    private String age;
    private String biography;
    private String imdbId;
    private String tmdbId;
    private String poster;
    private ArrayList<String> movieIds;

    public Actor() {
        movieIds = new ArrayList();
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }

//        @Override
//    public boolean equals(Object o){
//        if(this == o){
//            return true;
//        }
//        if(!(o instanceof Actor)){
//            return false;
//        }
//        
//        Actor actor = (Actor) o;
//        return actor.name.equals(name) && actor.birthDate.equals(birthDate) && actor.age.equals(age) && actor.biography.equals(biography);
//    }    
//    
//    @Override
//    public int hashCode(){
//        int result = 1;
//        result = result * name.hashCode();
//        result = result * birthDate.hashCode();
//        result = result * age.hashCode();
//        result = result * biography.hashCode();
//        return result;
//    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * @param biography the biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * @return the movieIds
     */
    public ArrayList<String> getMovieIds() {
        return movieIds;
    }

    /**
     * @param movieIds the movieIds to set
     */
    public void setMovieIds(ArrayList<String> movieIds) {
        this.movieIds = movieIds;
    }
}
