import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class MyHelloPanel extends JPanel {
	MyHelloPanel() {
		setLayout(null);
		
		JLabel l = new JLabel("Id :");
		JTextField t = new JTextField(10);
		JButton b = new JButton("click");
		
		l.setSize(50,30);
		t.setSize(100,30);;
		b.setSize(150,30);
		l.setLocation(70,50);
		t.setLocation(100,50);
		b.setLocation(50,90);
		
		add(l);
		add(t);
		add(b);
	}
}

public class HelloSwing extends JFrame{
	public static void main(String[] args) {
		new HelloSwing();
	}
	HelloSwing() {
		setTitle("HelloSwing");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 //패널 하나 만든다
		//setLayout(new FlowLayout());
		this.setLayout(new BorderLayout(30,30));
		add(new JButton(), BorderLayout.NORTH);
		add(new MyHelloPanel(), BorderLayout.CENTER); //패널을 프레임 객체에 추가한다
		//프레임에 컴포넌트를 붙이면 기본적으로 모두 채우도록 됨
		
		
		setVisible(true); //나타나라 얍
	}
}
