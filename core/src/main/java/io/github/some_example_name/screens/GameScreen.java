package io.github.some_example_name.screens;

import static io.github.some_example_name.keys.KeyStorage.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.some_example_name.battleField.BattleField;
import io.github.some_example_name.battleField.RectCoordinates;
import io.github.some_example_name.service.MathService;
import io.github.some_example_name.ships.Ship;

public class GameScreen extends ScreenAdapter {
    private TextButton autoLocateShipsButton;
    private TextButton deleteMaskButton;
    private Stage stage;
    private BattleField battleField;
    private ShaderProgram shaderCircle;
    //private Texture mask;
    private Texture rectangle;
    private Texture verticalLine;
    private Texture horizontalLine;
    private SpriteBatch spriteBatch;
    private SpriteBatch sheaderBatch;
    private FrameBuffer frameBuffer;
    private OrthographicCamera camera;
    private BitmapFont bitmapFont;
    private boolean resetShipsFlag=false;
    private boolean needToSetMask=false;
    private float circleCenterX=0.5f;
    private float circleCenterY=0.5f;
    private float circleRadius=0.1f;
    private void initialize() {
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        sheaderBatch=new SpriteBatch();
        try {
            shaderCircle =new ShaderProgram(Gdx.files.internal(WAY_TO_CIRCLE_VERT),Gdx.files.internal(WAY_TO_CIRCLE_FRAG));
            if (!shaderCircle.isCompiled()) {
                throw new RuntimeException("Shader compilation failed: " + shaderCircle.getLog());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public GameScreen(MathService mathService) {
        super();
        initFont();
        spriteBatch=new SpriteBatch();
        initCamera();
        initBattleField();
        initStage();
        initialize();
        initRectangle();
        horizontalLine=createLineTexture(800,1);
        verticalLine=createLineTexture(1,800);
    }



    private void initRectangle() {
        Pixmap pixmap = new Pixmap((int)battleField.getCellSize(),(int)battleField.getCellSize(), Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();
        rectangle = new Texture(pixmap);
        pixmap.dispose();
    }
    private Texture createLineTexture(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private void initStage() {
        stage=new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    needToSetMask = true;
                    circleCenterX = (float) screenX / Gdx.graphics.getWidth();
                    circleCenterY = 1.0f - (float) screenY / Gdx.graphics.getHeight();
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
        Pixmap pixmapUp=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapUp.setColor(Color.BLUE);
        pixmapUp.fill();
        TextureRegion textureRegionUp=new TextureRegion();
        textureRegionUp.setTexture(new Texture(pixmapUp));

        Pixmap pixmapDown=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapDown.setColor(Color.DARK_GRAY);
        pixmapDown.fill();
        TextureRegion textureRegionDown=new TextureRegion();
        textureRegionDown.setTexture(new Texture(pixmapDown));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(textureRegionUp);
        textButtonStyle.down = new TextureRegionDrawable(textureRegionDown);

        textButtonStyle.font=bitmapFont;
        textButtonStyle.fontColor=Color.WHITE;
        autoLocateShipsButton=initButtonAuto();
        deleteMaskButton=initButtonDeleteMask();
        stage.addActor(autoLocateShipsButton);
        stage.addActor(deleteMaskButton);
    }

    private TextButton initButtonDeleteMask() {
        Pixmap pixmapUp=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapUp.setColor(Color.BLUE);
        pixmapUp.fill();
        TextureRegion textureRegionUp=new TextureRegion();
        textureRegionUp.setTexture(new Texture(pixmapUp));

        Pixmap pixmapDown=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapDown.setColor(Color.DARK_GRAY);
        pixmapDown.fill();
        TextureRegion textureRegionDown=new TextureRegion();
        textureRegionDown.setTexture(new Texture(pixmapDown));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(textureRegionUp);
        textButtonStyle.down = new TextureRegionDrawable(textureRegionDown);

        textButtonStyle.font=bitmapFont;
        textButtonStyle.fontColor=Color.WHITE;
        deleteMaskButton=new TextButton(CLEAR_BUTTON_NAME,textButtonStyle);
        deleteMaskButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                needToSetMask=false;
            }
        });
        deleteMaskButton.setPosition(1000,700);
        deleteMaskButton.setSize(300,200);
        deleteMaskButton.setVisible(true);
        return deleteMaskButton;
    }

    private TextButton initButtonAuto() {
        Pixmap pixmapUp=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapUp.setColor(Color.BLUE);
        pixmapUp.fill();
        TextureRegion textureRegionUp=new TextureRegion();
        textureRegionUp.setTexture(new Texture(pixmapUp));

        Pixmap pixmapDown=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmapDown.setColor(Color.DARK_GRAY);
        pixmapDown.fill();
        TextureRegion textureRegionDown=new TextureRegion();
        textureRegionDown.setTexture(new Texture(pixmapDown));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(textureRegionUp);
        textButtonStyle.down = new TextureRegionDrawable(textureRegionDown);

        textButtonStyle.font=bitmapFont;
        textButtonStyle.fontColor=Color.WHITE;
        autoLocateShipsButton=new TextButton(AUTO_BUTTON_NAME,textButtonStyle);
        autoLocateShipsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                resetShipsFlag=true;
            }
        });
        autoLocateShipsButton.setPosition(1000,400);
        autoLocateShipsButton.setSize(300,200);
        autoLocateShipsButton.setVisible(true);
        return autoLocateShipsButton;
    }

    private void initBattleField() {
        battleField=new BattleField();
        battleField.setCellSize(80);
        battleField.setGridSize(10);
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    private void initFont() {
        bitmapFont=new BitmapFont();
        bitmapFont.getData().setScale(4);
        bitmapFont.setColor(Color.BLUE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        try {
        frameBuffer.begin();
        renderMainPart();
        frameBuffer.end();
        sheaderBatch.begin();
        if(needToSetMask) {
            initShader();
        }
        sheaderBatch.draw(frameBuffer.getColorBufferTexture(),0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0, 0, 1, 1);
        sheaderBatch.end();
        sheaderBatch.setShader(null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initShader() {
        sheaderBatch.setShader(shaderCircle);
        shaderCircle.setUniformi(TEXTURE_ATTRIBUTE_NAME, 0);
        frameBuffer.getColorBufferTexture().bind(0);
        shaderCircle.setUniformf(CIRCLE_CENTER_ATTRIBUTE_NAME, circleCenterX, circleCenterY);
        shaderCircle.setUniformf(CIRCLE_RADIUS_ATTRIBUTE_NAME, circleRadius);
    }

    private void renderMainPart() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        spriteBatch.begin();
        drawBattlefield();
        if (resetShipsFlag) {
            battleField.generateShips();
            resetShipsFlag = false;
        }
        redrawShips();
        drawGameCoordinates();
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void redrawShips() {
        List<Ship>shipList=battleField.getListShips();
        for(int i=0;i<shipList.size();i++){
            for(RectCoordinates currentRect:shipList.get(i).getShipsRect()) {
                int currentIndexX = currentRect.getxCoordinate();
                int currentIndexY = currentRect.getyCoordinate();
                battleField.getAllRectList()[currentIndexX][currentIndexY].setUse(true);
                spriteBatch.draw(rectangle,battleField.getAllRectList()[currentIndexX][currentIndexY].getxCoordinate()
                    , battleField.getAllRectList()[currentIndexX][currentIndexY].getyCoordinate());
            }
        }
    }



    private void drawGameCoordinates() {
        battleField.drawBattleCoordinates(bitmapFont,spriteBatch);
    }


    private void drawBattlefield() {
        battleField.drawBattleField(spriteBatch,verticalLine,horizontalLine);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shaderCircle.dispose();
        stage.dispose();
        sheaderBatch.dispose();
    }
}
