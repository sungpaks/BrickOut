
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/*------------------------버튼 클래스-------------------------*/
class MyButton extends JToggleButton { //사용할 버튼들의 공통적 동작을 담당할 부모 Button클래스
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setSize(40,40); //사이즈를 동일하게 설정하며,
		setMargin(new Insets(17,17,17,17));; //왼쪽오른쪽 여백을 설정하여 버튼끼리 살짝만 떨어지도록 조정
	}
}
//6종류의 버튼들은 MyButton을 상속받아 같은 모양을 가지도록 하며, 버튼 내부의 그림만이 다르도록 paintComponent메소드를 재정의
class LineButton extends MyButton { //선 버튼
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2)); //선 두껍게
		g2.drawLine(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 4 * 3, this.getHeight() / 4 * 3);
		g2.setStroke(new BasicStroke(1)); //선 두께 다시 정상화
	}
}
class RectButton extends MyButton { //사각형 버튼
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
}
class CircleButton extends MyButton { //원 버튼
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawOval(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
}
class CurveButton extends MyButton { //곡선 버튼
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = this.getWidth();
		int h = this.getHeight();
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		CubicCurve2D c = new CubicCurve2D.Double();
		c.setCurve(w/4, h/4, w/4, h/4*3, w/4*3,h/4, w/4*3, h/4*3);
		g2.draw(c);
		g2.setStroke(new BasicStroke(1));
	}
}
class ColorButton extends MyButton { //색상 선택 버튼
	Color color = Color.blue; //초기값은 blue
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color); //버튼 내부는 현재 색상이 채워진 직사각형 그림 존재
		g2.setStroke(new BasicStroke(2));
		g2.fillRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
	public void setColor(Color c) { //현재 선택된 색상이 버튼 내부 그림에 적용되도록 함
		color = c;
		repaint();
	}
}
class MousePointerButton extends MyButton { //선택(화살표) 버튼
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = this.getWidth();
		int h = this.getHeight();
		Graphics2D g2 = (Graphics2D) g;
		int[]x = {w/2,w/5,w/5*2,w/5*2,w/5*3,w/5*3,w/5*4}; //다각형을 적당히 그려서 화살표 모양을 만든다
		int[]y = {h/5,h/2,h/2,h/5*4,h/5*4,h/2,h/2};
		g2.setStroke(new BasicStroke(2));
		g2.drawPolygon(x,y,7);
		g2.setStroke(new BasicStroke(1));
	}
}


