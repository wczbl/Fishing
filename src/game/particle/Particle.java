package game.particle;

import java.util.Random;

import game.GameComponent;
import game.gfx.Bitmap;
import game.gfx.Renderable;

public class Particle implements Renderable {

	protected static final Random random = new Random();
	public double x;
	public double y;
	public double xa;
	public double ya;
	public double r = 8.0;
	public double rot;
	public double rotA;
	public double scale = 1.0;
	public int time;
	public int col;
	public int sprite = -1;
	public ParticleSystem ps;
	protected double gravity = 0.09;
	public boolean removed;
	
	public void init(ParticleSystem ps) { this.ps = ps; }
	
	public void tick() {
		if(this.removed) return;
		if(this.time-- <= 0) remove();
		
		this.xa *= 0.999;
		this.ya *= 0.999;
		this.rot += this.rotA;
		this.rotA *= 0.7;
		this.ya += this.gravity;
		
		attemptMove();
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.removed) return;
	}
	
	public void attemptMove() {
		int steps = (int)(Math.sqrt(this.xa * this.xa + this.ya * this.ya) + 1);
		for(int i = 0; i < steps; i++) {
			move(this.xa / steps, 0.0);
			move(0.0, this.ya / steps);
		}
	}
	
	private void move(double xa, double ya) {
		double x0 = this.x - this.r * this.scale + xa;
		double y0 = this.y - this.r * this.scale + ya;
		double x1 = this.x + this.r * this.scale + xa;
		double y1 = this.y + this.r * this.scale + ya;
		
		if(x0 < 0.0 || y0 < 0.0 || x1 >= GameComponent.WIDTH || y1 >= GameComponent.HEIGHT) {
			collide(null, xa, ya);
			return;
		}
		
		this.x += xa;
		this.y += ya;
	}
	
	protected void collide(Particle p, double xxa, double yya) {
		if(Math.abs(xxa) > 0.0) this.xa *= -0.7;
		if(Math.abs(yya) > 0.0) this.ya *= -0.7;
	}

	protected void remove() { this.removed = true; }
	
}