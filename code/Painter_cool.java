package painter;

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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import painter.DrawPanel.DrawMouseL;

class MyShape{
	Shape s;
	Color c;
	boolean f;
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
	String type="";
	int x,y;
	MyShape tempshape;
	Color currColor;
	DrawPanel(){
		DrawMouseL ml=new DrawMouseL();
		this.addMouseListener((MouseListener) ml);
		DrawMouseML mml=new DrawMouseML();
		this.addMouseMotionListener((MouseMotionListener) mml);

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
			if(tempshape.f) {
				g2d.fill(tempshape.s);
			}
			else {
				g2d.draw(tempshape.s);
			}
			
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
				if(type.equals("Oval")||type.equals("SolidOval")) {
					Ellipse2D el = (Ellipse2D)(tempshape.s);
					el.setFrame(el.getX(), el.getY(), e.getX()-el.getX(),e.getY()-el.getY());
				}
				else if(type.equals("Rect")||type.equals("SolidRect")) {
					Rectangle2D rl=(Rectangle2D)(tempshape.s);
					rl.setFrame(rl.getX(), rl.getY(), e.getX()-rl.getX(), e.getY()-rl.getY());
				}
				else {
					Line2D l=(Line2D)(tempshape.s);
					l.setLine(l.getX1(), l.getY1(), e.getX(), e.getY());
				}
		    	shapelist.add(tempshape);	
		    }
		    tempshape=new MyShape();
		    if(type.equals("Oval")||type.equals("SolidOval")) {
		    	Ellipse2D el = new Ellipse2D.Double();
		    	el.setFrame(el.getX(), el.getY(), e.getX()-el.getX(),e.getY()-el.getY());
		    	tempshape.c=currColor;
				tempshape.s=el;
				if(type.equals("Oval")) {
					tempshape.f=false;
				}
				else {
					tempshape.f=true;
				}
		    }
		    else if(type.equals("Rect")||type.equals("SolidRect")) {
		    	Rectangle2D rl = new Rectangle2D.Double();
		    	rl.setFrame(rl.getX(), rl.getY(), e.getX()-rl.getX(), e.getY()-rl.getY());
		    	tempshape.c=currColor;
				tempshape.s=rl;
				if(type.equals("Rect")) {
					tempshape.f=false;
				}
				else {
					tempshape.f=true;
				}
		    }
		    else if(type.equals("Line")) {
		    	Line2D line = new Line2D.Double();
		    	line.setLine(line.getX1(), line.getY1(), e.getX(), e.getY());
		    	tempshape.c=currColor;
				tempshape.s=line;
				tempshape.f=false;
		    }
		    else {
		    	Line2D l=new Line2D.Double();
				l.setLine(e.getX(), e.getY(), e.getX(), e.getY());
				tempshape.c=currColor;
				tempshape.s=l;
				tempshape.f=false;
		    }
			repaint();
		}
	}
    class DrawMouseL extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e){
			x=e.getX();
			y=e.getY();
		    if(tempshape!=null) {
		    	shapelist.add(tempshape);
		    }
		    tempshape=new MyShape();
		    if(type.equals("Oval")||type.equals("SolidOval")) {
		    	Ellipse2D el = new Ellipse2D.Double();
		    	el.setFrame(el.getX(), el.getY(), e.getX()-el.getX(),e.getY()-el.getY());
		    	tempshape.c=currColor;
				tempshape.s=el;
				if(type.equals("Oval")) {
					tempshape.f=false;
				}
				else {
					tempshape.f=true;
				}
		    }
		    else if(type.equals("Rect")||type.equals("SolidRect")) {
		    	Rectangle2D rl = new Rectangle2D.Double();
		    	rl.setFrame(rl.getX(), rl.getY(), e.getX()-rl.getX(), e.getY()-rl.getY());
		    	tempshape.c=currColor;
				tempshape.s=rl;
				if(type.equals("Rect")) {
					tempshape.f=false;
				}
				else {
					tempshape.f=true;
				}
		    }
		    else if(type.equals("Line")) {
		    	Line2D line = new Line2D.Double();
		    	line.setLine(line.getX1(), line.getY1(), e.getX(), e.getY());
		    	tempshape.c=currColor;
				tempshape.s=line;
				tempshape.f=false;
		    }
		    else {
		    	Line2D l=new Line2D.Double();
				l.setLine(e.getX(), e.getY(), e.getX(), e.getY());
				tempshape.c=currColor;
				tempshape.s=l;
				tempshape.f=false;
		    }
		}
		@Override
		public void mouseReleased(MouseEvent e){
			int x1=e.getX();
			int y1=e.getY();
		    if(type.equals("Line")) { 
		    	Line2D l=(Line2D)(tempshape.s);
				l.setLine(x, y, x1, y1);
		    }
		    else if(type.equals("Rect")||type.equals("SolidRect")) {	
		    	Rectangle2D rl=(Rectangle2D)(tempshape.s);
				rl.setFrame(x, y, x1-x, y1-y);
		    }
		    else if(type.equals("Oval")||type.equals("SolidOval")) {
				Ellipse2D el = (Ellipse2D)(tempshape.s);
				el.setFrame(x, y, x1-x, y1-y);
		    }
		    else {
		    	Line2D l=(Line2D)(tempshape.s);
				l.setLine(l.getX1(), l.getY1(), e.getX(), e.getY());
		    }	
		    repaint();
			
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

	Painter parent;
	
	public BtnHandler(Painter parent) {
		super();
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().getClass()==JButton.class) {
			JButton sur=(JButton)e.getSource();
			if(sur.getText().equals("File")) {
				JFileChooser jf=new JFileChooser();
				jf.showOpenDialog(parent);				
				parent.message.append("File open"+jf.getName());
			}
			if(sur.getText().equals("Color")) {
				JColorChooser jf=new JColorChooser();				
				Color c=jf.showDialog(parent, "请选择颜色", null);
				parent.dpanel.currColor=c;
				parent.dpanel.setBackground(c);
			}
			if(sur.getText().equals("Save")) {
				 JFileChooser jf = new JFileChooser();
				 int cnt = jf.showDialog(null,"保存");
				 if(cnt == 0 ) {
                    //用户选中的文件
                    File file = jf.getSelectedFile();
                    System.out.println("file = " + file.getAbsolutePath());
                    //parent.dpanel.save(file);
                 }
			}
			if(sur.getText().equals("Line")) {
				parent.dpanel.type="Line";
			}
			if(sur.getText().equals("Rect")) {
				parent.dpanel.type="Rect";
			}
			if(sur.getText().equals("Oval")) {
				parent.dpanel.type="Oval";		 
			}
			if(sur.getText().equals("Curve")) {
				parent.dpanel.type="Curve";		 
			}
			if(sur.getText().equals("SolidRect")) {
				parent.dpanel.type="SolidRect";		 
			}
			if(sur.getText().equals("SolidOval")) {
				parent.dpanel.type="SolidOval";			 
			}
		};
	}
	
}

