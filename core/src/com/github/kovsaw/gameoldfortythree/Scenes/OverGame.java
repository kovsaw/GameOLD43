package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.MainGame;
import javafx.scene.Scene;

public class OverGame implements Screen {
    private final MainGame game;
    private OrthographicCamera camera;
    private FileHandle fontFile;
    private Texture deadSheep;

    public OverGame(MainGame game) {
        this.game = game;
        fontFile = Gdx.files.internal("./core/assets/graf.ttf");
        deadSheep = new Texture("./core/assets/dead-sheep.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        game.font = generator.generateFont(parameter);
        game.font.draw(game.batch, "All sheep died!" , 260, 540);
        game.batch.draw(deadSheep, 1000, 200);
        parameter.size = 30;
        game.font = generator.generateFont(parameter);
        generator.dispose();

        game.batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new MainMenuScreen(game));
        }
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

    @Override
    public void dispose() {

    }
}
