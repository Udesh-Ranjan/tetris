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

public class FallingBlock implements EventHandler<KeyEvent>{
	Queue<Shape>queueShapes;

	private BackgroundManager backgroundManager;
	private  GraphicsContext gc;
	private final int blockSizePixels;
	public FallingBlock(BackgroundManager backgroundManager,Shape shape){
		Objects.requireNonNull(backgroundManager);
		Objects.requireNonNull(shape);
		this.backgroundManager=backgroundManager;
		queueShapes.add(shape);
		gc=backgroundManager.gc;
		blockSizePixels=backgroundManager.blockSizePixels;
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
	@Override
	public void handle(KeyEvent keyEvent){
		final KeyCode keyCode=keyEvent.getCode();
		boolean collision=false;
		double x,y;
		x=y=0;
		if(keyCode=KeyEvent.DOWN){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.DOWN)){
				collision=true;
				y+=blockSizePixels;
			}
		}
		if(keyCode=KeyEvent.LEFT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.LEFT)){
				collision=true;
				x-=blockSizePixels;
			}
		}
		if(keyCode=KeyEvent.RIGHT){
			if(backgroundManager.isCollision(queueShapes.peek(),DIRECTION.RIGHT)){
				collision=true;
				x+=blockSizePixels;
			}
		}
		if(collision){
			backgroundManager.addShape(queueShapes.poll());
			Shape shape=getRandomShape();
			queueShapes.add(shape);
		}else{
			queueShapes.peek().translate(x,y);
		}

	}
}
