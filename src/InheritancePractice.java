/*
abstract class MyUSB{
	abstract void charge();
} 이렇게 하지 않고*/

import java.util.Scanner;

interface MyUSB{
	static final int MAX_WATT = 40;
	static int getMaxWatt() {
		return MAX_WATT;
	}
	public abstract void charge();
	private void funcfunc() {}
	public default void sendData() {
		funcfunc();
		System.out.println("Sending nothing");
	}
	//기본적으로 public abstract임
}


class MyComputer implements MyUSB{
	int a, b;
	@Override
	public void charge() {
		System.out.println("Computer is now Charging");
	}
}
class MyFan implements MyUSB{
	@Override
	public void charge() {
		System.out.println("Fan is now Charging");
	}
}


abstract class SeShape {
	int x, y;
	String color;
	SeShape(int _x, int _y) {
		this(_x, _y, "black"); //생성자 내부에서 자신의 생성자 호출
		color = "black";
	}
	SeShape(int _x, int _y, String _c) {
		x = _x;
		y = _y;
		color = _c;
	}
	void firstClick() {}; //이런 경우는, 자식이 오버라이딩해도 안해도 상관없음
	abstract void draw(); //이런 경우는, 자식이 반드시 오버라이딩해야함
}
class SeCircle extends SeShape {
	float r;
	SeCircle(int _x, int _y, int _r) {
		super(_x, _y); //부모의 생성자 명시적으로 호출
		r = _r;
	}
	SeCircle(int _x, int _y, int _r, String _c) {
		super (_x,_y,_c);
		r = _r;
	}
	@Override //명시적으로 오버라이딩함을 알린다 : 실수를 막아줌
	void draw() {
		//부모 함수 바꿔치기 : 오버라이딩 : visibility도 동일해야하고, 등.. 
		System.out.println("[CIRCLE] (" + x+","+y+") r=" + r + " c=" + color);
	}
}
class SeRect extends SeShape implements MyUSB {
	int w, h;
	SeRect(int _x, int _y, int _w, int _h) {
		super (_x, _y);
		w = _w;
		h = _h;
	}
	SeRect(int _x, int _y, int _w, int _h, String _c) {
		super (_x, _y, _c);
		w = _w;
		h = _h;
	}
	@Override
	void draw() {
		System.out.println("[CIRCLE] (" + x+","+y+") r=" + w + "h=" + h + " c=" + color);
	}
	@Override
	public void charge() {
		System.out.println("Rect is now Charging");
	}
}

class Counting {
	static int n = 0;
	Counting() {
		n++;
	}
}

public class InheritancePractice {
	public static void func(MyUSB m) {
		m.charge();
	}

	public static void main(String[] args) {
/*	SeShape a = new SeCircle(10, 20, 5);
		SeShape b = new SeCircle(1,2,3); //이렇게 하면 업캐스팅되며
		//SeCircle b = new SeCircle(1,2,3); 이렇게 말고
		SeShape c = new SeRect(10,20,100,200,"red");
		a.draw();
		b.draw(); //여기서 동적바인딩한다
		c.draw();
		System.out.println("----------");
		// => SeShape 타입의 배열로 만들 수 있다
		 * 
		 */
/*
 * SeShape[] arr = new SeShape[3]; arr[0] = new SeRect(10, 10, 50, 50); arr[1] =
 * new SeCircle(3,4,5); arr[2] = new SeRect(10, 10, 200, 200, "blue"); for(int
 * i=0;i<arr.length;i++) arr[i].draw(); for(SeShape e : arr) e.draw(); //극단적으로,
 * object배열로 하여 무엇이든 배열로 만들 수 있다
 * 
 * 
 * MyUSB[] usbs = new MyUSB[3]; usbs[0]= new MyComputer(); usbs[1]= new MyFan();
 * usbs[2] = (SeRect)arr[0];
 */
		Counting a = new Counting();
		Counting b = new Counting();
		System.out.println("Counting 변수 개수 " + Counting.n);
		Counting c = new Counting();
		System.out.println("Counting 변수 개수 " + Counting.n);
	}

}
