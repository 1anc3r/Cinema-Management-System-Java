package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iPlayDAO;
import me.lancer.cms.model.Play;

public class PlaySrv {
	
	private iPlayDAO plyDAO=DAOFactory.creatPlayDAO();
	
	public int add(Play ply){
		return plyDAO.insert(ply); 		
	}
	
	public int modify(Play ply){
		return plyDAO.update(ply); 		
	}
	
	public int delete(int ID){
		return plyDAO.delete(ID); 		
	}
	
	public List<Play> Fetch(String condt){
		return plyDAO.select(condt);		
	}
	
	public List<Play> FetchAll(){
		return plyDAO.select("");		
	}
	
	public List<Play> selectScheduledPlay(String condt){
		return plyDAO.selectScheduledPlay("");
	}
}
