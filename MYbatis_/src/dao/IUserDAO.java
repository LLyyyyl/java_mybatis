package dao;

import java.util.List;

import vo.User;

public interface IUserDAO {
	//���Ӽ�¼
	public boolean insert(User user) throws Exception;
	
	//��������(id)ɾ����¼
	public boolean delete(String uId) throws Exception;
	
	//��������(id)������Ϣ
	public boolean update(User user) throws Exception;
	
	//��������(id)��ѯ��Ϣ
	public User find(String uId) throws Exception;
	
	//����������ѯ��Ϣ:��ѯ������װ��user�����У���user�����ĳ����Ա����ֵΪnull�����ʾ��ѯʱ���Ը��ֶβ�ѯ����
	public List<User> query(User user) throws Exception;
	
}
