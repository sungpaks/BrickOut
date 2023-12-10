import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MyActionLabel extends JLabel implements ActionListener{
	MyActionLabel(String s) {
		super(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = this.getText();
		str += "+";
		this.setText(str);
	}
}



class EventPractice extends JFrame implements ActionListener {
	private class EventPracticePanel extends JPanel implements ActionListener {
		int count = 0;
		private class MyButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click!");
			}
		}
		JButton b2;
		JButton b4;
		EventPracticePanel() {
			JButton b1 = new JButton("Click!");
			
			MyButtonListener bl = new MyButtonListener();
			b1.addActionListener(bl);
			
			b2 = new JButton("Change Color");
			b2.addActionListener(this);
			b2.addActionListener(bl);
			
			JButton b3 = new JButton("Change Text");
			
			String def_str = "Sejong";
			MyActionLabel l = new MyActionLabel(def_str);
			b3.addActionListener(l);
			
			b4 = new JButton("Change Button1");
			b4.addActionListener(this);
			
			JButton b5 = new JButton("reset");
			b5.addActionListener((e)->l.setText(def_str));
			
			JButton b6 = new JButton("change Title");
			b6.addActionListener(EventPractice.this);
			
			add(b1);
			add(b2);
			add(b3);
			add(b4);
			add(b5);
			add(b6);
			add(l);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == b2) {
				if (count%2 == 0) this.setBackground(Color.orange);
				else this.setBackground(Color.LIGHT_GRAY);
				count++;
			}
			else if (e.getSource() == b4) {
				String str = b2.getText();
				str += "*";
				b2.setText(str);
			}
		}
	}
	
	EventPractice() {
		setSize(400,300);
		setTitle("Event Practice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new EventPracticePanel());
		setVisible(true);
	}
	public static void main (String[] args) {
		new EventPractice();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		setTitle("!!!");
	}
}
