package io.github.my_pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen, InputProcessor {
    private final Main game;
    private int pointsToWin = 5;
    private String inputBuffer = "5";
    private float timer = 0;
    private boolean showCursor = true;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        timer += delta;
        if(timer >= 0.5f) {
            showCursor = !showCursor;
            timer = 0;
        }

        game.batch.begin();

        game.font.draw(game.batch, "MENU PONG", 250, 400);

        String cursorChar = showCursor ? "|" : "";
        game.font.draw(game.batch, "Puntos para ganar: [ " + inputBuffer + cursorChar + " ]", 100, 300);
        game.font.draw(game.batch, "(Escribe el numero y pulsa ENTER para jugar)", 100, 220);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            try {
                pointsToWin = Integer.parseInt(inputBuffer);
            } catch (NumberFormatException e) {
                pointsToWin = 5;
            }

            game.setScreen(new FirstScreen(game, pointsToWin));
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(Character.isDigit(character) && inputBuffer.length() < 2) {
            inputBuffer += character;
            showCursor = true;
            timer = 0;
        }
        else if(character == '\b' && inputBuffer.length() > 0) {
            inputBuffer = inputBuffer.substring(0, inputBuffer.length() - 1);
            showCursor = true;
            timer = 0;
        }

        return true;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
