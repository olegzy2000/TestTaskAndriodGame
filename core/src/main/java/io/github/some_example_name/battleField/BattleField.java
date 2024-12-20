package io.github.some_example_name.battleField;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;
import java.util.Arrays;
import java.util.List;

import io.github.some_example_name.service.MathServiceImpl;
import io.github.some_example_name.service.ShipsGeneratorService;
import io.github.some_example_name.service.ShipsGeneratorServiceImpl;
import io.github.some_example_name.ships.Ship;

public class BattleField {
    private ShipsGeneratorService shipsGeneratorService;
    private int gridSize = 10;
    private int cellSize = 80;
    private List<String> listCharacter =Arrays.asList("A","B","C","D","E","F","G","H","I","J");
    private List<Ship> listShips;
    private RectCoordinates allRectList[][];
    public BattleField(){
        shipsGeneratorService=new ShipsGeneratorServiceImpl(new MathServiceImpl());
        initAllRectList();
        while (listShips==null) {
            clearAllRectList();
            listShips = shipsGeneratorService.generateRandomShip(allRectList);
        }
    }
    public void generateShips(){
        listShips.clear();
        listShips=null;
        while (listShips==null) {
            clearAllRectList();
            this.listShips = shipsGeneratorService.generateRandomShip(allRectList);
        }
    }
    public void clearAllRectList(){
        for(int i=0;i<allRectList.length;i++){
            for(int j=0;j<allRectList[i].length;j++){
                allRectList[i][j].setUse(false);
            }
        }
    }
    public List<String> getListCharacter() {
        return listCharacter;
    }

    public void setListCharacter(List<String> listCharacter) {
        this.listCharacter = listCharacter;
    }

    public List<Ship> getListShips() {
        return listShips;
    }

    public void setListShips(List<Ship> listShips) {
        this.listShips = listShips;
    }

    public RectCoordinates[][] getAllRectList() {
        return allRectList;
    }

    public void setAllRectList(RectCoordinates[][] allRectList) {
        this.allRectList = allRectList;
    }

    private void initAllRectList() {
        allRectList=new RectCoordinates[gridSize][gridSize];
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++) {
                RectCoordinates rectCoordinates = new RectCoordinates();
                rectCoordinates.setxCoordinate(cellSize+cellSize*j);
                rectCoordinates.setyCoordinate(cellSize*i);
                allRectList[i][j]=rectCoordinates;
            }
        }
    }

    public float getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
    public void drawBattleCoordinates(BitmapFont bitmapFont, SpriteBatch spriteBatch){
        drawNumberField(bitmapFont,spriteBatch);
        drawSymbolsField(bitmapFont,spriteBatch);
    }
    private void drawSymbolsField(BitmapFont bitmapFont, SpriteBatch spriteBatch) {
        int yOffset=10;
        int beginYCoordinate=10*(int)cellSize-yOffset;
        int xCoordinate=10;
        for(String current:listCharacter){
            bitmapFont.draw(spriteBatch,current,xCoordinate,beginYCoordinate);
            beginYCoordinate-=(int)cellSize;
        }
    }

    private void drawNumberField(BitmapFont bitmapFont,SpriteBatch spriteBatch) {
        String numberField=initNumberField();
        int yOffset=60;
        int yCoordinateForNumberField=10*((int)cellSize)+yOffset;
        bitmapFont.draw(spriteBatch,numberField,100,yCoordinateForNumberField);
    }
    private String initNumberField() {
        com.badlogic.gdx.utils.StringBuilder result=new StringBuilder();
        for(int i=0;i<10;i++){
            result.append(i+1).append("   ");
        }
        return result.toString();
    }

    public void drawBattleField(SpriteBatch spriteBatch, Texture verticalLine,Texture horizontalLine) {
        Color color=new Color();
        color.set(0, 0, 255,1);
        for (int i = 0; i <= gridSize; i++) {
            spriteBatch.draw(verticalLine,80+i * cellSize,  0);
            spriteBatch.draw(horizontalLine,80,  i * cellSize);
        }
    }
}
