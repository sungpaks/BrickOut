package homework2;

abstract class PairMap{
	protected String keyArray[];//key들을 저장하는 배열
	protected String valueArray[];//value들을 저장하는 배열
	PairMap(int n) { //추가 : Array의 크기를 인자로 받는 생성자
		keyArray = new String[n];
		valueArray = new String[n];
	}
	abstract String get(String key); //key값을 가진 value 리턴, 없으면 null리턴
	abstract void put(String key, String value); //key와 value를 쌍으로 저장, 기존에 key가 있으면 값을 value로 수정
	abstract String delete(String key); //key값을 가진 아이템(value와 함꼐) 삭제 , 삭제된 value값 리턴
	abstract int length(); //현재 저장된 아이템 개수 리턴
}

class Dictionary extends PairMap {
	Dictionary(int n) {
		super(n); //부모 클래스의 생성자를 호출하여 부모 클래스의 필드 초기화
	}
	
	@Override
	String get(String key) {
		int i = 0;
		for(String k : keyArray) { 
			//keyArray에서 key와 동일한 원소가 있는 위치를 찾고, 해당 자리의 value리턴
			if (key.equals(k)) return valueArray[i];
			i++;
		}
		//keyArray에 key가 없으면 null반환
		return null;
	}

	@Override
	void put(String key, String value) {
		if (this.get(key) == null) {
			//key 없는 경우, 새로 삽입
			int i = 0;
			//비어있는 위치를 찾는다
			while (keyArray[i] != null) {
				i++;
			}
			//array의 빈 공간에 key,value쌍 삽입
			keyArray[i] = key;
			valueArray[i] = value;
		}
		else {
			//key 있는 경우, 값을 수정
			int i = 0;
			//key의 위치를 찾는다
			while (key.equals(keyArray[i]) == false) {
				i++;
			}
			//해당 위치의 value 수정
			valueArray[i] = value; 
		}
	}

	@Override
	String delete(String key) {
		int i = 0;
		for(String k : keyArray) { 
			//keyArray에서 key와 동일한 원소가 있는 위치를 찾음
			if (key.equals(k)) {
				//key에 해당하는 원소를 발견하면, 해당 자리의 keyArray, valueArray에 null저장(삭제)하고,
				String v = valueArray[i];
				keyArray[i] = null;
				valueArray[i] = null;
				//삭제한값 반환
				return v;
			}
			i++;
		}
		//원소가 없으면 null 반환
		return null;
	}

	@Override
	int length() {
		//valueArray에서, null이 아닌 값의 갯수를 센다
		int cnt = 0;
		for(String v : valueArray) {
			if (v != null) cnt++;
		}
		return cnt;
	}
}

public class Hw2_2 {
	public static void main(String[] args) {
		Dictionary dic = new Dictionary(10);
		dic.put("황기태", "자바");
		dic.put("이재문", "파이썬");
		dic.put("이재문", "C++");
		System.out.println("이재문의 값은 " + dic.get("이재문"));
		System.out.println("황기태의 값은 " + dic.get("황기태"));
		dic.delete("황기태");
		System.out.println("황기태의 값은 " + dic.get("황기태"));
	}
}
