package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iSaleDAO;
import me.lancer.cms.model.Sale;
import me.lancer.cms.model.Ticket;

public class SaleSrv {

	private iSaleDAO saleDAO = DAOFactory.creatSaleDAO();

	public int add(Sale sale) {
		return saleDAO.insert(sale);
	}

	public int modify(Sale sale) {
		return saleDAO.update(sale);
	}

	public int delete(int ID) {
		return saleDAO.delete(ID);
	}

	public boolean doSale(List<Ticket> tickets, Sale sale) {
		return saleDAO.doSale(tickets, sale);
	}

	public List<Sale> Fetch(String condt) {
		return saleDAO.select(condt);
	}

	public List<Sale> FetchAll() {
		return saleDAO.select("");
	}
}
