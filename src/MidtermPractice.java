import java.util.Arrays;
import java.util.Scanner;

class Employee implements Comparable {
	String name;
	Employee(String in) {name=in;}
	@Override
	public int compareTo(Object o) {
		if (o instanceof Employee)
		{
			Employee tmp = (Employee)o;
			return name.compareTo(tmp.name);
		}
		else return 0;
	}
}

public class MidtermPractice {
	public static void main(String[] args) {
		Employee [] arr = new Employee[3];
		arr[0] = new Employee("Kim");
		arr[1] = new Employee("Park");
		arr[2] = new Employee("Lee");
		Arrays.sort(arr);
		for(Employee e : arr) System.out.println(e.name);
	}
}
/*
 * import java.util.Arrays;
 * implements Comparable
 * @override
 * public int compareTo(Object o) {
 * 	if (o intanceof Employee) {
 * 		Employee tmp = (Employee)o;
 * 		return name.compareTo(tmp.name);
 * 	}
 * else return 0;
 * }
 * 
 */