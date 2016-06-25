package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.IDataDictDAO;
import me.lancer.cms.model.DataDict;

public class DataDictSrv {

private IDataDictDAO dictDAO=DAOFactory.creatDataDictDAO();
	
	public int add(DataDict ddict){
		return dictDAO.insert(ddict); 		
	}
	
	public int modify(DataDict ddict){
		return dictDAO.update(ddict); 		
	}
	
	public int delete(int id){
		return dictDAO.delete(id); 		
	}
	
	public List<DataDict> Fetch(String condt){
		return dictDAO.select(condt);		
	}
	
	public List<DataDict> FetchAll(){
		return dictDAO.select("");		
	}
	
	public List<DataDict> findByID(int id) {
		return dictDAO.findByID(id);
	}
	
	public void findAllSonByID(List<DataDict> list, int id){
		dictDAO.findAllSonByID(list, id);
	}
	
	public boolean hasChildren(int id){
		return dictDAO.hasChildren(id);
	}
	
}
