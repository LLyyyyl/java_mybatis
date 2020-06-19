package vo;

public class Course {
	private String cTeacherId;//��ʦid
	private String cTeacherName;//��ʦ����
	private String cCourseId;//�γ̱��
	private String cCourseName;//�γ���
	private int cClassHours;//�γ�ѧʱ
	
	public Course() {
		super();
	}
	
	public Course(String cTeacherId, String cTeacherName, String cCourseId, String cCourseName, int cClassHours) {
		super();
		this.cTeacherId = cTeacherId;
		this.cTeacherName = cTeacherName;
		this.cCourseId = cCourseId;
		this.cCourseName = cCourseName;
		this.cClassHours = cClassHours;
	}

	public String getcTeacherId() {
		return cTeacherId;
	}

	public void setcTeacherId(String cTeacherId) {
		this.cTeacherId = cTeacherId;
	}

	public String getcTeacherName() {
		return cTeacherName;
	}

	public void setcTeacherName(String cTeacherName) {
		this.cTeacherName = cTeacherName;
	}

	public String getcCourseId() {
		return cCourseId;
	}

	public void setcCourseId(String cCourseId) {
		this.cCourseId = cCourseId;
	}

	public String getcCourseName() {
		return cCourseName;
	}

	public void setcCourseName(String cCourseName) {
		this.cCourseName = cCourseName;
	}

	public int getcClassHours() {
		return cClassHours;
	}

	public void setcClassHours(int cClassHours) {
		this.cClassHours = cClassHours;
	}

	@Override
	public String toString() {
		return cTeacherName + "\t" + cCourseId + "\t" + cCourseName + "\t" + cClassHours + "\t";
	}
	
	
}
