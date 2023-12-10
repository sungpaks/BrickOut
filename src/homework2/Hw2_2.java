package homework2;

abstract class PairMap{
	protected String keyArray[];//key���� �����ϴ� �迭
	protected String valueArray[];//value���� �����ϴ� �迭
	PairMap(int n) { //�߰� : Array�� ũ�⸦ ���ڷ� �޴� ������
		keyArray = new String[n];
		valueArray = new String[n];
	}
	abstract String get(String key); //key���� ���� value ����, ������ null����
	abstract void put(String key, String value); //key�� value�� ������ ����, ������ key�� ������ ���� value�� ����
	abstract String delete(String key); //key���� ���� ������(value�� �Բ�) ���� , ������ value�� ����
	abstract int length(); //���� ����� ������ ���� ����
}

class Dictionary extends PairMap {
	Dictionary(int n) {
		super(n); //�θ� Ŭ������ �����ڸ� ȣ���Ͽ� �θ� Ŭ������ �ʵ� �ʱ�ȭ
	}
	
	@Override
	String get(String key) {
		int i = 0;
		for(String k : keyArray) { 
			//keyArray���� key�� ������ ���Ұ� �ִ� ��ġ�� ã��, �ش� �ڸ��� value����
			if (key.equals(k)) return valueArray[i];
			i++;
		}
		//keyArray�� key�� ������ null��ȯ
		return null;
	}

	@Override
	void put(String key, String value) {
		if (this.get(key) == null) {
			//key ���� ���, ���� ����
			int i = 0;
			//����ִ� ��ġ�� ã�´�
			while (keyArray[i] != null) {
				i++;
			}
			//array�� �� ������ key,value�� ����
			keyArray[i] = key;
			valueArray[i] = value;
		}
		else {
			//key �ִ� ���, ���� ����
			int i = 0;
			//key�� ��ġ�� ã�´�
			while (key.equals(keyArray[i]) == false) {
				i++;
			}
			//�ش� ��ġ�� value ����
			valueArray[i] = value; 
		}
	}

	@Override
	String delete(String key) {
		int i = 0;
		for(String k : keyArray) { 
			//keyArray���� key�� ������ ���Ұ� �ִ� ��ġ�� ã��
			if (key.equals(k)) {
				//key�� �ش��ϴ� ���Ҹ� �߰��ϸ�, �ش� �ڸ��� keyArray, valueArray�� null����(����)�ϰ�,
				String v = valueArray[i];
				keyArray[i] = null;
				valueArray[i] = null;
				//�����Ѱ� ��ȯ
				return v;
			}
			i++;
		}
		//���Ұ� ������ null ��ȯ
		return null;
	}

	@Override
	int length() {
		//valueArray����, null�� �ƴ� ���� ������ ����
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
		dic.put("Ȳ����", "�ڹ�");
		dic.put("���繮", "���̽�");
		dic.put("���繮", "C++");
		System.out.println("���繮�� ���� " + dic.get("���繮"));
		System.out.println("Ȳ������ ���� " + dic.get("Ȳ����"));
		dic.delete("Ȳ����");
		System.out.println("Ȳ������ ���� " + dic.get("Ȳ����"));
	}
}
