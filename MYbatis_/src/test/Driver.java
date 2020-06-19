package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import dao.ICourseDAO;
import dao.IGradeDAO;
import dao.ISelectionDetailDAO;
import dao.IUserDAO;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import vo.Course;
import vo.Grade;
import vo.SelectionDetail;
import vo.User;

public class Driver {
	public static void main(String[] args) throws Exception {
		// 指定全局配置文件
		String resource = "mybatis-config.xml";
		// 读取配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 构建sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
				
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		User user = new User();
		List<User> userlist=userDAO.query(user);
		
		System.out.println("-----------------------------------------------------");
		System.out.println("欢迎使用武汉纺织大学教务管理系统，请登录！");
		String userName = "";
		String uId = "";
		String password = "";
		String role = "";
		
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入用户名");
		userName= scan.nextLine();
		System.out.println("请输入密码");
		password=scan.nextLine();
		
		boolean success =false;//判断密码是否正确
		for(User users:userlist) {
			String name=users.getUserName();
			String word=users.getPassword();
			if(name.equals(userName) && word.equals(password)) {
				userName=name;
				password=word;
				uId=users.getuId();
				role=users.getRole();
				success = true;//密码正确
				break;
			}
		}
		
		if(success==false) {
			System.out.println("用户名或密码错误！感谢您的使用！");
			return;
		}
		
		
		/*
		 * 登录成功，进入主菜单
		 */
		
		int choice=menu();
		while(choice!=5) {
			switch(choice) {
			case 1:
				studentManage(sqlSessionFactory,role,uId);//进入学生管理系统
				break;
			case 2:
				teacherManage(sqlSessionFactory,role,uId);//进入教师管理系统
				break;
			case 3:
				userManage(sqlSessionFactory,role,uId);//进入用户管理系统
				break;
			case 4:
				changepassword(sqlSessionFactory,password,uId);//修改账户密码
				break;
			case 5:
				return;//退出
			default:
				System.out.print("输入无效！请重新输入:");
				break;
			}
			choice=menu();
		}
		
		System.out.println("感谢您的使用！");
		sqlSession.commit();
		sqlSession.close();
	}


	
	//菜单
	public static int menu() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("武汉纺织大学教务管理系统");
		System.out.println("1.我是学生——>进入学生管理系统");
		System.out.println("2.我是教师——>进入教师管理系统");
		System.out.println("3.我是管理员——>进入用户管理系统");
		System.out.println("4.修改账户密码");
		System.out.println("5.退出");
		System.out.print("请输入:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	
	//进入学生管理系统
	public static void studentManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("学生"))) {
			System.out.println("无权限进入！您当前的身份不是学生！");
			return;
		}else {
			int choose=menu2();
			while(choose!=5) {
				switch(choose) {
					case 1:
						studentView(sqlSessionFactory,uId);//查看当前已选课程
						break;
					case 2:
						boolean insert = studentInsert(sqlSessionFactory,uId);//选课
						if(insert) {
							System.out.println("选课成功！");
						}
						break;
					case 3:
						boolean delete = studentDelete(sqlSessionFactory,uId);//退课
						if(delete) {
							System.out.println("退课成功！");
						}
						break;
					case 4:
						studentGrade(sqlSessionFactory,uId);//查询个人成绩
						break;
					case 5:
						return;//返回主菜单
					default:
						System.out.print("输入无效！请重新输入:");
						break;
				}
				choose=menu2();
			}
		}
		
	}
	
	
	//学生管理系统菜单
	public static int menu2() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("武汉纺织大学教务管理系统——>您当前所处位置：学生管理系统");
		System.out.println("1.查看当前已选课程");
		System.out.println("2.选课");
		System.out.println("3.退课");
		System.out.println("4.查询个人成绩");
		System.out.println("5.返回主菜单");
		System.out.print("请输入:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	//查看当前已选课程
	public static void studentView(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		List<SelectionDetail> detail = seDAO.find(uId);
		
		System.out.println("姓名\t学号\t课程编号\t课程名\t任课教师\t学时\t");
		System.out.println("===\t===\t===\t===\t===\t===\t===\t");
		for(SelectionDetail info:detail) {
			System.out.println(info);
		}
		System.out.println("请按任意键返回主界面");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//选课
	public static boolean studentInsert(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.println("请输入选课信息(姓名，教师id，教师姓名,课程编号,课程名,课程学时,并用逗号隔开):");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|，");//用逗号分隔
		
		String sStudentName=str[0];//学生姓名
		String sTeacherId=str[1];//教师id
		String sTeacherName=str[2];//教师姓名
		String sCourseId=str[3];//课程编号
		String sCourseName=str[4];//课程名
		int sClassHours=Integer.parseInt(str[5]);//课程学时
		

		SelectionDetail sd = new SelectionDetail(uId,sStudentName,sTeacherId,sTeacherName,sCourseId,sCourseName,sClassHours);
		success = seDAO.insert(sd);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//退课
	public static boolean studentDelete(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.println("请输入退选课程的课程编号:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		success = seDAO.delete(sCourseId, uId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//查询个人成绩
	public static void studentGrade(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		
		List<Grade> grade = gradeDAO.find(uId);

		System.out.println("姓名\t学号\t课程编号\t课程名\t成绩\t");
		System.out.println("===\t===\t===\t===\t===\t");
		for(Grade info:grade) {
			System.out.println(info);
		}
		System.out.println("请按任意键返回主界面");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	
	
	//进入教师管理系统
	public static void teacherManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("教师"))) {
			System.out.println("无权限进入！您当前的身份不是教师！");
			return;
		}else {
			int choose=menu3();
			while(choose!=5) {
				switch(choose) {
					case 1:
						teacherView( sqlSessionFactory, uId);//查看我的课程
						break;
					case 2:
						studentName(sqlSessionFactory);//查看选课名单
						break;
					case 3:
						WriteCourseExcel(sqlSessionFactory);//导出选课名单
						break;
					case 4:
						boolean write = gradewrite(sqlSessionFactory);
						if(write) {
							System.out.println("成绩录入成功！");
						}//录入学生成绩
						break;
					case 5:
						WriteGradeExcel(sqlSessionFactory);//导出学生成绩
						break;
					case 6:
						return;//返回主菜单
					default:
						System.out.print("输入无效！请重新输入:");
						break;
				}
				choose=menu3();
			}
		}
			
	}
		
		
	//教师管理系统菜单
	public static int menu3() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("武汉纺织大学教务管理系统——>您当前所处位置：教师管理系统");
		System.out.println("1.查看我的课程");
		System.out.println("2.查看选课名单");
		System.out.println("3.导出选课名单");
		System.out.println("4.录入学生成绩");
		System.out.println("5.导出学生成绩");
		System.out.println("6.返回主菜单");
		System.out.print("请输入:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	//查看我的课程
	public static void teacherView(SqlSessionFactory sqlSessionFactory,String uId) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		
		List<Course> course = courseDAO.query(uId);
	
		System.out.println("教师姓名\t课程编号\t课程名\t学时\t");
		System.out.println("===\t===\t===\t===\t");
		for(Course info:course) {
			System.out.println(info);
		}
		System.out.println("请按任意键返回主界面");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//查看选课名单
	public static void studentName(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		System.out.print("请输入课程编号:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		List<SelectionDetail> detail = seDAO.query(sCourseId);
		
		System.out.println("学生姓名\t学号\t");
		System.out.println("===\t===\t");
		
		 for (int i = 0; i < detail.size(); i++) {
	            System.out.println(detail.get(i).getsStudentName()+"\t"+detail.get(i).getsStudentId());
	        }
		System.out.println("请按任意键返回主界面");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	//导出选课名单(excel文件)
	public static void WriteCourseExcel(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.print("请输入课程编号:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		List<SelectionDetail> detail=seDAO.query(sCourseId);

		//创建可写入的Excel工作簿
		WritableWorkbook wwb = Workbook.createWorkbook(new File("SelectionDetail.xls"));
		//创建工作表
		WritableSheet sheet = wwb.createSheet("选课名单", 0);
		//设置titles
		String[] titles = {"学生姓名","学生学号"};
		//单元格
		Label label = null;
		for(int i=0;i<titles.length;i++) {
			label = new Label(i,0,titles[i]);
			//添加单元格
			sheet.addCell(label);
		}
		for(int j=1;j<=detail.size();j++) {
			label = new Label(0,j,detail.get(j-1).getsStudentName());
			label = new Label(1,j,detail.get(j-1).getsStudentId());
		}
		
		wwb.write();
		wwb.close();
		System.out.println("成功导出"+detail.size()+"条数据到excel文件中");
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//录入学生成绩
	public static boolean gradewrite(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		
		System.out.println("--------录入成绩--------");
		System.out.print("请输入（课程编号，课程名称，学生学号，学生姓名，课程成绩）:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|，");//用逗号分隔
		
		String gCourseId=str[0];
		String gName=str[1];
		String gStudentId=str[2];
		String gStudentName=str[3];
		String mark=str[4];
		
		Grade grade = new Grade(gCourseId,gName,gStudentId,gStudentName,mark);
		success = gradeDAO.insert(grade);
		
		sqlSession.commit();
		sqlSession.close();
		
		return false;
	}
	
	//导出学生成绩(excel文件)
	public static void WriteGradeExcel(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
			
		System.out.print("请输入课程编号:");
		Scanner scan=new Scanner(System.in);
		String gCourseId = scan.nextLine();
			
		List<Grade> detail=gradeDAO.query(gCourseId);

		//创建可写入的Excel工作簿
		WritableWorkbook wwb = Workbook.createWorkbook(new File("StudentMark.xls"));
		//创建工作表
		WritableSheet sheet = wwb.createSheet("学生成绩表", 0);
		//设置titles
		String[] titles = {"学生姓名","学生学号","课程编号","课程名称","学生成绩"};
		//单元格
		Label label = null;
		for(int i=0;i<titles.length;i++) {
			label = new Label(i,0,titles[i]);
			//添加单元格
			sheet.addCell(label);
		}
		for(int j=1;j<=detail.size();j++) {
			label = new Label(0,j,detail.get(j-1).getgStudentName());
			label = new Label(1,j,detail.get(j-1).getgStudentId());
			label = new Label(2,j,detail.get(j-1).getgCourseId());
			label = new Label(3,j,detail.get(j-1).getgName());
			label = new Label(4,j,detail.get(j-1).getMark());
		}
			
		wwb.write();
		wwb.close();
		System.out.println("成功导出"+detail.size()+"条数据到excel文件中");
			
		sqlSession.commit();
		sqlSession.close();
	}
	
		

	//进入用户管理系统
	public static void userManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("管理员"))) {
			System.out.println("无权限进入！您当前的身份不是管理员！");
			return;
		}else {
			int choose=menu4();
			while(choose!=5) {
				switch(choose) {
					case 1:
						boolean insertuser = insertuser(sqlSessionFactory);
						if(insertuser) {
							System.out.println("信息添加成功！");
						}//增加用户信息
						break;
					case 2:
						boolean deleteuser =deleteuser(sqlSessionFactory);
						if(deleteuser) {
							System.out.println("信息删除成功！");
						}//删除用户信息
						break;
					case 3:
						boolean insertcourse = insertcourse(sqlSessionFactory);
						if(insertcourse) {
							System.out.println("课程添加成功！");
						}//增加课程信息
						break;
					case 4:
						boolean deletecourse = deletecourse(sqlSessionFactory);
						if(deletecourse) {
							System.out.println("课程删除成功！");
						}
						break;//删除课程信息
					case 5:
						return;//返回主菜单
					default:
						System.out.print("输入无效！请重新输入:");
						break;
				}
				choose=menu4();
			}
			
		}
				
	}
			
			
	//用户管理系统菜单
	public static int menu4() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("武汉纺织大学教务管理系统——>您当前所处位置：用户管理系统");
		System.out.println("1.增加用户信息");
		System.out.println("2.删除用户信息");
		System.out.println("3.增加课程信息");
		System.out.println("4.删除课程信息");
		System.out.println("5.返回主菜单");
		System.out.print("请输入:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	
	//增加用户
	public static boolean insertuser(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		System.out.print("请输入（用户名，id，密码，角色）:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|，");//用逗号分隔
		
		String userName=str[0];
		String uId=str[1];
		String password=str[2];
		String role=str[3];
		
		User user = new User(userName,uId,password,role);
		success = userDAO.insert(user);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//删除用户
	public static boolean deleteuser(SqlSessionFactory sqlSessionFactory) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		System.out.println("请输入删除的账号id:");
		Scanner scan=new Scanner(System.in);
		String uId = scan.nextLine();
		
		success = userDAO.delete(uId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//增加课程
	public static boolean insertcourse(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);

		System.out.print("请输入（教师id，教师姓名，课程编号，课程名,课程学时）:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|，");//用逗号分隔
		
		String cTeacherId=str[0];
		String cTeacherName=str[1];
		String cCourseId=str[2];
		String cCourseName=str[3];
		int cClassHours=Integer.parseInt(str[4]);
		
		Course course = new Course(cTeacherId,cTeacherName,cCourseId,cCourseName,cClassHours);
		success = courseDAO.insert(course);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//删除课程
	public static boolean deletecourse(SqlSessionFactory sqlSessionFactory) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		
		System.out.println("请输入删除的课程编号:");
		Scanner scan=new Scanner(System.in);
		String cCourseId = scan.nextLine();
		
		success = courseDAO.delete(cCourseId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	
	//修改密码
	public static void changepassword(SqlSessionFactory sqlSessionFactory,String password,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		boolean success = false;//密码是否修改成功
		String newPassword1 = "";
		
		System.out.println("请输入原密码");
		Scanner scan=new Scanner(System.in);
		String oldpassword=scan.nextLine();
			
		if(oldpassword.equals(password)) {
			System.out.print("请输入新的密码:");
			newPassword1=scan.nextLine();
			System.out.print("请输入确认密码:");
			String newPassword2=scan.nextLine();
			if(newPassword1.equals(newPassword2)) {
				success = true;
				//break;
			}else {
				System.out.println("两次密码不一致,请重新输入!");
			}
		}
		
		if(oldpassword.equals(password)==false) {
			System.out.println("密码不正确!");
			System.out.println("返回主菜单");
		}
		
		if(success) {
			User user = new User();
			user.setPassword(newPassword1);
			user.setUserName(uId);
			userDAO.update(user);
			System.out.println("修改成功!");
		}
		sqlSession.commit();
		sqlSession.close();
	}

}
