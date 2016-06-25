package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.Ticket;

public interface iTicketDAO {
	
	public int insert(Ticket ticket);

	public int update(Ticket ticket);

	public int delete(int id);

	public int lockTicket(int id, String time);

	public int unlockTicket(int id);

	public List<Ticket> select(String condt);
}
