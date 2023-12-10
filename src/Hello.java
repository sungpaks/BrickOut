import java.util.Scanner;

/*
 * class MyStudent {
	static private MyStudent president = null;
	
	final static private int LIMIT_NUM = 1000;
	private static int currentYear = 2023;
	private static int studentSize = 0;
	private int id; 
	private String name;
	private String dept;
	private MyStudent(String _n) {
		//생성자를 private으로 만들어버리면 바로 생성할 수 없고,
		setId();
		name = _n;
		dept = "n/d";
	}
	static public MyStudent registerNewStudent(String n) {
		if (studentSize >= LIMIT_NUM) return null;
		return new MyStudent(n);
	}//이렇게 MyStudent 객체를 생성하고 반환하는 함수를 static으로 만들면
	//생성 그 자체를 컨트롤함!! 
	
	static public MyStudent getPresident() {
		if (president == null) {
			president = new MyStudent("PRESIDENT");
		}
		return president;
	}
	
	private void setId() {
		id = currentYear*1000 + (++studentSize);
	}
	static public int getStudentSize() {
		return studentSize;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String newDept) {
		if (newDept == "") return;
		dept = newDept;
	}
	void print() {
		String str = "[" + id + "] name: " + name + ", dept: " +dept;
		System.out.println(str);
	}
}

 */

class MyPoint2D{
	int x;
	int y;
	
	MyPoint2D() {
		x = 1;
		y = 1;
	}
	MyPoint2D(int _x, int _y) {
		x = _x;
		y = _y;
	}
	void draw() {
		String str = "[Point] (" +x+"," +y+")";
		System.out.println(str);
	}
	void add(MyPoint2D in) {
		x += in.x;
		y += in.y;
	}
}
class MyColorPoint2D extends MyPoint2D{
	String color;
	
	MyColorPoint2D() {
		color = "red";
	}
	MyColorPoint2D(int _x, int _y, String _c) {
		super(_x, _y);
		color = _c;
	}
	void colorDraw() {
		String str = "[ColorPoint]";
		System.out.println(str);
	}
}


public class Hello {

	
	
	public static void main(String[] args) {
		
		MyPoint2D p = new MyPoint2D();
		MyPoint2D q = new MyPoint2D(10,10);
		p.draw();
		q.draw();
	}

}
