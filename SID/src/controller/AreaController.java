package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Area;
import model.AreaDB;
import view.AlertBox;
import view.ConfirmBox;

public class AreaController implements Initializable{

	public TableView<Area> areaTable;
	public TableColumn<Area, Integer> idColumn;
	public TableColumn<Area, String> nameColumn;
	public ObservableList<Area> areas;
	public TextField searchAreaTF;
	public AreaFormController areaFormController;

	public void initialize(URL location, ResourceBundle resources) {
		this.initTable();
		//Agregando listener a textfield de búsqueda
		searchAreaTF.textProperty().addListener((v, oldValue, newValue) -> {
			searchAreas();
		});
	}

	private void setColumns() {
		this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	private void searchAreas(){
		if(searchAreaTF.getText().equals(""))
			this.getAreas();
		else
			this.getAreas(searchAreaTF.getText());		
	}

	private void initTable(){
		this.setColumns();
		areas = FXCollections.observableArrayList();
		areaTable.setItems(areas);
		this.getAreas();
	}

	private void getAreas(){
		try {
			AreaDB.getAreas(areas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAreas(String query){
		try {
			AreaDB.getAreas(areas, query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void openAreaFormAdd(){
		try {
			Stage form = new Stage();
			form.setTitle("Agregando área");
			form.initModality(Modality.APPLICATION_MODAL);
			form.getIcons().add(new Image("/res/escudo_dulce.png"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AreaForm.fxml"));
			Parent root = loader.load();
			this.areaFormController = loader.getController();
			this.areaFormController.setAreaController(this);
			this.areaFormController.setStateAdd();
			form.setScene(new Scene(root));
			form.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void openAreaFormEdit(){	
		Area area = areaTable.getSelectionModel().getSelectedItem();
		if(area != null)
			try {
				Stage form = new Stage();
				form.setTitle("Editando área");
				form.initModality(Modality.APPLICATION_MODAL);
				form.getIcons().add(new Image("/res/escudo_dulce.png"));
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AreaForm.fxml"));
				Parent root = loader.load();
				this.areaFormController = loader.getController();
				this.areaFormController.setAreaController(this);
				this.areaFormController.loadAreaData(area);
				this.areaFormController.setStateEdit();
				form.setScene(new Scene(root));
				form.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		else
			AlertBox.display("Error", "Seleccione un área.", AlertBox.ERROR_IMG_PATH);
	}

	public void deleteArea(){
		Area area = areaTable.getSelectionModel().getSelectedItem();
		int index = areaTable.getSelectionModel().getSelectedIndex();
		if(area != null){
			ConfirmBox confirmBox = new ConfirmBox("Eliminando área",
					"¿Está seguro que desea eliminar el área seleccionada?", ConfirmBox.WARNING_IMG_PATH);
			if(confirmBox.isAnswer())
				try {
					AreaDB.deleteArea(area.getId());
					areas.remove(index);
				} catch (SQLException e) {
					if(e.getErrorCode() == 1451)
						AlertBox.display("Error", "No se puede eliminar el área. Para eliminarla,"
								+ " debe eliminar todos los docentes relacionados con ésta.", AlertBox.ERROR_IMG_PATH);
				}
		}
		else
			AlertBox.display("Error", "Seleccione un área.", AlertBox.ERROR_IMG_PATH);	
	}

}
