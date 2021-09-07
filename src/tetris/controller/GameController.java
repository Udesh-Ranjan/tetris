package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.Canvas;

import tetris.backgroundManager.BackgroundManager;
import tetris.shapes.Cube;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import tetris.application.GameApplication;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import tetris.fallingBlock.FallingBlock;

public class GameController extends AnimationTimer implements EventHandler<KeyEvent>{
	private final int BLOCK_LENGTH;
	private final LongProperty lastUpdateTime;
	private final LongProperty time;//totalTime in nano
	//private Cube cube;
	private Canvas canvas;
	BackgroundManager backgroundManager;
	private final GameApplication gameApplication;
	//private final Queue<Shape>shapesQueue;
	final FallingBlock fallingBlock;
	public final Random random=new Random();
	private int blockSizePixels=50;

	public GameController(Canvas canvas,BackgroundManager backgroundManager,GameApplication application){
		//shapesQueue=new LinkedList<>();
		//shapesQueue.add(cube);
		BLOCK_LENGTH=50;
		this.canvas=canvas;
		//cube=new Cube(50);
		fallingBlock=new FallingBlock(this.backgroundManager,new Cube(blockSizePixels));
		lastUpdateTime=new SimpleLongProperty(0);	
		time=new SimpleLongProperty(System.currentTimeMillis()*1000000l);
		this.backgroundManager=backgroundManager;
		gameApplication=application;
		gameApplication.registerForKeyEvents(this);
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
			fallingBlock.draw();
			//cube.draw(canvas.getGraphicsContext2D());
			//}
		}

		lastUpdateTime.set(nano);	
		//time.set(time.get()+nano);
	}

	@Override
	public void handle(KeyEvent keyEvent){
		//TODO
	}
}
