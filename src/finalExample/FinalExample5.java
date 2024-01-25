package finalExample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class RightPanel extends JPanel {
	LeftPanel leftPanel;
	int selected = -1;
	LinkedList<Point>points = new LinkedList<>();
	RightPanel() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				points.add(e.getPoint());
				leftPanel.updateList();
				repaint();
			}
		});
	}
	@Override public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int i = 0;
		for(var p : points) {
			if (i == selected) g.setColor(Color.red);
			else g.setColor(Color.black);
			g.drawOval(p.x-10, p.y-10, 20, 20);
			i++;
		}
	}
	public void removeSelected() {
		if (selected == -1) return;
		points.remove(selected);
	}
}

class LeftPanel extends JPanel implements ListSelectionListener{
	RightPanel rightPanel;
	JList list;
	DefaultListModel listModel;
	JButton b;
	LeftPanel() {
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		list.setPreferredSize(new Dimension(200,400));
		add(list);
		b = new JButton("DELETE");
		b.addActionListener((e)->{
			rightPanel.removeSelected();
			updateList();
		});
		add(b);
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		int index = list.getSelectedIndex();
		rightPanel.selected = index;
		rightPanel.repaint();
	}
	public void updateList() {
		listModel.clear();
		int i = 0;
		for(Point p : rightPanel.points) {
			String str = new String();
			str = i+": center(" + p.x + "," + p.y + ")";
			listModel.addElement(str);
			i++;
		}
	}
}

public class FinalExample5 extends JFrame{
	FinalExample5() {
		setLayout(new BorderLayout());
		setSize(800,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LeftPanel p1 = new LeftPanel();
		RightPanel p2 = new RightPanel();
		p1.setPreferredSize(new Dimension(200,500));
		p1.setBackground(Color.lightGray);
		add(BorderLayout.WEST, p1);
		add(BorderLayout.CENTER, p2);
		p1.rightPanel = p2;
		p2.leftPanel = p1;
		setVisible(true);
	}
	public static void main(String[] args) {
		new FinalExample5();
	}

}
