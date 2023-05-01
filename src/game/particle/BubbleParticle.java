package game.particle;

import game.gfx.Bitmap;

public class BubbleParticle extends Particle {

	private int time;
	private double minR;
	private double maxR;
	private int maxTime;
	
	public BubbleParticle(double x, double y, double xa, double ya) {
		this.gravity = -0.008;
		this.x = x;
		this.y = y;
		this.minR = this.r = 1.5 + random.nextDouble() * 0.6;
		this.maxR = 6.0;
		this.time = this.maxTime = random.nextInt(120) + 120;
		double speed = random.nextDouble() * 0.9;
		this.xa = xa * speed;
		this.ya = ya * speed;
	}
	
	public void tick() {
		if(this.removed) return;
		
		this.r = this.minR + (this.maxR - this.minR) * (this.maxTime - this.time) / (double)this.maxTime;
		
		if(this.time-- <= 0) {
			remove();
			return;
		}
		
		this.xa += random.nextGaussian() * random.nextGaussian() * 0.04;
		this.xa *= 0.98;
		this.ya *= 0.98;
		this.ya += this.gravity;
		this.x += this.xa;
		this.y += this.ya;
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.removed) return;
		screen.drawCircle((int)this.x, (int)this.y, (int)this.r, 0xFFFFFF);
	}
	
}