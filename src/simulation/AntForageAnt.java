package simulation;

import java.util.*;

public class AntForageAnt{

    private int lifeCount;
    private int xIndex;
    private int yIndex;
    private int orientation;//0 = N,1 = NE,2 = E,3 = SE,4 = S,5 = SW,6 = W,7 = NW
    private boolean haveFood;
    private Random r;

    public AntForageAnt(int xIndex, int yIndex, int lifeCount){
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.lifeCount = lifeCount;
        this.orientation = r.nextInt(8);//get 1 of the 8 random orientation;
        this.haveFood = false;
    }

    public int getOrientation(){
        return orientation;
    }

    public void setOrientation(int i){
        orientation = i;
    }

    public boolean getHaveFood(){
        return haveFood;
    }

    public void changeHaveFood(){
        haveFood = !haveFood;
    }

    public int getxIndex(){
        return xIndex;
    }

    public int getyIndex(){
        return yIndex;
    }

    public void setxIndex(int x){
        xIndex = x;
    }

    public void setyIndex(int y){
        yIndex = y;
    }



}