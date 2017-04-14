/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import MovieAgency.MoviePush;
import MovieAgency.MovieAgency;
import TheatreAgency.TheatreAgency;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author johnlegutko
 */
public class AgencyController {
    
    public static void main(String[] args) throws IOException, XMLStreamException {
        MovieAgency ms = new MovieAgency();
        ms.genXMLFile();
        
        TheatreAgency ts = new TheatreAgency();
        ts.genXMLFile();
       
    }
    
}
