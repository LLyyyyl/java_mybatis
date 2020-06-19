package dao;

import java.util.List;

import vo.Grade;

public interface IGradeDAO {
	//���Ӽ�¼
	public boolean insert(Grade grade) throws Exception;
	
	//��������(�γ̺ź�ѧ��ѧ��)ɾ����¼
	public boolean delete(String gCourseId,String gStudentId) throws Exception;
	
	//��������(�γ̺ź�ѧ��ѧ��)���ĳɼ�
	public boolean update(String gCourseId,String gStudentId) throws Exception;
	
	//����ѧ��id��ѯ�ɼ�
	public List<Grade> find(String gStudentId) throws Exception;
	
	//���ݿγ̱�Ų�ѯ�ɼ�(����ʦ�˲�ѯ�ɼ��ķ�ʽ)
	public List<Grade> query(String gCourseId) throws Exception;
}
