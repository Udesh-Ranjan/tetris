package tetris.util;

import javafx.stage.Screen;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

public class EnvironmentConstants{
	public static final double SCREEN_WIDTH=Screen.getPrimary().getBounds().getWidth();
	public static final double SCREEN_HEIGHT=Screen.getPrimary().getBounds().getHeight();
	public static final byte HORIZONTAL_BLOCKS=16;
	public static final byte VERTICAL_BLOCKS=14;
	public static final int LEFT_MIN_SIZE_PIXELS=250; 
	public static final byte  RIGHT_MIN_SIZE_BLOCKS=2;

	public static List<Integer> computeEnvironSize(){
		int pixelsPerBlock=50;
		int pixels=EnvironmentConstants.LEFT_MIN_SIZE_PIXELS+EnvironmentConstants.HORIZONTAL_BLOCKS*pixelsPerBlock+
			EnvironmentConstants.RIGHT_MIN_SIZE_BLOCKS*pixelsPerBlock;
		if(pixels > EnvironmentConstants.SCREEN_WIDTH || 
				pixelsPerBlock*EnvironmentConstants.VERTICAL_BLOCKS > EnvironmentConstants.SCREEN_HEIGHT){
			do{
				pixelsPerBlock--;
				if(pixelsPerBlock==0){
					System.out.println("screen size is less to render the game");
					System.exit(0);
				}
				pixels=EnvironmentConstants.LEFT_MIN_SIZE_PIXELS+EnvironmentConstants.HORIZONTAL_BLOCKS*pixelsPerBlock+
					EnvironmentConstants.RIGHT_MIN_SIZE_BLOCKS*pixelsPerBlock;
			}while(pixels>EnvironmentConstants.SCREEN_WIDTH || 
					pixelsPerBlock*EnvironmentConstants.VERTICAL_BLOCKS>EnvironmentConstants.SCREEN_HEIGHT);
		}else{
		}
		int pixelsLeft=(int)EnvironmentConstants.SCREEN_WIDTH-
			(pixelsPerBlock*EnvironmentConstants.HORIZONTAL_BLOCKS+EnvironmentConstants.LEFT_MIN_SIZE_PIXELS+pixelsPerBlock*EnvironmentConstants.RIGHT_MIN_SIZE_BLOCKS);
		int leftSizePixels=EnvironmentConstants.LEFT_MIN_SIZE_PIXELS+pixelsLeft/2;
		int centerSizePixels=pixelsPerBlock*EnvironmentConstants.HORIZONTAL_BLOCKS;	
		int rightSizePixels=(int)EnvironmentConstants.SCREEN_WIDTH-(leftSizePixels+centerSizePixels);
		return Stream.of(leftSizePixels,centerSizePixels,rightSizePixels)
			.collect(Collectors.toList());
	}
}
