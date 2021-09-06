package tetris.backgroundManager;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import tetris.enums.DIRECTION;
import java.util.Objects;
import tetris.shapes.Shape;

/////////BACKGROUND MANAGER////////
public abstract class BackgroundManager{
	public final Canvas canvas;
	public final int HORIZONTAL_BLOCKS,VERTICAL_BLOCKS;
	public final int blockSizePixels;
	public final Block block;
	public BackgroundManager(final Canvas canvas,final int HORIZONTAL_BLOCKS,final int VERTICAL_BLOCKS,final int blockSizePixels){
		this.canvas=canvas;
		this.HORIZONTAL_BLOCKS=HORIZONTAL_BLOCKS;
		this.VERTICAL_BLOCKS=VERTICAL_BLOCKS;
		this.blockSizePixels=blockSizePixels;
		block=new Block(HORIZONTAL_BLOCKS,VERTICAL_BLOCKS);
	}
	public abstract void drawBackground();
	public abstract boolean isCollision(Shape shape,DIRECTION direction); 
	//////////BLOCK//////////
	public class Block{
		public final int row,col;
		public final boolean occupiedBlocks [][]; 
		public final Color colors[][];
		public Block(final int row,final int col){
			this.row=row;
			this.col=col;
			occupiedBlocks=new boolean[row][col];
			for(int i=0;i<row;i++){
				occupiedBlocks[i]=new boolean[col];
			}
			colors=new Color[row][col];
			for(int i=0;i<row;i++){
				colors[i]=new Color[col];
				for(int j=0;j<col;j++)
					colors[i][j]=Color.GRAY;
			}
		}
		public void setBlockOccupied(final int row,final int col){
			occupiedBlocks[row][col]=true;
		}
		public void setBlockUnoccupied(final int row,final int col){
			occupiedBlocks[row][col]=false;
		}
		public boolean isBlockOccupied(final int row,final int col){
			return occupiedBlocks[row][col];
		}
		public void setBlockColor(final int row,final int col,final Color color){
			Objects.requireNonNull(color);
			colors[row][col]=color;
		}
		public Color getBlockColor(final int row,final int col){
			return colors[row][col];
		}
	}
	/////////////////////////
}
