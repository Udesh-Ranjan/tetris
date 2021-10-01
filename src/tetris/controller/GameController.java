package tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.Canvas;

import tetris.backgroundManager.BackgroundManager;
import tetris.shapes.Cube;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import tetris.application.GameApplication;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import tetris.fallingBlock.FallingBlock;

import tetris.logger.TetrisLogger;

public class GameController extends AnimationTimer implements EventHandler<KeyEvent>{
	private final int BLOCK_LENGTH;
	private final LongProperty lastTimeUpdated;
	private final LongProperty time;//totalTime in nano
	private volatile boolean pauseGame;
	//private Cube cube;
	private Canvas canvas;
	BackgroundManager backgroundManager;
	private final GameApplication gameApplication;
	//private final Queue<Shape>shapesQueue;
	final FallingBlock fallingBlock;
	public final Random random=new Random();
	private int blockSizePixels=50;

	private final TetrisLogger logger=TetrisLogger.getLogger();

	public GameController(Canvas canvas,BackgroundManager backgroundManager,GameApplication application){
		//shapesQueue=new LinkedList<>();
		//shapesQueue.add(cube);
		BLOCK_LENGTH=50;
		this.canvas=canvas;
		//cube=new Cube(50);
		lastTimeUpdated=new SimpleLongProperty(0);	
		time=new SimpleLongProperty(System.currentTimeMillis()*1000000l);
		this.backgroundManager=backgroundManager;
		gameApplication=application;
		gameApplication.registerForKeyEvents(this);
		fallingBlock=new FallingBlock(this.backgroundManager,new Cube(blockSizePixels),application);
	}
	private static final long FALLING_BLOCK_DELAY=1000000000l;//nano
	private static final long BACKGROUND_UPDATE_DELAY=500000000l;//nano 
	private LongProperty lasttimeBgUpdated=new SimpleLongProperty();
	private LongProperty lasttimeBlockFell=new SimpleLongProperty();
	@Override
	public void handle(long nano){
		if(!pauseGame){
			if(lastTimeUpdated.get()>0){
				//System.out.println("handle");
				//TODO
				final long elapsedTime=nano-lastTimeUpdated.get();
				//System.out.println("elapsedTime : "+elapsedTime);
				//if(elapsedTime>=1000000000l){
				//System.out.println("Cube drawn");
				//fallingBlock.draw();
				//cube.draw(canvas.getGraphicsContext2D());
				//}
				if(nano-lasttimeBgUpdated.get()>=BACKGROUND_UPDATE_DELAY){
					backgroundManager.drawBackground();
					fallingBlock.draw();
					lasttimeBgUpdated.set(nano);
				}
				if(nano-lasttimeBlockFell.get()>=FALLING_BLOCK_DELAY){
					fallingBlock.fall();
					lasttimeBlockFell.set(nano);
				}
			}

			lastTimeUpdated.set(nano);	
		}
	}

	@Override
	public void handle(KeyEvent keyEvent){
		final KeyCode keyCode=keyEvent.getCode();
		if(keyCode==KeyCode.SPACE){
			logger.logInfo("Space is hitten");
			pauseGame=!pauseGame;

		}
	}
}
