package dao;

import java.util.List;

import vo.SelectionDetail;

public interface ISelectionDetailDAO {
	//����һ��ѡ����Ϣ
	public boolean insert(SelectionDetail selectionDetail) throws Exception;
		
	//ɾ��һ��ѡ����Ϣ
	public boolean delete(String sCourseId,String sStudentId) throws Exception;
		
	//����ѡ����Ϣ
	public boolean update(String sCourseId,String sStudentId) throws Exception;
		
	//����ѧ��ѧ�Ų�ѯѡ����Ϣ
	public List<SelectionDetail> find(String sStudentId) throws Exception;
	
	//���ݿγ̱�Ų�ѯѡ����Ϣ
	public List<SelectionDetail> query(String sCourseId) throws Exception;
}
