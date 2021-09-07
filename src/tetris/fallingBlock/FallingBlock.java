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

public class FallingBlock implements EventHandler<KeyEvent>{
	private static final int DELAY=300;//delay for 300 milliseconds
	private Queue<Shape>queueShapes;

	private BackgroundManager backgroundManager;
	private  GraphicsContext gc;
	private final int blockSizePixels;
	private final LongProperty lastTimeUpdated;//stores time in millisec
	public FallingBlock(BackgroundManager backgroundManager,Shape shape){
		Objects.requireNonNull(backgroundManager);
		Objects.requireNonNull(shape);
		this.backgroundManager=backgroundManager;
		queueShapes=new LinkedList<>();
		queueShapes.add(shape);
		gc=backgroundManager.gc;
		blockSizePixels=backgroundManager.blockSizePixels;
		lastTimeUpdated=new SimpleLongProperty(0);
	}
	public Shape getShape(){
		return queueShapes.peek();
	}
	public void draw(){
		queueShapes.peek().draw(gc);
	}
	//TODO
	public Shape getRandomShape(){
		Shape shape=null;
		shape=new Cube(backgroundManager.blockSizePixels);
		return shape;
	}
	//BLOCK IS FALLING
	public void fall(){
		System.out.println("Block is falling");
		if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.DOWN)){
			System.out.println("Collision");
			backgroundManager.addShape(queueShapes.poll());
			Shape shape=getRandomShape();
			queueShapes.add(shape);
		}else{
			queueShapes.peek().translate(DIRECTION.DOWN);
		}
	}
	@Override
	public void handle(KeyEvent keyEvent){
		final long time=System.currentTimeMillis();
		//WE ARE WAITNG FOR SOME MILLISECONDS FOR NEXT TIME TO MOVE THE BLOCK
		if(time-lastTimeUpdated.get() < DELAY)
			return;

		final KeyCode keyCode=keyEvent.getCode();
		boolean collision=false;
		double x,y;
		x=y=0;
		if(keyCode==KeyCode.DOWN){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.DOWN)){
				collision=true;
			}
			y+=blockSizePixels;
		}
		if(keyCode==KeyCode.LEFT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.LEFT)){
				collision=true;
			}
			x-=blockSizePixels;
		}
		if(keyCode==KeyCode.RIGHT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.RIGHT)){
				collision=true;
			}
			x+=blockSizePixels;
		}
		if(collision){
			backgroundManager.addShape(queueShapes.poll());
			Shape shape=getRandomShape();
			queueShapes.add(shape);
		}else{
			queueShapes.peek().translate(x,y);
		}
		lastTimeUpdated.set(time);

	}
}
