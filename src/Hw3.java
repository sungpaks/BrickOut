
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

/*------------------------��ư Ŭ����-------------------------*/
class MyButton extends JToggleButton { //����� ��ư���� ������ ������ ����� �θ� ButtonŬ����
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setSize(40,40); //����� �����ϰ� �����ϸ�,
		setMargin(new Insets(17,17,17,17));; //���ʿ����� ������ �����Ͽ� ��ư���� ��¦�� ���������� ����
	}
}
//6������ ��ư���� MyButton�� ��ӹ޾� ���� ����� �������� �ϸ�, ��ư ������ �׸����� �ٸ����� paintComponent�޼ҵ带 ������
class LineButton extends MyButton { //�� ��ư
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2)); //�� �β���
		g2.drawLine(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 4 * 3, this.getHeight() / 4 * 3);
		g2.setStroke(new BasicStroke(1)); //�� �β� �ٽ� ����ȭ
	}
}
class RectButton extends MyButton { //�簢�� ��ư
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
}
class CircleButton extends MyButton { //�� ��ư
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawOval(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
}
class CurveButton extends MyButton { //� ��ư
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
class ColorButton extends MyButton { //���� ���� ��ư
	Color color = Color.blue; //�ʱⰪ�� blue
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color); //��ư ���δ� ���� ������ ä���� ���簢�� �׸� ����
		g2.setStroke(new BasicStroke(2));
		g2.fillRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);
		g2.setStroke(new BasicStroke(1));
	}
	public void setColor(Color c) { //���� ���õ� ������ ��ư ���� �׸��� ����ǵ��� ��
		color = c;
		repaint();
	}
}
class MousePointerButton extends MyButton { //����(ȭ��ǥ) ��ư
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = this.getWidth();
		int h = this.getHeight();
		Graphics2D g2 = (Graphics2D) g;
		int[]x = {w/2,w/5,w/5*2,w/5*2,w/5*3,w/5*3,w/5*4}; //�ٰ����� ������ �׷��� ȭ��ǥ ����� �����
		int[]y = {h/5,h/2,h/2,h/5*4,h/5*4,h/2,h/2};
		g2.setStroke(new BasicStroke(2));
		g2.drawPolygon(x,y,7);
		g2.setStroke(new BasicStroke(1));
	}
}


