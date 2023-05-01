package game.screen;

import java.util.Random;

import game.GameComponent;
import game.InputHandler;
import game.State;
import game.entity.Fish;
import game.entity.HungryFish;
import game.entity.YellowBoy;
import game.entity.cuttle.Cuttle;
import game.entity.cuttle.CuttlePart;
import game.gfx.Bitmap;
import game.gfx.Font;
import game.level.Level;

public class GameScreen extends Screen {
	
	private Level level;
	private Random random = new Random();
	private CuttlePart cuttle;
	private int cuttleLength;
	private int maxCuttleLength = 5;
	
	public GameScreen() { newGame(); }
	
	public void newGame() {
		this.level = new Level();
		this.level.add(new YellowBoy(this.random.nextInt(GameComponent.WIDTH), this.random.nextInt(GameComponent.HEIGHT)));
		this.level.add(new HungryFish(this.random.nextInt(GameComponent.WIDTH), this.random.nextInt(GameComponent.HEIGHT)));		
	
		int tentacleCount = 5;
		for(int i = 0; i < tentacleCount; i++) {
			Cuttle.buildCuttle(this.level, GameComponent.WIDTH / 2.0 - Math.sin(i * Math.PI * 2.0 / tentacleCount) * 16.0, GameComponent.HEIGHT / 2.0 + Math.cos(i * Math.PI * 2.0 / tentacleCount) * 16.0, 3, i * Math.PI * 2.0 / tentacleCount * (tentacleCount - 1));
		}
	}
	
	public void destroy() {}

	public void tick(InputHandler input) {
		this.level.tick();
		
		if(input.leftClicked) {
			int xx = input.x;
			int yy = input.y;
			this.level.add(new Fish(xx, yy));
		}
		
		if(input.rightClicked && this.cuttleLength != this.maxCuttleLength) {
			this.cuttle = Cuttle.addPart(this.level, GameComponent.WIDTH / 2.0, GameComponent.HEIGHT / 2.0, this.cuttle, Math.PI / 2);
			this.cuttleLength++;
		}
		
	}

	public void render(Bitmap screen, double delta) {
		this.level.render(screen, delta);
		
		
		Font.scaleDraw("Eaten:" + State.eaten, screen, 10, 10, 0xFFFFFF, 2);
	}
	
}