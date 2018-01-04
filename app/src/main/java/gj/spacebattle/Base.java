package gj.spacebattle;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gilsonsantos on 03/01/18.
 */

public class Base extends SurfaceView implements Runnable {

    boolean isRunning = false;
    Thread renderThread = null;
    SurfaceHolder holder;
    Paint paint;
    private int playerY = 300;
    private int playerX = 300;
    private float enemyRadius = 50;
    private int enemyX, enemyY;
    private int playerRadius = 50;
    private double distance;
    private boolean isGameOver;
    private int score;

    public Base(Context context) {
        super(context);
        paint = new Paint();
        holder = getHolder();
    }

    @Override
    public void run() {
        while(isRunning) {
            // verifica se a tela já está pronta
            if(!holder.getSurface().isValid())
                continue;
            // bloqueia o canvas
            Canvas canvas = holder.lockCanvas();
            //Seta o background com a cor preta
            canvas.drawColor(Color.BLACK);
            //Seta uma imagem como background
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.sky), 0, 0, null);

            // desenha o player
            drawPlayer(canvas);

            // desenha o inimigo
            drawEnemy(canvas);

            // detecta colisão
            checkCollision(canvas);

            if(isGameOver) {
                stopGame(canvas);
                break;
            }

            // atualiza o placar
            drawScore(canvas);

            // Restart e Exit
            drawButtons(canvas);

            // atualiza e libera o canvas
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void init() {
        enemyX = 0;
        enemyY = 0;
        enemyRadius = 0;
        playerX = playerY = 300;
        playerRadius = 50;
        isGameOver = false;
    }

    public void moveDown(int pixels) {
        playerY += pixels;
    }

    public void moveUp(int pixels) {
        playerY -= pixels;
    }


    private void drawButtons(Canvas canvas) {
        // Restart
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Restart", 50, 300, paint);
        // Exit
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Exit", 50, 500, paint);
    }

    private void drawEnemy(Canvas canvas) {
        paint.setColor(Color.GRAY);
        enemyRadius++;
        canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
    }

    private void drawPlayer(Canvas canvas) {
        paint.setColor(Color.GREEN);
        //canvas.drawCircle(playerX, playerY, playerRadius, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.nave), playerX, playerY, null);
    }

    private void checkCollision(Canvas canvas) {
        // calcula a hipotenusa
        distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
        distance = Math.sqrt(distance);

        // verifica distancia entre os raios
        if (distance <= playerRadius + enemyRadius) {
            isGameOver = true;
        }
    }

    public void addScore(int points) {
        score += points;
    }

    private void drawScore(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText(String.valueOf(score), 50, 200, paint);
    }

    private void stopGame(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(100);
        canvas.drawText("GAME OVER!", 50, 150, paint);
    }

    public void resume() {
        isRunning = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
}