/*------------------------도형 클래스-------------------------*/
abstract class MyShape { //모든 도형의 부모클래스가 되는 추상클래스인 MyShape 클래스
	int x1, y1, x2, y2; //마우스 press위치와 release위치
	int pressedX, pressedY; //도형 이동 시 이동할 거리를 알기 위해 사용
	boolean isSelected = false; //선택되었는지 여부
	boolean isMoving = false; //선택 후 드래깅중인지 여부
	Color color;
	int dx=0; //도형 이동 시 움직여야 할 변위
	int dy=0;
	MyShape(int x, int y) {
		x1 = x;
		y1 = y;
		x2 = x;
		y2 = y;
	}
	public void draw(Graphics g) {
		g.setColor(color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		//기본 동작 : 색상 설정, 선 두께 설정
	}
	public void mouseReleased(int x, int y) {
		mouseDragged(x,y); //마우스 release 시 : drag와 동일
	}
	public void mouseDragged(int x, int y) {
		if (isMoving) { //움직이는 경우라면
			dx = x - pressedX;
			dy = y - pressedY; //변위를 구하고
			x1 += dx;
			y1 += dy;
			x2 += dx;
			y2 += dy; //변위만큼 이동
			pressedX = x;
			pressedY = y; //변위는 다시 초기화(초기화하지 않으면 누적합이 됨)
		}
		else {
			x2 = x; //그려지는 경우라면, 도형의 종료좌표 설정 
			y2 = y;
		}
	}
	abstract public boolean isClicked(int x, int y); //도형이 선택되었는지 여부는 각 자식클래스에서 계산
	public void setPressedPoint(int x, int y) { //모든 자식 클래스에 공통 : Override X
		isMoving = true; //도형이 움직이는 중인 것으로 세팅
		pressedX = x;
		pressedY = y;
	}
}
class MyLine extends MyShape{ //직선 도형 클래스
	MyLine(int _x, int _y) {
		super(_x, _y);
	}
	@Override
	public void draw(Graphics g) { 
		super.draw(g);
		if (isSelected) { //도형이 선택된 경우, 본래의 도형을 그리기 전에 두꺼운 stroke로 똑같이 한번 더 그리고 덮어쓰면 테두리가 생김
			Graphics2D g2 = (Graphics2D)g;
			g.setColor(Color.red);
			g2.setStroke(new BasicStroke(6));
			g.drawLine(x1, y1, x2, y2);
			g2.setStroke(new BasicStroke(4));
			g.setColor(color);
		}
		g.drawLine(x1, y1, x2, y2);
	}
	@Override
	public void mouseReleased(int x, int y) {
		super.mouseReleased(x, y);
	}
	@Override
	public void mouseDragged(int x, int y) {
		super.mouseDragged(x, y);
	}
	@Override
	public boolean isClicked(int x, int y) { 
		//클릭되었는지 확인 : 직선의 거리의 합 활용
		double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		double d1 = Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
		double d2 = Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
		if (d1+d2-0.25<=d && d<=d1+d2+0.25) return true; //두께에 의한 오차범위 내에 있으면 OK
		else if (d1 <= 4 || d2 <= 4) return true;
		//첫번째 조건만으로는 양 끝 점의 두께에 의한 오차범위를 확인하기에 부족하여, 양 끝 점의 원을 기준으로 한번 더 확인
		else return false;
	}
}
class MyRect extends MyShape{
	MyRect(int _x, int _y) {
		super(_x, _y);
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		int w = x2-x1;
		int h = y2-y1;
		g.setColor(isSelected ? Color.red : Color.black); //선택된 경우 테두리는 빨간색, 아닌 경우는 검은색 테두리
		g.drawRect(w >= 0 ? x1 : x2, h >= 0 ? y1 : y2, Math.abs(w), Math.abs(h));
		g.setColor(color);
		g.fillRect(w >= 0 ? x1 : x2, h >= 0 ? y1 : y2, Math.abs(w), Math.abs(h));
	}
	@Override
	public void mouseReleased(int x, int y) {
		super.mouseReleased(x, y);
	}
	@Override
	public void mouseDragged(int x, int y) {
		super.mouseDragged(x, y);
	}
	@Override
	public boolean isClicked(int x, int y) { //사각형 영역 내에 x,y좌표가 존재하는지 확인 
		if (Math.min(x1, x2) <= x && x<= Math.max(x1, x2) && Math.min(y1, y2) <= y && y <= Math.max(y1, y2)) return true;
		else return false;
	}
}
class MyCircle extends MyShape{
	MyCircle(int _x, int _y) {
		super(_x, _y);
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		int w = x2-x1;
		int h = y2-y1;
		int r = (int)Math.sqrt(w*w + h*h);
		g.setColor(isSelected ? Color.red : Color.black); //선택된 경우 테두리는 빨간 색, 아닌 경우 테두리는 검은 색
		g.drawOval((w >= 0 ? x1 : x2-w)-r, (h >= 0 ? y1 : y2-h)-r, 2*r, 2*r);
		g.setColor(color);
		g.fillOval((w >= 0 ? x1 : x2-w)-r, (h >= 0 ? y1 : y2-h)-r, 2*r, 2*r);
	}
	@Override
	public void mouseReleased(int x, int y) {
		super.mouseReleased(x, y);
	}
	@Override
	public void mouseDragged(int x, int y) {
		super.mouseDragged(x, y);
	}
	@Override
	public boolean isClicked(int x, int y) {
		int w = x2-x1;
		int h = y2-y1;
		int rr = (w*w + h*h);
		int dd = (x-x1)*(x-x1) + (y-y1)*(y-y1);
		//원의 중심에서 좌표까지의 거리^2이 원의 반지름^2보다 작으면 내부에 있는 것임
		if (dd <= rr) return true;
		else return false;
	}
}
class MyCurve extends MyShape {
	class pos { //곡선을 그리기 위한 점들을 기억하기 위한 좌표 클래스(구조체처럼 사용)
		int x, y;
		pos(int _x, int _y) {
			x = _x;
			y = _y;
		}
	}
	LinkedList<pos>posList = new LinkedList<>(); //곡선을 그리기 위해 점들을 기억하는 링크드리스트
	MyCurve(int _x, int _y) {
		super(_x,_y);
		posList.add(new pos(_x,_y));
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (isSelected) { //도형이 선택된 경우 : 더 두꺼운 stroke로 먼저 한번 그리고, 그 위에 원래 도형을 그린다
			Graphics2D g2 = (Graphics2D)g;
			g.setColor(Color.red);
			g2.setStroke(new BasicStroke(6));
			pos prevPos = posList.getFirst();
			for(pos p : posList) {
				g.drawLine(prevPos.x, prevPos.y, p.x, p.y);
				prevPos = p;
			}
			g2.setStroke(new BasicStroke(4));
			g.setColor(color);
		}
		pos prevPos = posList.getFirst();
		for(pos p : posList) { //곡선 그리기 : 좌표리스트의 모든 점들을 직선으로 잇는다
			g.drawLine(prevPos.x, prevPos.y, p.x, p.y);
			prevPos = p;
		}
	}
	@Override
	public void mouseReleased(int x, int y) {
		super.mouseReleased(x, y);
		mouseDragged(x,y);
	}
	@Override
	public void mouseDragged(int x, int y) {
		super.mouseDragged(x, y);
		if (isMoving) { //곡선은 움직일 때 모든 점들을 변화시켜야 함
			for(int i=0;i<posList.size();i++) {
				posList.get(i).x += dx;
				posList.get(i).y += dy;
			}
		}
		else posList.add(new pos(x,y));
		//그려지는 경우, 지나가는 모든 점들을 좌표리스트에 기억한다
	}
	@Override
	public boolean isClicked(int x, int y) { //곡선의 모든 점들의 좌표들에 대해, 클릭 좌표와 적당한 범위 내에서 겹치는지 확인
		for(pos p : posList) if (p.x-3 <= x && x <= p.x+3 && p.y-3 <= y && y <= p.y+3) return true;
		return false;
	}
}


/*------------------------JPanel, JFrame 클래스-------------------------*/
class MyPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	ButtonGroup buttonGroup = new ButtonGroup(); //JToggleButton은 그룹화하여 관리해야 토글 등의 특징이 연동된다
	JToggleButton[] buttons = new JToggleButton[6]; //6개의 각기 다른 버튼들을 업캐스팅하여 유지할 배열
	LinkedList<MyShape> shapes = new LinkedList<>(); //모든 도형들을 업캐스팅하여 관리할 LinkedList
	JColorChooser cc; //색상 선택 버튼을 누르면 등장할 JColorChooser
	
