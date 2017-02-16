package controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import view.ConfirmBox;

public class ConfirmBoxController {
	
	public Label messageLB;
	public ImageView image;
	public ConfirmBox confirmBox;
	
	public void setMessage(String message) {
		this.messageLB.setText(message);
	}


	public void setImage(String imagePath) {
		Image image = new Image(imagePath);
		this.image.setImage(image);
	}

	public void setController(ConfirmBox confirmBox) {
		this.confirmBox = confirmBox;
	}
	
	public void ok(){
		this.confirmBox.setAnswer(true);
		this.closeWindow();
	}
	
	public void cancel(){
		this.confirmBox.setAnswer(false);
		this.closeWindow();
	}
	
	public void closeWindow(){
		Stage window = (Stage) messageLB.getScene().getWindow();
		window.close();
	}

}