public class Painter extends JFrame{

	JPanel toolp;
	JButton btnfilechoose;
	JButton btnColorchoose;
	JButton btnTextInput;
	JButton btnfileSave;
	JButton btndrawline;
	JButton btndrawrect;
	JButton btndrawoval;
	JButton btndrawcurve;
	JButton btnsolidrect;
	JButton btnsolidoval;
	DrawPanel dpanel;
	JTextArea message;
	Painter(){
		super();
		toolp=new JPanel(new GridLayout(10,1));
		toolp.setBackground(Color.darkGray);
		btnfilechoose=new JButton("File");
		btnColorchoose=new JButton("Color");
		btnTextInput=new JButton("Text");
		btnfileSave=new JButton("Save");
		btndrawline=new JButton("Line");
		btndrawrect=new JButton("Rect");
		btndrawoval=new JButton("Oval");
		btndrawcurve=new JButton("Curve");
		btnsolidrect=new JButton("SolidRect");
		btnsolidoval=new JButton("SolidOval");
		toolp.add(btnfilechoose);
		toolp.add(btnColorchoose);
		toolp.add(btnTextInput);
		toolp.add(btnfileSave);
		toolp.add(btndrawline);
		toolp.add(btndrawrect);
		toolp.add(btndrawoval);
		toolp.add(btndrawcurve);
		toolp.add(btnsolidrect);
		toolp.add(btnsolidoval);
		this.getContentPane().add("East",toolp);
		dpanel=new DrawPanel();
		dpanel.setBackground(Color.lightGray);
		this.getContentPane().add("Center",dpanel);
		message=new JTextArea(3,20);
		this.getContentPane().add("West",message);
		this.setSize(1200,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addLis();
		this.setVisible(true);		
		
	}
	Painter(String title){
		this();
		this.setTitle(title);
		
	}
	
	void addLis(){
		BtnHandler bh=new BtnHandler(this);
		btnfilechoose.addActionListener(bh);
		btnColorchoose.addActionListener(bh);
		btnfileSave.addActionListener(bh);
		btndrawline.addActionListener(bh);
		btndrawrect.addActionListener(bh);
		btndrawoval.addActionListener(bh);
		btndrawcurve.addActionListener(bh);
		btnsolidrect.addActionListener(bh);
		btnsolidoval.addActionListener(bh);
		btnTextInput.addMouseListener(new MouseAdapter() {
			@Override
			public
			void mouseClicked(MouseEvent me) {
				if(me.getClickCount()==1) {
					//message.append("single click\n");
					String inputValue = JOptionPane.showInputDialog("Please input a value"); 
					message.append(inputValue);
				}
			}
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Painter ge=new Painter("Test");		
		
	}

}

