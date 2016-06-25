package me.lancer.cms.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.lancer.cms.model.Sale;
import me.lancer.cms.model.Schedule;
import me.lancer.cms.model.Seat;
import me.lancer.cms.model.Ticket;

public class SellTicketHandler {

	private List<Ticket> ticketList;

	public Ticket makeNewTicket(Schedule sched, Seat seat) {
		Ticket ticket = new Ticket();
		ticket.setSeatId(seat.getId());
		ticket.setScheduleId(sched.getId());
		ticket.setPrice((float) sched.getPrice());
		ticket.setStatus(0);
		ticket.setSchedule(sched);
		ticket.setSeat(seat);
		ticket.setPlayName(new PlaySrv().Fetch("play_id=" + sched.getPlayId()).get(0).getName());
		new TicketSrv().add(ticket);
		return ticket;
	}

	public void makeNewSale() {
		ticketList = new ArrayList<Ticket>();
	}

	public void addTicket(Ticket ticket) {
		ticketList.add(ticket);
		Date date = new Date();
		TicketSrv ticketSrv = new TicketSrv();
		ticketSrv.lockTicket(ticket.getId(), DateFormat.getDateTimeInstance().format(date));
		ticket.setCurrentLockedTime(date);
	}

	public void removeTicket(Ticket ticket) {
		ticketList.remove(ticket);
		TicketSrv ticketSrv = new TicketSrv();
		ticketSrv.unlockTicket(ticket.getId());
		ticket.setCurrentLockedTime(null);
	}

	public void removeAllTicket() {
		for (Ticket item : ticketList) {
			TicketSrv ticketSrv = new TicketSrv();
			ticketSrv.unlockTicket(item.getId());
			item.setCurrentLockedTime(null);
		}
		ticketList.removeAll(ticketList);
	}

	public String getInfo() {
		int i = 0;
		double price = 0;
		String info = "";
		for (Ticket t : ticketList) {
			info += "影片：" + t.getPlayName() + "\n";
			info += "场次：" + DateFormat.getDateTimeInstance().format(t.getSchedule().getTime()) + "\n";
			info += "座位：" + t.getSeat().getRow() + "排" + t.getSeat().getColumn() + "座\n";
			info += "价格：" + t.getSchedule().getPrice() + "元\n\n";
			i++;
			price += t.getSchedule().getPrice();
		}
		if (ticketList.size() > 0) {
			info += "=================\n";
			info += "共" + i + "张票  " + price + "元\n";
		}
		return info;
	}

	public boolean isTicketSelected(Ticket ticket) {
		for (Ticket t : ticketList) {
			if (t.getId() == ticket.getId())
				return true;
		}
		return false;
	}

	public void clearSale() {
		while (ticketList.size() > 0) {
			removeTicket(ticketList.get(0));
		}
		makeNewSale();
	}

	public boolean doSale(Sale sale) {
		if (new SaleSrv().doSale(ticketList, sale)) {
			makeNewSale();
			return true;
		}
		return false;
	}
}
