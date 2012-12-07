package pl.wat.edu.pl.pw.MediaPlayer.other;

/*Do obs³ugi SQLite polecam SQLite manager'a
 * Mo¿na go pobrac jako wtyczka do Firefoxa
 * */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class SQLiteConnection {

	Property property = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;


	public SQLiteConnection() {
		property = new Property();
		initConnection();
	}

	private void initConnection()  {
		try{
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:sqlite:db/"+property.getDatabaseName());
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
		}catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
	}


	public void endConnection() {
		try{
			if(connection != null)
				connection.close();
		}// connection close failed.
		catch(SQLException e){
			System.err.println(e);
		}
	}

	//to tak dla przykladu...
	protected void createTable(String tableName) {
		try {
			statement.executeUpdate("drop table if exists "+tableName);
			statement.executeUpdate("create table "+tableName+" (id integer, name string)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertIntoPlaylistDB(String URLPath){
		try {
			statement.executeUpdate("INSERT INTO Playlist VALUES (?,'" + URLPath + "')");
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertIntoPlaylistDB(int ID, String URLPath){
		try {
			statement.executeUpdate("INSERT INTO Playlist VALUES (" +
					"'"+ ID + "'," +
					"'" + URLPath + "')");
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getPlaylistFromDB(){
		LinkedList<String> list = new LinkedList<String>();
		try {
			resultSet = statement.executeQuery("select * from Playlist");
			while (resultSet.next()) {
				list.add(resultSet.getString("URL")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String [] tab = list.toArray(new String[list.size()]);
		
		return tab;
	}
	
	
	
	public void deleteAllRecords(String tableName) {
		try {
			statement.execute("DELETE FROM " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void deleteRecord(int productionID){
		try {
			statement.execute("DELETE FROM Playlist " +
								"where ID ="+productionID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createPlaylistTable() {
		try {
			statement.executeUpdate("drop table if exists Playlist");
			statement.executeUpdate("create table Playlist (ID integer, url string)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void getInfoFromTable(){
		try {
			resultSet = statement.executeQuery("select * from MusicFileInfo");
			while (resultSet.next()) {
				System.out.print(resultSet.getString("Wykonawca"));
				System.out.print(" - "+resultSet.getString("Tytul"));
				System.out.print(" ("+resultSet.getString("Typ")+")");
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws ClassNotFoundException {
		SQLiteConnection sqLiteConnection = new SQLiteConnection();
		sqLiteConnection.getInfoFromTable();
		//sqLiteConnection.insertIntoPlaylistDB("http://www.lol2.xd");
		//sqLiteConnection.insertIntoPlaylistDB(2, "lalala");
		sqLiteConnection.createPlaylistTable();
		String [] tab = sqLiteConnection.getPlaylistFromDB();
		
		//for(String t:tab)System.out.println(t);
		//sqLiteConnection.deleteAllRecords();
		
		sqLiteConnection.deleteRecord(32);
		sqLiteConnection.endConnection();
	}


}
