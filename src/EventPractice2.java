import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class EventPractice2Panel extends JPanel implements MouseListener, MouseMotionListener{
	JLabel l;
	JButton b;
	JTextField tf;
	int mousePressed = 0;
	
	EventPractice2Panel(JFrame frame) {
		setLayout(null);
		this.setBackground(Color.orange);
		tf = new JTextField(frame.getTitle());
		b = new JButton("OK");
		b.addActionListener((e)->{
			String str = tf.getText();
			frame.setTitle(str);
		});
		l = new JLabel("SSS");
		
		tf.setSize(150, 30);
		b.setSize(80,30);
		l.setSize(80,30);
		
		tf.setLocation(50,50);
		b.setLocation(200,50);
		l.setLocation(290,50);

		add(tf);
		add(b);
		add(l);
		
		l.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				l.setText(l.getText()+"+");
			}
		});

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//setBackground(Color.blue);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = e.getButton();
		if (e.getButton() == MouseEvent.BUTTON1)
		{
		System.out.println("Pressed" + " " +e.getXOnScreen() + " " + e.getYOnScreen());
		l.setLocation(e.getX(), e.getY());
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = 0;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.pink);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(Color.DARK_GRAY);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Dragged : " + e.getX() + " " + e.getY());
		if (mousePressed == MouseEvent.BUTTON1) l.setLocation(e.getX(), e.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Moved : " + e.getX() + " " + e.getY());
	}
}

public class EventPractice2 extends JFrame{
	static public EventPractice2 frame = null;
	EventPractice2 () {
		frame = this;
		setSize(400,300);
		setTitle("Event Practice2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * JButton b = new JButton(); JLabel l = new JLabel();
		 * b.addActionListener((e)->{ setTitle(l.getText()); });
		 */
		add(new EventPractice2Panel(this));
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new EventPractice2();
	}
}