/*------------------------���� Ŭ����-------------------------*/
abstract class MyShape { //��� ������ �θ�Ŭ������ �Ǵ� �߻�Ŭ������ MyShape Ŭ����
	int x1, y1, x2, y2; //���콺 press��ġ�� release��ġ
	int pressedX, pressedY; //���� �̵� �� �̵��� �Ÿ��� �˱� ���� ���
	boolean isSelected = false; //���õǾ����� ����
	boolean isMoving = false; //���� �� �巡�������� ����
	Color color;
	int dx=0; //���� �̵� �� �������� �� ����
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
		//�⺻ ���� : ���� ����, �� �β� ����
	}
	public void mouseReleased(int x, int y) {
		mouseDragged(x,y); //���콺 release �� : drag�� ����
	}
	public void mouseDragged(int x, int y) {
		if (isMoving) { //�����̴� �����
			dx = x - pressedX;
			dy = y - pressedY; //������ ���ϰ�
			x1 += dx;
			y1 += dy;
			x2 += dx;
			y2 += dy; //������ŭ �̵�
			pressedX = x;
			pressedY = y; //������ �ٽ� �ʱ�ȭ(�ʱ�ȭ���� ������ �������� ��)
		}
		else {
			x2 = x; //�׷����� �����, ������ ������ǥ ���� 
			y2 = y;
		}
	}
	abstract public boolean isClicked(int x, int y); //������ ���õǾ����� ���δ� �� �ڽ�Ŭ�������� ���
	public void setPressedPoint(int x, int y) { //��� �ڽ� Ŭ������ ���� : Override X
		isMoving = true; //������ �����̴� ���� ������ ����
		pressedX = x;
		pressedY = y;
	}
}
class MyLine extends MyShape{ //���� ���� Ŭ����
	MyLine(int _x, int _y) {
		super(_x, _y);
	}
	@Override
	public void draw(Graphics g) { 
		super.draw(g);
		if (isSelected) { //������ ���õ� ���, ������ ������ �׸��� ���� �β��� stroke�� �Ȱ��� �ѹ� �� �׸��� ����� �׵θ��� ����
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
		//Ŭ���Ǿ����� Ȯ�� : ������ �Ÿ��� �� Ȱ��
		double d = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		double d1 = Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
		double d2 = Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
		if (d1+d2-0.25<=d && d<=d1+d2+0.25) return true; //�β��� ���� �������� ���� ������ OK
		else if (d1 <= 4 || d2 <= 4) return true;
		//ù��° ���Ǹ����δ� �� �� ���� �β��� ���� ���������� Ȯ���ϱ⿡ �����Ͽ�, �� �� ���� ���� �������� �ѹ� �� Ȯ��
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
		g.setColor(isSelected ? Color.red : Color.black); //���õ� ��� �׵θ��� ������, �ƴ� ���� ������ �׵θ�
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
	public boolean isClicked(int x, int y) { //�簢�� ���� ���� x,y��ǥ�� �����ϴ��� Ȯ�� 
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
		g.setColor(isSelected ? Color.red : Color.black); //���õ� ��� �׵θ��� ���� ��, �ƴ� ��� �׵θ��� ���� ��
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
		//���� �߽ɿ��� ��ǥ������ �Ÿ�^2�� ���� ������^2���� ������ ���ο� �ִ� ����
		if (dd <= rr) return true;
		else return false;
	}
}
class MyCurve extends MyShape {
	class pos { //��� �׸��� ���� ������ ����ϱ� ���� ��ǥ Ŭ����(����üó�� ���)
		int x, y;
		pos(int _x, int _y) {
			x = _x;
			y = _y;
		}
	}
	LinkedList<pos>posList = new LinkedList<>(); //��� �׸��� ���� ������ ����ϴ� ��ũ�帮��Ʈ
	MyCurve(int _x, int _y) {
		super(_x,_y);
		posList.add(new pos(_x,_y));
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (isSelected) { //������ ���õ� ��� : �� �β��� stroke�� ���� �ѹ� �׸���, �� ���� ���� ������ �׸���
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
		for(pos p : posList) { //� �׸��� : ��ǥ����Ʈ�� ��� ������ �������� �մ´�
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
		if (isMoving) { //��� ������ �� ��� ������ ��ȭ���Ѿ� ��
			for(int i=0;i<posList.size();i++) {
				posList.get(i).x += dx;
				posList.get(i).y += dy;
			}
		}
		else posList.add(new pos(x,y));
		//�׷����� ���, �������� ��� ������ ��ǥ����Ʈ�� ����Ѵ�
	}
	@Override
	public boolean isClicked(int x, int y) { //��� ��� ������ ��ǥ�鿡 ����, Ŭ�� ��ǥ�� ������ ���� ������ ��ġ���� Ȯ��
		for(pos p : posList) if (p.x-3 <= x && x <= p.x+3 && p.y-3 <= y && y <= p.y+3) return true;
		return false;
	}
}


/*------------------------JPanel, JFrame Ŭ����-------------------------*/
class MyPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	ButtonGroup buttonGroup = new ButtonGroup(); //JToggleButton�� �׷�ȭ�Ͽ� �����ؾ� ��� ���� Ư¡�� �����ȴ�
	JToggleButton[] buttons = new JToggleButton[6]; //6���� ���� �ٸ� ��ư���� ��ĳ�����Ͽ� ������ �迭
	LinkedList<MyShape> shapes = new LinkedList<>(); //��� �������� ��ĳ�����Ͽ� ������ LinkedList
	JColorChooser cc; //���� ���� ��ư�� ������ ������ JColorChooser
	
	Color selectedColor = Color.blue; //���� ���õ� ����
	int selectedButton = 0; //���� ���õ� ��ư
	MyShape selectedShape = null; //���� ���õ� ����
	MyPanel() { //������ : �ʱ� ����
		this.setBackground(Color.white);
		cc = new JColorChooser(selectedColor);
		initButtons(); //��ư ��ü�� �����ϴ� �Լ� ȣ��
		
		for(JToggleButton b : buttons) add(b); //��� ��ư���� Panel�� add

		cc.setSize(100,100);
		cc.setLocation(80,450); //JColorChooser�� ��ư���� ���̸鼭�� Frame�� �߰��� ������ �صΰ�,
		cc.setVisible(false); //ó������ ������ �ʰ� ��
		cc.getSelectionModel().addChangeListener((e)->{ //���ٽ����� JColorChooser�� ChangeListener�� �ۼ�
			selectedColor = cc.getColor(); //���õ� ���� ����
			cc.setVisible(false); //JColorButton�� �ٽ� �������ʵ��� setVisible
			ColorButton cb = (ColorButton)buttons[4]; 
			cb.setColor(selectedColor); //ColorButton�� �׷����� �簢���� �� ����
			buttonGroup.setSelected(buttons[selectedButton].getModel(), true); //���� �����ϰ��� ������ �����ִ� ��ư���� �ٽ� ����
		});
		//��� ������Ʈ��� �̺�Ʈ�����ʵ��� �߰�
		add(cc);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		//keyListener�� focus�� �־�� ��
		this.setFocusable(true);
		this.requestFocus();
	}
	private void initButtons() {
		//6�� ��ư�� ���� �ٸ� ��ü�� �ʱ�ȭ�ϰ�, ��ĳ�����Ͽ� �θ�Ŭ������ �迭�� ����
		buttons[0] = new LineButton();
		buttons[1] = new RectButton();
		buttons[2] = new CircleButton();
		buttons[3] = new CurveButton();
		buttons[4] = new ColorButton();
		buttons[5] = new MousePointerButton();
		//ButtonGroup�� �߰��Ͽ� JToggleButton �׷�ȭ
		buttonGroup.add(buttons[0]);
		buttonGroup.add(buttons[1]);
		buttonGroup.add(buttons[2]);
		buttonGroup.add(buttons[3]);
		buttonGroup.add(buttons[4]);
		buttonGroup.add(buttons[5]);
		//Button�� ������ ���� �� � ������ ���� ����ǥ�������� �ۼ�
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
		//button�� focus�� �Ѿ�� �ʵ��� �Ͽ� key event�� panel���� ��� ���� �� �ֵ��� ��
		buttons[0].setFocusable(false);
		buttons[1].setFocusable(false);
		buttons[2].setFocusable(false);
		buttons[3].setFocusable(false);
		buttons[4].setFocusable(false);
		buttons[5].setFocusable(false);
		//�����ϸ� ���� ��ư�� ����ä ����
		buttonGroup.setSelected(buttons[0].getModel(), true);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(MyShape s : shapes) s.draw(g);
		//�������� ��� �׸���
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(MyShape s : shapes) { //���� ���� ���� ��� �ʱ�ȭ�ϰ� ����
			s.isSelected = false;
			s.isMoving = false;
			selectedShape = null;
		}
		int x = e.getX();
		int y = e.getY();
		switch (selectedButton) {
		//���� ��ư�� ���õ� ���, �� ���� ��ü�� �����ϰ� ����Ʈ�� �߰�, ���� ����
		case 0:
			shapes.add(new MyLine(x, y)); shapes.getLast().color = selectedColor; break;
		case 1:
			shapes.add(new MyRect(x, y)); shapes.getLast().color = selectedColor; break;
		case 2:
			shapes.add(new MyCircle(x,y)); shapes.getLast().color = selectedColor; break;
		case 3:
			shapes.add(new MyCurve(x,y)); shapes.getLast().color = selectedColor; break;
		//���� ���� ���� ���� ���� �� ���� ����
		case 4: break;
		case 5: //���� �������� ��
			//LinkedList�� �ε��� ��� ������ ��ȿ������ �� �����Ƿ�, ���� iterator�� �����ϰ� �̸� �̿��Ͽ� ��ȸ
			//�������� ��ȸ�ؾ� �ֱ�(���̾� ���� ������)�� �׷��� ������ ������ �� �ִ�
			Iterator<MyShape> iterator = shapes.descendingIterator();
			while (iterator.hasNext()) {
			    MyShape shape = iterator.next();
			    if (shape.isClicked(x, y)) { //��� �������� ��ȸ�ϸ� ���ÿ��� Ȯ��
					selectedShape = shape;
					selectedShape.isSelected = true;
					selectedShape.setPressedPoint(x, y);
					break;
				}
			}
			repaint();//repaint�Ͽ� ���� ����
			break;
		default:
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (selectedButton == 5) { //���� ���� ����� ���, ���õ� ������ �޼ҵ带 �θ���
			if (selectedShape != null) selectedShape.mouseDragged(x, y);
		}
		else shapes.getLast().mouseDragged(x, y); //�׸��� ���� ���, �ֱٿ� �߰��� ������ �޼ҵ带 ȣ��
		this.repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDragged(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//back_space�� �Էµ� ���, ���� �ֱٿ� �׷��� ���� ����
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !shapes.isEmpty()) {
			shapes.removeLast(); //�� ��(���� �ֱٿ� �׸� ����) ����
			repaint();
		}
		//delete�� �Էµ� ���, ���õ� ���� ����
		if (e.getKeyCode() == KeyEvent.VK_DELETE && !shapes.isEmpty()) {
			Iterator<MyShape> iterator = shapes.descendingIterator();
			while (iterator.hasNext()) {
			    MyShape shape = iterator.next();
			    if (shape.isSelected) {
			    	iterator.remove();
					//shapes.remove(index); //���õ� ������ ������ remove
				}
			}
			repaint();
		}
	}
	
	//������� �ʴ� ������ �޼ҵ�
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
		//�ʱ� ����
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
