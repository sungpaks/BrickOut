import java.util.ArrayList;
import java.util.Iterator;

class MyData {
	int data;
	MyData(int n) {
		data = n;
	}
}

public class CollectionPractice {

	public static void main(String[] args) {
		ArrayList<MyData>list = new ArrayList<>();
		list.add(new MyData(10));
		list.add(new MyData(3));
		for(MyData d : list) {
			System.out.println(d.data);
		}
		
		ArrayList<Integer> iList = new ArrayList<>();
		
		Iterator<Integer>it = iList.iterator();
		while(it.hasNext()) {
			it.next(); //������ �����͸� ���� �������� ����
			
		}
		
		
	}

}
