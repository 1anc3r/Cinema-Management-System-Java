package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iTicketDAO;
import me.lancer.cms.model.Ticket;

public class TicketSrv {

	private iTicketDAO tciketDAO = DAOFactory.creatTicketDAO();

	public int add(Ticket tciket) {
		return tciketDAO.insert(tciket);
	}

	public int modify(Ticket tciket) {
		return tciketDAO.update(tciket);
	}

	public int delete(int ID) {
		return tciketDAO.delete(ID);
	}

	public List<Ticket> Fetch(String condt) {
		return tciketDAO.select(condt);
	}

	public List<Ticket> FetchAll() {
		return tciketDAO.select("");
	}

	public int lockTicket(int ID, String time) {
		return tciketDAO.lockTicket(ID, time);
	}

	public int unlockTicket(int ID) {
		return tciketDAO.unlockTicket(ID);
	}
}
