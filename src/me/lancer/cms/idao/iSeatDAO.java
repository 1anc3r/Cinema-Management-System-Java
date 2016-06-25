package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.Seat;


public interface iSeatDAO {

	public int insert(Seat seat);

	public int update(Seat seat);

	public int delete(int id);

	public List<Seat> select(String condt);
}
