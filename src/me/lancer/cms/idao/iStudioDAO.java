package me.lancer.cms.idao;

import java.util.List;

import me.lancer.cms.model.Studio;

public interface iStudioDAO {

	public int insert(Studio stud);

	public int update(Studio stud);

	public int delete(int id);

	public List<Studio> select(String condt);

	public int createSeats(Studio stud);
}
