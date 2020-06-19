package dao;

import java.util.List;

import vo.SelectionDetail;

public interface ISelectionDetailDAO {
	//增加一条选课信息
	public boolean insert(SelectionDetail selectionDetail) throws Exception;
		
	//删除一条选课信息
	public boolean delete(String sCourseId,String sStudentId) throws Exception;
		
	//更改选课信息
	public boolean update(String sCourseId,String sStudentId) throws Exception;
		
	//根据学生学号查询选课信息
	public List<SelectionDetail> find(String sStudentId) throws Exception;
	
	//根据课程编号查询选课信息
	public List<SelectionDetail> query(String sCourseId) throws Exception;
}
