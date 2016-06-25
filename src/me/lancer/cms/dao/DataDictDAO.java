package me.lancer.cms.dao;

import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.List;

import me.lancer.cms.idao.IDataDictDAO;
import me.lancer.cms.model.DataDict;
import me.lancer.cms.util.DBUtil;

public class DataDictDAO implements IDataDictDAO {

	@Override
	public int insert(DataDict ddict) {
		try {
			String sqlstr = "insert into data_dict( dict_parent_id,  dict_index , dict_name , dict_value ) values( "
					+ ddict.getSuperId() + ", " + ddict.getIndex() + ", '" + ddict.getName() + "' , '"
					+ ddict.getValue() + "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sqlstr);
			if (rst != null && rst.first()) {
				ddict.setId(rst.getInt(1));
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
	public int update(DataDict ddict) {
		int rtn = 0;
		try {
			String sqlstr = "update data_dict set dict_parent_id = " + ddict.getSuperId() + ", dict_index = "
					+ ddict.getIndex() + ", dict_name = '" + ddict.getName() + "', dict_value = '" + ddict.getValue()
					+ "' ";
			sqlstr += " where dict_id = " + ddict.getId();
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
			String sqlstr = "delete from data_dict ";
			sqlstr += " where dict_id = " + ID;
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
	public List<DataDict> select(String condt) {
		List<DataDict> ddictList = null;
		ddictList = new LinkedList<DataDict>();
		try {
			String sqlstr = "select dict_id, dict_parent_id,  dict_index , dict_name , dict_value from data_dict ";
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
					DataDict ddict = new DataDict();
					ddict.setId(rst.getInt("dict_id"));
					ddict.setSuperId(rst.getInt("dict_parent_id"));
					ddict.setIndex(rst.getInt("dict_index"));
					ddict.setName(rst.getString("dict_name"));
					ddict.setValue(rst.getString("dict_value"));
					ddictList.add(ddict);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return ddictList;
	}

	public List<DataDict> findByID(int id) {
		return select("dict_parent_id = " + id);
	}

	public void findAllSonByID(List<DataDict> list, int id) {
		List<DataDict> childList = findByID(id);
		for (int i = 0; i < childList.size(); i++) {
			if (!hasChildren(childList.get(i).getId()))
				list.add(childList.get(i));
			else {
				findAllSonByID(list, childList.get(i).getId());
			}
		}
	}

	public boolean hasChildren(int id) {
		List<DataDict> list = select("dict_parent_id = " + id);
		return list.size() > 0 ? true : false;
	}
}
