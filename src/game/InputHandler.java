package game;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements MouseListener {
	
	public int x;
	public int y;
	
	private int mx;
	private int my;
	
	public boolean leftClicked;
	public boolean leftPressed;
	private boolean m0p;
	
	public boolean rightClicked;
	public boolean rightPressed;
	private boolean m1p;
	
	public InputHandler(Canvas canvas) { canvas.addMouseListener(this); }

	public void tick() {
		this.leftClicked = !this.leftPressed && this.m0p;
		this.rightClicked = !this.rightPressed && this.m1p;
		this.x = this.mx;
		this.y = this.my;
		this.leftPressed = this.m0p;
		this.rightPressed = this.m1p;
	}
	
	public void mouseClicked(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	}

	public void mousePressed(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
		
		this.m0p = e.getButton() == 1;
		this.m1p = e.getButton() == 3;
	}

	public void mouseReleased(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	
		this.m0p = this.m1p = false;
	}

	public void mouseEntered(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	}

	public void mouseExited(MouseEvent e) {
		this.mx = e.getX() / GameComponent.SCALE;
		this.my = e.getY() / GameComponent.SCALE;
	}

}