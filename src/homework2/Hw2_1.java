package homework2;

import java.util.Scanner;

class seat {
	private String[] name = new String[10];
	seat() { //생성자 : 10개 좌석의 이름들을 모두 기본값으로 초기화
		for(int i=0;i<name.length;i++) {
			name[i] = "---";
		}
	}
	String getName(int num) { //좌석번호에 해당하는 이름 출력
		return name[num-1];
	}
	void setName(int num, String name) { //좌석번호에 해당하는 이름을 수정
		this.name[num-1] = name;
	}
	void showSeatInfo() { //각 좌석의 이름 출력
		for(String s : name) {
			System.out.print(" " + s);
		}
		System.out.println();
	}
}
class seatS extends seat {
	seatS() { //생성자 : 부모 생성자 호출
		super();
	}
	@Override
	void showSeatInfo() { //좌석 등급 정보만 추가로 출력하고, 원본함수의 동작 호출
		System.out.print("S>>");
		super.showSeatInfo();
	}
}
class seatA extends seat {
	seatA() { //생성자 : 부모 생성자 호출
		super();
	}
	@Override
	void showSeatInfo() { //좌석 등급 정보만 추가로 출력하고, 원본함수의 동작 호출
		System.out.print("A>>");
		super.showSeatInfo();
	}
}
class seatB extends seat {
	seatB() {//생성자 : 부모 생성자 호출
		super();
	}
	@Override
	void showSeatInfo() {//좌석 등급 정보만 추가로 출력하고, 원본함수의 동작 호출
		System.out.print("B>>");
		super.showSeatInfo();
	}
}

class ConcertReservation{
	seat[] seatList = new seat[3]; //seat클래스의 객체 배열 선언/생성
	ConcertReservation() { //생성자 : S,A,B좌석에 해당하는 객체 인스턴스 생성. 부모클래스로 업캐스팅하여 한번에 관리
		System.out.println("명품콘서트홀 예약 시스템입니다.");
		seatList[0] = new seatS(); 
		seatList[1] = new seatA();
		seatList[2] = new seatB();
	}
	private void reservation(Scanner in) { //메뉴에서 1번을 누른 예약 시스템
		int seatClass; //좌석 등급
		while (true) { //유효한 좌석등급 입력(1~3)이 들어올 때까지 입력을 진행
			System.out.print("좌석구분 S(1), A(2), B(3)>>");
			try {
				seatClass = in.nextInt();
			} catch (Exception e) { //입력이 정수가 아닌 경우 예외 발생
				seatClass = 0;
				in.next();
			}
			if (seatClass <= 3 && seatClass > 0) break;
			else System.out.println("유효하지 않은 좌석등급 입력입니다.");
		}//유효한 범위인 경우 반복문 중단, 유효하지 않은 경우 오류메시지 출력 후 다시 입력
		seatList[seatClass-1].showSeatInfo(); //좌석 등급이 선택되면 해당 등급의 좌석정보 나열
		
		String name; //이름
		System.out.print("이름>>");
		name = in.next();
		int seatNumber; //좌석번호
		while (true) { //유효한 좌석번호 범위의 입력(1~10)이 들어올 때까지 반복
			try {
				System.out.print("번호>>");
				seatNumber = in.nextInt();
			} catch (Exception e) { //입력이 정수가 아닌 경우 예외 발생
				seatNumber = 0;
				in.next();
			}
			if (seatNumber <= 10 && seatNumber > 0) break;
			else System.out.println("유효하지 않은 좌석번호 입력입니다.");
		} //유효한 범위인 경우 반복문 중단, 유효하지 않은 경우 오류메시지 출력 후 다시 입력
		seatList[seatClass-1].setName(seatNumber, name);
		//seatClass는 S:1, A:2, B:3이므로, seatClass-1을 인덱스로 seatList에 접근하면 해당 등급의 좌석정보.
	}
	private void showInfo() { //2번 메뉴 : 조회
		for(seat s : seatList) s.showSeatInfo(); //S,A,B 좌석정보출력 메소드를 호출
		System.out.println("<<<조회를 완료하였습니다.>>>");
	}
	private void cancel(Scanner in) { //3번 메뉴 : 삭제
		int seatClass; //좌석 등급
		while (true) { //유효한 범위의 입력(1~3)이 들어올 때까지 반복하여 입력받음
			System.out.print("좌석 S:1, A:2, B:3>>");
			try {
				seatClass = in.nextInt();
			} catch (Exception e) { //입력이 정수가 아닌 경우 예외 발생
				seatClass = 0;
				in.next();
			}
			if (seatClass > 0 && seatClass <= 3) break;
			else System.out.println("유효하지 않은 좌석등급 입력입니다.");
		} //유효한 범위인 경우 반복문 중단, 유효하지 않은 경우 오류메시지 출력 후 다시 입력
		seatList[seatClass-1].showSeatInfo(); //해당 등급 좌석 나열

		System.out.print("이름>>");
		String name = in.next(); //삭제할 이름을 입력받는다
		for(int i=1;i<=10;i++) { //이름에 해당하는 좌석을 찾는다
			if (seatList[seatClass-1].getName(i).compareTo(name)==0) //compareTo 메소드로 String 비교
			{
				seatList[seatClass-1].setName(i, "---"); //비교 후 동일하면 해당 이름을 지우고 함수 종료
				return;
			}
		}
		System.out.println("일치하는 이름이 없습니다. 다시 입력해주세요.");
		cancel(in); //일치하는 이름이 없는 경우 오류메시지를 출력하고, 처음부터 다시 입력받음
	}
	void menuInput(Scanner in) { //4번 끝내기 메뉴를 선택할 때까지 메뉴를 선택받음
		System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
		int menu;
		try {
			menu = in.nextInt();
		} catch(Exception e) {
			menu = 0;
			in.next();
		}
		switch(menu) {
		case 1 : reservation(in); break; // menu 1 : 예약
		case 2 : showInfo(); break; //menu 2 : 조회
		case 3 : cancel(in); break; //menu 3 : 취소
		case 4 : break; //menu 4 : 끝내기
		default : System.out.println("유효하지 않은 메뉴 입력입니다."); break;
		}
		if (menu != 4) menuInput(in);
		//고른 메뉴가 4번이 아니면 재귀호출로 계속 메뉴를 입력받는다
	}
}

public class Hw2_1 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		new ConcertReservation().menuInput(in);
		//ConcertReservation 객체를 생성하고, menuInput메소드로 진입하여 메뉴를 입력받음
	}
}
