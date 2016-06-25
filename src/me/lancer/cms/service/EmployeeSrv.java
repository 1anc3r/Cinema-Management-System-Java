package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iEmployeeDAO;
import me.lancer.cms.model.Employee;

public class EmployeeSrv {
	
	private iEmployeeDAO empDAO = DAOFactory.creatEmployeeDAO();

	public int add(Employee emp) {
		return empDAO.insert(emp);
	}

	public int modify(Employee emp) {
		return empDAO.update(emp);
	}

	public int delete(int id) {
		return empDAO.delete(id);
	}

	public List<Employee> Fetch(String condt) {
		return empDAO.select(condt);
	}

	public List<Employee> FetchAll() {
		return empDAO.select("");
	}
}
