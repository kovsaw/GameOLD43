package com.github.kovsaw.gameoldfortythree.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.github.kovsaw.gameoldfortythree.Entities.Hunter;
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
    private Texture hunterTexture;
    private Texture img;
    private OrthographicCamera camera;
    private Sheep sheep;
    private int sheepSpeed;
    private List<OtherSheep> listSheeps;
    private List<Hunter> listHunters;
    private ThreadLocalRandom random;
    private int countSheeps = 50;
    private int countHunters = 10;
    private List<Vector2> listDirections;
    private Map<Vector2, Float> angleForVector;
    private long frameCount;
    private boolean love = false;
    private int kostil = 20;
    private int resultCount = 0;

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

        random = ThreadLocalRandom.current();
    }

    public void moveRandomly(OtherSheep sheepToMove, Vector2 direction) {
//        while (direction.x == 0 && direction.y == 0) {
//            direction = new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1);
//        }
        try {
            sheepToMove.setRotation(angleForVector.get(direction));
        } catch (NullPointerException e) {
            System.out.println(direction.x + " " + direction.y);
        }

        sheepToMove.setPosition(sheepToMove.getX() + direction.x, sheepToMove.getY() + direction.y);
    }

    public void makeHunters() {
        for (int i = 0; i < countHunters; i++) {
            listHunters.add(new Hunter(new Sprite(new Texture("./core/assets/black-hunter.png")),
                    random.nextFloat() * 1720 + 110, 1070));
        }
    }
    @Override
    public void show() {
        listDirections = new ArrayList<Vector2>();
        listSheeps = new ArrayList<OtherSheep>();
        listHunters = new ArrayList<Hunter>();
        bg = new Texture("./core/assets/background.png");
        hunterTexture = new Texture("./core/assets/shooting-hunter.png");
        sheepSpeed = 10;
        batch = new SpriteBatch();
        sheep = new Sheep(new Sprite(new Texture(Gdx.files.internal("./core/assets/new-sheep.png"))), 960, 540);
        sheep.setOrigin(sheep.getWidth() / 2, sheep.getHeight() / 2);


        makeHunters();

        for (int i = 0; i < countSheeps; i++) {
            listSheeps.add(new OtherSheep(new Sprite(new Texture(Gdx.files.internal("./core/assets/new-sheep.png"))),
                    random.nextFloat() * 1720 + 100, random.nextFloat() * 880 + 100));
            listSheeps.get(i).setOrigin(listSheeps.get(i).getWidth() / 2, listSheeps.get(i).getHeight() / 2);
            listDirections.add(new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }


    private void handleInput() {
        if (love) {
            int newSheeps = random.nextInt(1, 2);
            countSheeps += newSheeps;
            System.out.println("Add " + countSheeps + " sheep!");
            listSheeps.add(new OtherSheep(new Sprite(new Texture(Gdx.files.internal("./core/assets/new-sheep.png"))),
                    random.nextFloat() * 1720 + 100, random.nextFloat() * 880 + 100));
            listSheeps.get(countSheeps - 1).setOrigin(listSheeps.get(countSheeps - 1).getWidth() / 2,
                    listSheeps.get(countSheeps - 1).getHeight() / 2);
            listDirections.add(new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
            love = false;
            resultCount += newSheeps;
        }
        for (int i = 0; i < countSheeps; i++) {
            if (Intersector.overlapConvexPolygons(sheep.getBounds(), listSheeps.get(i).getBounds())) {
//                listSheeps.get(i).setPosition(listSheeps.get(i).getX() - listDirections.get(i).x,
// listSheeps.get(i).getY() - listDirections.get(i).y);
                System.out.println("DA BLYAT");
                if (kostil > 15) {
                    love = true;
                    kostil = 0;
                }
                kostil += 1;
                return;
            }

            System.out.println("Our: " + sheep.getBounds().getX() + " " + sheep.getBounds().getY());
            System.out.println("Bot: " + listSheeps.get(i).getBounds().getX() + " " + listSheeps.get(i).getBounds().getY());
            System.out.println("Intersection: " + Intersector.overlapConvexPolygons(sheep.getBounds(),
                    listSheeps.get(i).getBounds()) + "\n\n");
        }

        for (int i = 0; i < countHunters; i++) {
            if (Intersector.overlapConvexPolygons(sheep.getBounds(), listHunters.get(i).getBounds())) {
//                game.setScreen(new OverGame(game));
                System.out.println("1: " + Intersector.overlapConvexPolygons(sheep.getBounds(),
                        listHunters.get(i).getBounds()) + "\n\n");

            }
        }

        if (sheep.getX() < 0) {
            sheep.setPosition(1920, sheep.getY());
        }

        if (sheep.getX() > 1920) {
            sheep.setPosition(0, sheep.getY());
        }

        if (sheep.getY() < 0) {
            sheep.setPosition(sheep.getX(), 1080);
        }

        if (sheep.getY() > 1080) {
            sheep.setPosition(sheep.getX(), 0);
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
            sheep.setPosition(sheep.getX(), 1080);
        }

        if (sheep.getY() > 1080) {
            sheep.setPosition(sheep.getX(), 0);
        }

        moveRandomly(sheep, direction);
    }

    private void randomAI(Hunter sheep) {

        if (sheep.getY() < 0) {
            listHunters.clear();
            makeHunters();
        }
        moveRandomly(sheep);
    }

    public void moveRandomly(Hunter sheepToMove) {
//        while (direction.x == 0 && direction.y == 0) {
//            direction = new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1);
//        }
        if (countSheeps >= 50) {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 12);
        } else if (countSheeps >= 40) {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 10);
        } else if (countSheeps > 30 ) {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 8);
        } else if (countSheeps > 20) {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 6);
        } else if (countSheeps > 10) {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 4);
        } else {
            sheepToMove.setPosition(sheepToMove.getX(), sheepToMove.getY() - 2);
        }
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 1, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        if (frameCount % 150 == 0 && frameCount != 0) {
            for (int i = 0; i < countSheeps; i++) {
                listDirections.set(i, new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
            }
            frameCount = 0;
        }
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < countSheeps; i++) {
            for (int j = 0; j < countHunters; j++) {
                try {
                    if (Intersector.overlapConvexPolygons(listSheeps.get(i).getBounds(), listHunters.get(j).getBounds())) {
                        countSheeps-=1;
                        listSheeps.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("FAIL with count sheep \n");
                }
            }
        }

        for (int i = 0; i < countSheeps; i++) {
            for (int j = 0; j < countSheeps; j++) {
                if (i != j && Intersector.overlapConvexPolygons(listSheeps.get(i).getBounds(), listSheeps.get(j).getBounds())) {
                    listSheeps.get(i).setPosition(listSheeps.get(i).getX() - listDirections.get(i).x,
                            listSheeps.get(i).getY() - listDirections.get(i).y);
                    listSheeps.get(j).setPosition(listSheeps.get(j).getX() - listDirections.get(j).x,
                            listSheeps.get(j).getY() - listDirections.get(j).y);

                    if (listDirections.get(i).x != 0) {
                        listDirections.get(i).x = -listDirections.get(i).x;
                    }

                    if (listDirections.get(i).y != 0) {
                        listDirections.get(i).y = -listDirections.get(i).y;
                    }

                    if (listDirections.get(j).x != 0) {
                        listDirections.get(j).x = -listDirections.get(j).x;
                    }

                    if (listDirections.get(j).y != 0) {
                        listDirections.get(j).y = -listDirections.get(j).y;
                    }
                }
            }

            randomAI(listSheeps.get(i), listDirections.get(i));

            listSheeps.get(i).draw(game.batch, 1);

            sr.setColor(0, 0, 1, 0.5f);
            sr.polygon(listSheeps.get(i).getBounds().getVertices());
        }
        for (int i = 0; i < countHunters; i++) {
            randomAI(listHunters.get(i));
            listHunters.get(i).draw(game.batch, 1);
        }
        frameCount++;
        sheep.draw(game.batch, 1);
        sheep.setZIndex(0);
        game.font.draw(game.batch, "Sheep amount: " + countSheeps, 100, 100);
        game.font.draw(game.batch, "Spawn sheep amount: " + resultCount, 900, 100);
        game.batch.end();
        sr.end();
        handleInput();
//        game.game.batch.font.draw(game.game.batch, "Count sheep: " + countSheeps, 100, 100);
//        game.game.batch.draw();
        if (countSheeps == 0) {
            game.setScreen(new OverGame(game));
        }


//        if (Gdx.input.isTouched()) {
//            game.setScreen(new OverGame(game));
//        }
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
        game.batch.dispose();
        img.dispose();
    }
}
