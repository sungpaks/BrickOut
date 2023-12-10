import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MyBall {
	double x, y;
	double vx, vy;
	double ax, ay;
	Color color;
	double radius;
	MyBall(int w, int h) {
		x = Math.random()*w/2 + w/4;
		y = Math.random()*h/2 + h/4;
		vx = Math.random()*200 - 100;
		vy = Math.random()*400 - 200;
		ax = 0;
		ay = 9.8;
		int r = (int)(Math.random()*255);
		int g = (int)(Math.random()*255);
		int b = (int)(Math.random()*255);
		color = new Color(r,g,b);
		radius = Math.random()*10 + 5;
	}
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(x-radius), (int)(y-radius), 2*(int)radius, 2*(int)radius);
	}
	void update(double dt) {
		ax = ax;
		ay = ay;
		vx = vx + ax*dt;
		vy = vy + ay*dt;
		x = x + vx*dt;
		y = y + vy*dt;
	}
	void resolve(int w, int h, ArrayList<MyBall>balls) {
		if (x > w-radius) {x = w-radius; vx = -vx;}
		if (y > h-radius) {y = h-radius; vy = -vy;}
		if (x<0+radius) {x=radius;vx=-vx;}
		if (y<0+radius) {y=radius;vy=-vy;}
		for(int i=0;i<balls.size();i++) {
			MyBall b = balls.get(i);
			if (b.equals(this)) continue;
			double dSquare = (x-b.x)*(x-b.x)+(y-b.y)*(y-b.y);
			double d = Math.sqrt(dSquare);
			if (dSquare < (radius + b.radius)*(radius + b.radius)) {
				double tmpx = vx;
				double tmpy = vy;
				vx = b.vx;
				vy = b.vx;
				b.vx = tmpx;
				b.vy = tmpy;
				b.x += b.vx*0.016*4;
				b.y += b.vy*0.016*4;
				x += vx*0.016*4;
				y += vy*0.016*4;
			}
		}
	}
}

class ThreadDynamicPracticePanel extends JPanel implements Runnable,KeyListener{
	ArrayList<MyBall> balls = new ArrayList<>();
	ThreadDynamicPracticePanel() {
		balls.add(new MyBall(getWidth(),getHeight()));
		Thread t = new Thread(this);
		t.start();
		
		addKeyListener(this); //리스너 등록
		this.setFocusable(true); //포커스 가능하게 함
		requestFocus(); //포커스 내놔
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(MyBall b : balls) b.draw(g);
	}
	@Override
	public void run() {
		while(true) { //GAME LOOP
			for(MyBall b : balls) b.update(0.016);
			for(MyBall b : balls) b.resolve(getWidth(), getHeight(),balls);
			repaint();
			try {
				Thread.sleep(4);
			} catch(InterruptedException e) {
				return;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		balls.add(new MyBall(getWidth(),getHeight()));
		/*
		 * switch (e.getKeyCode()) { case KeyEvent.VK_LEFT: dx = -10; break; case
		 * KeyEvent.VK_RIGHT: dx = +10; break; case KeyEvent.VK_UP: dy = -10; break;
		 * case KeyEvent.VK_DOWN: dy = +10; break; default: break; }
		 */
		//repaint(); 이제 필요없음 : 스레드에서 다시그릴거
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}

public class ThreadSynchronization extends JFrame{
	ThreadSynchronization() {
		setSize(500,500);
		setTitle("Dynamics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new ThreadDynamicPracticePanel());
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new ThreadSynchronization();
	}

}
