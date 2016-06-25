package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.Employee;

public interface iEmployeeDAO {

	public int insert(Employee emp);

	public int update(Employee emp);

	public int delete(int id);

	public List<Employee> select(String condt);
}
