package finalExample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MyBall {
	double x, y, vy, ay;
	double r = 5;
	MyBall(double _x, double _y) {
		x = _x;
		y = _y;
		vy = 0;
		ay = 10;
	}
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)(x-r), (int)(y-r), 2*(int)r, 2*(int)r);
	}
	public void update(double dt) {
		vy += ay*dt;
		y += vy*dt;
	}
	public void resolve(int h) {
		if (y > h) {
			vy = -vy*0.9;
			y = h;
		}
	}
}

class examplePanel12 extends JPanel implements KeyListener,Runnable {
	ArrayList<MyBall>balls = new ArrayList<>();
	int size = 0;
	double startY;
	int time = 0;
	examplePanel12() {
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		startY = 100;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(var b : balls) b.draw(g);
	}
	@Override
	public void run() {
		while (true) {
			time++;
			for(int i=0;i<balls.size();i++) {
				if (i < time*16/100)
					balls.get(i).update(0.16);
			}
			for(var b : balls) b.resolve(getHeight());
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		balls.clear();
		if(e.getKeyCode()==KeyEvent.VK_SPACE) size++;
		for(int i=0;i<size;i++) {
			balls.add(new MyBall(getWidth()/(size+1)*(i+1),startY));
		}
		time = 0;
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
public class FinalExample12 extends JFrame{
	FinalExample12() {
		setSize(1000,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		examplePanel12 p = new examplePanel12();
		add(p);
		setVisible(true);
		Thread t = new Thread(p);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			return;
		}
	}
	public static void main(String[] args) {
		new FinalExample12();
	}
}
