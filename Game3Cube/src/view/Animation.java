package view;

import java.awt.Graphics;
import java.awt.Point;
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
	int numPics = 15;
	BufferedImage[] pics;
    BufferedImage all_imgs[][]=new BufferedImage[numPics][10];
    private BufferedImage backgroundImage;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	final static int[] imgWidth={256,243,227,254,249,241,256,252,253,279,256,254,256,257,254};
	final static int[] imgHeight={166,119,125,97,132,249,256,84,95,173,166,124,256,257,159};
	static int imgWidthTemp;
	static int imgHeightTemp;
	static Board board;
	boolean dragging=false;
	private int curX;
	private int curY;
	private int nextX;
	private int nextY;
	int selectedImage;
	int[] randNums;
	
	@Override
	public void paint(Graphics g){
		g.drawImage(backgroundImage, 0, 0, this);
		for (int z=0;z<=1100;z+=220){
			
			g.drawRect(10+z,10,200,200);
		}
		System.out.println("painted");
		
		for(int i=0;i<board.getNumCubes();i++){
			System.out.println(randNums[i]);
			Rectangle rTemp=board.getCubes().get(i).getLocation();
			g.fillRect((int)rTemp.getX(),(int)rTemp.getY(),(int)rTemp.getWidth(),(int)rTemp.getHeight());
			int r=randNums[i];///make sure this is the right number
			g.drawImage(all_imgs[r][0], (int) (rTemp.getX()+rTemp.getWidth()/2-imgWidth[r]/2),(int) (rTemp.getY()+rTemp.getHeight()/2-imgHeight[r]/2), 
					(int) imgWidth[r],(int) imgHeight[r], (ImageObserver) this);
			
		}//for
		
	}
	
	
	
	public Animation(Board b){
		board=b;
		String img_list[]={  //place actual pictures
				"images/blackduck_right.png",
    			"images/bluecrab_0.png",
    			"images/bogturtle_left_0.png",
    			"images/clam_left_1.png",
    			"images/fish_bass_left.png",
    			"images/fish_catfish_left_0.png",
    			"images/fish_group_right.png",
    			"images/fish_pickerel_right.png",
    			"images/fish_trout_right.png",
    			"images/horseshoe_crab_left_1.png",
    			"images/mallard_left.png",
    			"images/mittencrab_0.png",
    			"images/mittencrabs_spawn_2.png",
    			"images/otter_left.png",
    			"images/wood_duck_right.png"			
    	};
    	
    	for(int j=0;j<numPics;j++)
    	{
    		BufferedImage img = createImage(img_list[j]);
        	//pics = new BufferedImage[10];
        	for(int i = 0; i < frameCount; i++)
        		all_imgs[j][i] = img.getSubimage(imgWidth[j]*i, 0, imgWidth[j], imgHeight[j]);
    	}
    	backgroundImage = createImage("images/tempBackGround.jpg");
    	randNums= new int[board.getNumCubes()];
		chooseImages();
    	
	}
	
	public void paintBoard(){
		JFrame frame = new JFrame();
    	frame.getContentPane().add(new Animation(board));
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(Animation.frameWidth, Animation.frameHeight);
    	frame.addMouseListener(this);
    	frame.addMouseMotionListener(this);
    	frame.setVisible(true);
    	for(int i = 0; i < 1000; i++){
    		frame.repaint();
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
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
	
	private void chooseImages(){
		
		Random rand=new Random();
		for(int i=0;i<board.getNumCubes();i++){
			randNums[i]=rand.nextInt(all_imgs.length);
			System.out.println(randNums[i]);
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e){
		System.out.println("Mouse Pressed");
		Rectangle rTemp;
		selectedImage=-1;
		Point point=e.getPoint();
		curX=point.x;
		curY=point.y;
		dragging=true;
		for(int i=0;i< board.getNumCubes();i++){
			rTemp=board.getCubes().get(i).getLocation();
			if(curX>rTemp.getX()&&curX<(rTemp.getX()+rTemp.getWidth())&&curY>rTemp.getY()&&curY<(rTemp.getY()+rTemp.getHeight()))
			{
				selectedImage=i;
				System.out.println(i);
				System.out.println(selectedImage);
			}	
		}//for each cube
	
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		Point curPoint=e.getPoint();
		nextX=curPoint.x;
		nextY=curPoint.y;
		System.out.println(nextX);
		System.out.println(nextY);
		if(dragging){
			System.out.println("Mouse Dragged, image selected: "+selectedImage);
			if(selectedImage>=0){
				Rectangle rTemp=board.getCubes().get(selectedImage).getLocation();
				board.getCubes().get(selectedImage).changeLocation((int)rTemp.getX()+(nextX-curX),(int)rTemp.getY()+(nextY-curY));
				System.out.println("changed location");
				repaint();
			}
			System.out.println("passed repaint");
			curX=nextX;
			curY=nextY;
		}//if dragging
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
		Point curPoint=e.getPoint();
		curX=curPoint.x;
		curY=curPoint.y;
		System.out.println("Mouse Released");
		dragging=false;
		for(int z=0;z<=1100;z+=220){
			if(curX>(10+z) && curX<(10+z+200) && curY>10 && curY<(10+200))
				board.getCubes().get(selectedImage).changeLocation(10+z,10);
			repaint();
		}
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
