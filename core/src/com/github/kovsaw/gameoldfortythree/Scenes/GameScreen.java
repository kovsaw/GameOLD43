package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.github.kovsaw.gameoldfortythree.Entities.OtherSheep;
import com.github.kovsaw.gameoldfortythree.Entities.Sheep;
import com.github.kovsaw.gameoldfortythree.MainGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private Texture bg;
    private Texture img;
    private OrthographicCamera camera;
    private Sheep sheep;
    private int sheepSpeed;
    private List<OtherSheep> listSheeps;
    private ThreadLocalRandom random;
    private int countSheeps = 3;
    private Vector2[] directions;
    private Map<Vector2, Float> angleForVector;
    private long frameCount;
    ShapeRenderer sr = new ShapeRenderer();

    public GameScreen(final MainGame game) {
        this.game = game;
        frameCount = 0;
        angleForVector = new HashMap<Vector2, Float>();
        angleForVector.put(new Vector2(0, 1), 0f);
        angleForVector.put(new Vector2(1, 0), 90f);
        angleForVector.put(new Vector2(0, -1), 180f);
        angleForVector.put(new Vector2(-1, 0), 270f);
        angleForVector.put(new Vector2(-1, -1), 135f);
        angleForVector.put(new Vector2(1, 1), 315f);
        angleForVector.put(new Vector2(-1, 1), 45f);
        angleForVector.put(new Vector2(1, -1), 225f);

        directions = new Vector2[countSheeps];
        random = ThreadLocalRandom.current();

        for (int i = 0; i < countSheeps; i++) {
            directions[i] = new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1);
        }

    }

    public void moveRandomly(OtherSheep sheepToMove, Vector2 direction) {
        while (direction.x == 0 && direction.y == 0) {
            direction = new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1);
        }

        try {
            sheepToMove.setRotation(angleForVector.get(direction));
        } catch (NullPointerException e) {
            System.out.println(direction.x + " " + direction.y);
        }

        sheepToMove.setPosition(sheepToMove.getX() + direction.x, sheepToMove.getY() + direction.y);
    }

    @Override
    public void show() {
        listSheeps = new ArrayList<OtherSheep>();
        bg = new Texture("./core/assets/background.png");
        sheepSpeed = 10;
        batch = new SpriteBatch();
        sheep = new Sheep(new Sprite(new Texture(Gdx.files.internal("./core/assets/new-sheep.png"))), 960, 540);

        sheep.setOrigin(sheep.getWidth() / 2, sheep.getHeight() / 2);

        for (int i = 0; i < countSheeps; i++) {
            listSheeps.add(new OtherSheep(new Sprite(new Texture(Gdx.files.internal("./core/assets/new-sheep.png"))),
                    random.nextFloat() * 1720 + 100, random.nextFloat() * 880 + 100));
            listSheeps.get(i).setOrigin(listSheeps.get(i).getWidth() / 2, listSheeps.get(i).getHeight() / 2);
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }


    private void handleInput() {
        for (int i = 0; i < countSheeps; i++) {
            if (Intersector.overlapConvexPolygons(sheep.getBounds(), listSheeps.get(i).getBounds())) {
//                listSheeps.get(i).setPosition(listSheeps.get(i).getX() - directions[i].x, listSheeps.get(i).getY() - directions[i].y);
                System.out.println("DA BLYAT");
                return;
            }

            System.out.println("Our: " + sheep.getBounds().getX() + " " + sheep.getBounds().getY());
            System.out.println("Bot: " + listSheeps.get(i).getBounds().getX() + " " + listSheeps.get(i).getBounds().getY());
            System.out.println("Intersection: " + Intersector.overlapConvexPolygons(sheep.getBounds(), listSheeps.get(i).getBounds()) + "\n\n");
        }

        if (sheep.getX() < 0) {
            sheep.setPosition(1920, sheep.getY());
        }

        if (sheep.getX() > 1920) {
            sheep.setPosition(0, sheep.getY());
        }

        if (sheep.getY() < 0) {
            sheep.setPosition(sheep.getX(),1080);
        }

        if (sheep.getY() > 1080) {
            sheep.setPosition(sheep.getX(),0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sheep.setPosition(sheep.getX() + sheepSpeed, sheep.getY() + sheepSpeed);
            sheep.setRotation(315);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sheep.setPosition(sheep.getX() + sheepSpeed, sheep.getY() - sheepSpeed);
            sheep.setRotation(225);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sheep.setPosition(sheep.getX() - sheepSpeed, sheep.getY() + sheepSpeed);
            sheep.setRotation(45);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sheep.setPosition(sheep.getX() - sheepSpeed, sheep.getY() - sheepSpeed);
            sheep.setRotation(135);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sheep.setPosition(sheep.getX(), sheep.getY() + sheepSpeed);
            sheep.setRotation(0);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sheep.setPosition(sheep.getX(), sheep.getY() - sheepSpeed);
            sheep.setRotation(180);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sheep.setPosition(sheep.getX() - sheepSpeed, sheep.getY());
            sheep.setRotation(90);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sheep.setPosition(sheep.getX() + sheepSpeed, sheep.getY());
            sheep.setRotation(270);
            return;
        }
    }

    private void randomAI(OtherSheep sheep, Vector2 direction) {
        if (sheep.getX() < 0) {
            sheep.setPosition(1920, sheep.getY());
        }

        if (sheep.getX() > 1920) {
            sheep.setPosition(0, sheep.getY());
        }

        if (sheep.getY() < 0) {
            sheep.setPosition(sheep.getX(),1080);
        }

        if (sheep.getY() > 1080) {
            sheep.setPosition(sheep.getX(),0);
        }

        moveRandomly(sheep, direction);
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 1, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0, 0);

        if (frameCount % 150 == 0 && frameCount != 0) {
            for (int i = 0; i < countSheeps; i++) {
                directions[i] = new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1);
            }
            frameCount = 0;
        }
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0; i < countSheeps; i++) {
            for (int j = 0; j <countSheeps; j++) {
                if (i != j && Intersector.overlapConvexPolygons(listSheeps.get(i).getBounds(), listSheeps.get(j).getBounds())) {
                    listSheeps.get(i).setPosition(listSheeps.get(i).getX() - directions[i].x, listSheeps.get(i).getY() - directions[i].y);
                    listSheeps.get(j).setPosition(listSheeps.get(j).getX() - directions[j].x, listSheeps.get(j).getY() - directions[j].y);

                    if (directions[i].x != 0) {
                        directions[i].x = -directions[i].x;
                    }

                    if (directions[i].y != 0) {
                        directions[i].y = -directions[i].y;
                    }

                    if (directions[j].x != 0) {
                        directions[j].x = -directions[j].x;
                    }

                    if (directions[j].y != 0) {
                        directions[j].y = -directions[j].y;
                    }
                }
//                if (listSheeps.get(i).getBounds().overlaps(listSheeps.get(j).getBounds())) {
//                    listSheeps.get(i).setPosition(listSheeps.get(i).getX() - directions[i].x, listSheeps.get(i).getY() - directions[i].y);
//                }
//                if (listSheeps.get(i).getBounds().overlaps(sheep.getBounds())) {
//                    listSheeps.get(i).setPosition(listSheeps.get(i).getX() - directions[i].x, listSheeps.get(i).getY() - directions[i].y);
//                }
            }

//            randomAI(listSheeps.get(i), directions[i]);
            listSheeps.get(i).draw(batch, 1);
            sr.setColor(0, 0, 1, 0.5f);
            sr.polygon(listSheeps.get(i).getBounds().getVertices());

        }

        frameCount++;
        sheep.draw(batch, 1);
        sheep.setZIndex(0);
        batch.end();
        sr.end();
        handleInput();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
//        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
