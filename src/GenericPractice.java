import java.util.LinkedList;
import java.util.Vector;

class MyCon <T> {
	private T value;
	public void setValue(T v) {
		value = v;
	}
	public T getValue() {
		return value;
	}
}

public class GenericPractice {
	public static <T extends Comparable> T myMax(T a, T b) {
		if(a.compareTo(b) > 0) return a;
		return b;
	}
	public static void main(String[] args) {
		int a = 300;
		int b = 400;
		System.out.println("max-"+myMax(a,b));
		
		LinkedList<String> con = new LinkedList<>();
		con.add("University");
		con.add("Seoul");
		con.add("Sejong");
		con.add("software");
		for(int i=0;i<con.size();i++) {
			System.out.println(con.get(i));
		}
	}
}
