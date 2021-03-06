package com.github.kovsaw.gameoldfortythree.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.GameExtension;

public class GameOverScreen implements Screen {
    private final GameExtension gameObject;
    private OrthographicCamera camera;
    private Texture deadSheep;
    private FreeTypeFontGenerator generator;

    public GameOverScreen(GameExtension gameObject) {
        this.gameObject = gameObject;
        deadSheep = new Texture("Dead_sheep.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Main_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        gameObject.setBigFont(generator.generateFont(parameter));
    }

    @Override
    public void render(float delta) {
        camera.update();
        gameObject.getBatch().begin();
        gameObject.getBigFont().draw(gameObject.getBatch(), "All sheep died!" , 260, 540);
        gameObject.getBatch().draw(deadSheep, 1000, 200);
        gameObject.getBatch().end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            gameObject.setScreen(new StartScreen(gameObject));
        }
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameObject.getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        generator.dispose();
        deadSheep.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
