package io.github.some_example_name.service;
import java.util.List;

import io.github.some_example_name.battleField.RectCoordinates;
import io.github.some_example_name.ships.Ship;
import io.github.some_example_name.struct.Pair;

public class MathServiceImpl implements MathService{
    @Override
    public boolean canDrawShip( RectCoordinates[][] allRectList, Pair startRectIndexes,Pair friendlyPair) {
            return checkNeiborsRects(allRectList,startRectIndexes);
    }
    public boolean checkNeiborsRects(RectCoordinates[][] allRectList,Pair startRectIndexes){
            if(startRectIndexes.getFirst()==0){
                return checkFirstRow(allRectList,startRectIndexes);
            }
            else if(startRectIndexes.getFirst()==9){
                return checkLastRow(allRectList,startRectIndexes);
            }
            else if(startRectIndexes.getSecond()==0){
                return checkFirstColumn(allRectList,startRectIndexes);
            }
            else if(startRectIndexes.getSecond()==9){
                return checkLastColumn(allRectList,startRectIndexes);
            }
            else{
                return checkCenterAria(allRectList,startRectIndexes);
            }
    }

    private boolean checkCenterAria( RectCoordinates[][] allRectList, Pair startRectIndexes) {
        if(allRectList[startRectIndexes.getFirst()+1][startRectIndexes.getSecond()+1].isUse()
            || allRectList[startRectIndexes.getFirst()+1][startRectIndexes.getSecond()].isUse()
            || allRectList[startRectIndexes.getFirst()+1][startRectIndexes.getSecond()-1].isUse()
            || allRectList[startRectIndexes.getFirst()-1][startRectIndexes.getSecond()+1].isUse()
            || allRectList[startRectIndexes.getFirst()-1][startRectIndexes.getSecond()].isUse()
            || allRectList[startRectIndexes.getFirst()-1][startRectIndexes.getSecond()-1].isUse()
            || allRectList[startRectIndexes.getFirst()][startRectIndexes.getSecond()+1].isUse()
            || allRectList[startRectIndexes.getFirst()][startRectIndexes.getSecond()-1].isUse()
            ){
            return false;
        }
        return true;
    }

    private boolean checkFirstColumn( RectCoordinates[][] allRectList, Pair startRectIndexes) {
        if(allRectList[startRectIndexes.getFirst()+1][0].isUse()
            || allRectList[startRectIndexes.getFirst()-1][0].isUse()
            || allRectList[startRectIndexes.getFirst()+1][1].isUse()
            || allRectList[startRectIndexes.getFirst()][1].isUse()
            || allRectList[startRectIndexes.getFirst()-1][1].isUse()){
            return false;
        }
        return true;
    }
    private boolean checkLastColumn(RectCoordinates[][] allRectList, Pair startRectIndexes) {
        if(allRectList[startRectIndexes.getFirst()+1][9].isUse()
            || allRectList[startRectIndexes.getFirst()-1][9].isUse()
            || allRectList[startRectIndexes.getFirst()+1][8].isUse()
            || allRectList[startRectIndexes.getFirst()][8].isUse()
            || allRectList[startRectIndexes.getFirst()-1][8].isUse()){
            return false;
        }
        return true;
    }

    public boolean checkFirstRow(RectCoordinates[][] allRectList,Pair startRectIndexes){
        if(startRectIndexes.getSecond()==0){
            if(allRectList[0][1].isUse() || allRectList[1][0].isUse() ||allRectList[1][1].isUse()){
                return false;
            }
        }
        else if(startRectIndexes.getSecond()==9){
            if(allRectList[0][8].isUse() || allRectList[8][8].isUse() ||allRectList[1][9].isUse()){
                return false;
            }
        }
        else{
            if(allRectList[0][startRectIndexes.getSecond()-1].isUse()
                || allRectList[0][startRectIndexes.getSecond()+1].isUse()
                || allRectList[1][startRectIndexes.getSecond()].isUse()
                || allRectList[1][startRectIndexes.getSecond()-1].isUse()
                || allRectList[1][startRectIndexes.getSecond()+1].isUse()){
                return false;
            }
        }
        return true;
    }
    public boolean checkLastRow(RectCoordinates[][] allRectList,Pair startRectIndexes){
        if(startRectIndexes.getSecond()==0){
            if(allRectList[9][1].isUse() || allRectList[8][0].isUse() ||allRectList[8][1].isUse()){
                return false;
            }
        }
        else if(startRectIndexes.getSecond()==9){
            if(allRectList[9][8].isUse() || allRectList[8][8].isUse() ||allRectList[8][9].isUse()){
                return false;
            }
        }
        else{
            if(allRectList[9][startRectIndexes.getSecond()-1].isUse()
                || allRectList[9][startRectIndexes.getSecond()+1].isUse()
                || allRectList[8][startRectIndexes.getSecond()].isUse()
                || allRectList[8][startRectIndexes.getSecond()-1].isUse()
                || allRectList[8][startRectIndexes.getSecond()+1].isUse()){
                return false;
            }
        }
        return true;
    }
}
