import java.util.Arrays;

interface studiable {
	void study();
}

interface workable{
	int work(int h);
}

class SeStudent implements Comparable, studiable {
	enum SORT{NAME_INC, NAME_DEC, NUM_INC, NUM_DEC};
	static SORT sort_mode;
	
	private class Score{ //SeStudent내부에서만 쓸거니까 inner로, private으로 하여 선언
		int korean;
		int math;
		int sci;
		int eng;
		public String toString() {
			String str = "[" + num + "] {" + math +"," + eng + "}";
			return str;
		}
	}
	
	int num;
	String name;
	Score score; //점수를, 클래스로 유지
	SeStudent (int _n, String _name) {
		num = _n;
		name = _name;
		sort_mode = SORT.NAME_INC; //0 : 이름 오름차순, 1 : 이름 내림차순, 
		//2 : number 오름차순, 3 : number 내림차순
	}
	@Override
	public String toString() {
		String str = "["+num+"] Name : " + name;
		return str;
	}
	@Override
	public int compareTo(Object o) {
		if (o instanceof SeStudent) {
			SeStudent in = (SeStudent) o; //SeStudent의 인스턴스이면 일단 형변환하고,
			switch(sort_mode) {
			case NAME_INC: return name.compareTo(in.name);
			case NAME_DEC: return -name.compareTo(in.name);
			case NUM_INC: return num - in.num;
			case NUM_DEC: return in.num - this.num;
			default : break;
			}
		}
		return 0;
	}
	@Override
	public void study() {
		// TODO Auto-generated method stub
		System.out.println("not studying");
	}
}

public class VariousClasses {
	static studiable aaa;
	static void func(studiable s) {
		s.study();
		aaa = s;
	}

	public static void main(String[] args) {
		//SeStudent a = new SeStudent(10, "kim");
		//System.out.println(a);
		String [] names = {"kim","lee","park","hong","choi","kang"};
		SeStudent [] arr = new SeStudent[names.length];
		for(int i=0;i<arr.length;i++) {
			arr[i] = new SeStudent(i+1, names[i]);
		}
		SeStudent.sort_mode = SeStudent.SORT.NAME_DEC;
		Arrays.sort(arr);
		for(SeStudent s : arr) {
			System.out.println(s);
			func(s);
		}
		
		SeStudent a = new SeStudent(10, "kim") {
			int work_hour = 10;
			void func() {
				work_hour++;
			}
			@Override
			public String toString() {
				func();
				String str = "*"+num+"* Name : " + name + " work_hour : " + work_hour;
				return str;
			}
		};
		System.out.println(a);
		
		/*
		 * studiable aa = new studiable() {
		 * 
		 * @Override public void study() { System.out.println("Studing now!!"); } };
		 */
		int hour = 3;
		func(new studiable() {
			@Override
			public void study() {
				System.out.println("Studing JAVA for " + hour + " hours!");
			}
		});//anonymouse class
		func(()->{System.out.println("Studing NOWNOWNOW");});
		//Lambda expression
		
	}

}
