package homework2;

import java.util.Scanner;

abstract class Shape { //주어진 추상클래스 Shape
	private Shape next;
	public Shape() {next = null;}
	public void setNext(Shape obj) {next = obj;}//링크연결
	public Shape getNext() {return next;}
	public abstract void draw();
}
 
class Line extends Shape{ //Shape를 상속하는 Line 클래스
	@Override
	public void draw() { //추상메소드 draw 오버라이드
		System.out.println("Line");
	}
}
class Rect extends Shape{ //Shape를 상속하는 Rect클래스
	@Override
	public void draw() { //추상메소드 draw 오버라이드
		System.out.println("Rect");
	}
}
class Circle extends Shape{ //Shape를 상속하는 Circle클래스
	@Override
	public void draw() { //추상메소드 draw 오버라이드
		System.out.println("Circle");
	}	
}
//슈퍼클래스인 Shape가 default생성자를 가지므로 서브클래스에서 따로 생성자를 명시할 필요 X

class GraphicEditor{ //삽입,삭제,모두 보기 등의 그래픽 편집 기능을 갖는 GraphicEditor클래스
	private Shape list; //연결리스트의 헤드 레퍼런스
	//모든 도형들은 슈퍼클래스인 Shape로 업캐스팅되어 연결리스트 형태로 관리됨. 
	
	GraphicEditor() {//생성자
		System.out.println("그래픽 에디터 beauty을 실행합니다.");
	}
	
	void add(int type) { //삽입 연산
		Shape newObj = null; //Shape 레퍼런스를 선언
		switch (type) { //1번이면 Line, 2번은 Rect, 3번 Circle의 객체로 생성
		case 1 : newObj = new Line(); break;
		case 2 : newObj = new Rect(); break;
		case 3 : newObj = new Circle(); break;
		}
		if (list == null) list = (Shape)newObj; //리스트에 원소가 없었던 경우 : newObj를 헤드로 함
		else {
			//리스트에 원소가 하나라도 있었던 경우 : 맨 뒤에 새로 연결
			Shape cur = list;
			while (cur.getNext() != null) {
				cur = cur.getNext();
			}
			cur.setNext(newObj);
		}
	}
	boolean remove(int index) { //삭제 연산 : 삭제할 수 있으면 true, 없으면 false 반환
		if (list == null || index <= 0) return false; //리스트에 원소가 하나도 없음, or 위치가 양수가 아님
		if (index == 1) {
			//리스트에 원소가 있으면서 맨 앞의 원소를 삭제하고자 하는 경우
			list = list.getNext();
			return true;
		}
		//맨 앞의 원소를 삭제하고자 하는 경우를 제외했으니, 2번째 위치부터 검색 시작
		Shape cur = list.getNext();
		Shape prev = list;
		int i = 2;
		while (i < index && cur != null) {
			//위치를 찾았거나 리스트의 끝에 도달하면 검색 종료
			prev = cur;
			cur = cur.getNext();
			i++;
		}
		if (cur == null) return false; //리스트의 끝까지 간 경우, 해당 위치에 원소가 존재하지 않음
		//위치를 찾은 경우, cur 직전을 cur 직후와 연결하고 cur자리를 떼어내 삭제한다
		prev.setNext(cur.getNext());
		cur = null;
		return true;
	}
	void showAll() { //모두 보기 : 리스트를 순회
		Shape cur = list;
		while (cur != null) {
			cur.draw();
			cur = cur.getNext();
		}
	}
	void end() {
		//종료 : 종료메시지만 출력
		System.out.println("beauty을 종료합니다.");
	}
}

public class Hw2_3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		GraphicEditor ge = new GraphicEditor();
		//GraphicEditor객체 인스턴스 생성
		while (true) {
			System.out.print("삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>");
			int menu = in.nextInt();
			//메뉴 입력받고, 해당하는 동작 수행
			switch(menu) {
			case 1 : //삽입 연산.
				System.out.print("Line(1), Rect(2), Circle(3)>>");
				int type = in.nextInt();
				if (1 > type || type > 3) System.out.println("invalid input"); //값의 유효성 검사
				else ge.add(type); //type이 유효한 값이면 add메소드 호출
				break;
			case 2: //삭제 연산.
				System.out.print("삭제할 도형의 위치>>");
				int index = in.nextInt();
				if (ge.remove(index) == false) System.out.println("삭제할 수 없습니다.");
				//삭제하고, 반환값을 확인하여 false이면 삭제할 수 없다고 알림
				break;
			case 3: //모두 보기
				ge.showAll(); //showAll메소드 호출
				break;
			case 4: //종료 : 종료 메시지 출력 후 main 종료
				ge.end();
				return;
			default : System.out.println("invalid input");
			//1~4 사이의 값이 아닌 경우 유효하지 않은 메뉴 입력임
			}
		}
	}
}
