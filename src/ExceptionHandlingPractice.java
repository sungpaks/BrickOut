import java.io.IOException;

class MyAIsNegativeException extends Exception {
	MyAIsNegativeException(int a) {
		super("A is Negative! a = " + a);
	}
}
class MyAIsTooBigException extends Exception {
	MyAIsTooBigException(int a) {
		super("A is too big! a = " + a);
	}
}

public class ExceptionHandlingPractice {
	public static void func() throws IOException{
		
	}
	public static void func2() throws IOException {
		func();
		//func는 위험하지만, 이걸 알고 있고, 여기서 해결하지는 않고 다시 던져 넘긴다 : 같은 Exception을 던지도록 함
	}
	
	public static int calcArea(int a) throws MyAIsNegativeException, MyAIsTooBigException{
		if (a < 0) throw new MyAIsNegativeException(a);
		if (a > 10000) throw new MyAIsTooBigException(a);
		System.out.println("Area = " + (a*a));
		return a*a;
	}

	public static void main(String[] args) throws IOException{
		func2(); //또한 main도 함수이므로, Exception을 던져 미룰 수 있다
		
		int area = 0;
		try {
			calcArea(-10);
			func();
		} catch(MyAIsNegativeException e) {
			e.printStackTrace();
		} catch (MyAIsTooBigException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done!! area=" + area);
	}
}
