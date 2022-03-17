package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Employee;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcEmployeeDao implements EmployeeDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Employee> getAllEmployees() {

	List<Employee> allEmployees = new ArrayList<>();
	String sql = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next())

	{
		Employee employeeResult = mapRowToEmployees(results);
		allEmployees.add(employeeResult);
	}
		return allEmployees;
}
	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employeesByName = new ArrayList<>();
		String sql = "SELECT first_name, last_name FROM employee WHERE first_name ILIKE '?' AND last_name ILIKE '?'";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Employee employeesByNameResult = mapRowToEmployees(results);
			employeesByName.add(employeesByNameResult);
		}
		return employeesByName;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employeesByProjectId = new ArrayList<>();
		String sql = "SELECT employee_id FROM employee JOIN project_id ON e";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()){
			Employee employeesByProjectId = mapRowToEmployees(results);
			employeesByProjectId.add(employeesByProjectIdResult);
		}
		return employeesByProjectId;
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sql = "INSERT INTO employye_project (project_id, employee_id) VALUES (?, ?);";
		jdbcTemplate.update(sql, projectId, employeeId);
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sql = "DELETE FROM employee_project WHERE project_id = ? AND employee_id = ?;";
		jdbcTemplate.update(sql, projectId, employeeId);
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		return new ArrayList<>();
	}

	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee employee = new Employee();
		employee.setId(results.getLong("department_id"));
		employee.setFirstName(results.getString("first_name"));
		employee.setLastName(results.getString("last_name"));
		if(results.getDate("birth_date") != null){
			employee.setBirthDate(results.getDate("birth_date").toLocalDate());
		}
		if(results.getDate("hire_date") != null){
			employee.setHireDate(results.getDate("hire_date").toLocalDate());
		}
		return employee;
	}
}
