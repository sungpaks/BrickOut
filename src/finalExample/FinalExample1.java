package finalExample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class point {
	double x, y;
	point(double _x, double _y) {
		x = _x;
		y = _y;
	}
	public void draw(Graphics g, double w, double h) {
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g.drawLine((int)(x*w), (int)(y*h), (int)(w*x), (int)(h*y));
	}
}
class Rect{
	int x1, x2, y1, y2;
	Rect(int _x1, int _y1) {
		x1 = _x1;
		y1 = _y1;
		x2 = x1;
		y2 = y1;
	}
	public void draw(Graphics g) {
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		int minX = Math.min(x2, x1);
		int minY = Math.min(y1, y2);
		int maxX = Math.max(x1, x2);
		int maxY = Math.max(y1, y2);
		g.drawRect(minX, minY, maxX-minX, maxY-minY);
	}
	public void released(int x, int y) {
		x2 = x;
		y2 = y;
	}
}

class examPanel1 extends JPanel implements MouseListener, MouseMotionListener{
	boolean isLeft = false;
	LinkedList<point>points = new LinkedList<>();
	JLabel l;
	Rect rect = null;
	examPanel1() {
		l = new JLabel(Integer.toString(points.size()));
		add(l);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double w = getWidth();
		double h = getHeight();
		double prevX=-1, prevY=-1;
		for(point p : points) {
			p.draw(g, w,h);
			if (prevX != -1 && prevY != -1) {
				g.drawLine((int)(p.x*w),(int)(p.y*h), (int)(prevX*w), (int)(prevY*h));
			}
			prevX = p.x;
			prevY = p.y;
		}
		if (rect != null) rect.draw(g);
		l.setText(Integer.toString(points.size()));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		double w = getWidth();
		double h = getHeight();
		if (e.getButton() == MouseEvent.BUTTON1) {
			isLeft = true;
			points.add(new point((double)e.getX()/w, (double)e.getY()/h));	
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			isLeft = false;
			rect = new Rect(e.getX(),e.getY());
		}
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (isLeft == false) {
			var it = points.iterator();
			while(it.hasNext()) {
				var p = it.next();
				int minX = Math.min(rect.x2, rect.x1);
				int minY = Math.min(rect.y1, rect.y2);
				int maxX = Math.max(rect.x1, rect.x2);
				int maxY = Math.max(rect.y1, rect.y2);
				if (minX <= p.x && p.x <= maxX && minY <= p.y && p.y <= maxY)
					it.remove();
			}
			rect = null;
		}
		isLeft = false;
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		double w = getWidth();
		double h = getHeight();
		if (isLeft)
			points.add(new point((double)e.getX()/w, (double)e.getY()/h));
		else
			rect.released(e.getX(), e.getY());
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class FinalExample1 extends JFrame{
	FinalExample1() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new examPanel1());
		setVisible(true);
	}
	public static void main(String[] args) {
		new FinalExample1();
	}

}
