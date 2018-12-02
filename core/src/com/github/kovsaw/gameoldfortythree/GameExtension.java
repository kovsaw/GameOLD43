package com.github.kovsaw.gameoldfortythree;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kovsaw.gameoldfortythree.Scenes.MainMenuScreen;

public class GameExtension extends com.badlogic.gdx.Game {
    public SpriteBatch batch;
    public BitmapFont bigFont, smallFont, tinyFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bigFont = new BitmapFont();
        smallFont = new BitmapFont();
        tinyFont = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        bigFont.dispose();
    }
}
