package me.lancer.cms.model;

public class Employee {

	private int access;
	private int id;
	private String name;
	private String password;
	private String addr;
	private String tel;
	private String email;
	private int no;

	public Employee() {
	}

	public Employee(int access, int id, String name, String password, int no, String tel, String email, String addr) {
		this.access = access;
		this.id = id;
		this.name = name;
		this.password = password;
		this.no = no;
		this.tel = tel;
		this.email = email;
		this.addr = addr;
	}

	public int getAccess() {
		return access;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getNo() {
		return no;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void showValue() {
		System.out.println("编号：" + id + "\t姓名：" + name);
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
