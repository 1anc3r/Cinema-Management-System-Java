package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iScheduleDAO;
import me.lancer.cms.model.Schedule;

public class ScheduleSrv {

	private iScheduleDAO schedDAO = DAOFactory.creatScheduleDAO();

	public int add(Schedule sched) {
		return schedDAO.insert(sched);
	}

	public int modify(Schedule sched) {
		return schedDAO.update(sched);
	}

	public int delete(int ID) {
		return schedDAO.delete(ID);
	}

	public List<Schedule> Fetch(String condt) {
		return schedDAO.select(condt);
	}

	public List<Schedule> FetchAll() {
		return schedDAO.select("");
	}
}
