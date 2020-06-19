package dao;

import java.util.List;

import vo.Course;

public interface ICourseDAO {
	//增加一门课程信息
	public boolean insert(Course course) throws Exception;
		
	//删除一门课程
	public boolean delete(String cCourseId) throws Exception;
		
	//更改一门课程信息
	public boolean update(String cCourseId) throws Exception;
		
	//查询课程信息
	public List<Course> query(String cTeacherId) throws Exception;
	
}
