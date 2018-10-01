package simulation;

import java.util.*;

public class AntForageAnt{

    private int rowIndex;
    private int colIndex;
    private int orientation;//0 = N,1 = NE,2 = E,3 = SE,4 = S,5 = SW,6 = W,7 = NW
    private boolean haveFood;
    private Random r;

    public AntForageAnt(int rowIndex, int colIndex){
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
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

    public int getrowIndex(){
        return rowIndex;
    }

    public int getcolIndex(){
        return colIndex;
    }

    public void setrowIndex(int x){
        rowIndex = x;
    }

    public void setcolIndex(int y){
        colIndex = y;
    }



}