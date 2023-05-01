package game.level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import game.entity.Entity;
import game.gfx.Bitmap;
import game.gfx.Renderable;
import game.particle.Particle;
import game.particle.ParticleSystem;

public class Level {
	
	public List<Entity> entities = new ArrayList<Entity>();
	public ParticleSystem ps = new ParticleSystem();

	public void tick() {
		this.ps.tick();
		for(int i = 0; i < this.entities.size(); i++) {
			Entity e = this.entities.get(i);
			
			if(!e.removed) {
				e.tick();
				continue;
			}
			
			this.entities.remove(i--);
		}
	}
	
	public void render(Bitmap screen, double delta) {
		TreeSet<Renderable> renderList = new TreeSet<Renderable>(new Comparator<Renderable>() {
			public int compare(Renderable r0, Renderable r1) {
				double y0 = r0 instanceof Particle ? ((Particle)r0).y : ((Entity)r0).y;
				double y1 = r1 instanceof Particle ? ((Particle)r1).y : ((Entity)r1).y;
				return y1 < y0 ? 1 : -1;
			}
		});
		
		renderList.addAll(this.entities);
		renderList.addAll(this.ps.particles);
		for(Renderable r : renderList) {
			r.render(screen, delta);
		}
	}
	
	public void add(Entity e) {
		this.entities.add(e);
		e.init(this);
	}
	
	public Entity findTarget(Entity owner, Checker checker) {
		Entity result = null;
		for(Entity e : this.entities) {
			if(e == owner | !checker.check(owner, e)) continue;
			result = e;
			break;
		}
		
		return result;
	}
	
}