package tetris.animation;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class AnimationClass extends AnimationTimer{
	private final LongProperty lastUpdateTime;
	public AnimationClass(){
		lastUpdateTime=new SimpleLongProperty(0);	
	}
	@Override
	public void handle(long nano){
		if(lastUpdateTime.get()>0){
			//TODO
			final long elapsedTime=nano-lastUpdateTime.get();
		}
		lastUpdateTime.set(nano);	
	}
}
