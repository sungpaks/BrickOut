import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class MyObject { //화면 물체들에 대한 기본클래스
	MyObject() {}
	abstract public void draw(Graphics g); //물체들은 그릴거고 : abstract라 무조건 override
	public void update(double dt) {} //좌표가 업데이트될거고 : 오버라이드 하든말든 optional
	public void resolve(MyObject o) {}; //물체와 물체끼리 부딪힐 때
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
		//직사각형 벽의 크기를 r만큼 키우고, 그 안에 중심좌표가 존재하면 true로 할게요
		//꼭짓점에서 완벽하지 않겠지만, 일단은 쉽게 이렇게
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
	double vx, vy; //등속도운동
	Color color;
	double age;
	MySphere(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		prevX = x;
		prevY = y;
		double angle = Math.random()*3.141592*2; //2 pi r 중에 각도 하나 잡고
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
		prevX = x; //직전위치를 기억. 이걸 주루룩 기억하면 잔상도 만들 수 있겠죠?
		prevY = y;
		x += vx*dt;
		y += vy*dt;
		r = MAX_AGE - age;
	}
	@Override
	public void resolve(MyObject o) {
		if (o instanceof MyWall != true) return;
		MyWall w = (MyWall) o; //아 MyWall이구나 : 하향형변환 ㄱㄱ
		if (w.isIn(this) == false) return;
		//벽이 직각임을 가정
		double xmin = w.x-r;
		double ymin = w.y-r;
		double xmax = w.x+w.width+r;
		double ymax = w.y+w.height+r;
		if (prevX < xmin) {vx = -vx;x = xmin;} //경계 왼쪽 
		if (prevX > xmax) {vx = -vx;x = xmax;} //경계 오른쪽
		if (prevY < ymin) {vy = -vy;y = ymin;} //경계 위쪽
		if (prevY > ymax) {vy = -vy;y = ymax;} //경계 아래쪽
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
		for(var o : objs) o.draw(g); //var : 자동 타입추론
	}
	@Override
	public void run() {
		while(true) {
			//1. update
			for(var o : objs) {
				o.update(0.016);
			}
			//2. resolve
			for(var o1 : objs) { //좀 무식한 방법으로 보여드릴게요
				for(var o2 : objs) {
					if (o1 == o2) continue;
					o1.resolve(o2);
				}
			}
			var it = objs.iterator(); //for-each에서 변경하면 안되니까
			while (it.hasNext()) {
				var o = it.next();
				if (o.isDead()) it.remove();
			}
			
			//3. render
			repaint();
			try {
				Thread.sleep(16); //1/60초마다 , gameloop
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
