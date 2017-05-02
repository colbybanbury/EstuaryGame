package view;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements MouseMotionListener{

	CubeController.addMouseMotionListener(this);
    addMouseMotionListener(this);
    
	public void mousePressed(MouseEvent e){
		int x=e.getX();
		int y=e.getY();
		currentRect= new Rectangle(x,y,0,0);
		updateDrawableRect(getWidth(),getHeight());
		repaint();
	}
	
	public void mouseDragged(MouseEvent e){
		updateSize(e);
	}
	
	public void mouseReleased(MouseEvent e){
		updateSize(e);
	}
	
	public void updateSize(MouseEvent e){
		int x=e.getX();
		int y=e.getY();
		currentRect.setSize(x-currentRect.x,y-currentRect.y);
		updateDrawableRect(getWidth(),getHeight());
	}
}//MouseListener