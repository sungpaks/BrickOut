import java.util.Scanner;

public class Hw1_4 {

	public static void main(String[] args) {
		int[][] arr = new int[4][4]; //4x4 2차원 배열 생성 : 모두 0으로 초기화되어있음
		for(int k=0;k<10;k++) {
			int randomIndex = (int)(Math.random()*16);
			//임의의 위치 생성 : 0~15 범위의 정수를 생성하고, 이 정수에 대해
			int i = randomIndex/4; //4로 나눈 몫을 i
			int j = randomIndex%4; //4로 나눈 나머지를 j
			if (arr[i][j] != 0) k--; //이미 해당 자리에 랜덤 정수가 있으면 다른 자리를 알아본다
			else arr[i][j] = (int)(Math.random()*10+1);
			//해당 자리가 비어있으면 1~10 범위의 정수를 랜덤하게 삽입한다
		}
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				//완성된 2차원 배열을 출력 : 10자리는 랜덤 정수(1~10), 6자리는 0
				System.out.print(arr[i][j] + "  ");
			}
			System.out.println();
		}
	}

}
