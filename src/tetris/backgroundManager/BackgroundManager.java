package tetris.backgroundManager;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import tetris.enums.DIRECTION;
import java.util.Objects;
import tetris.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

/////////BACKGROUND MANAGER////////
public abstract class BackgroundManager{
	public final Canvas canvas;
	public final int HORIZONTAL_BLOCKS,VERTICAL_BLOCKS;
	public final int blockSizePixels;
	public final Block block;
	public final GraphicsContext gc;

	public BackgroundManager(final Canvas canvas,final int HORIZONTAL_BLOCKS,final int VERTICAL_BLOCKS,final int blockSizePixels){
		this.canvas=canvas;
		this.HORIZONTAL_BLOCKS=HORIZONTAL_BLOCKS;
		this.VERTICAL_BLOCKS=VERTICAL_BLOCKS;
		this.blockSizePixels=blockSizePixels;
		block=new Block(VERTICAL_BLOCKS,HORIZONTAL_BLOCKS);
		System.out.println("backgroundManager init horizontal_blocks(HORIZONTAL_BLOCKS,VERTICAL_BLOCKS)"+HORIZONTAL_BLOCKS+","+VERTICAL_BLOCKS);
		gc=canvas.getGraphicsContext2D();
	}

	public abstract void drawBackground();

	public abstract boolean isCollision(Shape shape,DIRECTION direction); 

	public abstract void addShape(Shape shape);

	public abstract boolean isOutOfBoundary(final Shape shape,final DIRECTION direction);

	//////////BLOCK//////////
	public class Block{
		public final int row,col;
		public final boolean occupiedBlocks [][]; 
		public final Color colors[][];
		final Color DEFAULT_COLOR=Color.GREY;
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
					colors[i][j]=DEFAULT_COLOR;
			}
		}
		public void setBlockOccupied(final int row,final int col,final Color color){
			occupiedBlocks[row][col]=true;
			setBlockColor(row,col,color);
		}
		public void setBlockUnoccupied(final int row,final int col){
			occupiedBlocks[row][col]=false;
			setBlockColor(row,col,DEFAULT_COLOR);
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
		public boolean rowsColumnSame(final int row){
			boolean same=true;
			final Color color=colors[row][0];
			for(int c=0;c<col;c++)
				if(!occupiedBlocks[row][c] || color!=colors[row][c]){
					return false;
				}
			return same;
		}
		public void setRowOccupied(final int r,Color color){
			for(int i=0;i<col;i++)
				setBlockOccupied(r,i,color);
		}
		public void setRowUnoccupied(final int r){
			for(int i=0;i<col;i++)
				setBlockUnoccupied(r,i);
		}
		public boolean isLeftBlock(final int r,final int c){
			if(isBlockOccupied(r,c)){
				if(r!=row-1)
					return !isBlockOccupied(r+1,c);
			}
			return false;
		}
		public boolean isLeftRow(final int row){
			for(int c=0;c<col;c++)
				if(isLeftBlock(row,c))
					return true;
			return false;
		}
		private void printBlock(){
			for(int i=0;i<row;i++){
				for(int j=0;j<col;j++)
					if(isBlockOccupied(i,j))
						System.out.print("X");
					else System.out.print("-");
				System.out.println();
			}
		}
		public int updateBlock(){
			System.out.println("UpdateBlock");
			printBlock();
			int rowsMatched=0;
			for(int r=0;r<row;r++){
				System.out.println("row : "+r);
				if(rowsColumnSame(r)){
					rowsMatched++;
					setRowUnoccupied(r);
				}
				System.out.println("rowsMatched : "+rowsMatched);
			}
			if(rowsMatched!=0){
				for(int r=0;r<row;r++)
					for(int c=0;c<col;c++)
						if(isLeftBlock(r,c)){
							int _r=r;
							while(_r!=row-1 && !isBlockOccupied(_r+1,c))
								_r--;
							setBlockOccupied(_r,c,getBlockColor(r,c));
							setBlockUnoccupied(r,c);
						}
				rowsMatched+=updateBlock();
			}
			return rowsMatched;
		}
	}
	/////////////////////////
}
