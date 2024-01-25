package finalExample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


class examplePanel11 extends JPanel implements Runnable, KeyListener {
	double vx=0;
	double vy=0;
	double x = 0, y = 0;
	double width=50;
	examplePanel11() {
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setStroke(new BasicStroke(2));
		g.setColor(Color.blue);
		g.drawRect((int)x, (int)y, (int)width, (int)width);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) vx = -0.5;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) vx = 0.5;
		if(e.getKeyCode() == KeyEvent.VK_UP) vy = -0.5;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) vy = 0.5;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	void update(double dt) {
		x += vx*dt;
		y += vy*dt;
	}
	void resolve() {
		if (x < 0) {vx = -vx; x = 0;}
		if (y < 0) {vy = -vy; y = 0;}
		if (x+width > getWidth()) {vx=-vx;x=getWidth()-width;}
		if (y+width > getHeight()) {vy=-vy;y=getHeight()-width;}
	}
	@Override
	public void run() {
		while(true) {
			try {
				update(16);
				resolve();
				repaint();
				Thread.sleep(16);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}

public class FinalExample11 extends JFrame{
	FinalExample11() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		examplePanel11 p = new examplePanel11();
		add(p);
		Thread t = new Thread(p);
		setVisible(true);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			return;
		}
	}
	public static void main(String[] args) {
		new FinalExample11();
	}

}
