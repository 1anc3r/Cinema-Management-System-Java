package me.lancer.cms.model;

import java.text.DateFormat;
import java.util.Date;

public class Schedule {

	private int id;
	private int studioId;
	private int playId;
	private Date time;
	private double price;

	public Schedule() {
	}

	public String toString() {
		if (getTime() == null)
			return "0:00:00";
		else
			return DateFormat.getTimeInstance().format(getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudioId() {
		return studioId;
	}

	public void setStudioId(int studioId) {
		this.studioId = studioId;
	}

	public int getPlayId() {
		return playId;
	}

	public void setPlayId(int playId) {
		this.playId = playId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
