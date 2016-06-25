package me.lancer.cms.model;

public class DataDict {

	private int id;
	private int superId;
	private int index;
	private String name;
	private String value;

	public DataDict() {
	}

	public DataDict(int id, int superId, int index, String name, String value) {
		super();
		this.id = id;
		this.superId = superId;
		this.index = index;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name;
	}

}
