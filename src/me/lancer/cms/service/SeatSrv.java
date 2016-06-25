package me.lancer.cms.service;

import java.util.List;

import me.lancer.cms.idao.DAOFactory;
import me.lancer.cms.idao.iSeatDAO;
import me.lancer.cms.model.Seat;

public class SeatSrv {
	
	private iSeatDAO seatDAO=DAOFactory.creatSeatDAO();
	
	public int add(Seat seat){
		return seatDAO.insert(seat); 		
	}
	
	public int modify(Seat seat){
		return seatDAO.update(seat); 		
	}
	
	public int delete(int ID){
		return seatDAO.delete(ID); 		
	}
	
	public List<Seat> Fetch(String condt){
		return seatDAO.select(condt);		
	}
	
	public List<Seat> FetchAll(){
		return seatDAO.select("");		
	}
	
}
