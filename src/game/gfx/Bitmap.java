package game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Bitmap {

	public final int w;
	public final int h;
	public int[] pixels;
	
	public int xOffs;
	public int yOffs;
	
	public Bitmap(BufferedImage image) {
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
	}

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}
	
	public void clear(int col) { Arrays.fill(this.pixels, col); }

	public void setPixel(int xp, int yp, int col) {
		xp += this.xOffs;
		yp += this.yOffs;
		
		if(xp >= 0 && yp >= 0 && xp < this.w && yp < this.h) {
			this.pixels[xp + yp * this.w] = col;
		}
	}
	
	public void draw(Bitmap b, int xp, int yp, int col) {
		xp += this.xOffs;
		yp += this.yOffs;
		
		int rr = (col >> 16) & 0xFF;
		int gg = (col >> 8) & 0xFF;
		int bb = (col >> 0) & 0xFF;
		
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w;
		int y1 = yp + b.h;
		
		rr = rr * 0x55 / 0xFF;
		gg = gg * 0x55 / 0xFF;
		bb = bb * 0x55 / 0xFF;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		
		for(int y = y0; y < y1; y++) {
			int sp = (y - y0) * b.w;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int c = rr << 16 | gg << 8 | bb;
				int src = b.pixels[sp++];
				if(src == 0) continue;
				this.pixels[dp + x] = src * c;
			} 
		}
	}
	
	public void scaleDraw(Bitmap b, int xp, int yp, int col, int scale) {
		xp += this.xOffs;
		yp += this.yOffs;
		
		int rr = (col >> 16) & 0xFF;
		int gg = (col >> 8) & 0xFF;
		int bb = (col >> 0) & 0xFF;
		
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w * scale;
		int y1 = yp + b.h * scale;
		
		rr = rr * 0x55 / 0xFF;
		gg = gg * 0x55 / 0xFF;
		bb = bb * 0x55 / 0xFF;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		
		for(int y = y0; y < y1; y++) {
			int sp = ((y - y0) / scale) * b.w;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int c = rr << 16 | gg << 8 | bb;
				int src = b.pixels[(sp + (x - xp) / scale)];
				if(src == 0) continue;
				this.pixels[dp + x] = src * c;
			} 
		}
	}
	
	public void drawCircle(int xp, int yp, int r, int col) {
		int x = 0;
		int y = r;
		int d = 3 - 2 * r;
		
		while(x <= y) {
			setPixel(xp + x, yp + y, col);
			setPixel(xp + y, yp + x, col);
			setPixel(xp - x, yp + y, col);
			setPixel(xp - y, yp + x, col);
			setPixel(xp + x, yp - y, col);
			setPixel(xp + y, yp - x, col);
			setPixel(xp - x, yp - y, col);
			setPixel(xp - y, yp - x, col);
			
			if(d < 0) d = d + 4 * x + 6;
			else {
				d = d + 4 * (x - y) + 10;
				y--;
			}
			
			x++;
		}
	}
	
	public Bitmap rotate(double angle) {
	    if (angle == 0.0) {
	        return this;
	    }
	    
	    double cos = -Math.cos(angle);
	    double sin = -Math.sin(angle);
	    
	    int ww = (int)Math.round(Math.abs(this.w * cos) + Math.abs(this.h * sin) + 1);
	    int hh = (int)Math.round(Math.abs(this.h * cos) + Math.abs(this.w * sin) + 1);
	    int[] result = new int[ww * hh];
	    
	    int centerX = this.w / 2;
	    int centerY = this.h / 2;
	    
	    for (int y = 0; y < hh; y++) {
	        for (int x = 0; x < ww; x++) {
	            int oldX = (int)Math.round((x - centerX) * cos + (y - centerY) * sin + centerX);
	            int oldY = (int)Math.round(-(x - centerX) * sin + (y - centerY) * cos + centerY);
	            
	            if (oldX >= 0 && oldX < this.w && oldY >= 0 && oldY < this.h) {
	            	int col = this.pixels[oldX + oldY * this.w];
	            	result[x + y * ww] = col;
	            }
	        }
	    }
	    
	    return new Bitmap(ww, hh, result);
	}
	
}