import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class MyObject { //ȭ�� ��ü�鿡 ���� �⺻Ŭ����
	MyObject() {}
	abstract public void draw(Graphics g); //��ü���� �׸��Ű� : abstract�� ������ override
	public void update(double dt) {} //��ǥ�� ������Ʈ�ɰŰ� : �������̵� �ϵ縻�� optional
	public void resolve(MyObject o) {}; //��ü�� ��ü���� �ε��� ��
	public boolean isDead() {return false;}
}
class MyWall extends MyObject {
	int x, y;
	int width, height;
	Color color;
	MyWall(int _x, int _y, int w, int h, Color c) {
		x = _x;
		y = _y;
		width = w;
		height = h;
		color = c;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	public boolean isIn(MySphere o) {
		//���簢�� ���� ũ�⸦ r��ŭ Ű���, �� �ȿ� �߽���ǥ�� �����ϸ� true�� �ҰԿ�
		//���������� �Ϻ����� �ʰ�����, �ϴ��� ���� �̷���
		double xmin = x-o.r;
		double ymin = y-o.r;
		double xmax = x+width+o.r;
		double ymax = y+height+o.r;
		if (o.x>xmin && o.x<xmax && o.y>ymin && o.y<ymax) return true;
		return false;
	}
}

class MySphere extends MyObject {
	static final double MAX_AGE = 5.0;
	double x,y,r;
	double prevX, prevY;
	double vx, vy; //��ӵ��
	Color color;
	double age;
	MySphere(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		prevX = x;
		prevY = y;
		double angle = Math.random()*3.141592*2; //2 pi r �߿� ���� �ϳ� ���
		double speed = Math.random()*100 + 100.0;
		vx = Math.cos(angle)*speed;
		vy = Math.sin(angle)*speed;
		color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		age = 0;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(x-r), (int)(y-r), 2*(int)r, 2*(int)r);
	}
	@Override
	public void update(double dt) {
		age += dt;
		prevX = x; //������ġ�� ���. �̰� �ַ�� ����ϸ� �ܻ� ���� �� �ְ���?
		prevY = y;
		x += vx*dt;
		y += vy*dt;
		r = MAX_AGE - age;
	}
	@Override
	public void resolve(MyObject o) {
		if (o instanceof MyWall != true) return;
		MyWall w = (MyWall) o; //�� MyWall�̱��� : ��������ȯ ����
		if (w.isIn(this) == false) return;
		//���� �������� ����
		double xmin = w.x-r;
		double ymin = w.y-r;
		double xmax = w.x+w.width+r;
		double ymax = w.y+w.height+r;
		if (prevX < xmin) {vx = -vx;x = xmin;} //��� ���� 
		if (prevX > xmax) {vx = -vx;x = xmax;} //��� ������
		if (prevY < ymin) {vy = -vy;y = ymin;} //��� ����
		if (prevY > ymax) {vy = -vy;y = ymax;} //��� �Ʒ���
	}
	@Override
	public boolean isDead() {
		if (age > MAX_AGE) return true;
		return false;
	}
}

class MovingBallPracticePanel extends JPanel implements KeyListener, Runnable{
	LinkedList<MyObject>objs;
	MovingBallPracticePanel() {
		objs = new LinkedList<>();
		objs.add(new MyWall(0,0,500,30,Color.black));
		objs.add(new MyWall(0,440,500,30,Color.black));
		objs.add(new MyWall(0,0,30,500,Color.black));
		objs.add(new MyWall(460,0,30,500,Color.black));
		objs.add(new MyWall(100,100,150,50,Color.black));
		objs.add(new MyWall(250,300,150,80,Color.black));
		
		Thread t = new Thread(this);
		t.start();
		
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(var o : objs) o.draw(g); //var : �ڵ� Ÿ���߷�
	}
	@Override
	public void run() {
		while(true) {
			//1. update
			for(var o : objs) {
				o.update(0.016);
			}
			//2. resolve
			for(var o1 : objs) { //�� ������ ������� �����帱�Կ�
				for(var o2 : objs) {
					if (o1 == o2) continue;
					o1.resolve(o2);
				}
			}
			var it = objs.iterator(); //for-each���� �����ϸ� �ȵǴϱ�
			while (it.hasNext()) {
				var o = it.next();
				if (o.isDead()) it.remove();
			}
			
			//3. render
			repaint();
			try {
				Thread.sleep(16); //1/60�ʸ��� , gameloop
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			objs.add(new MySphere(getWidth()/2, getHeight()/2, 5));
			repaint();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}

public class MovingBallPractice extends JFrame{
	MovingBallPractice() {
		setSize(500,500);
		setTitle("Moving Balls");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MovingBallPracticePanel());
		setVisible(true);
	}
	public static void main(String[] args) {
		new MovingBallPractice();
	}
}
