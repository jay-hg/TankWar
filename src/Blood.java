import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	private int x;
	private int y;
	public final static int BLOOD_SIZE = 20;
	private int step;
	private boolean live;
	private int[][] pos = {
			{300,300},{330,280},{360,310},{395,343},
			{423,366},{418,388},{425,400},{400,416}
	};
	public Blood() {
		super();
		this.step = 0;
		this.x = pos[step][0];
		this.y = pos[step][1];
		this.live = true;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		if(!this.isLive()) return;
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, BLOOD_SIZE, BLOOD_SIZE);
		g.setColor(c);
		
		step ++;
		if(step == pos.length) step = 0;
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,this.BLOOD_SIZE,this.BLOOD_SIZE);
	}
}
