package game.particle;

import game.gfx.Bitmap;

public class BloodParticle extends Particle {

	public BloodParticle(double x, double y, double xa, double ya) {
		this.x = x;
		this.y = y;
		this.gravity = 0.0;
		double speed = random.nextDouble() * 2.9;
		this.xa = xa * speed;
		this.ya = ya * speed;
		this.time = (random.nextInt(3) + 1) * 60;
	}
	
	public BloodParticle(double x, double y) {
		this.x = x;
		this.y = y;
		this.gravity = 0.0;
		do {
			this.xa = random.nextDouble() * 2.0 - 1.0;
			this.ya = random.nextDouble() * 2.0 - 1.0;
		} while(this.xa * this.xa + this.ya * this.ya > 1.0);
		
		double dd = Math.sqrt(this.xa * this.xa + this.ya * this.ya);
		double speed = random.nextDouble() * 0.9;
		
		this.xa = this.xa / dd * speed;
		this.ya = this.ya / dd * speed;
	
		this.time = (random.nextInt(3) + 1) * 60;
	}
	
	protected void collide(Particle p, double xxa, double yya) { remove(); }
	
	public void render(Bitmap screen, double delta) {
		super.render(screen, delta);
		screen.setPixel((int)this.x, (int)this.y, 0x7F0700);
	}
	
}