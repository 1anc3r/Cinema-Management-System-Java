package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iPlayDAO;
import me.lancer.cms.model.Play;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class PlayDAO implements iPlayDAO {

	@Override
	public int insert(Play ply) {
		try {
			String sqlstr = "insert into play( play_type_id, play_lang_id, play_name, play_introduction, play_image, play_length, play_ticket_price, play_status ) values( "
					+ ply.getTypeId() + ", " + ply.getLangId() + ", '" + ply.getName() + "', '" + ply.getIntroduction()
					+ "', " + ply.getImage() + ", " + ply.getLength() + ", " + ply.getPrice() + ", " + ply.getStatus()
					+ " )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				ply.setId(rst.getInt(1));
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
	public int update(Play ply) {
		int rtn = 0;
		try {
			String sqlstr = "update play set " + "play_id = " + ply.getId() + ", play_type_id = " + ply.getTypeId()
					+ ", play_lang_id = " + ply.getLangId() + ", play_name = '" + ply.getName()
					+ "', play_introduction = '" + ply.getIntroduction() + "', play_image = " + ply.getImage()
					+ ", play_length = " + ply.getLength() + ", play_ticket_price = " + ply.getPrice()
					+ ", play_status = " + ply.getStatus();

			sqlstr += " where play_id = " + ply.getId();
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
	public int delete(int ID) {
		int rtn = 0;
		try {
			String sqlstr = "delete from play ";
			sqlstr += " where play_id = " + ID;
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
	public List<Play> select(String condt) {
		List<Play> plyList = null;
		plyList = new LinkedList<Play>();
		try {
			String sqlstr = "select play_id ,play_type_id, play_lang_id, play_name, play_introduction, play_image, play_length, play_ticket_price, play_status from play ";
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
					Play ply = new Play();
					ply.setId(rst.getInt("play_id"));
					ply.setTypeId(rst.getInt("play_type_id"));
					ply.setLangId(rst.getInt("play_lang_id"));
					ply.setName(rst.getString("play_name"));
					ply.setIntroduction(rst.getString("play_introduction"));
					ply.setImage(rst.getBytes("play_image"));
					ply.setLength(rst.getInt("play_length"));
					ply.setPrice(rst.getInt("play_ticket_price"));
					ply.setStatus(rst.getInt("play_status"));
					plyList.add(ply);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return plyList;
	}

	@Override
	public List<Play> selectScheduledPlay(String condt) {
		List<Play> stuList = null;
		stuList = new LinkedList<Play>();
		try {
			String sqlstr = "select play.play_id, play_name from play, schedule where play.play_id=schedule.play_id ";
			condt.trim();
			if (!condt.isEmpty())
				sqlstr += " where " + condt;
			sqlstr += " group by play_name";
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				return null;
			}
			ResultSet rst = db.execQuery(sqlstr);
			if (rst != null) {
				while (rst.next()) {
					Play stu = new Play();
					stu.setId(rst.getInt("play_id"));
					stu.setName(rst.getString("play_name"));
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
