package com.github.kovsaw.gameoldfortythree.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kovsaw.gameoldfortythree.MainGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1920;
        config.height = 1080;
        config.fullscreen = true;
        config.vSyncEnabled = false;
        config.backgroundFPS = 60;
        config.foregroundFPS = 60;
        new LwjglApplication(new MainGame(), config);
    }
}
