package pl.edu.wat.wcy.prz.mediaplayer.other;

/* Instrukcja odnoœnie instalacji LastFm.jar: 
 * 
 * I. Copy the JARs you'll be using to your project. Here's how it's conventionally done:
 *   1. Create a new folder named lib in your project folder. This stands for "libraries" and will contain all the JARs you'll be using for that project.
 *   2. Copy the JARs you need to lib.
 *   3.Copied JARs to the project. Refresh your project by right clicking the project name and selecting Refresh. The lib folder will now be visible in Eclipse with the JARs inside
 * 
 * II. Use method under:
 *   1. Expand lib in Eclipse and select all the JARs you need.
 *   2. Right-click the JARs and navigate to Build Path.
 *   3. Select Add to Build Path. The JARs will disappear from lib and reappear in Referenced Libraries.
 *   
 * Thats all.
 * More help: http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-(Java)  
 * */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.Geo;
import de.umass.lastfm.Playlist;
import de.umass.lastfm.User;

public class LastFmApi {
	
	Property p = null;
	
	public LastFmApi() {
		p = new Property();
	}
	

	protected void getYourCountryTopArtists(String country) throws FileNotFoundException, IOException {
		Collection<Artist> geo = Geo.getTopArtists(country, p.getApiKey());
		for (Artist a : geo) {
			System.out.println(a.toString());
		}
	}
	
	public String getUserTopAlbums(String userName) {
		Collection<Album> alb = User.getTopAlbums(userName, p.getApiKey());
		int a = 0;
		String top="";
		for (Album album : alb) {
			if(a == 1) break;
			//System.out.println(album);
			a++;
			top = album.getName() +" "+ album.getArtist();
			
		}
		return top;
	}

	public String getUserName(String userName) {
		String username = User.getInfo(userName, p.getApiKey()).getName();
		//System.out.println(username);
		return username;
	}
	
	public String getUserCountry(String userName){
		//System.out.println(User.getInfo(p.getLFUser(), p.getApiKey()).getCountry());
		return User.getInfo(userName, p.getApiKey()).getCountry();
	}
	
	public String getUserAge(String userName){
	//	System.out.println(User.getInfo(p.getLFUser(), p.getApiKey()).getAge());
		return String.valueOf(User.getInfo(userName, p.getApiKey()).getAge());
		
	}
	
	public int getUserIntAge(String userName){
	//	System.out.println(User.getInfo(p.getLFUser(), p.getApiKey()).getAge());
		return User.getInfo(userName, p.getApiKey()).getAge();
		
	}
	
	protected void getUserPlaylist(){
		Collection<Playlist> pl = User.getPlaylists(p.getLFUser(), p.getApiKey());
		
		for(Playlist playlist : pl){
			System.out.println(playlist);
		}
	}
	
	
	public String getEventInfo(int nrID, int e){
		String tab = "";
		String [] event = {"3332235", "3346016", "3441776", "3363246", "3399795"};
		
		String id= event[e];
		
		if(nrID == 0){
			tab = Event.getInfo(id, p.getApiKey()).getTitle();
		//	System.out.println("Wydarzenie:\t" + tab);
		}
		else if (nrID == 1) {
			tab = Event.getInfo(id, p.getApiKey()).getStartDate().toString();
		//	System.out.println("Data:      \t" + tab);
		}
		else if (nrID == 2) {
			tab = Event.getInfo(id, p.getApiKey()).getUrl();
			//System.out.println("www:       \t" + tab);

		}
		
		return tab;
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		LastFmApi net = new LastFmApi();
		//net.getYourCountryTopArtists("Poland");
		//net.getUserTopAlbums("mario6q");
		//net.getUserCountry();
		//net.getUserName();
		//net.getEventInfo();
	}

}
