package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.GameExtension;

public class GameOverScreen implements Screen {
    private final GameExtension game;
    private OrthographicCamera camera;
    private Texture deadSheep;
    private FreeTypeFontGenerator generator;

    public GameOverScreen(GameExtension game) {
        this.game = game;
        deadSheep = new Texture("./core/assets/Dead_sheep.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("./core/assets/Main_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        game.bigFont = generator.generateFont(parameter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.bigFont.draw(game.batch, "All sheep died!" , 260, 540);
        game.batch.draw(deadSheep, 1000, 200);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void show() {
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
        generator.dispose();
    }
}
