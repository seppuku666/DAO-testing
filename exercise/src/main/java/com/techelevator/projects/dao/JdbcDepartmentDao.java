package com.techelevator.projects.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;

public class JdbcDepartmentDao implements DepartmentDao {

	private final JdbcTemplate jdbcTemplate;


	public JdbcDepartmentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Department getDepartment(Long id) {

		Department department = null;
		String sqlGetDepartment = "SELECT department_id, name FROM department Where department_id=?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetDepartment, id);
		if (results.next()) {
			department = mapRowToDepartment(results);
		}
		return department;
	}
//
//		while (results.next()) {
//			int id = results.getInt("department_id");
//			String departmentName = results.getString("name");
//			not sure if necessary
//			System.out.println("Department ID: " + departmentId + ", Department Name: " + departmentName + ".");
//		}


	@Override
	public List<Department> getAllDepartments() {
		List<Department> allDepartments = new ArrayList<>();
		String sqlGetAllDepartments = "SELECT department_id, name FROM department";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllDepartments);
			while (results.next()) {
				Department departmentResult = mapRowToDepartment(results);
				allDepartments.add(departmentResult);
			}
		return allDepartments;
	}

	@Override
	public void updateDepartment(Department updatedDepartment) {
			String sqlUpdateDepartmentName = "UPDATE department SET name=? WHERE department_id=?";

			jdbcTemplate.update(sqlUpdateDepartmentName, updatedDepartment.getName(), updatedDepartment.getId());
		}


		//use to update
//		SqlRowSet results = jdbcTemplate.update();



	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		department.setName(results.getString("name"));
		department.setId(results.getLong("department_id"));
		return department;
	}
}





