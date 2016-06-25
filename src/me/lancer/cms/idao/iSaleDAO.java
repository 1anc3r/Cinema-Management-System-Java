package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.Sale;
import me.lancer.cms.model.Ticket;

public interface iSaleDAO {

	public boolean doSale(List<Ticket> tickets, Sale sale);

	public int insert(Sale sale);

	public int update(Sale sale);

	public int delete(int id);

	public List<Sale> select(String condt);
}
