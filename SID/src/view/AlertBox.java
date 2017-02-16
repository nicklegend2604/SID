package view;

import java.io.IOException;

import controller.AlertBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static final String ERROR_IMG_PATH = "/res/error.png";
	
	 public static void display(String title, String message, String imagePath) {
		 try {
				Stage window = new Stage();
				window.setTitle(title);
				window.getIcons().add(new Image("/res/escudo_dulce.png"));
				window.initModality(Modality.APPLICATION_MODAL);
				window.setResizable(false);
				AlertBoxController controller = null;

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AlertBox.class.getResource("/view/AlertBox.fxml"));
				Parent root;
				root = loader.load();
				controller = loader.getController();
				controller.setMessage(message);
				controller.setImage(imagePath);
				Scene scene = new Scene(root);
				window.setScene(scene);
				window.showAndWait();	
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

}
