package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iEmployeeDAO;
import me.lancer.cms.model.Employee;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class EmployeeDAO implements iEmployeeDAO {

	@Override
	public int insert(Employee emp) {
		try {
			String sqlstr = "insert into employee(emp_access, emp_no, emp_name, emp_password, emp_addr, emp_tel_num, emp_email ) values( "
					+ emp.getAccess() + ", " + emp.getNo() + ", '" + emp.getName() + "', '" + emp.getPassword() + "', '"
					+ emp.getAddr() + "', '" + emp.getTel() + "', '" + emp.getEmail() + "')";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				emp.setId(rst.getInt(1));
			}
			db.close(rst);
			db.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(Employee emp) {
		int rtn = 0;
		try {
			String sqlstr = "update employee set emp_id = " + emp.getId() + ", emp_access = " + emp.getAccess()
					+ ", emp_name = '" + emp.getName() + "', emp_password = '" + emp.getPassword() + "', emp_tel = '"
					+ emp.getTel() + "', emp_addr = '" + emp.getAddr() + "', emp_email = '" + emp.getEmail()
					+ "', emp_no = " + emp.getNo();
			sqlstr += " where emp_id = " + emp.getId();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sqlstr);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public int delete(int id) {
		int rtn = 0;
		try {
			String sqlstr = "delete from employee ";
			sqlstr += " where emp_id = " + id;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sqlstr);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public List<Employee> select(String condt) {
		List<Employee> empList = null;
		empList = new LinkedList<Employee>();
		try {
			String sqlstr = "select emp_id, emp_access, emp_name, emp_password, emp_tel, emp_addr, emp_email,emp_no from employee ";
			condt.trim();
			if (!condt.isEmpty())
				sqlstr += " where " + condt;
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				return null;
			}
			ResultSet rst = db.execQuery(sqlstr);
			if (rst != null) {
				while (rst.next()) {
					Employee emp = new Employee();
					emp.setId(rst.getInt("emp_id"));
					emp.setAccess(rst.getInt("emp_access"));
					emp.setName(rst.getString("emp_name"));
					emp.setPassword(rst.getString("emp_password"));
					emp.setTel(rst.getString("emp_tel"));
					emp.setAddr(rst.getString("emp_addr"));
					emp.setEmail(rst.getString("emp_email"));
					emp.setNo(rst.getInt("emp_no"));
					empList.add(emp);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return empList;
	}
}
