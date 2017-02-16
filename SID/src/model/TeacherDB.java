package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.ObservableList;

public class TeacherDB {

	public static void getTeachers(ObservableList<Teacher> teachers) throws SQLException {
		teachers.clear();
		Statement statement = DBConnection.getConnectionInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT DOCUMENTO_DOCENTE, NOMBRE, APELLIDO,"
				+" SALARIO, TITULO, FECHA_INICIO, FECHA_FIN,"
				+" NOMBRE_AREA, D.ID_AREA"
				+" FROM AREAS A, DOCENTES D"
				+" WHERE D.ID_AREA = A.ID_AREA");	
		while(rs.next()){

			LocalDate start = rs.getDate(6).toLocalDate();
			LocalDate end = rs.getDate(7) != null ?rs.getDate(7).toLocalDate(): null;

			Teacher teacher = new Teacher(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getDouble(4), rs.getString(5),start, end, rs.getString(8) , rs.getInt(9));
			teachers.add(teacher);
		}	
	}

	public static void geTeachers(ObservableList<Teacher> teachers, String query) throws SQLException {
		teachers.clear();
		Statement statement = DBConnection.getConnectionInstance().createStatement();
		ResultSet rs = statement.executeQuery("SELECT DOCUMENTO_DOCENTE, NOMBRE, APELLIDO,"
				+" SALARIO, TITULO, FECHA_INICIO, FECHA_FIN,"
				+" NOMBRE_AREA, D.ID_AREA"
				+" FROM AREAS A, DOCENTES D"
				+" WHERE D.ID_AREA = A.ID_AREA"
				+" AND (DOCUMENTO_DOCENTE LIKE '%"+ query+"%'"
				+" OR UPPER(NOMBRE) LIKE UPPER('%"+ query+"%')"
				+" OR UPPER(APELLIDO) LIKE UPPER('%"+ query+"%')"
				+" OR CAST(SALARIO AS CHAR(30)) LIKE '%"+ query+"%'"
				+" OR UPPER(TITULO) LIKE UPPER('%"+ query+"%')"
				+" OR DATE_FORMAT(FECHA_INICIO, '%d/%m/%Y') LIKE '%"+ query+"%'"
				+" OR DATE_FORMAT(FECHA_FIN, '%d/%m/%Y') LIKE '%"+ query+"%'"
				+" OR UPPER(NOMBRE_AREA)LIKE UPPER('%"+ query+"%')"
				+")");	
		while(rs.next()){

			LocalDate start = rs.getDate(6).toLocalDate();
			LocalDate end = rs.getDate(7) != null ?rs.getDate(7).toLocalDate(): null;

			Teacher teacher = new Teacher(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getDouble(4), rs.getString(5),start, end, rs.getString(8) , rs.getInt(9));
			teachers.add(teacher);
		}	
	}

	public static void insertTeacher(Teacher teacher) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("INSERT INTO DOCENTES (documento_docente ,id_area, "
						+ " nombre, apellido, salario, titulo, fecha_inicio, fecha_fin)"
						+ " VALUES(?,?,?,?,?,?,?,?)");
		ps.setString(1, teacher.getDocument());
		ps.setInt(2, teacher.getAreaId());
		ps.setString(3, teacher.getFirstName());
		ps.setString(4, teacher.getLastName());
		ps.setDouble(5, teacher.getSalary());
		ps.setString(6, teacher.getTitle());
		ps.setDate(7, teacher.getStartDate() != null ? Date.valueOf(teacher.getStartDate())
				:Date.valueOf(LocalDate.now()));
		ps.setDate(8, teacher.getEndDate() != null ? Date.valueOf(teacher.getEndDate()): null);
		ps.executeUpdate();		
	}

	public static void updateTeacher(Teacher teacher, String document) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("UPDATE DOCENTES SET documento_docente = ?,id_area = ?" 
						+ " ,nombre = ? ,apellido = ? ,salario = ? ,titulo = ?" 
						+ " ,fecha_inicio = ? ,fecha_fin = ? WHERE documento_docente = ?");
		ps.setString(1, teacher.getDocument());
		ps.setInt(2, teacher.getAreaId());
		ps.setString(3, teacher.getFirstName());
		ps.setString(4, teacher.getLastName());
		ps.setDouble(5, teacher.getSalary());
		ps.setString(6, teacher.getTitle());
		ps.setDate(7, teacher.getStartDate() != null ? Date.valueOf(teacher.getStartDate())
				:Date.valueOf(LocalDate.now()));
		ps.setDate(8, teacher.getEndDate() != null ? Date.valueOf(teacher.getEndDate()): null);
		ps.setString(9, document);
		ps.executeUpdate();	
	}
	
	public static void deleteTeacher(String document) throws SQLException{
		PreparedStatement ps = DBConnection.getConnectionInstance().
				prepareStatement("DELETE FROM DOCENTES WHERE documento_docente = ?");
		ps.setString(1, document);
		ps.executeUpdate();	
	}

}
