package game3.view;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import game3.model.Board;
import game3.model.Cube;

public class Animation extends JPanel implements MouseMotionListener, MouseListener{
	private static final long serialVersionUID = -6799082996186437878L;
	int frameCount=1;
	int numPics;
	int numFrame;
	BufferedImage[] pics;
    BufferedImage all_imgs[][];
    private BufferedImage backgroundImage;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int frameWidth=(int) screenSize.getWidth();
	final static int frameHeight=(int) screenSize.getHeight();
	//final static int[] imgWidth={100,256,243,227,254,249,241,256,252,253,279,256,254,256,257,254};
	//final static int[] imgHeight={100,166,119,125,97,132,249,256,84,95,173,166,124,256,257,159};
	static int imgWidthTemp;
	static int imgHeightTemp;
	static Board board;
	boolean dragging=false;
	private int curX;
	private int curY;
	private int nextX;
	private int nextY;
	int selectedImage;
	private String ans="";
	JLabel storyText=new JLabel();
	JTextField storyField=new JTextField("Enter your story here",20);
	JButton rollButton=new JButton("ROLL");
	JButton submitButton=new JButton("SUBMIT STORY");
	private boolean filled=false;
	
	///////
	
	JFrame frame = new JFrame();
	
	///////
	
	@Override
	public void paintComponent(Graphics g){
		System.out.println("entered paintComponent");
		super.paintComponent(g);
		System.out.println("exited super paintComponent");
		//draw background
		g.drawImage(backgroundImage, 0, 0, this);
		System.out.println("drew background image");
		
		
		for (Cube c:board.getCubes()){
			//draw boxes	
			g.drawRect(10+c.getCubeNum()*(board.getWidth()-20)/Board.NUM_CUBES,board.getHeight()/4,c.getSideLength(),c.getSideLength());
		}
		for (Cube c:board.getCubes()){
			//draw cubes
			Rectangle rTemp=c.getLocation();
			g.fillRect((int)rTemp.getX(),(int)rTemp.getY(),(int)rTemp.getWidth(),(int)rTemp.getHeight());
			BufferedImage curPic = pics[c.getPicNum()];
			g.drawImage(curPic, (int) (rTemp.getX()+rTemp.getWidth()/2-curPic.getWidth()/2),(int) (rTemp.getY()+rTemp.getHeight()/2-curPic.getHeight()/2), 
					(int) curPic.getHeight(),(int) curPic.getHeight(), (ImageObserver) this);
		}//for each loop
		
	}
	
	
	
