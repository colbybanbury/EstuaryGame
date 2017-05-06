package view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.imageio.ImageIO;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import model.Board;

public class Animation extends JPanel implements MouseMotionListener, MouseListener{
	int frameCount=1;
	int imageCount;
	int numFrame;
	BufferedImage[] pics;
    BufferedImage all_imgs[][]=new BufferedImage[5][10];
    private BufferedImage backgroundImage;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	final static int[] imgWidth={243,227,254,249,279};
	final static int[] imgHeight={119,125,97,132,173};
	static int imgWidthTemp;
	static int imgHeightTemp;
	static Board board;
	
	
	public void paint(Graphics g){
		Random rand = new Random();
		g.drawImage(backgroundImage, 0, 0, this);
		for(int i=0;i<board.getNumCubes();i++){
			Rectangle rTemp=board.getCubes().get(i).getLocation();
			g.drawRect((int)rTemp.getX(),(int)rTemp.getY(),(int)rTemp.getWidth(),(int)rTemp.getHeight());
			int r=rand.nextInt(5);///make sure this is the right number
			g.drawImage(all_imgs[r][0], (int) rTemp.getX(),(int) rTemp.getY(), 
					(int) imgWidth[r],(int) imgHeight[r],Color.white, (ImageObserver) this);
			//g.drawImage(all_imgs[r][0], (int) rTemp.getX(),(int) rTemp.getY(), 
			//		(int) rTemp.getX()+(int) rTemp.getWidth(),(int) rTemp.getY()+(int) rTemp.getHeight(),(int) rTemp.getX(),
			//		(int) rTemp.getY(),(int) rTemp.getX()+imgWidth[r],(int) rTemp.getY()+imgHeight[r],Color.white, (ImageObserver) this);
			
		}
	}
	
	
	
	public Animation(Board b){
		board=b;
		String img_list[]={
    			"images/bluecrab_0.png",///place actual pictures
    			"images/bogturtle_left_0.png",
    			"images/clam_left_1.png",
    			"images/fish_bass_left.png",
    			"images/horseshoe_crab_left_1.png"
    	};
    	
    	for(int j=0;j<5;j++)
    	{
    		BufferedImage img = createImage(img_list[j]);
        	//pics = new BufferedImage[10];
        	for(int i = 0; i < frameCount; i++)
        		all_imgs[j][i] = img.getSubimage(imgWidth[j]*i, 0, imgWidth[j], imgHeight[j]);
    	}
    	backgroundImage = createImage("images/tempBackGround.jpg");
	}
	
	public void paintBoard(){
		System.out.println(all_imgs.length);
		JFrame frame = new JFrame();
    	frame.getContentPane().add(new Animation(board));
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(Animation.frameWidth, Animation.frameHeight);
    	frame.setVisible(true);
    	/*for(int i = 0; i < 1000; i++){
    		frame.repaint();
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}*/
	}
	private BufferedImage createImage(String file_name){
		BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File(file_name));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		return null;
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		System.out.println("Mouse Pressed");
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		System.out.println("Mouse Dragged");
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		System.out.println("Mouse Moved");
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		System.out.println("Mouse Clicked");
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		System.out.println("Mouse Released");
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		System.out.println("Mouse Entered");
	}
	
	@Override
	public void mouseExited(MouseEvent e){
		System.out.println("Mouse Exited");
	}
}
