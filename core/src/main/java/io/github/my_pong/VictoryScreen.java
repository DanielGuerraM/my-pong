package io.github.my_pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class VictoryScreen implements Screen {
    private final Main game;
    private String winner;

    public VictoryScreen(Main game, String winner) {
        this.game = game;
        this.winner = winner;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0.5f, 0, 1);
        game.batch.begin();
        game.font.draw(game.batch, "¡GANADOR: " + winner + "!", 200, 300);
        game.font.draw(game.batch, "Presiona ENTER para volver al inicio", 150, 200);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game));
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

    }

    @Override
    public void dispose() {

    }
}