	Color selectedColor = Color.blue; //현재 선택된 색상
	int selectedButton = 0; //현재 선택된 버튼
	MyShape selectedShape = null; //현재 선택된 도형
	MyPanel() { //생성자 : 초기 설정
		this.setBackground(Color.white);
		cc = new JColorChooser(selectedColor);
		initButtons(); //버튼 객체를 생성하는 함수 호출
		
		for(JToggleButton b : buttons) add(b); //모든 버튼들을 Panel에 add

		cc.setSize(100,100);
		cc.setLocation(80,450); //JColorChooser는 버튼들의 밑이면서도 Frame의 중간에 오도록 해두고,
		cc.setVisible(false); //처음에는 보이지 않게 둠
		cc.getSelectionModel().addChangeListener((e)->{ //람다식으로 JColorChooser의 ChangeListener를 작성
			selectedColor = cc.getColor(); //선택된 색을 저장
			cc.setVisible(false); //JColorButton이 다시 보이지않도록 setVisible
			ColorButton cb = (ColorButton)buttons[4]; 
			cb.setColor(selectedColor); //ColorButton에 그려지는 사각형의 색 변경
			buttonGroup.setSelected(buttons[selectedButton].getModel(), true); //색을 변경하고나면 이전에 눌려있던 버튼으로 다시 선택
		});
		//모든 컴포넌트들과 이벤트리스너들을 추가
		add(cc);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		//keyListener는 focus가 있어야 함
		this.setFocusable(true);
		this.requestFocus();
	}
	private void initButtons() {
		//6개 버튼을 각기 다른 객체로 초기화하고, 업캐스팅하여 부모클래스의 배열로 유지
		buttons[0] = new LineButton();
		buttons[1] = new RectButton();
		buttons[2] = new CircleButton();
		buttons[3] = new CurveButton();
		buttons[4] = new ColorButton();
		buttons[5] = new MousePointerButton();
		//ButtonGroup에 추가하여 JToggleButton 그룹화
		buttonGroup.add(buttons[0]);
		buttonGroup.add(buttons[1]);
		buttonGroup.add(buttons[2]);
		buttonGroup.add(buttons[3]);
		buttonGroup.add(buttons[4]);
		buttonGroup.add(buttons[5]);
		//Button들 각각을 누를 때 어떤 동작을 할지 람다표현식으로 작성
		buttons[0].addItemListener((e)->{
			selectedButton = 0;
			cc.setVisible(false);
		});
		buttons[1].addItemListener((e)->{
			selectedButton = 1;
			cc.setVisible(false);
		});
		buttons[2].addItemListener((e)->{
			selectedButton = 2;
			cc.setVisible(false);
		});
		buttons[3].addItemListener((e)->{
			selectedButton = 3;
			cc.setVisible(false);
		});
		buttons[4].addItemListener((e)->{
			cc.setVisible(true);
		});
		buttons[5].addItemListener((e)->{
			selectedButton = 5;
			cc.setVisible(false);
		});
		//button에 focus가 넘어가지 않도록 하여 key event를 panel에서 계속 받을 수 있도록 함
		buttons[0].setFocusable(false);
		buttons[1].setFocusable(false);
		buttons[2].setFocusable(false);
		buttons[3].setFocusable(false);
		buttons[4].setFocusable(false);
		buttons[5].setFocusable(false);
		//시작하면 직선 버튼이 눌린채 시작
		buttonGroup.setSelected(buttons[0].getModel(), true);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(MyShape s : shapes) s.draw(g);
		//도형들을 모두 그린다
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(MyShape s : shapes) { //먼저 선택 관련 모두 초기화하고 시작
			s.isSelected = false;
			s.isMoving = false;
			selectedShape = null;
		}
		int x = e.getX();
		int y = e.getY();
		switch (selectedButton) {
		//도형 버튼이 선택된 경우, 새 도형 객체를 생성하고 리스트에 추가, 색상 설정
		case 0:
			shapes.add(new MyLine(x, y)); shapes.getLast().color = selectedColor; break;
		case 1:
			shapes.add(new MyRect(x, y)); shapes.getLast().color = selectedColor; break;
		case 2:
			shapes.add(new MyCircle(x,y)); shapes.getLast().color = selectedColor; break;
		case 3:
			shapes.add(new MyCurve(x,y)); shapes.getLast().color = selectedColor; break;
		//색상 선택 중일 때는 딱히 할 동작 없음
		case 4: break;
		case 5: //도형 선택중일 시
			//LinkedList는 인덱스 기반 접근이 비효율적일 수 있으므로, 역순 iterator를 생성하고 이를 이용하여 순회
			//역순으로 순회해야 최근(레이어 가장 위쪽의)에 그려진 도형을 선택할 수 있다
			Iterator<MyShape> iterator = shapes.descendingIterator();
			while (iterator.hasNext()) {
			    MyShape shape = iterator.next();
			    if (shape.isClicked(x, y)) { //모든 도형들을 순회하며 선택여부 확인
					selectedShape = shape;
					selectedShape.isSelected = true;
					selectedShape.setPressedPoint(x, y);
					break;
				}
			}
			repaint();//repaint하여 변경 적용
			break;
		default:
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (selectedButton == 5) { //현재 선택 모드인 경우, 선택된 도형의 메소드를 부른다
			if (selectedShape != null) selectedShape.mouseDragged(x, y);
		}
		else shapes.getLast().mouseDragged(x, y); //그리는 중인 경우, 최근에 추가된 도형의 메소드를 호출
		this.repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDragged(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//back_space가 입력된 경우, 가장 최근에 그려진 도형 삭제
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !shapes.isEmpty()) {
			shapes.removeLast(); //맨 뒤(가장 최근에 그린 도형) 삭제
			repaint();
		}
		//delete가 입력된 경우, 선택된 도형 삭제
		if (e.getKeyCode() == KeyEvent.VK_DELETE && !shapes.isEmpty()) {
			Iterator<MyShape> iterator = shapes.descendingIterator();
			while (iterator.hasNext()) {
			    MyShape shape = iterator.next();
			    if (shape.isSelected) {
			    	iterator.remove();
					//shapes.remove(index); //선택된 도형이 있으면 remove
				}
			}
			repaint();
		}
	}
	
	//사용하지 않는 리스너 메소드
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}

public class Hw3 extends JFrame{
	Hw3() {
		//초기 설정
		setSize(900,600);
		setTitle("Hw3");
		this.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MyPanel());
		setVisible(true);
	}

	public static void main(String[] args) {
		new Hw3();
	}

}
