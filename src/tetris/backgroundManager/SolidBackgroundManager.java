package tetris.backgroundManager;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import tetris.shapes.Rectangle;
import javafx.scene.canvas.Canvas;
import java.util.List;
import tetris.shapes.Shape;
import tetris.enums.DIRECTION;
import java.util.concurrent.atomic.AtomicBoolean;
import com.google.common.util.concurrent.AtomicDouble;

public class SolidBackgroundManager extends BackgroundManager{

	public SolidBackgroundManager(final Canvas canvas,final int HORIZONTAL_BLOCKS,final int VERTICAL_BLOCKS,final int blockSizePixels){
		super(canvas,HORIZONTAL_BLOCKS,VERTICAL_BLOCKS,blockSizePixels);
	}
	@Override
	public boolean isCollision(final Shape shape,final DIRECTION direction){
		final List<Rectangle>rectangles=shape.getRectangles();
		/*for(int i=0;i<block.row;i++){
		  for(int j=0;j<block.col;j++){
		  if(block.isBlockOccupied(i,j)){
		  double topX=blockSizePixels*j;
		  double topY=blockSizePixels*i;
		  if(direction==DIRECTION.UP)
		  topY-=blockSizePixels;
		  else if(direction==DIRECTION.DOWN)
		  topY+=blockSizePixels;
		  else if(direction==DIRECTION.LEFT)
		  topX-=blockSizePixels;
		  else if(direction==DIRECTION.RIGHT)
		  topX+=blockSizePixels;
		  else throw new IllegalStateException("Direction value if not diefined");
		  for(final Rectangle rectangle:rectangles){
		  if(rectangle.getX()==topX && rectangle.getY()==topY)
		  return true;
		  }
		  }
		  }
		}
		*/
		for(final Rectangle rectangle:rectangles){
			double topX=rectangle.getX();
			double topY=rectangle.getY();
			if(direction==DIRECTION.UP)
				topY-=blockSizePixels;
			else if(direction==DIRECTION.DOWN)
				topY+=blockSizePixels;
			else if(direction==DIRECTION.LEFT)
				topX-=blockSizePixels;
			else if(direction==DIRECTION.RIGHT)
				topX+=blockSizePixels;
			else throw new IllegalStateException("Direction value if not diefined");

			for(int i=0;i<block.row;i++){
				for(int j=0;j<block.col;j++){
					if(block.isBlockOccupied(i,j)){
						if(i*blockSizePixels==topY && j*blockSizePixels==topX)
							return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public boolean isOutOfBoundary(final Shape shape,final DIRECTION direction){
		System.out.println("isOutOfBoundary");
		System.out.println("direction : "+direction);
		AtomicDouble x,y;
		x=new AtomicDouble(0);
		y=new AtomicDouble(0);
		if(direction==DIRECTION.UP)
			y.set(y.get()-blockSizePixels);
		else if(direction==DIRECTION.DOWN)
			y.set(y.get()+blockSizePixels);
		else if(direction==DIRECTION.LEFT)
			x.set(x.get()-blockSizePixels);
		else if(direction==DIRECTION.RIGHT)
			x.set(x.get()+blockSizePixels);
		final AtomicBoolean outOfBoundary=new AtomicBoolean(false);
		shape.rectangles.stream()
			.forEach(rectangle->{
				System.out.println("x,y "+rectangle.getX()+","+rectangle.getY());
				if(direction==DIRECTION.LEFT){
					if(rectangle.getX()+x.get()<0)
						outOfBoundary.set(true);//return true;
				}
				if(direction==DIRECTION.RIGHT){
					if(rectangle.getX()+rectangle.getWidth()+x.get()>blockSizePixels*block.col)
						outOfBoundary.set(true);//return true;
				}
				if(direction==DIRECTION.UP){
					if(rectangle.getY()+y.get()<0)
						outOfBoundary.set(true);//return true;
				}
				if(direction==DIRECTION.DOWN){
					if(rectangle.getY()+rectangle.getHeight()+y.get()>blockSizePixels*block.row)
						outOfBoundary.set(true);//return true;
				}
			});
		return outOfBoundary.get();
	}
	@Override
	public void drawBackground(){
		final GraphicsContext gc=canvas.getGraphicsContext2D();
		//gc.fillRect(0,0,HOROZONTAL_BLOCKS*blockSizePixel,VERTICAL_BLOCKS*blockSizePixel);
		for(int i=0;i<block.row;i++){
			for(int j=0;j<block.col;j++){
				Color color=block.colors[i][j];
				gc.setFill(color);
				gc.fillRect(j*blockSizePixels,i*blockSizePixels,blockSizePixels,blockSizePixels);
			}
		}
	}
	@Override
	public void addShape(final Shape shape){
		shape.rectangles.stream()
			.forEach(rectangle->{
				int row=(int)rectangle.getY()/blockSizePixels;
				int col=(int)rectangle.getX()/blockSizePixels;
				block.setBlockOccupied(row,col,rectangle.getFill());
				block.setBlockColor(row,col,rectangle.getFill());
			});
	}
}
