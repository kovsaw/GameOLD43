package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.MainGame;
import com.github.kovsaw.gameoldfortythree.Scenes.GameScreen;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private OrthographicCamera camera;
    private Texture bg;
    private FileHandle fontFile;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        fontFile = Gdx.files.internal("./core/assets/graf.ttf");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }
    @Override
    public void show() {
        bg = new Texture("./core/assets/bg.jpg");
        System.out.println("SHOW-TEST");
        Music music = Gdx.audio.newMusic(Gdx.files.internal("./core/assets/MainMenu.mp3"));
        music.play();
        music.setVolume(0.5f);
        music.setLooping(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(bg, 0 ,0);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        game.font = generator.generateFont(parameter);
        game.font.draw(game.batch, "SURVIVE!", 260, 540);

        parameter.size = 100;
        game.font = generator.generateFont(parameter);
        game.font.draw(game.batch, "Press MOUSE CLICK to start!", 260, 780);

        generator.dispose();


        game.batch.end();
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
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
