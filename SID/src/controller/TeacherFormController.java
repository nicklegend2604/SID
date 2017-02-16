package controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Area;
import model.AreaDB;
import model.Teacher;
import model.TeacherDB;
import model.TextFieldValidator;

public class TeacherFormController implements Initializable{

	public TextField documentTF;
	public TextField nameTF;
	public TextField lastNameTF;
	public TextField salaryTF;
	public Label salaryFormatLb;
	public TextField titleTF;
	public DatePicker startDateDP;
	public DatePicker endDateDP;
	public ChoiceBox<Area> areaCB;
	public TeacherController teacherController;
	public final static String EDIT = "edit";
	public final static String ADD = "add";
	private String state = ADD;
	private Teacher teacher;
	public TextFieldValidator validator;

	public void initialize(URL location, ResourceBundle resources) {
		this.teacher = null;
		this.setValidators();
		this.setTextFieldLenghts();
		this.setSalaryLabel();
	}

	private void setSalaryLabel(){
		this.salaryFormatLb.setText(NumberFormat.getCurrencyInstance().format(0));
		this.salaryTF.textProperty().addListener( (v,o,n) -> {
			String value = "";
			if(n.equals(""))
				value = NumberFormat.getCurrencyInstance().format(0);
			else
				value = NumberFormat.getCurrencyInstance().format(Double.parseDouble(n));

			this.salaryFormatLb.setText(value);
		});
	}

	private void setTextFieldLenghts() {
		TextFieldValidator.applyDigitsOnly(documentTF, 10);
		TextFieldValidator.applyLettersOnly(nameTF, 30);
		TextFieldValidator.applyLettersOnly(lastNameTF, 30);
		TextFieldValidator.applyDigitsOnly(salaryTF, 8);
		TextFieldValidator.applyLettersOnly(titleTF, 30);
	}

	private void setValidators(){
		ObservableList<TextField> textFields = FXCollections.observableArrayList();
		textFields.addAll(documentTF,nameTF,lastNameTF,salaryTF,titleTF);
		validator = new TextFieldValidator(textFields,"error");
	}

	public void loadTeacherData(Teacher teacher){
		this.teacher = teacher;
		documentTF.setText(teacher.getDocument());
		nameTF.setText(teacher.getFirstName());
		lastNameTF.setText(teacher.getLastName());
		salaryTF.setText(String.valueOf(teacher.getSalary()));
		salaryFormatLb.setText(NumberFormat.getCurrencyInstance().format(teacher.getSalary()));
		titleTF.setText(teacher.getTitle());
		startDateDP.setValue(teacher.getStartDate());
		endDateDP.setValue(teacher.getEndDate());
		areaCB.setValue(this.findArea(teacher.getAreaId()));
	}

	private Area findArea(int id){
		ObservableList<Area> areas = this.areaCB.getItems();
		for (Area area : areas) {
			if(area.getId() == id) return area;
		}
		return null;
	}

	public void handleOk(){
		if(state.equals(ADD))
			this.insertTeacher();
		else
			this.updateTeacher();
	}

	public void handleCancel(){
		this.closeWindow();

	}

	public void insertTeacher(){
		if(validator.validateNullInputs())
			try {
				//Creando objeto docente
				Teacher teacher = new Teacher(documentTF.getText(),
						nameTF.getText(), lastNameTF.getText(), Double.parseDouble(salaryTF.getText()),
						titleTF.getText(), startDateDP.getValue(), endDateDP.getValue(),
						areaCB.getSelectionModel().getSelectedItem().getName(),
						areaCB.getSelectionModel().getSelectedItem().getId());
				//Agregando a base de datos
				TeacherDB.insertTeacher(teacher);
				//Agregando a lista
				teacherController.teachers.add(teacher);
				this.closeWindow();
			} catch (SQLException e) {
				validator.validatePrimaryKey(e, documentTF, "Ya existe un docente con este documento");
			}
	}

	public void updateTeacher(){
		if(validator.validateNullInputs()){
			try {
				//Creando objeto docente
				Teacher teacher = new Teacher(documentTF.getText(),
						nameTF.getText(), lastNameTF.getText(), Double.parseDouble(salaryTF.getText()),
						titleTF.getText(), startDateDP.getValue(), endDateDP.getValue(),
						areaCB.getSelectionModel().getSelectedItem().getName(),
						areaCB.getSelectionModel().getSelectedItem().getId());
				//Editando en base de datos
				TeacherDB.updateTeacher(teacher, this.teacher.getDocument());
				//Editando en lista
				int index = teacherController.teacherTable.getSelectionModel().getSelectedIndex();
				teacherController.teachers.set(index, teacher);
				this.closeWindow();
			} catch (SQLException e) {
				validator.validatePrimaryKey(e, documentTF, "Ya existe un docente con este documento");
			}
		}
	}

	public void setChoiceBox(){
		try {
			AreaDB.getAreas(this.areaCB.getItems());
			areaCB.setValue(this.areaCB.getItems().get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTeacherController(TeacherController teacherController){
		this.teacherController = teacherController;
	}

	private void closeWindow(){
		Stage window = (Stage) documentTF.getScene().getWindow();
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

	public Teacher getTeacher() {
		return teacher;
	}

}
