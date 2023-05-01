package game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	
	public static final Bitmap[] sprites = loadAndCut("/sprites.png", 16);
	public static final Bitmap[] font = loadAndCut("/font.png", 8);

	public static Bitmap[] loadAndCut(String path, int size) {
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		int sw = sheet.getWidth() / size;
		int sh = sheet.getHeight() / size;
		Bitmap[] result = new Bitmap[sw * sh];
		for(int y = 0; y < sh; y++) {
			for(int x = 0; x < sw; x++) {
				BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * size, -y * size, null);
				g.dispose();
				result[x + y * sw] = new Bitmap(image);
			
				for(int i = 0; i < result[x + y * sw].pixels.length; i++) {
					int in = (result[x + y * sw].pixels[i]);
					int col = (in & 0xF) >> 2;
					result[x + y * sw].pixels[i] = col;
				}
			}
		}
		
		return result;
	}
	
}