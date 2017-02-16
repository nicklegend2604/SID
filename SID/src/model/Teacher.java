package model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Teacher {
	private String document;
	private String firstName;
	private String lastName;
	private String salaryFormat;
	private String title;
	private String startDateFormat;
	private String endDateFormat;
	private LocalDate startDate;
	private LocalDate endDate;
	private String areaName;
	private int areaId;
	private double salary;
	
	public Teacher() {
		this.document = "";
		this.firstName = "";
		this.lastName = "";
		this.salaryFormat = "";
		this.title = "";
		this.startDateFormat = "";
		this.endDateFormat = "";
		this.endDate = null;
		this.startDate = null;
		this.areaName = "";
		this.areaId = 0;
		this.salary = 0;
	}
	
	public Teacher(String document, String firstName, String lastName, double salary,
			String title, LocalDate startDate, LocalDate endDate, String areaName, int areaId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.document = document;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salaryFormat = NumberFormat.getCurrencyInstance().format(salary);
		this.title = title;
		this.startDateFormat = startDate != null ? startDate.format(formatter): LocalDate.now().format(formatter);
		this.endDateFormat = endDate != null ?endDate.format(formatter):null;
		this.areaName = areaName;
		this.areaId = areaId;
		this.salary = salary;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSalaryFormat() {
		return salaryFormat;
	}

	public void setSalaryFormat(String salaryFormat) {
		this.salaryFormat = salaryFormat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDateFormat() {
		return startDateFormat;
	}

	public void setStartDateFormat(String startDateFormat) {
		this.startDateFormat = startDateFormat;
	}

	public String getEndDateFormat() {
		return endDateFormat;
	}

	public void setEndDateFormat(String endDateFormat) {
		this.endDateFormat = endDateFormat;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}	
}
