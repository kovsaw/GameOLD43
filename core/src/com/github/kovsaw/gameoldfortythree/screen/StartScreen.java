package com.github.kovsaw.gameoldfortythree.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.kovsaw.gameoldfortythree.GameExtension;

public class StartScreen implements Screen {
    private final GameExtension gameObject;
    private OrthographicCamera camera;
    private Texture background;
    private FreeTypeFontGenerator generator;
    private Music mainThemeMusic;

    public StartScreen(GameExtension gameObject) {
        this.gameObject = gameObject;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Main_font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        gameObject.setBigFont(generator.generateFont(parameter));
        parameter.size = 100;
        gameObject.setSmallFont(generator.generateFont(parameter));

        mainThemeMusic = Gdx.audio.newMusic(Gdx.files.internal("Main_theme.mp3"));
        background = new Texture("Menu_bg.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void show() {
        mainThemeMusic.play();
        mainThemeMusic.setVolume(0.3f);
        mainThemeMusic.setLooping(true);

        Gdx.gl.glClearColor(0,0,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameObject.getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        camera.update();
        gameObject.getBatch().begin();
        gameObject.getBatch().draw(background, 0 ,0);

        gameObject.getBigFont().draw(
                gameObject.getBatch(),
                "SAVE AND LOVE AS \n    MANY SHEEP AS YOU CAN!",
                260,
                540
        );

        gameObject.getSmallFont().draw(gameObject.getBatch(), "Press SPACE to start!", 260, 780);
        gameObject.getBatch().end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameObject.setScreen(new GameScreen(gameObject));
        }
    }

    @Override
    public void dispose() {
        generator.dispose();
        background.dispose();
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
