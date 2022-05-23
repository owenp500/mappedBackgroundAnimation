import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class OCPSprite implements DisplayableSprite, MovableSprite,CollidingSprite {
	private int currentFrame = 0;
	private long elapsedTime = 0;
	private final static int FRAMES = 4;
	private double framesPerSecond = 30;
	private double milliSecondsPerFrame = 1000 / framesPerSecond;
	private static Image[] frames = new Image[FRAMES];
	private static boolean framesLoaded = false;	

	private double centerX = 200;
	private double centerY = -100;
	private double height = 50;
	private double width = 50;
	private boolean dispose = false;
	private boolean isAtExit = false;
	private static String proximityMessage;
	
	
	private double velocityX = 0;
	private double velocityY = 0;
	private long score = 0;
	
	private final double ACCELERATION = 4;
	
	public OCPSprite(int height, int width) {
		
		
		//this.centerX =centerX;
		//this.centerY = centerY;	
		
		if (framesLoaded == false) {
			for (int frame = 0; frame < FRAMES; frame++) {
				String filename = String.format("res/OCP/saw_%d.png" , frame + 4);
				
				try {
					frames[frame] = ImageIO.read(new File(filename));
				}
				catch (IOException e) {
					System.err.println(e.toString());
				}		
			}
			
			if (frames[0] != null) {
				framesLoaded = true;
			}
		}		
	}	
	public Image getImage() {		
		return frames[currentFrame];
	}
	public void setCenterX(double centerX) {		
		this.centerX = centerX;
	}
	
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
	public void setVelocityX(double pixelsPerSecond) {		
		this.velocityX = pixelsPerSecond;
	}
	
	public void setVelocityY(double pixelsPerSecond) {		
		this.velocityY = pixelsPerSecond;
	}
	
	public boolean getVisible() {
		return true;
	}

	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		
		return height;
	}
	
	public double getWidth() {
		
		return width;
	}
	
	public double getCenterX() {
		
		return centerX;
	}
	
	public double getCenterY() {
		
		return centerY;
	}
	
	public boolean getDispose() {
		
		return dispose;
	}
	
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}
						
	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
	
	/*private boolean checkCollisionWithCoin(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof CoinSprite) {
				
				
				if (CollisionDetection.covers(this, sprite, deltaX, deltaY)) {
					colliding = true;
					sprite.setDispose(true);
					score += 100;						
				}
			}
		}		
		return colliding;		
	}*/
	/*
	 private boolean checkIsAtExit(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean colliding = false;
		
		for (DisplayableSprite sprite : sprites)
			if (sprite instanceof ExitSprite) {
				if (CollisionDetection.covers(sprite, this, deltaX, deltaY)) {
					colliding = true;
					sprite.setDispose(true);
					break;	
				}
			}
		return colliding;
	}
	*/
	/*
	 private boolean checkProximity(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) { 
	
		boolean close = false;
		double distance;
		
		for (DisplayableSprite sprite : sprites)
			if (sprite instanceof OtherSprite) {
				distance = Math.sqrt(Math.pow((getMinX() - sprite.getCenterX()), 2) + Math.pow((getMinY() - sprite.getCenterY()),2));
				if (distance < 100) {
					close = true;
					break;
				}
			}
		return close;
	} 
	*/
	public long getScore() {
		return score;
	}
	public String getProximityMessage() {	
		return proximityMessage;
	}
	
	public boolean getIsAtExit() {
		return isAtExit;
	}
	
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		double absVelocityX = Math.abs(velocityX);
		
		
		if (keyboard.keyDown(37)) {
			if (velocityX > 0) {
				velocityX -= ACCELERATION * ACCELERATION;
			}
			else {
				velocityX += -ACCELERATION;
				}
		}
		//RIGHT ARROW
		if (keyboard.keyDown(39)) {
			if (velocityX < 0) {
				velocityX += ACCELERATION * ACCELERATION;
			}
			else {
				velocityX += ACCELERATION;
			}
		}
		// DOWN ARROW
		if (keyboard.keyDown(40)) {
			velocityY += 50;			
		}		
		//constant downward y velocity for gravity
		velocityY += 23;
		
		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;
		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);
		boolean collidingBarrierY = checkCollisionWithBarrier(universe.getSprites(), 0, deltaY);
		//boolean proximity = checkProximity(universe.getSprites(), deltaX, deltaY);
		//boolean collidingCoin = checkCollisionWithCoin(universe.getSprites(), deltaX, deltaY);
		//boolean atExit = checkIsAtExit(universe.getSprites(), deltaX,deltaY);
		if (collidingBarrierX == false) {
			this.centerX += actual_delta_time * 0.001 * velocityX;
		}
		else {
			velocityX = 0;
		}
		if (collidingBarrierY == false) {
			this.centerY += actual_delta_time * 0.001 * velocityY;
		}
		else {
			if (velocityX != 0) {
				velocityX -= velocityX * velocityX / 999999;
			}
			
			velocityY = 0;
			if (keyboard.keyDown(38)) {
				velocityY -= 550;
			}
		}
		
		/*if (atExit) {
			isAtExit = true;
		}
		if (proximity) {
			proximityMessage = "watch out";
		}
		else {
			proximityMessage = "chillin";
		}
		*/
		
		elapsedTime += actual_delta_time;
		long elapsedFrames = (long) (elapsedTime / milliSecondsPerFrame);
		currentFrame = (int) (elapsedFrames % FRAMES);		
		
		}
}
