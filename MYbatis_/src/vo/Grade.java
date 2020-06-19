package vo;

public class Grade {
	private String gCourseId;//�γ̱��
	private String gName;//�γ�����
	private String gStudentId;//ѧ��ѧ��
	private String gStudentName;//ѧ������
	private String mark;//�γ̳ɼ�
	
	public Grade() {
		super();
	}

	public Grade(String gCourseId, String gName, String gStudentId, String gStudentName, String mark) {
		super();
		this.gCourseId = gCourseId;
		this.gName = gName;
		this.gStudentId = gStudentId;
		this.gStudentName = gStudentName;
		this.mark = mark;
	}

	public String getgCourseId() {
		return gCourseId;
	}

	public void setgCourseId(String gCourseId) {
		this.gCourseId = gCourseId;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getgStudentId() {
		return gStudentId;
	}

	public void setgStudentId(String gStudentId) {
		this.gStudentId = gStudentId;
	}

	public String getgStudentName() {
		return gStudentName;
	}

	public void setgStudentName(String gStudentName) {
		this.gStudentName = gStudentName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return gStudentName + "\t" + gStudentId  + "\t" + gCourseId + "\t" + gName  + "\t" + mark+ "\t";
	}

	
}
