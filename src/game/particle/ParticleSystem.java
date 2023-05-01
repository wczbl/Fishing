package game.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.gfx.Bitmap;

public class ParticleSystem {

	private Random random = new Random();
	public int avgTime = 300;
	public List<Particle> particles = new ArrayList<Particle>();
	
	public void add(Particle p) {
		if(p != null) {
			p.time = this.avgTime - this.random.nextInt(3) * 60;
			p.scale = 0.5 + this.random.nextDouble();
			this.particles.add(p);
			p.init(this);
		}
	}
	
	public void tick() {
		for(int i = 0; i < this.particles.size(); i++) {
			Particle p = this.particles.get(i);
			
			if(!p.removed) {
				p.tick();
				continue;
			}
			
			this.particles.remove(i--);
		}
	}
	
	public void render(Bitmap screen) {
		for(Particle p : this.particles) {
			p.render(screen, avgTime);
		}
	}
	
}