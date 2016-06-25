package me.lancer.cms.dao;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.iSaleItemDAO;
import me.lancer.cms.model.SaleItem;
import me.lancer.cms.util.DBUtil;

import java.sql.ResultSet;

public class SaleItemDAO implements iSaleItemDAO {
	@Override
	public int insert(SaleItem saleItem) {
		try {
			String sqlstr = "insert into sale_item( ticket_id, sale_id, sale_item_price ) values( "
					+ saleItem.getTicketId() + ", " + saleItem.getSaleId() + ", " + saleItem.getPrice() + " )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				saleItem.setId(rst.getInt(1));
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
	public int update(SaleItem saleItem) {
		int rtn = 0;
		try {
			String sqlstr = "update sale_item set ticket_id = " + saleItem.getTicketId() + ", sale_ID = '"
					+ saleItem.getSaleId() + ", sale_item_price='" + saleItem.getPrice();
			sqlstr += " where sale_item_id = " + saleItem.getId();
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
			String sqlstr = "delete from sale_item ";
			sqlstr += " where sale_item_id = " + id;
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
	public List<SaleItem> select(String condt) {
		List<SaleItem> saleItemList = null;
		saleItemList = new LinkedList<SaleItem>();
		try {
			String sqlstr = "select sale_item_id, ticket_id, sale_ID, sale_item_price from sale_item ";
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
					SaleItem sale_item = new SaleItem();
					sale_item.setId(rst.getInt("sale_item_id"));
					sale_item.setTicketId(rst.getInt("ticket_id"));
					sale_item.setSaleId(rst.getInt("sale_ID"));
					sale_item.setPrice(rst.getInt("sale_item_price"));
					saleItemList.add(sale_item);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return saleItemList;
	}
}
