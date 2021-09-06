package tetris.backgroundManager;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import tetris.shapes.Rectangle;
import javafx.scene.canvas.Canvas;
import java.util.List;
import tetris.shapes.Shape;
import tetris.enums.DIRECTION;

public class SolidBackgroundManager extends BackgroundManager{

	public SolidBackgroundManager(final Canvas canvas,final int HORIZONTAL_BLOCKS,final int VERTICAL_BLOCKS,final int blockSizePixels){
		super(canvas,HORIZONTAL_BLOCKS,VERTICAL_BLOCKS,blockSizePixels);
	}
	@Override
	public boolean isCollision(final Shape shape,final DIRECTION direction){
		final List<Rectangle>rectangles=shape.getRectangles();
		for(int i=0;i<block.row;i++){
			for(int j=0;j<block.col;j++){
				double topX=blockSizePixels*i;
				double topY=blockSizePixels*j;
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
		return false;
	}
	@Override
	public void drawBackground(){
		final GraphicsContext gc=canvas.getGraphicsContext2D();
		//gc.fillRect(0,0,HOROZONTAL_BLOCKS*blockSizePixel,VERTICAL_BLOCKS*blockSizePixel);
		for(int i=0;i<block.row;i++){
			for(int j=0;j<block.col;j++){
				Color color=block.colors[i][j];
				gc.setFill(color);
				gc.fillRect(i*blockSizePixels,j*blockSizePixels,blockSizePixels,blockSizePixels);
			}
		}
	}
}
