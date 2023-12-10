import java.util.Scanner;

public class Hw1_3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("정수 몇개?");
		int n = in.nextInt(); //정수 입력 : "정수를 몇 개 저장할지"
		int[] arr = new int[n]; //입력된 정수만큼의 배열 선언
		for(int i=0;i<n;i++) {
			int randomNumber = (int)(Math.random()*100 + 1);
			//1~100 범위의 정수를 랜덤하게 생성
			boolean isContain = false;
			for(int j=0;j<i;j++) { //중복 확인
				if (randomNumber == arr[j]) {
					isContain = true;
					break;
				}
			}
			if (isContain) i--; //중복이면 제자리걸음
			else arr[i] = randomNumber; //중복이 아니면 배열에 삽입
		}
		for(int i=0;i<n;i++) { //완성된 배열을 출력
			System.out.print(arr[i]);
			if (i%10 == 9) System.out.println();
			else System.out.print(" ");
		}
	}
}
