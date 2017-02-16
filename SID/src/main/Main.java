package main;

import java.sql.SQLException;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.DBConnection;
import view.AlertBox;

public class Main extends Application{

	public Stage window;
	public MainController mainController;

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage primaryStage) throws Exception {
		try {
			DBConnection.getConnectionInstance();
			window = primaryStage;
			window.setTitle("SID");
			window.getIcons().add(new Image("/res/escudo_dulce.png"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/MainScene.fxml"));
			Parent root = loader.load();
			this.mainController = loader.getController();
			window.setOnCloseRequest(e ->{
				e.consume();
				mainController.exitProgram();
			});
			window.setScene(new Scene(root));		
			window.show();
		} catch (SQLException e) {
			AlertBox.display("Error", "No es posible conectar a la base de datos. Código de error: " + e.getErrorCode(), AlertBox.ERROR_IMG_PATH);
		}
	}

}
