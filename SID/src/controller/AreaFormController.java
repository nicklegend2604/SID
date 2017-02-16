package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Area;
import model.AreaDB;
import model.TextFieldValidator;

public class AreaFormController implements Initializable{
	public TextField nameTF;
	public final static String EDIT = "edit";
	public final static String ADD = "add";
	private String state = ADD;
	private AreaController areaController;
	private Area area = null;
	private TextFieldValidator validator;

	public void initialize(URL location, ResourceBundle resources) {
		this.setValidators();
	}

	private void setValidators(){
		ObservableList<TextField> tf = FXCollections.observableArrayList();
		tf.add(nameTF);
		validator = new TextFieldValidator(tf,"error");
	}

	public void loadAreaData(Area area){
		nameTF.setText(area.getName());
		this.area = area;
	}


	public void handleOk(){
		if(state.equals(ADD))
			this.insertArea();
		else
			this.updateArea();
	}

	public void handleCancel(){
		this.closeWindow();	
	}

	public void insertArea(){
		if(validator.validateNullInputs())
			try {
				//Creando objeto docente
				Area area = new Area();
				area.setName(nameTF.getText());
				//Agregando a base de datos
				AreaDB.insertArea(area);
				//Agregando a lista
				areaController.areas.add(area);
				this.closeWindow();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	public void updateArea(){
		if(validator.validateNullInputs())
			try {
				//Creando objeto docente
				Area area = new Area(this.area.getId(), nameTF.getText());
				//Editando en base de datos
				AreaDB.updateArea(area, area.getId());
				//Editando en lista
				int index = areaController.areaTable.getSelectionModel().getSelectedIndex();
				areaController.areas.set(index, area);
				this.closeWindow();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}


	public void setAreaController(AreaController areaController){
		this.areaController = areaController;
	}

	private void closeWindow(){
		Stage window = (Stage) nameTF.getScene().getWindow();
		window.close();
	}

	public String getState() {
		return state;
	}

	public void setStateAdd() {
		this.state = ADD;
	}

	public void setStateEdit() {
		this.state = EDIT;
	}

	public Area getArea() {
		return this.area;
	}


}
