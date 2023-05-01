package game.particle;

import game.entity.Entity;
import game.entity.Fish;
import game.gfx.Bitmap;

public class SpawnParticle extends Particle {

	protected boolean die;
	protected double xo;
	protected double yo;
	protected double zo;
	protected double z;
	protected double za;
	protected Entity owner;
	
	public SpawnParticle(Entity owner, double x, double y, double xa, double ya) {
		this.gravity = 0.0;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.r = 1.2;
		this.xa = xa;
		this.ya = ya;
		this.za = 0.0;
	}
	
	public SpawnParticle(Entity owner, double x, double y) {
		this.gravity = 0.0;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.r = 1.2;
		
		do {
			this.xa = random.nextDouble() * 2.0 - 1.0;
			this.ya = random.nextDouble() * 2.0 - 1.0;
			this.za = random.nextDouble() * 2.0;
		} while(this.xa * this.xa + this.ya * this.ya + this.za * this.za > 1.0);
		
		double dd = Math.sqrt(this.xa * this.xa + this.ya * this.ya + this.za * this.za);
		double speed = random.nextDouble() * 0.3;
	
		this.xa = this.xa / dd * speed;
		this.ya = this.ya / dd * speed;
		this.za = (this.za / dd - 1.0) * speed;
	}
	
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		
		if(this.die) {
			this.r *= 1.02;
			if(this.r > 5) remove();
		}
		
		this.xa *= 0.98;
		this.ya *= 0.98;
		this.za *= 0.4;
		
		this.x += this.xa;
		this.y += this.ya;
		this.z += this.za;
		
		if(Math.abs(this.xo - this.x) < 0.01 && Math.abs(this.yo - this.y) < 0.01) die();
	}
	
	public void render(Bitmap screen, double delta) {
		screen.drawCircle((int)(this.x - this.r), (int)(this.y - this.r - this.z), (int)this.r, 0x7F0700);
	}
	
	public void die() { this.die = true; }
	
	public void remove() {
		super.remove();
		Fish fish = new Fish(this.x, this.y);
		fish.scale = 1;
		fish.minSwimSpeed = 0.006;
		this.owner.level.add(fish);
	}
	
}