	public Animation(Board b){
		board=b;
		String img_list[]={
				"game3.images/resized/QuestionMark.png",
				"game3.images/resized/apple.png",
				"game3.images/resized/banana.png",
				"game3.images/resized/blackduck_right.png",
				"game3.images/resized/bluecrab_0.png",
				"game3.images/resized/bogturtle_left_0.png",
				"game3.images/resized/can.png",
				"game3.images/resized/cattails.png",
				"game3.images/resized/clam_left_1.png",
				"game3.images/resized/cleanvessel.png",
				"game3.images/resized/cordgrass.png",
				"game3.images/resized/crumbledpaper.png",
				"game3.images/resized/crushedcan.png",
				"game3.images/resized/deadfish.png",
				"game3.images/resized/denrec_armsout.png",
				"game3.images/resized/dirtyvessel.png",
				"game3.images/resized/fish_bass_left.png",
				"game3.images/resized/fish_catfish_left_0.png",
				"game3.images/resized/fish_group_right.png",
				"game3.images/resized/fish_pickerel_right.png",
				"game3.images/resized/fish_trout_right.png",
				"game3.images/resized/fisherman_1.png",
				"game3.images/resized/fisherman_walk_left_2.png",
				"game3.images/resized/gabion0.png",
				"game3.images/resized/horseshoeCrabScientist_happy.png",
				"game3.images/resized/horseshoeCrabScientist_sad.png",
				"game3.images/resized/horseshoe_crab_left_1.png",
				"game3.images/resized/hotrod_vessel.png",
				"game3.images/resized/leaf.png",
				"game3.images/resized/list",
				"game3.images/resized/mallard_left.png",
				"game3.images/resized/mittencrab_0.png",
				"game3.images/resized/mittencrabs_spawn_2.png",
				"game3.images/resized/oil_spill.png",
				"game3.images/resized/otter_left.png",
				"game3.images/resized/researcher_standing.png",
				"game3.images/resized/seaWall0.png",
				"game3.images/resized/seaWall2.png",
				"game3.images/resized/soda.png",
				"game3.images/resized/trash.png",
				"game3.images/resized/trashbag.png",
				"game3.images/resized/vessel.png",
				"game3.images/resized/wood_duck_right.png"
    	};
    	
		numPics=img_list.length;
		all_imgs= new BufferedImage[numPics][10];
		System.out.println(numPics);
		System.out.println(all_imgs.length);
		pics = new BufferedImage[numPics];
    	for(int j=0;j<numPics;j++)
    	{
    		//BufferedImage img = createImage(img_list[j]);
        	pics[j] = createImage(img_list[j]);
        	//for(int i = 0; i < frameCount; i++)
        	//	all_imgs[j][i] = img.getSubimage(img.getWidth()*i, 0, img.getWidth(), img.getHeight());
    	}
    	backgroundImage = createImage("game3.images/tempBackGround.jpg");
    	
    	
		
		this.setMaximumSize(new Dimension(frameWidth,frameHeight));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
    	Font f = new Font("Dialog", Font.PLAIN, 45);
    	Font y = new Font("Dialog", Font.PLAIN, 18);
    	Font bold = new Font("Dialog", Font.BOLD, 20);

    	//add space at top
    	add(Box.createRigidArea(new Dimension(0,15)));
    	
    	//add roll button
    	rollButton.setPreferredSize(new Dimension(100,40));
    	rollButton.setMaximumSize(new Dimension(100,40));
    	rollButton.setFont(bold);
    	rollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	ActionListener rollListener=new RollAction();
    	rollButton.addActionListener(rollListener);
    	this.add(rollButton);
    	
    	//add space 
    	this.add(Box.createRigidArea(new Dimension(0,50)));
    	
    	//add story text box
    	storyText.setFont(f);
    	this.add(storyText);
    	storyText.setAlignmentX(Component.CENTER_ALIGNMENT);
    	storyText.setForeground(Color.WHITE);
    	
    	//make next ones appear at bottom
    	this.add(Box.createVerticalGlue());
    	
    	//define story field
    	storyField.setFont(new Font("Dialog", Font.PLAIN, 18));
    	storyField.setAlignmentX(Component.CENTER_ALIGNMENT);
    	storyField.setPreferredSize(new Dimension(1200,150));
    	storyField.setMaximumSize(new Dimension(1200,150));
    	
    	//define submit button
    	submitButton.setFont(new Font("Dialog", Font.BOLD, 20));
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submitButton.setPreferredSize(new Dimension(200,40));
    	submitButton.setMaximumSize(new Dimension(200,40));
    	ActionListener submitListener=new SubmitAction();
    	submitButton.addActionListener(submitListener);
    	
    	//add mouse motion listener
    	frame.addMouseListener(this);
    	frame.addMouseMotionListener(this);
    	System.out.println("added mouse motion listeners");
    	
    	
    	frame.setContentPane(this);
    	this.setOpaque(true);
    	System.out.println("added animation to frame");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(Animation.frameWidth, Animation.frameHeight);
    	frame.setVisible(true);
    	System.out.println("made frame visible");
    	
    	///////
	}
	
	public void paintBoard(){
		System.out.println("entered paintboard()");
    	
		///////
		
		
    	for(int i = 0; i < 1000; i++){
    		frame.revalidate();
    		System.out.println("about to paint");
    		frame.repaint();
    		System.out.println("repainted");
    		
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
	
	public void movedAllCubes(){
		
		System.out.println("Entered moved all cubes");
				//add TextField
		    	this.add(storyField);
		    	
		    	
		    	//add submit button
		    	this.add(submitButton);
		    
		    	//add space at bottom
		    	this.add(Box.createRigidArea(new Dimension(0,100)));
			
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
		for(int i=0;i< Board.NUM_CUBES;i++){
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
		/*System.out.println("Mouse Clicked");
		board.shuffle(numPics);
		repaint();*/
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		Point curPoint=e.getPoint();
		curX=curPoint.x;
		curY=curPoint.y;
		System.out.println("Mouse Released");
		dragging=false;
		for(int z=0;z<Board.NUM_CUBES;z++){
			if(curX>(10+z*(board.getWidth()-20)/Board.NUM_CUBES) && curX<(10+z*(board.getWidth()-20)/Board.NUM_CUBES)+board.getCubes().get(selectedImage).getSideLength() && curY>board.getHeight()/4 && curY<(board.getHeight()/4+board.getCubes().get(selectedImage).getSideLength()))
				board.getCubes().get(selectedImage).changeLocation(10+z*(board.getWidth()-20)/Board.NUM_CUBES,board.getHeight()/4);
			
		}
		filled=true;
		for(Cube c:board.getCubes()){
			if(Math.abs(c.getLocation().getY()-board.getHeight()/4)>=20){
				filled=false;
			}
		}
		if(filled)
			movedAllCubes();
		revalidate();
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		System.out.println("Mouse Entered");
	}
	
	@Override
	public void mouseExited(MouseEvent e){
		System.out.println("Mouse Exited");
	}
	
	class RollAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
		
			System.out.println("Roll Clicked");
			board.shuffle(numPics);
			storyField.setText("Enter your story here");
			storyText.setText("");
			repaint();
		}
	}//class RollAction
	class SubmitAction implements ActionListener{
	
		public void actionPerformed(ActionEvent a){
		
			System.out.println("Submit clicked");
			ans=storyField.getText();
	    	storyText.setText(ans);
			System.out.println("ANSWER IS: "+ans);
			repaint();
		}
	}//class SubmitAction
}
