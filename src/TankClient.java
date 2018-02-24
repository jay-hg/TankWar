import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankClient extends Frame {
	private final static int WINDOW_HEIGHT = 600;
	private final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_START_POSITION_X = 100;
	private final static int WINDOW_START_POSITION_Y = 80;
	private final static String TITLE = "TankWar";
	
	private int x = 50,y = 50;
	private Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 60, 60);
		g.setColor(c);
	}

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) offScreenImage = createImage(WINDOW_WIDTH,WINDOW_HEIGHT);
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		paint(gOffScreen);
		gOffScreen.setColor(c);
		g.drawImage(offScreenImage, 0 , 0, null);
	}



	public void launchFrame() {
		this.setLocation(WINDOW_START_POSITION_X, WINDOW_START_POSITION_Y);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle(TITLE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					x -= 10;
					break;
				case KeyEvent.VK_RIGHT:
					x += 10;
					break;
				case KeyEvent.VK_UP:
					y -= 10;
					break;
				case KeyEvent.VK_DOWN:
					y += 10;
					break;
				}
			}
			
		});
		this.setResizable(false);
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
