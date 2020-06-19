package vo;

public class SelectionDetail {
	private String sStudentId;//ѧ��ѧ��
	private String sStudentName;//ѧ������
	private String sTeacherId;//��ʦid
	private String sTeacherName;//��ʦ����
	private String sCourseId;//�γ̱��
	private String sCourseName;//�γ���
	private int sClassHours;//�γ�ѧʱ
	
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
