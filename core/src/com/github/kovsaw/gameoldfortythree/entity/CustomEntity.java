package com.github.kovsaw.gameoldfortythree.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CustomEntity extends Actor {
    private Sprite sprite;
    private Polygon bounds;

    public Polygon getBounds() {
        return bounds;
    }

    public CustomEntity(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        setSize(sprite.getWidth(), sprite.getHeight());
        setBounds(x, y, getWidth(), getHeight());

        setPosition(x, y);
        bounds.setOrigin(getWidth() / 2, getHeight() / 2);
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
        bounds.setRotation(degrees);
    }

    @Override
    public void setPosition(float x, float y) {
        bounds = new Polygon(new float[] {
                x, y,
                x + getWidth(), y,
                x + getWidth(), y + getHeight(),
                x, y + getHeight()
        });

        super.setPosition(x, y);
        bounds.setPosition(x, y);
    }
}
