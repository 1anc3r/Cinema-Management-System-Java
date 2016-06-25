package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.SaleItem;

public interface iSaleItemDAO {

	public int insert(SaleItem saleItem);

	public int update(SaleItem saleItem);

	public int delete(int id);

	public List<SaleItem> select(String condt);
}
