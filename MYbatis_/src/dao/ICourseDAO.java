package dao;

import java.util.List;

import vo.Course;

public interface ICourseDAO {
	//����һ�ſγ���Ϣ
	public boolean insert(Course course) throws Exception;
		
	//ɾ��һ�ſγ�
	public boolean delete(String cCourseId) throws Exception;
		
	//����һ�ſγ���Ϣ
	public boolean update(String cCourseId) throws Exception;
		
	//��ѯ�γ���Ϣ
	public List<Course> query(String cTeacherId) throws Exception;
	
}
