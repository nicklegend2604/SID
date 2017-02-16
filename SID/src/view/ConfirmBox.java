package view;

import java.io.IOException;

import controller.ConfirmBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

	public static final String WARNING_IMG_PATH = "/res/warning.png";
	private boolean answer;

	public ConfirmBox(String title, String message, String imagePath){	
		try {
			Stage window = new Stage();
			window.setTitle(title);
			window.getIcons().add(new Image("/res/escudo_dulce.png"));
			window.initModality(Modality.APPLICATION_MODAL);
			window.setResizable(false);
			ConfirmBoxController controller = null;

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ConfirmBox.fxml"));
			Parent root;
			root = loader.load();
			controller = loader.getController();
			controller.setMessage(message);
			controller.setImage(imagePath);
			controller.setController(this);
			Scene scene = new Scene(root);
			window.setScene(scene);
			window.showAndWait();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
}
