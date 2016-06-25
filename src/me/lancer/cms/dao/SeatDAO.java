package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iSeatDAO;
import me.lancer.cms.model.Seat;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class SeatDAO implements iSeatDAO {

	@Override
	public int insert(Seat seat) {
		try {
			String sqlstr = "insert into seat( studio_id, seat_row, seat_column, seat_status ) values( "
					+ seat.getStudioId() + ", " + seat.getRow() + ", " + seat.getColumn() + ", " + seat.getSeatStatus()
					+ " )";
			System.out.println(sqlstr);
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				seat.setId(rst.getInt(1));
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
	public int update(Seat seat) {
		int rtn = 0;
		try {
			String sqlstr = "update seat set studio_id = " + seat.getStudioId() + ", seat_row = " + seat.getRow()
					+ ", seat_column = " + seat.getColumn() + ", seat_status = " + seat.getSeatStatus();
			sqlstr += " where seat_id = " + seat.getId();
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
			String sqlstr = "delete from seat ";
			sqlstr += " where seat_id = " + id;
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
	public List<Seat> select(String condt) {
		List<Seat> seatList = null;
		seatList = new LinkedList<Seat>();
		try {
			String sqlstr = "select * from seat ";
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
					Seat seat = new Seat();
					seat.setId(rst.getInt("seat_id"));
					seat.setStudioId(rst.getInt("studio_id"));
					seat.setRow(rst.getInt("seat_row"));
					seat.setColumn(rst.getInt("seat_column"));
					seat.setSeatStatus(rst.getInt("seat_status"));
					seatList.add(seat);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return seatList;
	}
}
