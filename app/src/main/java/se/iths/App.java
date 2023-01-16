package se.iths;

import se.iths.persistency.dao.ArtistDAO;
import se.iths.persistency.model.Artist;

import java.util.Collection;

public class App {

    ArtistDAO artistDAO = new ArtistDAO();
    public static void main(String[] args){
        App app = new App();
        app.print();
    }

    private void print(){
        Collection<Artist> aritsts = artistDAO.findAll();
        for(Artist artist : aritsts){
            System.out.println(artist);
        }
    }

}
