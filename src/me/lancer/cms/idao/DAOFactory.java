package me.lancer.cms.idao;
import me.lancer.cms.dao.*;

public class DAOFactory {
	
	public static iEmployeeDAO creatEmployeeDAO(){
		return new EmployeeDAO();
	}
	
	public static iStudioDAO creatStudioDAO(){
		return new StudioDAO();
	}
	
	public static IDataDictDAO creatDataDictDAO(){
		return new DataDictDAO();
	}

	public static iScheduleDAO creatScheduleDAO(){
		return new ScheduleDAO();
	}
	
	public static iPlayDAO creatPlayDAO(){
		return new PlayDAO();
	}

	public static iSeatDAO creatSeatDAO(){
		return new SeatDAO();
	}

	public static iTicketDAO creatTicketDAO(){
		return new TicketDAO();
	}

	public static iSaleDAO creatSaleDAO(){
		return new SaleDAO();
	}
	
	public static iSaleItemDAO creatSaleItemDAO(){
		return new SaleItemDAO();
	}
}
