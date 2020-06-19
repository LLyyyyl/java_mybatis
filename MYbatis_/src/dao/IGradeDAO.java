package dao;

import java.util.List;

import vo.Grade;

public interface IGradeDAO {
	//增加记录
	public boolean insert(Grade grade) throws Exception;
	
	//根据主键(课程号和学生学号)删除记录
	public boolean delete(String gCourseId,String gStudentId) throws Exception;
	
	//根据主键(课程号和学生学号)更改成绩
	public boolean update(String gCourseId,String gStudentId) throws Exception;
	
	//根据学生id查询成绩
	public List<Grade> find(String gStudentId) throws Exception;
	
	//根据课程编号查询成绩(即教师端查询成绩的方式)
	public List<Grade> query(String gCourseId) throws Exception;
}
