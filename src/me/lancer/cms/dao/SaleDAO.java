package me.lancer.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iSaleDAO;
import me.lancer.cms.model.Sale;
import me.lancer.cms.model.Ticket;
import me.lancer.cms.util.DBUtil;

public class SaleDAO implements iSaleDAO {

	DBUtil db;
	Connection con;
	float prices = 0;

	@Override
	public int insert(Sale sale) {
		try {
			String sqlstr = "insert into sale( emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status ) values( "
					+ sale.getEmpId() + ", '" + sale.getTime() + "', " + sale.getPayment() + ", " + sale.getChange()
					+ ", " + sale.getType() + ", " + sale.getStatus() + " )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				sale.setId(rst.getInt(1));
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
	public boolean doSale(List<Ticket> tickets, Sale sale) {
		try {
			int id = -1;
			db = new DBUtil();
			db.openConnection();
			con = db.getConn();
			con.setAutoCommit(false);
			String sqlstr = "insert into sale( emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status ) VALUES( ?, ?, ?, 0, 1, 1 )";
			PreparedStatement prep = con.prepareStatement(sqlstr, PreparedStatement.RETURN_GENERATED_KEYS);
			prep.setInt(1, sale.getEmpId());
			prep.setTimestamp(2, new Timestamp(new Date().getTime()));
			prep.setFloat(3, sale.getPayment());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			if (id > 0) {
				for (Ticket t : tickets) {
					double price = t.getSchedule().getPrice();
					String sqlstr2 = "insert into sale_item( ticket_id, sale_id, sale_item_price ) VALUES( " + t.getId()
							+ ", " + id + ", " + price + ")";
					prices += price;
					int flag2 = db.execCommand(sqlstr2);
					if (flag2 == 1) {
						String sqlstr3 = "update ticket set ticket_status = 9 where ticket_id = " + t.getId();
						int flag3 = db.execCommand(sqlstr3);
						if (flag3 != 1) {
							return false;
						}
					} else
						return false;
				}
			}
			String sqlstr4 = "update sale set sale_change = " + (sale.getPayment() - prices) + " where sale_id = " + id;
			int flag4 = db.execCommand(sqlstr4);
			if (flag4 != 1) {
				return false;
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public int update(Sale sale) {
		int rtn = 0;
		try {
			String sqlstr = "update sale set emp_id = " + sale.getEmpId() + ", sale_time = '" + sale.getTime()
					+ "', sale_payment = " + sale.getPayment() + ", sale_change = " + sale.getChange()
					+ ", sale_type = " + sale.getType() + ", sale_status = " + sale.getStatus();
			sqlstr += " where sale_id = " + sale.getId();
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
			String sqlstr = "delete from sale ";
			sqlstr += " where sale_id = " + id;
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
	public List<Sale> select(String condt) {
		List<Sale> saleList = null;
		saleList = new LinkedList<Sale>();
		try {
			String sqlstr = "select sale_id, emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status from sale ";
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
					Sale sale = new Sale();
					sale.setId(rst.getInt("sale_id"));
					sale.setEmpId(rst.getInt("emp_id"));
					sale.setTime(rst.getTimestamp("sale_time"));
					sale.setPayment(rst.getFloat("sale_payment"));
					sale.setChange(rst.getFloat("sale_change"));
					sale.setType(rst.getInt("sale_type"));
					sale.setStatus(rst.getInt("sale_status"));
					saleList.add(sale);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return saleList;
	}
}
