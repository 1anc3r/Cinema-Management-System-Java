package me.lancer.cms.model;

import java.util.Date;

public class Ticket {

	private int id;
	private int seatId;
	private int scheduleId;
	private float price;
	private int status;
	private Date locked_time;

	private String playName;
	private Schedule sched;
	private Seat seat;
	private Date lockedTime;

	public Ticket() {
	}

	public Ticket(int id, int seatId, int scheduleId, float price, int status) {
		super();
		this.id = id;
		this.seatId = seatId;
		this.scheduleId = scheduleId;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLocked_time() {
		return locked_time;
	}

	public void setLocked_time(Date locked_time) {
		this.locked_time = locked_time;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Schedule getSchedule() {
		return sched;
	}

	public void setSchedule(Schedule sched) {
		this.sched = sched;
	}

	public Date getCurrentLockedTime() {
		return lockedTime;
	}

	public void setCurrentLockedTime(Date lockedTime) {
		this.lockedTime = lockedTime;
	}

}
