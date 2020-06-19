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
		// ָ��ȫ�������ļ�
		String resource = "mybatis-config.xml";
		// ��ȡ�����ļ�
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// ����sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// ��ȡsqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
				
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		User user = new User();
		List<User> userlist=userDAO.query(user);
		
		System.out.println("-----------------------------------------------------");
		System.out.println("��ӭʹ���人��֯��ѧ�������ϵͳ�����¼��");
		String userName = "";
		String uId = "";
		String password = "";
		String role = "";
		
		Scanner scan=new Scanner(System.in);
		System.out.println("�������û���");
		userName= scan.nextLine();
		System.out.println("����������");
		password=scan.nextLine();
		
		boolean success =false;//�ж������Ƿ���ȷ
		for(User users:userlist) {
			String name=users.getUserName();
			String word=users.getPassword();
			if(name.equals(userName) && word.equals(password)) {
				userName=name;
				password=word;
				uId=users.getuId();
				role=users.getRole();
				success = true;//������ȷ
				break;
			}
		}
		
		if(success==false) {
			System.out.println("�û�����������󣡸�л����ʹ�ã�");
			return;
		}
		
		
		/*
		 * ��¼�ɹ����������˵�
		 */
		
		int choice=menu();
		while(choice!=5) {
			switch(choice) {
			case 1:
				studentManage(sqlSessionFactory,role,uId);//����ѧ������ϵͳ
				break;
			case 2:
				teacherManage(sqlSessionFactory,role,uId);//�����ʦ����ϵͳ
				break;
			case 3:
				userManage(sqlSessionFactory,role,uId);//�����û�����ϵͳ
				break;
			case 4:
				changepassword(sqlSessionFactory,password,uId);//�޸��˻�����
				break;
			case 5:
				return;//�˳�
			default:
				System.out.print("������Ч������������:");
				break;
			}
			choice=menu();
		}
		
		System.out.println("��л����ʹ�ã�");
		sqlSession.commit();
		sqlSession.close();
	}


	
	//�˵�
	public static int menu() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("�人��֯��ѧ�������ϵͳ");
		System.out.println("1.����ѧ������>����ѧ������ϵͳ");
		System.out.println("2.���ǽ�ʦ����>�����ʦ����ϵͳ");
		System.out.println("3.���ǹ���Ա����>�����û�����ϵͳ");
		System.out.println("4.�޸��˻�����");
		System.out.println("5.�˳�");
		System.out.print("������:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	
	//����ѧ������ϵͳ
	public static void studentManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("ѧ��"))) {
			System.out.println("��Ȩ�޽��룡����ǰ����ݲ���ѧ����");
			return;
		}else {
			int choose=menu2();
			while(choose!=5) {
				switch(choose) {
					case 1:
						studentView(sqlSessionFactory,uId);//�鿴��ǰ��ѡ�γ�
						break;
					case 2:
						boolean insert = studentInsert(sqlSessionFactory,uId);//ѡ��
						if(insert) {
							System.out.println("ѡ�γɹ���");
						}
						break;
					case 3:
						boolean delete = studentDelete(sqlSessionFactory,uId);//�˿�
						if(delete) {
							System.out.println("�˿γɹ���");
						}
						break;
					case 4:
						studentGrade(sqlSessionFactory,uId);//��ѯ���˳ɼ�
						break;
					case 5:
						return;//�������˵�
					default:
						System.out.print("������Ч������������:");
						break;
				}
				choose=menu2();
			}
		}
		
	}
	
	
	//ѧ������ϵͳ�˵�
	public static int menu2() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("�人��֯��ѧ�������ϵͳ����>����ǰ����λ�ã�ѧ������ϵͳ");
		System.out.println("1.�鿴��ǰ��ѡ�γ�");
		System.out.println("2.ѡ��");
		System.out.println("3.�˿�");
		System.out.println("4.��ѯ���˳ɼ�");
		System.out.println("5.�������˵�");
		System.out.print("������:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	//�鿴��ǰ��ѡ�γ�
	public static void studentView(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		List<SelectionDetail> detail = seDAO.find(uId);
		
		System.out.println("����\tѧ��\t�γ̱��\t�γ���\t�ον�ʦ\tѧʱ\t");
		System.out.println("===\t===\t===\t===\t===\t===\t===\t");
		for(SelectionDetail info:detail) {
			System.out.println(info);
		}
		System.out.println("�밴���������������");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//ѡ��
	public static boolean studentInsert(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.println("������ѡ����Ϣ(��������ʦid����ʦ����,�γ̱��,�γ���,�γ�ѧʱ,���ö��Ÿ���):");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|��");//�ö��ŷָ�
		
		String sStudentName=str[0];//ѧ������
		String sTeacherId=str[1];//��ʦid
		String sTeacherName=str[2];//��ʦ����
		String sCourseId=str[3];//�γ̱��
		String sCourseName=str[4];//�γ���
		int sClassHours=Integer.parseInt(str[5]);//�γ�ѧʱ
		

		SelectionDetail sd = new SelectionDetail(uId,sStudentName,sTeacherId,sTeacherName,sCourseId,sCourseName,sClassHours);
		success = seDAO.insert(sd);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//�˿�
	public static boolean studentDelete(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.println("��������ѡ�γ̵Ŀγ̱��:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		success = seDAO.delete(sCourseId, uId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//��ѯ���˳ɼ�
	public static void studentGrade(SqlSessionFactory sqlSessionFactory,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		
		List<Grade> grade = gradeDAO.find(uId);

		System.out.println("����\tѧ��\t�γ̱��\t�γ���\t�ɼ�\t");
		System.out.println("===\t===\t===\t===\t===\t");
		for(Grade info:grade) {
			System.out.println(info);
		}
		System.out.println("�밴���������������");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	
	
	//�����ʦ����ϵͳ
	public static void teacherManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("��ʦ"))) {
			System.out.println("��Ȩ�޽��룡����ǰ����ݲ��ǽ�ʦ��");
			return;
		}else {
			int choose=menu3();
			while(choose!=5) {
				switch(choose) {
					case 1:
						teacherView( sqlSessionFactory, uId);//�鿴�ҵĿγ�
						break;
					case 2:
						studentName(sqlSessionFactory);//�鿴ѡ������
						break;
					case 3:
						WriteCourseExcel(sqlSessionFactory);//����ѡ������
						break;
					case 4:
						boolean write = gradewrite(sqlSessionFactory);
						if(write) {
							System.out.println("�ɼ�¼��ɹ���");
						}//¼��ѧ���ɼ�
						break;
					case 5:
						WriteGradeExcel(sqlSessionFactory);//����ѧ���ɼ�
						break;
					case 6:
						return;//�������˵�
					default:
						System.out.print("������Ч������������:");
						break;
				}
				choose=menu3();
			}
		}
			
	}
		
		
	//��ʦ����ϵͳ�˵�
	public static int menu3() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("�人��֯��ѧ�������ϵͳ����>����ǰ����λ�ã���ʦ����ϵͳ");
		System.out.println("1.�鿴�ҵĿγ�");
		System.out.println("2.�鿴ѡ������");
		System.out.println("3.����ѡ������");
		System.out.println("4.¼��ѧ���ɼ�");
		System.out.println("5.����ѧ���ɼ�");
		System.out.println("6.�������˵�");
		System.out.print("������:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	//�鿴�ҵĿγ�
	public static void teacherView(SqlSessionFactory sqlSessionFactory,String uId) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		
		List<Course> course = courseDAO.query(uId);
	
		System.out.println("��ʦ����\t�γ̱��\t�γ���\tѧʱ\t");
		System.out.println("===\t===\t===\t===\t");
		for(Course info:course) {
			System.out.println(info);
		}
		System.out.println("�밴���������������");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//�鿴ѡ������
	public static void studentName(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		System.out.print("������γ̱��:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		List<SelectionDetail> detail = seDAO.query(sCourseId);
		
		System.out.println("ѧ������\tѧ��\t");
		System.out.println("===\t===\t");
		
		 for (int i = 0; i < detail.size(); i++) {
	            System.out.println(detail.get(i).getsStudentName()+"\t"+detail.get(i).getsStudentId());
	        }
		System.out.println("�밴���������������");
		System.in.read();
		
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	//����ѡ������(excel�ļ�)
	public static void WriteCourseExcel(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
		
		System.out.print("������γ̱��:");
		Scanner scan=new Scanner(System.in);
		String sCourseId = scan.nextLine();
		
		List<SelectionDetail> detail=seDAO.query(sCourseId);

		//������д���Excel������
		WritableWorkbook wwb = Workbook.createWorkbook(new File("SelectionDetail.xls"));
		//����������
		WritableSheet sheet = wwb.createSheet("ѡ������", 0);
		//����titles
		String[] titles = {"ѧ������","ѧ��ѧ��"};
		//��Ԫ��
		Label label = null;
		for(int i=0;i<titles.length;i++) {
			label = new Label(i,0,titles[i]);
			//��ӵ�Ԫ��
			sheet.addCell(label);
		}
		for(int j=1;j<=detail.size();j++) {
			label = new Label(0,j,detail.get(j-1).getsStudentName());
			label = new Label(1,j,detail.get(j-1).getsStudentId());
		}
		
		wwb.write();
		wwb.close();
		System.out.println("�ɹ�����"+detail.size()+"�����ݵ�excel�ļ���");
		
		sqlSession.commit();
		sqlSession.close();
	}
	
	//¼��ѧ���ɼ�
	public static boolean gradewrite(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		
		System.out.println("--------¼��ɼ�--------");
		System.out.print("�����루�γ̱�ţ��γ����ƣ�ѧ��ѧ�ţ�ѧ���������γ̳ɼ���:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|��");//�ö��ŷָ�
		
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
	
	//����ѧ���ɼ�(excel�ļ�)
	public static void WriteGradeExcel(SqlSessionFactory sqlSessionFactory) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IGradeDAO gradeDAO=sqlSession.getMapper(IGradeDAO.class);
		ISelectionDetailDAO seDAO=sqlSession.getMapper(ISelectionDetailDAO.class);
			
		System.out.print("������γ̱��:");
		Scanner scan=new Scanner(System.in);
		String gCourseId = scan.nextLine();
			
		List<Grade> detail=gradeDAO.query(gCourseId);

		//������д���Excel������
		WritableWorkbook wwb = Workbook.createWorkbook(new File("StudentMark.xls"));
		//����������
		WritableSheet sheet = wwb.createSheet("ѧ���ɼ���", 0);
		//����titles
		String[] titles = {"ѧ������","ѧ��ѧ��","�γ̱��","�γ�����","ѧ���ɼ�"};
		//��Ԫ��
		Label label = null;
		for(int i=0;i<titles.length;i++) {
			label = new Label(i,0,titles[i]);
			//��ӵ�Ԫ��
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
		System.out.println("�ɹ�����"+detail.size()+"�����ݵ�excel�ļ���");
			
		sqlSession.commit();
		sqlSession.close();
	}
	
		

	//�����û�����ϵͳ
	public static void userManage(SqlSessionFactory sqlSessionFactory,String role,String uId) throws Exception{
		if(!(role.equals("����Ա"))) {
			System.out.println("��Ȩ�޽��룡����ǰ����ݲ��ǹ���Ա��");
			return;
		}else {
			int choose=menu4();
			while(choose!=5) {
				switch(choose) {
					case 1:
						boolean insertuser = insertuser(sqlSessionFactory);
						if(insertuser) {
							System.out.println("��Ϣ��ӳɹ���");
						}//�����û���Ϣ
						break;
					case 2:
						boolean deleteuser =deleteuser(sqlSessionFactory);
						if(deleteuser) {
							System.out.println("��Ϣɾ���ɹ���");
						}//ɾ���û���Ϣ
						break;
					case 3:
						boolean insertcourse = insertcourse(sqlSessionFactory);
						if(insertcourse) {
							System.out.println("�γ���ӳɹ���");
						}//���ӿγ���Ϣ
						break;
					case 4:
						boolean deletecourse = deletecourse(sqlSessionFactory);
						if(deletecourse) {
							System.out.println("�γ�ɾ���ɹ���");
						}
						break;//ɾ���γ���Ϣ
					case 5:
						return;//�������˵�
					default:
						System.out.print("������Ч������������:");
						break;
				}
				choose=menu4();
			}
			
		}
				
	}
			
			
	//�û�����ϵͳ�˵�
	public static int menu4() {
		int choice;
		System.out.println("-----------------------------------------------------");
		System.out.println("�人��֯��ѧ�������ϵͳ����>����ǰ����λ�ã��û�����ϵͳ");
		System.out.println("1.�����û���Ϣ");
		System.out.println("2.ɾ���û���Ϣ");
		System.out.println("3.���ӿγ���Ϣ");
		System.out.println("4.ɾ���γ���Ϣ");
		System.out.println("5.�������˵�");
		System.out.print("������:");
		Scanner scan=new Scanner(System.in);
		choice=scan.nextInt();
		return choice;
	}
	
	
	//�����û�
	public static boolean insertuser(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		System.out.print("�����루�û�����id�����룬��ɫ��:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|��");//�ö��ŷָ�
		
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
	
	//ɾ���û�
	public static boolean deleteuser(SqlSessionFactory sqlSessionFactory) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		System.out.println("������ɾ�����˺�id:");
		Scanner scan=new Scanner(System.in);
		String uId = scan.nextLine();
		
		success = userDAO.delete(uId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	//���ӿγ�
	public static boolean insertcourse(SqlSessionFactory sqlSessionFactory) throws Exception {
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);

		System.out.print("�����루��ʦid����ʦ�������γ̱�ţ��γ���,�γ�ѧʱ��:");
		Scanner scan=new Scanner(System.in);
		String info = scan.nextLine();
		
		String[] str=info.split(",|��");//�ö��ŷָ�
		
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
	
	//ɾ���γ�
	public static boolean deletecourse(SqlSessionFactory sqlSessionFactory) throws Exception{
		boolean success = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ICourseDAO courseDAO=sqlSession.getMapper(ICourseDAO.class);
		
		System.out.println("������ɾ���Ŀγ̱��:");
		Scanner scan=new Scanner(System.in);
		String cCourseId = scan.nextLine();
		
		success = courseDAO.delete(cCourseId);
		
		sqlSession.commit();
		sqlSession.close();
		return success;
	}
	
	
	//�޸�����
	public static void changepassword(SqlSessionFactory sqlSessionFactory,String password,String uId) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		IUserDAO userDAO = sqlSession.getMapper(IUserDAO.class);
		
		boolean success = false;//�����Ƿ��޸ĳɹ�
		String newPassword1 = "";
		
		System.out.println("������ԭ����");
		Scanner scan=new Scanner(System.in);
		String oldpassword=scan.nextLine();
			
		if(oldpassword.equals(password)) {
			System.out.print("�������µ�����:");
			newPassword1=scan.nextLine();
			System.out.print("������ȷ������:");
			String newPassword2=scan.nextLine();
			if(newPassword1.equals(newPassword2)) {
				success = true;
				//break;
			}else {
				System.out.println("�������벻һ��,����������!");
			}
		}
		
		if(oldpassword.equals(password)==false) {
			System.out.println("���벻��ȷ!");
			System.out.println("�������˵�");
		}
		
		if(success) {
			User user = new User();
			user.setPassword(newPassword1);
			user.setUserName(uId);
			userDAO.update(user);
			System.out.println("�޸ĳɹ�!");
		}
		sqlSession.commit();
		sqlSession.close();
	}

}
