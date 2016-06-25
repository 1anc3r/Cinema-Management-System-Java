package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iSaleItemDAO;
import me.lancer.cms.model.SaleItem;

public class SaleItemSrv {

	private iSaleItemDAO saleDAO = DAOFactory.creatSaleItemDAO();

	public int add(SaleItem saleItem) {
		return saleDAO.insert(saleItem);
	}

	public int modify(SaleItem saleItem) {
		return saleDAO.update(saleItem);
	}

	public int delete(int ID) {
		return saleDAO.delete(ID);
	}

	public List<SaleItem> Fetch(String condt) {
		return saleDAO.select(condt);
	}

	public List<SaleItem> FetchAll() {
		return saleDAO.select("");
	}
}
