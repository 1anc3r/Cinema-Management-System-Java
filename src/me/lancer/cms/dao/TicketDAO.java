package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iTicketDAO;
import me.lancer.cms.model.Ticket;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class TicketDAO implements iTicketDAO {

	@Override
	public int insert(Ticket ticket) {
		try {
			String sqlstr = "insert into ticket( seat_id, sched_id, ticket_price, ticket_status ) values("
					+ ticket.getSeatId() + ", " + ticket.getScheduleId() + ", " + ticket.getPrice() + ", "
					+ ticket.getStatus() + " )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				ticket.setId(rst.getInt(1));
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
	public int update(Ticket ticket) {
		int rtn = 0;
		try {
			String sqlstr = "update ticket set seat_id = " + ticket.getSeatId() + ", sched_id = "
					+ ticket.getScheduleId() + ", ticket_price = " + ticket.getPrice() + ", ticket_status = "
					+ ticket.getStatus() + ", ticket_locked_time = '" + ticket.getLocked_time() + "'";
			sqlstr += " where ticket_id = " + ticket.getId();
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
			String sqlstr = "delete from ticket ";
			sqlstr += " where ticket_id = " + id;
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
	public List<Ticket> select(String condt) {
		List<Ticket> ticketList = null;
		ticketList = new LinkedList<Ticket>();
		try {
			String sqlstr = "select ticket_id, seat_id, sched_id, ticket_price, ticket_status, ticket_locked_time from ticket ";
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
					Ticket ticket = new Ticket();
					ticket.setId(rst.getInt("ticket_id"));
					ticket.setSeatId(rst.getInt("seat_id"));
					ticket.setScheduleId(rst.getInt("sched_id"));
					ticket.setPrice(rst.getInt("ticket_price"));
					ticket.setStatus(rst.getInt("ticket_status"));
					ticket.setLocked_time(rst.getTimestamp("ticket_locked_time"));
					ticketList.add(ticket);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return ticketList;
	}

	@Override
	public int lockTicket(int ID, String time) {
		int rtn = 0;
		try {
			String sqlstr = "update ticket set ticket_status = 1, ticket_locked_time = '" + time + "'";
			sqlstr += " where ticket_id = " + ID;
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
	public int unlockTicket(int ID) {
		int rtn = 0;
		try {
			String sqlstr = "update ticket set ticket_status = 0";
			sqlstr += " where ticket_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sqlstr);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
