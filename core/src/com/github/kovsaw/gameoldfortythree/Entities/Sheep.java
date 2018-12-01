package com.github.kovsaw.gameoldfortythree.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Sheep extends Actor {
    private Sprite sprite;
//    private Rectangle bounds;
    private Polygon _bounds;

    public Polygon getBounds() {
        return _bounds;
    }

    public Sheep(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        setSize(sprite.getWidth(), sprite.getHeight());
        setBounds(x, y, getWidth(), getHeight());
//        bounds = new Rectangle(x, y, getWidth(), getHeight());
        _bounds = new Polygon(new float[] {
                x, y,
                x + getWidth(), y,
                x + getWidth(), y + getHeight(),
                x, y + getHeight()
        });

        _bounds.setPosition(getX(), getY());
        _bounds.setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                sprite,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation()
        );
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        _bounds.setRotation(degrees);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        _bounds.setPosition(x, y);
    }
}
