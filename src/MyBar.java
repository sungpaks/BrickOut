import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class SpringBall {
	double originX, originY;
	double x, y, vx, vy, ax, ay;
	double radius;
	SpringBall(double x, double y) {
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		ax = 0;
		ay = 0;
		radius = 20;
		originX = x;
		originY = y;
	}
	void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval((int)(x-radius), (int)(y-radius), 2*(int)radius, 2*(int)radius);
	}
	void updateCharging(double dt) {
		x -= dt*100;
	}
	void updateMoving(double dt) {
		vx = vx + dt*ax;
		vy = vy + dt*ay;
		ax = 0;
		x = x + vx*dt;
		y = y + vy*dt;
	}
	void updateAcc() {
		ax = (originX - x)*1000; //스프링상수는 10이라고 함
		ay = 9.8;
	}
	void resolve(int w, int h) {
		if (x > w-radius) {x = w-radius; vx = -vx*0.8;}
		if (y > h-radius) {y = h-radius; vy = -vy*0.8;}
		if (x<0+radius) {x=radius;vx=-vx*0.8;}
		if (y<0+radius) {y=radius;vy=-vy*0.8;}
	}
}

class MyBarPanel extends JPanel implements Runnable, KeyListener{
	int barSize = 0;
	int maxBarSize;
	SpringBall b;
	Thread t;
	MyBarPanel(int max) {
		maxBarSize = max;
		t = new Thread(this);
		setFocusable(true);
		addKeyListener(this);
		requestFocus();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow);
		g.fillRect(100, 100, maxBarSize, 20);
		g.setColor(Color.red);
		g.fillRect(100, 100, barSize, 20);
		if (b!=null)b.draw(g);
	}
	@Override
	public void run() {
		try {
			while(true) {
				if (b == null) continue;
				System.out.println(b.x);
				b.updateMoving(0.016);
				b.resolve(getWidth(),getHeight());
				repaint();
				Thread.sleep(8);
			}
		} catch (InterruptedException e) {
			return;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_CONTROL) {
			if (b == null) b = new SpringBall(110,60);
			b.updateCharging(0.016);
		}
		else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			b.updateAcc();
			t.start();
		}
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}

public class MyBar extends JFrame{
	MyBar() {
		setSize(500,500);
		setTitle("MyBar");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MyBarPanel(300));
		setVisible(true);
	}
	public static void main(String[] args) {
		new MyBar();
	}

}
