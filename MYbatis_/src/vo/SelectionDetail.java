package vo;

public class SelectionDetail {
	private String sStudentId;//学生学号
	private String sStudentName;//学生姓名
	private String sTeacherId;//教师id
	private String sTeacherName;//教师姓名
	private String sCourseId;//课程编号
	private String sCourseName;//课程名
	private int sClassHours;//课程学时
	
	public SelectionDetail() {
		super();
	}

	public SelectionDetail(String sStudentId, String sStudentName, String sTeacherId, String sTeacherName,
			String sCourseId, String sCourseName, int sClassHours) {
		super();
		this.sStudentId = sStudentId;
		this.sStudentName = sStudentName;
		this.sTeacherId = sTeacherId;
		this.sTeacherName = sTeacherName;
		this.sCourseId = sCourseId;
		this.sCourseName = sCourseName;
		this.sClassHours = sClassHours;
	}

	public String getsStudentId() {
		return sStudentId;
	}

	public void setsStudentId(String sStudentId) {
		this.sStudentId = sStudentId;
	}

	public String getsStudentName() {
		return sStudentName;
	}

	public void setsStudentName(String sStudentName) {
		this.sStudentName = sStudentName;
	}

	public String getsTeacherId() {
		return sTeacherId;
	}

	public void setsTeacherId(String sTeacherId) {
		this.sTeacherId = sTeacherId;
	}

	public String getsTeacherName() {
		return sTeacherName;
	}

	public void setsTeacherName(String sTeacherName) {
		this.sTeacherName = sTeacherName;
	}

	public String getsCourseId() {
		return sCourseId;
	}

	public void setsCourseId(String sCourseId) {
		this.sCourseId = sCourseId;
	}

	public String getsCourseName() {
		return sCourseName;
	}

	public void setsCourseName(String sCourseName) {
		this.sCourseName = sCourseName;
	}

	public int getsClassHours() {
		return sClassHours;
	}

	public void setsClassHours(int sClassHours) {
		this.sClassHours = sClassHours;
	}

	@Override
	public String toString() {
		return  sStudentName + "\t" + sStudentId + "\t" + sCourseId +"\t" + sCourseName +"\t"+
				sTeacherId + "\t"+ sClassHours + "\t";
	}

	
}
