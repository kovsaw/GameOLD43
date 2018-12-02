package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.GameExtension;

public class MainMenuScreen implements Screen {
    private final GameExtension game;
    private OrthographicCamera camera;
    private Texture bg;
    private FreeTypeFontGenerator generator;
    private Music music;


    public MainMenuScreen(GameExtension game) {
        this.game = game;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("./core/assets/Main_font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        game.bigFont = generator.generateFont(parameter);
        parameter.size = 100;
        game.smallFont = generator.generateFont(parameter);

        music = Gdx.audio.newMusic(Gdx.files.internal("./core/assets/Main_theme.mp3"));
        bg = new Texture("./core/assets/Menu_bg.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void show() {
        music.play();
        music.setVolume(0.3f);
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
        game.bigFont.draw(game.batch, "SAVE AND LOVE AS \n MANY SHEEP AS YOU CAN!", 260, 540);
        game.smallFont.draw(game.batch, "Press SPACE to start!", 260, 780);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
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
        generator.dispose();
    }
}
