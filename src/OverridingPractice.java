

class MyPerson{
	String name;
	int age;
	MyPerson(String n, int _age) {
		name = n;
		age = _age;
	}
	String getString() {
		return "["+name+"] age=" + age;
	}
	void print() {
		System.out.println(getString());
	}
	void func() {
		name += "*";
	}
}
class MyStudent extends MyPerson{
	String school;
	MyStudent(String n, int _age, String s) {
		super(n, _age); //부모에 default생성자가 으니 super로 명시하기 
		school = s;
	}
	@Override
	String getString() {
		return super.getString() + " school:"+school;
	}
	@Override
	void print() {
		System.out.println(getString());
	}
	@Override
	void func() {
		super.func();
		school += "!";
	}
}

public class OverridingPractice {
	
	public static void func(MyPerson in) {
		in.func();
		in.print();
	}//하나의 변수타입이 그 구체적 타입에 따라 다른 동작을 함 => 다형성 polymorphism

	public static void main(String[] args) {
		MyPerson a = new MyPerson("Kim", 10);
		MyStudent b = new MyStudent("Lee", 20, "Sejong");
		a.print();
		b.print();
		
		MyPerson c = b; //Type casting : Up-casting
		c.name = "Park";
		//c.school = "anything"; 더이상 학생이 아닌 그냥 사람이라 안됨
		c.print();
		
		//MyStudent d = a;  DOWN-casting : 안되는 경우 . 원래 사람이고 학생으로 확장 불가
		MyStudent d = (MyStudent)c;  //DOWN-casting : 형변환 하는 것임을 명시하고, 가능 => 자식이었다가 업캐스팅으로 부모가 된 경우, 되돌리는 경우
		
		func(a); 
		func(b); //학생이든 아니든, 사람이기만 하면 됨
		//c.func(); //레퍼런스가 나타내는 타입이 아닌, 원래 타입을 따라간다 => dynamic binding
		
	}

}
