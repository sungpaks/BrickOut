
import sejong.software.JavaProgramming;
import sejong.HelloWord;

public class PackagePractice {

	public static void main(String[] args) {
		sejong.HelloWord.main(null);
		sejong.software.JavaProgramming.main(null);
		Object a = new Object();
		Object b = new Object();
		if (a.equals(b)) System.out.println("same");
		a.hashCode();
	}
}
