package controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AlertBoxController {
	
	public Label messageLB;
	public ImageView image;
	
	public void setMessage(String message) {
		this.messageLB.setText(message);
	}


	public void setImage(String imagePath) {
		Image image = new Image(imagePath);
		this.image.setImage(image);
	}

	public void ok(){
		this.closeWindow();
	}
	
	public void cancel(){
		this.closeWindow();
	}
	
	public void closeWindow(){
		Stage window = (Stage) messageLB.getScene().getWindow();
		window.close();
	}

}
