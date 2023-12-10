import java.util.Scanner;

public class Hw1_2 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("연산>>");
		double a = in.nextDouble();
		String op = in.next();
		double b = in.nextDouble();
		//피연산자 a, b, 연산자 op.
		double result=0;
		//결과값 result : double
		switch(op) { //switch문
		case("+") : //더하기
			result = a + b;
			break;
		case("-") : //빼기
			result = a - b;
			break;
		case("*") : //곱하기
			result = a * b;
			break;
		case("/") : //나누기
			if (b == 0) {
				System.out.println("0으로 나눌 수 없습니다.");
				return; //0으로 나누는 경우
			}
			else result = a / b;
			break;
		default:
		}
		System.out.println(a + op + b + "의 계산 결과는 " + result);
		//결과 출력
	}

}
