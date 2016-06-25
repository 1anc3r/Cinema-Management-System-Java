package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iScheduleDAO;
import me.lancer.cms.model.Schedule;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class ScheduleDAO implements iScheduleDAO {
	@Override
	public int insert(Schedule sched) {
		try {
			String sqlstr = "insert into schedule( studio_id, play_id, sched_time, sched_ticket_price) values( "
					+ sched.getStudioId() + ", " + sched.getPlayId() + ", '" + sched.getTime() + "', "
					+ sched.getPrice() + " )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				sched.setId(rst.getInt(1));
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
	public int update(Schedule sched) {
		int rtn = 0;
		try {
			String sqlstr = "update schedule set studio_id ='" + sched.getStudioId() + "', play_id = "
					+ sched.getPlayId() + ", sched_time = " + sched.getTime() + ", sched_ticket_price = '"
					+ sched.getPrice() + "' ";
			sqlstr += " where sched_id = " + sched.getId();
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
			String sqlstr = "delete from schedule ";
			sqlstr += " where sched_id = " + id;
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
	public List<Schedule> select(String condt) {
		List<Schedule> stuList = null;
		stuList = new LinkedList<Schedule>();
		try {
			String sqlstr = "select sched_id, studio_id, play_id, sched_time, sched_ticket_price from schedule ";
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
					Schedule stu = new Schedule();
					stu.setId(rst.getInt("sched_id"));
					stu.setStudioId(rst.getInt("studio_id"));
					stu.setPlayId(rst.getInt("play_id"));
					stu.setTime(rst.getTimestamp("sched_time"));
					stu.setPrice(rst.getDouble("sched_ticket_price"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return stuList;
	}
}
