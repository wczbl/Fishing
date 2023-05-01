package game;

import game.gfx.Bitmap;
import game.screen.GameScreen;
import game.screen.Screen;

public class Game {
	
	private Screen screen;
	
	public Game() { setScreen(new GameScreen()); }
	
	public void setScreen(Screen screen) {
		if(this.screen != null) this.screen.destroy();
		this.screen = screen;
		if(this.screen != null) this.screen.init(this);
	}
	
	public void tick(InputHandler input) { this.screen.tick(input); }
	public void render(Bitmap screen, double delta) { this.screen.render(screen, delta); }
	
}