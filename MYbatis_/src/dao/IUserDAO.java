package dao;

import java.util.List;

import vo.User;

public interface IUserDAO {
	//增加记录
	public boolean insert(User user) throws Exception;
	
	//根据主键(id)删除记录
	public boolean delete(String uId) throws Exception;
	
	//根据主键(id)更改信息
	public boolean update(User user) throws Exception;
	
	//根据主键(id)查询信息
	public User find(String uId) throws Exception;
	
	//根据条件查询信息:查询条件封装在user对象中，若user对象的某个成员变量值为null，则表示查询时忽略该字段查询条件
	public List<User> query(User user) throws Exception;
	
}
