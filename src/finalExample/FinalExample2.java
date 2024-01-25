package finalExample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;


class MyRect {
	int x1, y1, x2, y2;
	Color color;
	MyRect(int x, int y, Color c) {
		x1 = x;
		y1 = y;
		x2 = x;
		y2 = y;
		color = c;
	}
	public void draw(Graphics g) {
		g.setColor(color);
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int maxX = Math.max(x1, x2);
		int maxY = Math.max(y1, y2);
		((Graphics2D)g).setStroke(new BasicStroke(3));
		g.drawRect(minX, minY, maxX-minX, maxY-minY);
	}
	public void mouseRelease(int x, int y) {
		x2 = x;
		y2 = y;
	}
}

class examPanel2 extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	LinkedList<MyRect>rects = new LinkedList<>();
	Color color;
	examPanel2() {
		color = Color.cyan;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(var r : rects) r.draw(g);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		rects.getLast().mouseRelease(e.getX(), e.getY());
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");
		rects.add(new MyRect(e.getX(),e.getY(), color));
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		rects.getLast().mouseRelease(e.getX(), e.getY());
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
	public void actionPerformed(ActionEvent e) {
		
	}
	public void setColor(Color c) {
		color = c;
	}
}

public class FinalExample2 extends JFrame {
	FinalExample2() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		examPanel2 p = new examPanel2();
		JMenuBar mb = new JMenuBar();
		JMenu m = new JMenu("Color");
		ButtonGroup bg = new ButtonGroup();
		JRadioButtonMenuItem redButton = new JRadioButtonMenuItem("red");
		redButton.addActionListener((e)->p.setColor(Color.red));
		JRadioButtonMenuItem greenButton = new JRadioButtonMenuItem("green");
		greenButton.addActionListener((e)->p.setColor(Color.green));
		JRadioButtonMenuItem blueButton = new JRadioButtonMenuItem("blue");
		blueButton.addActionListener((e)->p.setColor(Color.blue));
		bg.add(redButton);
		bg.add(greenButton);
		bg.add(blueButton);
		m.add(redButton);
		m.add(greenButton);
		m.addSeparator();
		m.add(blueButton);
		mb.add(m);
		add(mb);
		this.setJMenuBar(mb);
		add(p);
		setVisible(true);
	}
	public static void main(String[] args) {
		new FinalExample2();
	}
}
