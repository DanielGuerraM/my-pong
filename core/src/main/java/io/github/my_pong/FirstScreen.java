package io.github.my_pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle player, computer, ball;
    private Vector2 ballVelocity;
    private int playerScore, computerScore;
    private BitmapFont font;

    private Texture whiteTexture;
    private Sound hitSound;

    @Override
    public void show() {
        // Prepare your screen here.
        //1. Inicializamos el sistema de dibujo y camara
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        font = new BitmapFont();
        font.getData().setScale(2);

        camera.setToOrtho(false, 640, 480);

        //2. Crear una textura blanca simple
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        whiteTexture = new Texture(pixmap);
        pixmap.dispose();

        //3. Configurar los obejtos
        player = new Rectangle(20, 480 / 2f - 40, 15, 80);
        computer = new Rectangle(640 - 35, 480 / 2f - 40, 15, 80);
        ball = new Rectangle(640 / 2f - 8, 480 / 2f - 8, 16, 16);

        ballVelocity = new Vector2(200, 200);


        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(whiteTexture, player.x, player.y, player.width, player.height);
        batch.draw(whiteTexture, computer.x, computer.y, computer.width, computer.height);
        batch.draw(whiteTexture, ball.x, ball.y, ball.width, ball.height);

        for(int i = 0; i < 480; i += 20) {
            batch.draw(whiteTexture, 640 / 2f - 1, i, 2, 10);
        }

        font.draw(batch, "Jugador: " + playerScore, 100, 450);
        font.draw(batch, "IA: " + computerScore, 450, 450);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        batch.dispose();
        whiteTexture.dispose();
        font.dispose();
        hitSound.dispose();
    }

    private void update(float delta) {
        float paddleSpeed = 300f;
        float computerSpeed = 180f;

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.y += paddleSpeed * delta;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.y -= paddleSpeed * delta;
        }

        if(player.y < 0) player.y = 0;
        if(player.y > 480 - player.height) player.y = 480 - player.height;

        if(ball.y + ball.height / 2 > computer.y + computer.height / 2) {
            computer.y += computerSpeed * delta;
        } else {
            computer.y -= computerSpeed * delta;
        }

        if(computer.y < 0 ) computer.y = 0;
        if(computer.y > 480 - computer.height) computer.y = 480 - computer.height;

        ball.x += ballVelocity.x * delta;
        ball.y += ballVelocity.y * delta;

        // Colisión con el techo o el suelo
        if(ball.y <= 0 || ball.y >= 480 - ball.height) {
            ballVelocity.y *= -1;
            hitSound.play();
        }

        //Colisión con la paleta del jugador
        if (ball.overlaps(player)) {
            ballVelocity.x = Math.abs(ballVelocity.x);
            ballVelocity.scl(1.05f);
            hitSound.play(0.8f);
        }

        //Colisión con la paleta de la computadora
        if (ball.overlaps(computer)) {
            ballVelocity.x = -Math.abs(ballVelocity.x);
            ballVelocity.scl(1.05f);
            hitSound.play(0.8f);
        }

        //Detectar si alguien anoto un punto
        if(ball.x < 0) {
            computerScore++;
            resetBall();
        } else if(ball.x > 640) {
            playerScore++;
            resetBall();
        }
    }

    private void resetBall() {
        ball.x = 640 / 2 - ball.width / 2;
        ball.y = 480 / 2 - ball.height / 2;

        ballVelocity.x *= -1;

        ballVelocity.set(ballVelocity.x > 0 ? 200 : -200, 200);
    }
}
