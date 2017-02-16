package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ConfirmBox;

public class MainController implements Initializable{

	public BorderPane mainPane;
	public Stage window;

	public void switchToStart(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/StartPane.fxml"));
			mainPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando panel inicio ");
		}
	}

	public void switchToTeachers(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/TeachersPane.fxml"));
			mainPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando panel docentes ");
		}
	}

	public void switchToAreas(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/AreasPane.fxml"));
			mainPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando panel areas ");
		}
	}
	
	public void switchToAbout(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/AboutPane.fxml"));
			mainPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando panel areas ");
		}
	}

	public void exitProgram(){
		ConfirmBox cb = new ConfirmBox("Cerrando", "¿Está seguro que desea cerrar el programa?", ConfirmBox.WARNING_IMG_PATH);
		if(cb.isAnswer()){
			Stage window = (Stage) mainPane.getScene().getWindow();
			window.close();
		}
	}


	public void initialize(URL arg0, ResourceBundle arg1) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/StartPane.fxml"));
			mainPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando panel inicio ");
		}
	}

}
