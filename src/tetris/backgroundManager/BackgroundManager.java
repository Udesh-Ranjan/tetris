package tetris.backgroundManager;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import tetris.enums.DIRECTION;
import java.util.Objects;
import tetris.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import tetris.shapes.Rectangle;
import tetris.logger.TetrisLogger;

/////////BACKGROUND MANAGER////////
public abstract class BackgroundManager{
	public final Canvas canvas;
	public final int HORIZONTAL_BLOCKS,VERTICAL_BLOCKS;
	public final int blockSizePixels;
	public final Block block;
	public final GraphicsContext gc;

	private final TetrisLogger logger=TetrisLogger.getLogger();

	public BackgroundManager(final Canvas canvas,final int HORIZONTAL_BLOCKS,final int VERTICAL_BLOCKS,final int blockSizePixels){
		this.canvas=canvas;
		this.HORIZONTAL_BLOCKS=HORIZONTAL_BLOCKS;
		this.VERTICAL_BLOCKS=VERTICAL_BLOCKS;
		this.blockSizePixels=blockSizePixels;
		block=new Block(VERTICAL_BLOCKS,HORIZONTAL_BLOCKS,blockSizePixels,blockSizePixels);
		logger.logInfo("backgroundManager init horizontal_blocks(HORIZONTAL_BLOCKS,VERTICAL_BLOCKS)"+HORIZONTAL_BLOCKS+","+VERTICAL_BLOCKS);
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
		//public final Color colors[][];
		public final Rectangle rectangles[][];
		final Color DEFAULT_FILL_COLOR=Color.GREY,DEFAULT_STROKE_COLOR=Color.BLACK;
		final float DEFAULT_STROKE_WIDTH=1.0f;
		final int BLOCK_WIDTH,BLOCK_HEIGHT;

		public Block(final int row,final int col,final int blockWidth,final int blockHeight){
			this.row=row;
			this.col=col;
			this.BLOCK_WIDTH=blockWidth;
			this.BLOCK_HEIGHT=blockHeight;
			occupiedBlocks=new boolean[row][col];
			rectangles=new Rectangle[row][col];
			for(int i=0;i<row;i++){
				occupiedBlocks[i]=new boolean[col];
			}
			//colors=new Color[row][col];
			for(int i=0;i<row;i++){
				//colors[i]=new Color[col];
				rectangles[i]=new Rectangle[col];
				for(int j=0;j<col;j++){
					//colors[i][j]=DEFAULT_COLOR;
					Rectangle rectangle=null;
					rectangles[i][j]=rectangle=new Rectangle(j*BLOCK_WIDTH,i*BLOCK_HEIGHT,BLOCK_WIDTH,BLOCK_HEIGHT);
					rectangle.setFill(DEFAULT_FILL_COLOR);
					rectangle.setStroke(DEFAULT_STROKE_COLOR);
					rectangle.setStrokeWidth(DEFAULT_STROKE_WIDTH);
				}

			}
			logger.logInfo("Block Construction Starts");
			logger.logInfo("row : "+row+" col : "+col);
			for(int i=0;i<row;i++){
				String str="";
				for(int j=0;j<col;j++){
					final Rectangle rectangle=rectangles[i][j];
					str+="("+rectangle.getX()+","+rectangle.getY()+"), ";
				}
				logger.logInfo(str);
			}
			logger.logInfo("Block Construction Ends");
		}
		public void setBlockOccupied(final int row,final int col,final Rectangle rectangle){
			occupiedBlocks[row][col]=true;
			//setBlockColor(row,col,color);
			setRectangle(row,col,rectangle);
		}
		public void setBlockUnoccupied(final int row,final int col){
			occupiedBlocks[row][col]=false;
			//setBlockColor(row,col,DEFAULT_COLOR);
			final Rectangle oldRectangle = rectangles[row][col];
			final Rectangle rectangle=new Rectangle(oldRectangle.getX(),oldRectangle.getY(),oldRectangle.getWidth(),
					oldRectangle.getHeight());
			rectangle.setFill(DEFAULT_FILL_COLOR);
			rectangle.setStroke(DEFAULT_STROKE_COLOR);
			rectangle.setStrokeWidth(DEFAULT_STROKE_WIDTH);
			setRectangle(row,col,rectangle);
		}
		public boolean isBlockOccupied(final int row,final int col){
			return occupiedBlocks[row][col];
		}
		/*public void setBlockColor(final int row,final int col,final Color color){
		  Objects.requireNonNull(color);
		  colors[row][col]=color;
		}
		*/
		public void setRectangle(final int row,final int col,final Rectangle rectangle){
			rectangles[row][col]=rectangle;
		}
		/*public Color getBlockColor(final int row,final int col){
		  return colors[row][col];
		}
		*/
		public boolean rowsColumnSame(final int row){
			boolean same=true;
			//final Color color=colors[row][0];
			final Rectangle rectangle=rectangles[row][0];
			/*for(int c=0;c<col;c++)
			  if(!occupiedBlocks[row][c] || color!=colors[row][c]){
			  return false;
			  }
			  */
			for(int c=0;c<col;c++)
				if(!occupiedBlocks[row][c] || rectangle.getFill() != rectangles[row][c].getFill())
					return false;
			return same;
		}
		public void setRowOccupied(final int r,Rectangle rectangle){
			for(int i=0;i<col;i++)
				setBlockOccupied(r,i,rectangle);
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
		/*private void printBlock(){
		  for(int i=0;i<row;i++){
		  for(int j=0;j<col;j++)
		  if(isBlockOccupied(i,j))
		  System.out.print("X");
		  else System.out.print("-");
		//logger.logInfo();
		  }
		}
		*/
		private void printBlockStructure(){
			for(int i=0;i<row;i++){
				String str="";
				for(int j=0;j<col;j++)
					str+=(isBlockOccupied(i,j)?"X":"O");
				logger.logInfo(str);
			}
		}
		private void logBlockColor(){
			for(int i=0;i<row;i++){
				String str="";
				for(int j=0;j<col;j++)
					str+=rectangles[i][j].getFill().toString()+",";
				logger.logInfo(str);
			}
		}
		public int updateBlock(){
			logger.logInfo("Entering UpdateBlock");
			int tot=0;
			int rowsMatched=0;
			do{
				rowsMatched=0;
				for(int r=0;r<row;r++){
					logger.logInfo("row : "+r);
					if(rowsColumnSame(r)){
						rowsMatched++;
						setRowUnoccupied(r);
					}
					if(rowsMatched!=0)
						logger.logInfo("Alert rowsMatched : "+rowsMatched);
					else logger.logInfo("rowsMatched : "+rowsMatched);
				}
				tot+=rowsMatched;
				if(rowsMatched!=0){
					for(int r=row-2;r>=0;r--){
						for(int c=0;c<col;c++){
							if(isLeftBlock(r,c)){
								boolean grid[][]=getConnectedBlocks(null,r,c);
								boolean gridBackup[][]=java.util.Arrays.stream(grid)
									.map(boolean[]::clone)
									.toArray(boolean [][]::new);
								logger.logInfo("gridBackup : ");
								for(int i=0;i<row;i++){
									String str="";
									for(int j=0;j<col;j++)
										str+=(gridBackup[i][j]?"X":"O");
									logger.logInfo(str);
								}
								int shiftDown=0;
								boolean exe=false;
								do{
									exe=true;
									//checking weather we can move the block down
									for(int i=row-1;i>=0 && exe;i--)
										for(int j=0;j<col && exe;j++)
											if(grid[i][j]){
												if(i==row-1)
													exe=false;
												//if the below block is occupied but it
												//is not a part of the grid
												if(i+1<row && isBlockOccupied(i+1,j) && !grid[i+1][j])
													exe=false;
											}
									//if exe is true means we will move the block down
									if(exe){
										shiftDown++;
										for(int i=row-1;i>=0;i--)
											for(int j=0;j<col;j++)
												if(grid[i][j]){
													grid[i][j]=false;
													grid[i+1][j]=true;
												}

									}
								}while(exe);
								//Shifting the Block down
								if(shiftDown!=0){
									logger.logInfo("shiftDown : "+shiftDown);
									for(int i=row-1;i>=0;i--)
										for(int j=0;j<col;j++)
											if(gridBackup[i][j]){
												final Rectangle rectangle=rectangles[i][j];
												final Rectangle newRectangle=
													new Rectangle(rectangle);
												newRectangle.setY(rectangle.getY()+rectangle.getHeight()*shiftDown);
												setBlockUnoccupied(i,j);
												setBlockOccupied(i+shiftDown,j,newRectangle);
											}

									logger.logInfo("after shifting block structure : ");
									printBlockStructure();
									//logger.logInfo("block colors");
									//logBlockColor();
								}
							}
							//if(isLeftBlock ends
						}
					}
				}
			}while(rowsMatched!=0);
			if(tot!=0)
				printBlockStructure();
			logger.logInfo("Exiting UpdateBlock with value : "+tot);
			return tot;
		}
		boolean [][] getConnectedBlocks(boolean [][] grid,final int r,final int c){
			if(grid==null){
				grid=new boolean[row][col];
				for(int i=0;i<row;i++)
					grid[i]=new boolean[col];
			}
			grid[r][c]=true;
			if(r+1<row && !grid[r+1][c] && isBlockOccupied(r+1,c))
				getConnectedBlocks(grid,r+1,c);
			if(r-1>=0 && !grid[r-1][c] && isBlockOccupied(r-1,c))
				getConnectedBlocks(grid,r-1,c);
			if(c+1<col && !grid[r][c+1] && isBlockOccupied(r,c+1))
				getConnectedBlocks(grid,r,c+1);
			if(c-1>=0 && !grid[r][c-1] && isBlockOccupied(r,c-1))
				getConnectedBlocks(grid,r,c-1);
			return grid;
		}
	}
	/////////////////////////
}
