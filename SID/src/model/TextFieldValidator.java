package model;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class TextFieldValidator {

	public HashMap<TextField, ContextMenu> validations;
	public ObservableList<TextField> textFields;
	private String cssStyleClass;


	public TextFieldValidator(ObservableList<TextField> textFields, String cssStyleClass) {
		this.validations = new HashMap<>();
		this.textFields = textFields;
		this.cssStyleClass = cssStyleClass;
		this.setValidators();
	}

	private void setValidators(){
		//Inicializando contexMenus y asociando en hashmap
		for (TextField tf : textFields) {
			ContextMenu contextMenu = new ContextMenu();
			contextMenu.setAutoHide(false);
			contextMenu.getStyleClass().add("context-menu-error");
			contextMenu.getStyleClass().add("menu-item-error");
			validations.put(tf, contextMenu);
		}

		//Recorriendo hashmap y agregando listener
		for(Map.Entry<TextField, ContextMenu> entry: this.validations.entrySet()){
			TextField tf = entry.getKey();
			ContextMenu cm = entry.getValue();
			tf.focusedProperty().addListener((v,oldVal,newVal) ->{
				cm.hide();
				tf.getStyleClass().removeAll(Collections.singleton(this.cssStyleClass));    
			});
		}
	}
	

	public void validatePrimaryKey(SQLException e, TextField primaryKeyTF, String message){
		if(e.getErrorCode() == 1062){
			ContextMenu cm = this.validations.get(primaryKeyTF);
			this.addErrorValidation(message, cm, primaryKeyTF);
		}
	}
	
	private void addErrorValidation(String error, ContextMenu cm, TextField tf){
		cm.getItems().clear();
		cm.getItems().add(new MenuItem(error));
		cm.show(tf, Side.RIGHT, 10, 0);
	}
	
	public boolean validateNullInputs(){
		boolean validated = true;
		for(Map.Entry<TextField, ContextMenu> entry: this.validations.entrySet()){
			TextField tf = entry.getKey();
			ContextMenu cm = entry.getValue();
			ObservableList<String> styleClass = tf.getStyleClass();	
			if (tf.getText().equals("")) {
				if (! styleClass.contains(this.cssStyleClass)) 
					styleClass.add(this.cssStyleClass);
				validated = false;	
				this.addErrorValidation("Ingrese un valor no vacío", cm, tf);
			} else {
				styleClass.removeAll(Collections.singleton(this.cssStyleClass));    
				cm.hide();
			}
		}
		return validated;
	}



	public static void applyDigitsOnly(TextField tf, int maxLenght){
		tf.textProperty().addListener((v,oldVal,newVal) -> {
			if (!newVal.matches("\\d*") ) 
				tf.setText(newVal.replaceAll("[^\\d]", ""));

			if (tf.getText().length() > maxLenght) {
				String s = tf.getText().substring(0, maxLenght);
				tf.setText(s);
			}
		});
	}

	public static void applyLettersOnly(TextField tf, int maxLenght){
		tf.textProperty().addListener((v,oldVal,newVal) -> {
			if (!newVal.matches("[\\p{L} .'-]+$") ) 
				tf.setText(newVal.replaceAll("[^\\p{L} .'-]+$", ""));

			if (tf.getText().length() > maxLenght) {
				String s = tf.getText().substring(0, maxLenght);
				tf.setText(s);
			}
		});
	}
}
