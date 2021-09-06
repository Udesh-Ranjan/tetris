package tetris.animation;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import tetris.shapes.Cube;
import javafx.scene.canvas.Canvas;

public class AnimationClass extends AnimationTimer{
	private final int BLOCK_LENGTH;
	private final LongProperty lastUpdateTime;
	private final LongProperty time;//totalTime in nano
	private Cube cube;
	private Canvas canvas;
	public AnimationClass(Canvas canvas){
		BLOCK_LENGTH=50;
		this.canvas=canvas;
		cube=new Cube(50);
		lastUpdateTime=new SimpleLongProperty(0);	
		time=new SimpleLongProperty(System.currentTimeMillis()*1000000l);
	}
	@Override
	public void handle(long nano){
		if(lastUpdateTime.get()>0){
			System.out.println("handle");
			//TODO
			final long elapsedTime=nano-lastUpdateTime.get();
			System.out.println("elapsedTime : "+elapsedTime);
			//if(elapsedTime>=1000000000l){
				System.out.println("Cube drawn");
				cube.draw(canvas.getGraphicsContext2D());
			//}
		}

		lastUpdateTime.set(nano);	
		//time.set(time.get()+nano);
	}
}
