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
import model.Teacher;
import model.TeacherDB;
import view.AlertBox;
import view.ConfirmBox;

public class TeacherController implements Initializable{

	public TableView<Teacher> teacherTable;
	public TableColumn<Teacher, String> documentColumn;
	public TableColumn<Teacher, String> firstNameColumn;
	public TableColumn<Teacher, String> lastNameColumn;
	public TableColumn<Teacher, String> salaryColumn;
	public TableColumn<Teacher, String> titleColumn;
	public TableColumn<Teacher, String> startDateColumn;
	public TableColumn<Teacher, String> endDateColumn;
	public TableColumn<Teacher, String> areaColumn;
	public ObservableList<Teacher> teachers;
	public TextField searchTeacherTF;
	public TeacherFormController teacherFormController;

	public void initialize(URL arg0, ResourceBundle arg1) {
		searchTeacherTF.textProperty().addListener((v, oldValue, newValue) -> {
			this.searchTeachers();
		});
		//Inicializando tabla de profesores
		this.setColumns();
		teachers = FXCollections.observableArrayList();
		teacherTable.setItems(teachers);
		this.getTeachers();	

	}

	private void setColumns(){
		this.documentColumn.setCellValueFactory(new PropertyValueFactory<>("document"));
		this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		this.salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salaryFormat"));
		this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		this.startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateFormat"));
		this.endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateFormat"));
		this.areaColumn.setCellValueFactory(new PropertyValueFactory<>("areaName"));
	}

	private void searchTeachers(){
		if(searchTeacherTF.getText().equals(""))
			this.getTeachers();
		else
			this.getTeachers(searchTeacherTF.getText());		
	}

	private void getTeachers(){
		try {
			TeacherDB.getTeachers(teachers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getTeachers(String query){
		try {
			TeacherDB.geTeachers(teachers, query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void openTeacherFormAdd(){
		try {
			Stage form = new Stage();
			form.setTitle("Agregando docente");
			form.getIcons().add(new Image("/res/escudo_dulce.png"));
			form.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/TeacherForm.fxml"));
			Parent root = loader.load();
			this.teacherFormController = loader.getController();
			this.teacherFormController.setTeacherController(this);
			this.teacherFormController.setStateAdd();
			this.teacherFormController.setChoiceBox();
			form.setScene(new Scene(root));
			form.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		catch(IndexOutOfBoundsException iobE){
			AlertBox.display("Error", "No hay áreas agregadas. Por favor agrege un área para poder agregar un docente.",
					AlertBox.ERROR_IMG_PATH);
		}
		
	}

	public void openTeacherFormEdit(){	
		Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
		if(teacher != null)
			try {
				Stage form = new Stage();
				form.setTitle("Editando docente");
				form.getIcons().add(new Image("/res/escudo_dulce.png"));
				form.initModality(Modality.APPLICATION_MODAL);
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/TeacherForm.fxml"));
				Parent root = loader.load();
				this.teacherFormController = loader.getController();
				this.teacherFormController.setTeacherController(this);
				this.teacherFormController.loadTeacherData(teacher);
				this.teacherFormController.setStateEdit();
				this.teacherFormController.setChoiceBox();
				form.setScene(new Scene(root));
				form.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		else
			AlertBox.display("Error", "Seleccione un docente", AlertBox.ERROR_IMG_PATH);	
	}

	public void deleteTeacher(){
		Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
		int index = teacherTable.getSelectionModel().getSelectedIndex();
		if(teacher != null){
			ConfirmBox cb = new ConfirmBox("Eliminando docente",
					"¿Está seguro que desea eliminar el docente seleccionado?", 
					ConfirmBox.WARNING_IMG_PATH);
			if(cb.isAnswer())
				try {
					TeacherDB.deleteTeacher(teacher.getDocument());
					teachers.remove(index);
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Error borrando docente");
				}
		}
		else
			AlertBox.display("Error", "Seleccione un docente", AlertBox.ERROR_IMG_PATH);

	}

}
