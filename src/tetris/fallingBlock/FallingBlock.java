package tetris.fallingBlock;

import tetris.backgroundManager.BackgroundManager;
import tetris.shapes.Shape;
import java.util.Queue;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import tetris.enums.DIRECTION;
import javafx.event.EventHandler;
import java.util.Objects;
import tetris.shapes.Cube;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import tetris.application.GameApplication;
import tetris.logger.TetrisLogger;
import java.util.logging.Level;

public class FallingBlock implements EventHandler<KeyEvent>{
	private static final int DELAY=200;//delay for 300 milliseconds
	private Queue<Shape>queueShapes;

	private BackgroundManager backgroundManager;
	private  GraphicsContext gc;
	private final int blockSizePixels;
	private final LongProperty lastTimeUpdated;//stores time in millisec
	private final GameApplication application;

	private TetrisLogger logger=TetrisLogger.getLogger();

	public FallingBlock(BackgroundManager backgroundManager,Shape shape,GameApplication application){
		Objects.requireNonNull(backgroundManager);
		Objects.requireNonNull(shape);
		this.backgroundManager=backgroundManager;
		queueShapes=new LinkedList<>();
		queueShapes.add(shape);
		gc=backgroundManager.gc;
		blockSizePixels=backgroundManager.blockSizePixels;
		lastTimeUpdated=new SimpleLongProperty(0);
		this.application=application;
		application.registerForKeyEvents(this);
	}
	public Shape getShape(){
		return queueShapes.peek();
	}
	public void draw(){
		logger.logInfo("fallingBlock draw");
		Shape shape=queueShapes.peek();
		if(shape!=null){
			shape.draw(gc);
			shape.rectangles.forEach(rectangle->{
				logger.logInfo("(x,y) : "+rectangle.getX()+","+rectangle.getY());
			});
		}else logger.log(Level.WARNING,"shape is null");
	}
	//TODO
	public Shape getRandomShape(){
		Shape shape=null;
		shape=new Cube(backgroundManager.blockSizePixels);
		return shape;
	}
	//BLOCK IS FALLING
	public void fall(){
		logger.logInfo("Block is falling");
		Shape shape=queueShapes.peek();
		if(shape!=null){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.DOWN)){
				logger.logInfo("Collision");
				backgroundManager.addShape(queueShapes.poll());
				backgroundManager.block.updateBlock();
				shape=getRandomShape();
				queueShapes.add(shape);
			}else if(backgroundManager.isOutOfBoundary(queueShapes.peek(),DIRECTION.DOWN)){
				logger.logInfo("OutOfBoundary");
				backgroundManager.addShape(queueShapes.poll());
				backgroundManager.block.updateBlock();
				shape=getRandomShape();
				queueShapes.add(shape);
			}else{
				logger.logInfo("translating DOWN");
				shape.translate(DIRECTION.DOWN);
			}
		}else logger.log(Level.WARNING,"shape is null");
	}
	@Override
	public void handle(KeyEvent keyEvent){
		logger.logInfo("FallingBlock handlingkeyevent");
		final long time=System.currentTimeMillis();
		//WE ARE WAITNG FOR SOME MILLISECONDS FOR NEXT TIME TO MOVE THE BLOCK
		if(time-lastTimeUpdated.get() < DELAY)
			return;

		final KeyCode keyCode=keyEvent.getCode();
		boolean collision=false;
		double x,y;
		x=y=0;
		boolean outOfBoundary=false;
		if(keyCode==KeyCode.DOWN){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.DOWN)){
				collision=true;
			}
			if(backgroundManager.isOutOfBoundary(queueShapes.peek(),DIRECTION.DOWN)){
				outOfBoundary=true;
			}
			y+=blockSizePixels;
		}
		if(keyCode==KeyCode.LEFT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.LEFT)){
				collision=true;
			}
			if(backgroundManager.isOutOfBoundary(queueShapes.peek(),DIRECTION.LEFT)){
				outOfBoundary=true;
			}
			x-=blockSizePixels;
		}
		if(keyCode==KeyCode.RIGHT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.RIGHT)){
				collision=true;
			}
			if(backgroundManager.isOutOfBoundary(queueShapes.peek(),DIRECTION.RIGHT)){
				outOfBoundary=true;
			}
			x+=blockSizePixels;
		}
		if(collision)
			logger.logInfo("Collision");
		if(outOfBoundary)
			logger.logInfo("OutOfBoundary");

		if(collision){
			if(keyCode==KeyCode.DOWN){
				backgroundManager.addShape(queueShapes.poll());
				Shape shape=getRandomShape();
				queueShapes.add(shape);
			}
		}else if(!outOfBoundary){
			Shape shape=queueShapes.peek();
			if(shape!=null)
				shape.translate(x,y);
			else logger.log(Level.WARNING,"shape is null ");
		}
		lastTimeUpdated.set(time);
	}
}
