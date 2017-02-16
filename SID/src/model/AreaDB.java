package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class AreaDB {

	public static void getAreas(ObservableList<Area> areas) throws SQLException {
		areas.clear();
		Statement statement = DBConnection.getConnectionInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM AREAS");	
		while(rs.next()){
			Area area = new Area(rs.getInt(1), rs.getString(2));
			areas.add(area);
		}	
	}
	
	public static void getAreas(ObservableList<Area> areas, String query) throws SQLException {
		areas.clear();
		Statement statement = DBConnection.getConnectionInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM AREAS WHERE UPPER(NOMBRE_AREA)"
				+ " LIKE UPPER('%"+query+"%')"
						+ "OR CAST(ID_AREA AS CHAR(30)) LIKE '" + query+"'");	
	
		while(rs.next()){
			Area area = new Area(rs.getInt(1), rs.getString(2));
			areas.add(area);
		}	
	}
	
	public static void insertArea(Area area) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("INSERT INTO AREAS (nombre_area) VALUES(?)");
		ps.setString(1,area.getName());
		Statement st = DBConnection.getConnectionInstance().createStatement();
		ResultSet rs = st.executeQuery("SELECT `AUTO_INCREMENT`" 
										+" FROM  INFORMATION_SCHEMA.TABLES" 
										+" WHERE TABLE_SCHEMA = 'sid'"
										+" AND   TABLE_NAME   = 'areas'");
		rs.next();
		int index = rs.getInt(1);
		area.setId(index);	
		ps.executeUpdate();		
	}
	
	
	public static void updateArea(Area area, int id) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("UPDATE AREAS SET nombre_area = ? WHERE id_area = " + id);
		ps.setString(1, area.getName());
		ps.executeUpdate();		
	}
	
	public static void deleteArea(int id) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("DELETE FROM AREAS WHERE id_area = ?");
		ps.setInt(1, id);
		ps.executeUpdate();		
	}
}
