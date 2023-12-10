package homework2;

import java.util.Scanner;

abstract class Shape { //�־��� �߻�Ŭ���� Shape
	private Shape next;
	public Shape() {next = null;}
	public void setNext(Shape obj) {next = obj;}//��ũ����
	public Shape getNext() {return next;}
	public abstract void draw();
}
 
class Line extends Shape{ //Shape�� ����ϴ� Line Ŭ����
	@Override
	public void draw() { //�߻�޼ҵ� draw �������̵�
		System.out.println("Line");
	}
}
class Rect extends Shape{ //Shape�� ����ϴ� RectŬ����
	@Override
	public void draw() { //�߻�޼ҵ� draw �������̵�
		System.out.println("Rect");
	}
}
class Circle extends Shape{ //Shape�� ����ϴ� CircleŬ����
	@Override
	public void draw() { //�߻�޼ҵ� draw �������̵�
		System.out.println("Circle");
	}	
}
//����Ŭ������ Shape�� default�����ڸ� �����Ƿ� ����Ŭ�������� ���� �����ڸ� ����� �ʿ� X

class GraphicEditor{ //����,����,��� ���� ���� �׷��� ���� ����� ���� GraphicEditorŬ����
	private Shape list; //���Ḯ��Ʈ�� ��� ���۷���
	//��� �������� ����Ŭ������ Shape�� ��ĳ���õǾ� ���Ḯ��Ʈ ���·� ������. 
	
	GraphicEditor() {//������
		System.out.println("�׷��� ������ beauty�� �����մϴ�.");
	}
	
	void add(int type) { //���� ����
		Shape newObj = null; //Shape ���۷����� ����
		switch (type) { //1���̸� Line, 2���� Rect, 3�� Circle�� ��ü�� ����
		case 1 : newObj = new Line(); break;
		case 2 : newObj = new Rect(); break;
		case 3 : newObj = new Circle(); break;
		}
		if (list == null) list = (Shape)newObj; //����Ʈ�� ���Ұ� ������ ��� : newObj�� ���� ��
		else {
			//����Ʈ�� ���Ұ� �ϳ��� �־��� ��� : �� �ڿ� ���� ����
			Shape cur = list;
			while (cur.getNext() != null) {
				cur = cur.getNext();
			}
			cur.setNext(newObj);
		}
	}
	boolean remove(int index) { //���� ���� : ������ �� ������ true, ������ false ��ȯ
		if (list == null || index <= 0) return false; //����Ʈ�� ���Ұ� �ϳ��� ����, or ��ġ�� ����� �ƴ�
		if (index == 1) {
			//����Ʈ�� ���Ұ� �����鼭 �� ���� ���Ҹ� �����ϰ��� �ϴ� ���
			list = list.getNext();
			return true;
		}
		//�� ���� ���Ҹ� �����ϰ��� �ϴ� ��츦 ����������, 2��° ��ġ���� �˻� ����
		Shape cur = list.getNext();
		Shape prev = list;
		int i = 2;
		while (i < index && cur != null) {
			//��ġ�� ã�Ұų� ����Ʈ�� ���� �����ϸ� �˻� ����
			prev = cur;
			cur = cur.getNext();
			i++;
		}
		if (cur == null) return false; //����Ʈ�� ������ �� ���, �ش� ��ġ�� ���Ұ� �������� ����
		//��ġ�� ã�� ���, cur ������ cur ���Ŀ� �����ϰ� cur�ڸ��� ��� �����Ѵ�
		prev.setNext(cur.getNext());
		cur = null;
		return true;
	}
	void showAll() { //��� ���� : ����Ʈ�� ��ȸ
		Shape cur = list;
		while (cur != null) {
			cur.draw();
			cur = cur.getNext();
		}
	}
	void end() {
		//���� : ����޽����� ���
		System.out.println("beauty�� �����մϴ�.");
	}
}

public class Hw2_3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		GraphicEditor ge = new GraphicEditor();
		//GraphicEditor��ü �ν��Ͻ� ����
		while (true) {
			System.out.print("����(1), ����(2), ��� ����(3), ����(4)>>");
			int menu = in.nextInt();
			//�޴� �Է¹ް�, �ش��ϴ� ���� ����
			switch(menu) {
			case 1 : //���� ����.
				System.out.print("Line(1), Rect(2), Circle(3)>>");
				int type = in.nextInt();
				if (1 > type || type > 3) System.out.println("invalid input"); //���� ��ȿ�� �˻�
				else ge.add(type); //type�� ��ȿ�� ���̸� add�޼ҵ� ȣ��
				break;
			case 2: //���� ����.
				System.out.print("������ ������ ��ġ>>");
				int index = in.nextInt();
				if (ge.remove(index) == false) System.out.println("������ �� �����ϴ�.");
				//�����ϰ�, ��ȯ���� Ȯ���Ͽ� false�̸� ������ �� ���ٰ� �˸�
				break;
			case 3: //��� ����
				ge.showAll(); //showAll�޼ҵ� ȣ��
				break;
			case 4: //���� : ���� �޽��� ��� �� main ����
				ge.end();
				return;
			default : System.out.println("invalid input");
			//1~4 ������ ���� �ƴ� ��� ��ȿ���� ���� �޴� �Է���
			}
		}
	}
}
