package finalExample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;


class MyCircle {
	int x, y;
	double r;
	boolean isSelected = false;
	MyCircle(int _x, int _y) {
		x = _x;
		y = _y;
	}
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if (isSelected) g.setColor(Color.yellow);
		g.fillOval(x-(int)r, y-(int)r, 2*(int)r, 2*(int)r);
		g.setColor(Color.blue);
		g.drawOval(x-(int)r, y-(int)r, 2*(int)r, 2*(int)r);
	}
	public void mouseRelease(int _x, int _y) {
		r = Math.sqrt((x-_x)*(x-_x)+(y-_y)*(y-_y));
	}
	public boolean isIn(int _x, int _y) {
		double dist = Math.sqrt((x-_x)*(x-_x)+(y-_y)*(y-_y));
		if (dist <= r) return true;
		else return false;
	}
}

class examplePanel4 extends JPanel implements MouseListener, MouseMotionListener {
	LinkedList<MyCircle>circles = new LinkedList<>();
	MyCircle selectedCircle = null;
	examplePanel4() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(var c : circles) c.draw(g);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedCircle == null)
			circles.getLast().mouseRelease(e.getX(), e.getY());
		else
		{
			selectedCircle.x = e.getX();
			selectedCircle.y = e.getY();
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(var c : circles) c.isSelected = false;
		selectedCircle = null;
		for(var c : circles) {
			if(c.isIn(e.getX(), e.getY())) {
				if (selectedCircle != null) selectedCircle.isSelected = false;
				c.isSelected=true;
				selectedCircle = c;
			}
			else ;
		}
		if (selectedCircle == null)
			circles.add(new MyCircle(e.getX(),e.getY()));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//circles.getLast().mouseRelease(e.getX(), e.getY());
		//repaint();
		mouseDragged(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class FinalExample4 extends JFrame {
	FinalExample4() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new examplePanel4());
		setVisible(true);
	}
	public static void main(String[] args) {
		new FinalExample4();
	}
}
