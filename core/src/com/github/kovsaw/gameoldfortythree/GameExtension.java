package com.github.kovsaw.gameoldfortythree;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kovsaw.gameoldfortythree.scene.StartScreen;

public class GameExtension extends com.badlogic.gdx.Game {
    private SpriteBatch batch;
    private BitmapFont bigFont, smallFont;

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getBigFont() {
        return bigFont;
    }

    public void setBigFont(BitmapFont bigFont) {
        this.bigFont = bigFont;
    }

    public BitmapFont getSmallFont() {
        return smallFont;
    }

    public void setSmallFont(BitmapFont smallFont) {
        this.smallFont = smallFont;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        bigFont = new BitmapFont();
        smallFont = new BitmapFont();
        this.setScreen(new StartScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bigFont.dispose();
        smallFont.dispose();
        super.dispose();
    }
}
