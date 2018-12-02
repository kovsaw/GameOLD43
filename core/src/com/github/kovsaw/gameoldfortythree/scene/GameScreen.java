package com.github.kovsaw.gameoldfortythree.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.github.kovsaw.gameoldfortythree.entity.CustomEntity;
import com.github.kovsaw.gameoldfortythree.GameExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen implements Screen {
    private final GameExtension gameObject;
    private Texture background;
    private OrthographicCamera camera;
    private CustomEntity playerSheep;
    private int sheepSpeed;
    private List<CustomEntity> otherSheepList;
    private List<CustomEntity> hunterList;
    private ThreadLocalRandom random;
    private int sheepCount;
    private int hunterCount;
    private List<Vector2> sheepMovingDirectionsList;
    private Map<Vector2, Float> rotationAngleForDirectionVector;
    private long frameCount;
    private boolean love;
    private int hack;
    private int resultCount;
    private Music sheepDeathSound;
    private Music sheepSound;

    GameScreen(final GameExtension gameObject) {
        sheepDeathSound = Gdx.audio.newMusic(Gdx.files.internal("./core/assets/Sheep_death_sound.mp3"));
        sheepSound = Gdx.audio.newMusic(Gdx.files.internal("./core/assets/Sheep_sound.mp3"));
        sheepDeathSound.setVolume(0.5f);
        sheepSound.setVolume(0.3f);

        this.gameObject = gameObject;
        frameCount = 0;
        random = ThreadLocalRandom.current();
        love = false;
        sheepCount = 50;
        hunterCount = 10;
        hack = 20;
        resultCount = 0;
        sheepSpeed = 5;

        background = new Texture("./core/assets/Gameplay_bg.png");
        playerSheep = new CustomEntity(new Sprite(new Texture(Gdx.files.internal("./core/assets/Player_sheep.png"))), 960, 540);
        playerSheep.setOrigin(playerSheep.getWidth() / 2, playerSheep.getHeight() / 2);

        rotationAngleForDirectionVector = new ConcurrentHashMap<>();
        rotationAngleForDirectionVector.put(new Vector2(0, 1), 0f);
        rotationAngleForDirectionVector.put(new Vector2(1, 0), 270f);
        rotationAngleForDirectionVector.put(new Vector2(0, -1), 180f);
        rotationAngleForDirectionVector.put(new Vector2(-1, 0), 90f);
        rotationAngleForDirectionVector.put(new Vector2(-1, -1), 135f);
        rotationAngleForDirectionVector.put(new Vector2(1, 1), 315f);
        rotationAngleForDirectionVector.put(new Vector2(-1, 1), 45f);
        rotationAngleForDirectionVector.put(new Vector2(1, -1), 225f);

        otherSheepList = new ArrayList<>();
        sheepMovingDirectionsList = new ArrayList<>();
        for (int i = 0; i < sheepCount; i++) {
            otherSheepList.add(new CustomEntity(new Sprite(new Texture(Gdx.files.internal("./core/assets/Simple_sheep.png"))),
                    random.nextFloat() * 1720 + 100, random.nextFloat() * 880 + 100));
            otherSheepList.get(i).setOrigin(otherSheepList.get(i).getWidth() / 2, otherSheepList.get(i).getHeight() / 2);
            sheepMovingDirectionsList.add(new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
        }

        hunterList = new ArrayList<>();
        createHunters();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    private void createHunters() {
        for (int i = 0; i < hunterCount; i++) {
            hunterList.add(new CustomEntity(new Sprite(new Texture("./core/assets/Hunter_with_pike.png")),
                    random.nextFloat() * 1720 + 110, 1070));
        }
    }

    private void handleInput() {
        if (love) {
            int newSheeps = random.nextInt(1, 2);
            sheepCount += newSheeps;
            otherSheepList.add(new CustomEntity(new Sprite(new Texture(Gdx.files.internal("./core/assets/Simple_sheep.png"))),
                    random.nextFloat() * 1720 + 100, random.nextFloat() * 880 + 100));
            otherSheepList.get(sheepCount - 1).setOrigin(otherSheepList.get(sheepCount - 1).getWidth() / 2,
                    otherSheepList.get(sheepCount - 1).getHeight() / 2);
            sheepMovingDirectionsList.add(new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
            love = false;
            resultCount += newSheeps;
            sheepSound.play();
        }

        for (int i = 0; i < sheepCount; i++) {
            if (Intersector.overlapConvexPolygons(playerSheep.getBounds(), otherSheepList.get(i).getBounds())) {
//                otherSheepList.get(i).setPosition(otherSheepList.get(i).getX() - sheepMovingDirectionsList.get(i).x,
//                otherSheepList.get(i).getY() - sheepMovingDirectionsList.get(i).y);

                if (hack > 15) {
                    love = true;
                    hack = 0;
                }

                hack += 1;
                return;
            }
        }

//        for (int i = 0; i < hunterCount; i++) {
//            if (Intersector.overlapConvexPolygons(playerSheep.getBounds(), hunterList.get(i).getBounds())) {
//                gameObject.setScreen(new GameOverScreen(gameObject));
//            }
//        }

        handleScreenEdgeIntersection(playerSheep);

        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) &&
                (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))) {
            playerSheep.setPosition(playerSheep.getX() + sheepSpeed, playerSheep.getY() + sheepSpeed);
            playerSheep.setRotation(315);
            return;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                && (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))) {
            playerSheep.setPosition(playerSheep.getX() + sheepSpeed, playerSheep.getY() - sheepSpeed);
            playerSheep.setRotation(225);
            return;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) &&
                (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))) {
            playerSheep.setPosition(playerSheep.getX() - sheepSpeed, playerSheep.getY() + sheepSpeed);
            playerSheep.setRotation(45);
            return;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                && (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))) {
            playerSheep.setPosition(playerSheep.getX() - sheepSpeed, playerSheep.getY() - sheepSpeed);
            playerSheep.setRotation(135);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSheep.setPosition(playerSheep.getX(), playerSheep.getY() + sheepSpeed);
            playerSheep.setRotation(0);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSheep.setPosition(playerSheep.getX(), playerSheep.getY() - sheepSpeed);
            playerSheep.setRotation(180);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSheep.setPosition(playerSheep.getX() - sheepSpeed, playerSheep.getY());
            playerSheep.setRotation(90);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSheep.setPosition(playerSheep.getX() + sheepSpeed, playerSheep.getY());
            playerSheep.setRotation(270);
        }
    }

    private void executeSheepRandomMoving(CustomEntity sheepToMove, Vector2 direction) {
        handleScreenEdgeIntersection(sheepToMove);
        sheepToMove.setRotation(rotationAngleForDirectionVector.getOrDefault(direction, sheepToMove.getRotation()));
        sheepToMove.setPosition(sheepToMove.getX() + direction.x, sheepToMove.getY() + direction.y);
    }

    private void handleScreenEdgeIntersection(CustomEntity entity) {
        if (entity.getX() < 0) {
            entity.setPosition(1920, entity.getY());
        }

        if (entity.getX() > 1920) {
            entity.setPosition(0, entity.getY());
        }

        if (entity.getY() < 0) {
            entity.setPosition(entity.getX(), 1080);
        }

        if (entity.getY() > 1080) {
            entity.setPosition(entity.getX(), 0);
        }
    }

    private void executeHunterRandomMoving(CustomEntity hunterToMove) {
        if (sheepCount >= 50) {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 12);
        } else if (sheepCount >= 40) {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 10);
        } else if (sheepCount > 30) {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 8);
        } else if (sheepCount > 20) {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 6);
        } else if (sheepCount > 10) {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 4);
        } else {
            hunterToMove.setPosition(hunterToMove.getX(), hunterToMove.getY() - 2);
        }
    }

    @Override
    public void render(float delta) {
        camera.update();
        gameObject.batch.begin();
        gameObject.batch.draw(background, 0, 0);

        if (frameCount % 150 == 0 && frameCount != 0) {
            for (int i = 0; i < sheepCount; i++) {
                sheepMovingDirectionsList.set(i, new Vector2(random.nextInt(3) - 1, random.nextInt(3) - 1));
            }

            frameCount = 0;
        }

        for (CustomEntity listHunter : hunterList) {
            for (int i = sheepCount - 1; i >= 0; i--) {
                if (Intersector.overlapConvexPolygons(otherSheepList.get(i).getBounds(), listHunter.getBounds())) {
                    sheepCount -= 1;
                    otherSheepList.remove(i);
                    sheepDeathSound.play();
                }
            }
        }

        for (int i = 0; i < sheepCount; i++) {
            for (int j = 0; j < sheepCount; j++) {
                if (i != j && Intersector.overlapConvexPolygons(otherSheepList.get(i).getBounds(), otherSheepList.get(j).getBounds())) {
                    otherSheepList.get(i).setPosition(otherSheepList.get(i).getX() - sheepMovingDirectionsList.get(i).x,
                            otherSheepList.get(i).getY() - sheepMovingDirectionsList.get(i).y);
                    otherSheepList.get(j).setPosition(otherSheepList.get(j).getX() - sheepMovingDirectionsList.get(j).x,
                            otherSheepList.get(j).getY() - sheepMovingDirectionsList.get(j).y);

                    sheepMovingDirectionsList.get(i).x = inverseFloat(sheepMovingDirectionsList.get(i).x);
                    sheepMovingDirectionsList.get(i).y = inverseFloat(sheepMovingDirectionsList.get(i).y);
                    sheepMovingDirectionsList.get(j).x = inverseFloat(sheepMovingDirectionsList.get(j).x);
                    sheepMovingDirectionsList.get(j).y = inverseFloat(sheepMovingDirectionsList.get(j).y);
                }
            }

            executeSheepRandomMoving(otherSheepList.get(i), sheepMovingDirectionsList.get(i));
            otherSheepList.get(i).draw(gameObject.batch, 1);
        }

        hunterList.forEach(entity -> {
            executeHunterRandomMoving(entity);
            entity.draw(gameObject.batch, 1);
        });

        if (hunterList.get(0).getY() < 0) {
            hunterList.clear();
            createHunters();
        }

        frameCount++;
        playerSheep.draw(gameObject.batch, 1);
        playerSheep.setZIndex(0);
        gameObject.smallFont.draw(gameObject.batch, "Sheep amount: " + sheepCount, 100, 100);
        gameObject.smallFont.draw(gameObject.batch, "Born sheep amount: " + resultCount, 900, 100);
        gameObject.batch.end();
        handleInput();

        if (sheepCount == 0) {
            gameObject.setScreen(new GameOverScreen(gameObject));
        }
    }

    private float inverseFloat(float value) {
        return value != 0 ? -value : value;
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameObject.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        sheepSound.dispose();
        sheepDeathSound.dispose();
        background.dispose();
        gameObject.batch.dispose();
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
