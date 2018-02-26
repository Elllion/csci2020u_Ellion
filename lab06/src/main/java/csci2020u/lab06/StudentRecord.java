package csci2020u;

public class StudentRecord{
	private String studentID;
	float midterm;
	float assignments;
	float exam;
	float total;
	char grade;
	
	public StudentRecord(){
		studentID = "100000000";
		midterm = 0;
		assignments = 0;
		exam = 0;
		total = 0;
		grade = 'F';
	}
	
	public StudentRecord(String id, float mt, float as, float ex){
		studentID = id;
		midterm = mt;
		assignments = as;
		exam = ex;
		total = (assignments * 0.2f + midterm * 0.3f + exam * 0.5f);
		
		if(total > 80)
			grade = 'A';
		else if(total > 70)
			grade = 'B';
		else if (total > 60)
			grade = 'C';
		else if (total > 50)
			grade = 'D';
		else
			grade = 'F';
		
	}
	
	public String getStudentID(){
		return this.studentID;
	}
	
	public float getMidterm(){
		return this.midterm;
	}
	
	public float getAssignments(){
		return this.assignments;
	}
	
	public float getExam(){
		return this.exam;
	}
	
	public float getTotal(){
		return this.total;
	}
	
	public char getGrade(){
		return this.grade;
	}
}