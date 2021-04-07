package gui.lab;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
class MyShape{
	Shape s;
	Color c;
	
}
class MyString{
	Font f;
	String s;
	Color c;
	int x;
	int y;
}
class DrawPanel extends JPanel{
	ArrayList<MyShape> shapelist=new ArrayList();
	ArrayList<MyString> stringlist=new ArrayList();
	MyShape tempshape;
	Color currColor;
	DrawPanel(){
		DrawMouseL ml=new DrawMouseL();
		this.addMouseListener(ml);
		DrawMouseML mml=new DrawMouseML();
		this.addMouseMotionListener(mml);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		for(MyShape ms:shapelist) {
			g2d.setColor(ms.c);
			g2d.draw(ms.s);
		}
		if(tempshape!=null) {
			g2d.setColor(tempshape.c);
			g2d.draw(tempshape.s);
		}
		for(MyString ms:stringlist) {
			g2d.setColor(ms.c);
			g2d.setFont(ms.f);
			g2d.drawString(ms.s,ms.x,ms.y);
		}
		
	}
	class DrawMouseML extends MouseMotionAdapter{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(tempshape!=null) {
				Line2D l=(Line2D)(tempshape.s);
				l.setLine(l.getX1(), l.getY1(), e.getX(), e.getY());
		    	shapelist.add(tempshape);
		    }
		    tempshape=new MyShape();
		    
			Line2D l=new Line2D.Double();
			l.setLine(e.getX(), e.getY(), e.getX(), e.getY());
			tempshape.c=currColor;
			tempshape.s=l;
			repaint();
		}
	}
    class DrawMouseL extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e){
		    if(tempshape!=null) {
		    	shapelist.add(tempshape);
		    }
		    tempshape=new MyShape();
		    
			Line2D l=new Line2D.Double();
			l.setLine(e.getX(), e.getY(), e.getX(), e.getY());
			tempshape.c=currColor;
			tempshape.s=l;

		}
		@Override
		public void mouseReleased(MouseEvent e){
		    
			Line2D l=(Line2D)(tempshape.s);
			l.setLine(l.getX1(), l.getY1(), e.getX(), e.getY());
			repaint();
			//tempshape.s=l;

		}
		@Override
		public void mouseClicked(MouseEvent e){
		  //  System.out.println(e.getButton());
			if(e.getButton()==MouseEvent.BUTTON3) {
				String inputValue = JOptionPane.showInputDialog("Please input a value"); 
				Font f=new Font("宋体",10,10);
				MyString ms=new MyString();
				ms.s=inputValue;
				ms.c=currColor;
				ms.x=e.getX();
				ms.y=e.getY();
				stringlist.add(ms);
				repaint();
				}

		}
	}
}
class BtnHandler implements ActionListener{

	GUIEvent parent;
	
	public BtnHandler(GUIEvent parent) {
		super();
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().getClass()==JButton.class) {
			JButton sur=(JButton)e.getSource();
			if(sur.getText().equals("选择文件")) {
				JFileChooser jf=new JFileChooser();
				jf.showOpenDialog(parent);				
				parent.message.append("File open"+jf.getName());
			}
			if(sur.getText().equals("选择颜色")) {
				JColorChooser jf=new JColorChooser();				
				Color c=jf.showDialog(parent, null, null);
				parent.dpanel.currColor=c;
				parent.message.append("Color"+c);
			}
		};
	}
	
}
public class GUIEvent extends JFrame{

	JPanel toolp;
	JButton btnfilechoose;
	JButton btnColorchoose;
	JButton btnTextInput;
	DrawPanel dpanel;
	JTextArea message;
	GUIEvent(){
		super();
		toolp=new JPanel(new GridLayout(1,3));
		toolp.setBackground(Color.red);
		btnfilechoose=new JButton("选择文件");
		btnColorchoose=new JButton("选择颜色");
		btnTextInput=new JButton("输入文本");
		toolp.add(btnfilechoose);
		toolp.add(btnColorchoose);
		toolp.add(btnTextInput);
		this.getContentPane().add("North",toolp);
		dpanel=new DrawPanel();
		dpanel.setBackground(Color.yellow);
		this.getContentPane().add("Center",dpanel);
		message=new JTextArea(3,100);
		this.getContentPane().add("South",message);
		this.setSize(200,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addLis();
		this.setVisible(true);		
		
	}
	GUIEvent(String title){
		this();
		this.setTitle(title);
		
	}
	
	void addLis(){
		BtnHandler bh=new BtnHandler(this);
		btnfilechoose.addActionListener(bh);
		btnColorchoose.addActionListener(bh);
		btnTextInput.addMouseListener(new MouseAdapter() {
			@Override
			public
			void mousePressed(MouseEvent me) {
				message.append("sousePressed "+me.getX()+" "+me.getY()+"\n");
			}
			@Override
			public
			void mouseClicked(MouseEvent me) {
				if(me.getClickCount()==2) {
					message.append("double click\n");
				}
				if(me.getClickCount()==1) {
					//message.append("single click\n");
					String inputValue = JOptionPane.showInputDialog("Please input a value"); 
					message.append(inputValue+"single click "+me.getX()+" "+me.getY()+"\n");
				}
			}
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GUIEvent ge=new GUIEvent("测试");
		
		
	}

}
