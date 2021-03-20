package com.apps.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1;
    private Background background2;
    private int screenX;
    private int screenY;
    public static float screenRatioX;
    public static float screenRatioY;
    private Paint paint;
    private Shooter shooter;
    private List<Bullet> bullets = new ArrayList<>();

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.x = screenX;

        shooter = new Shooter(this, screenX, getResources());

        paint = new Paint();
    }

    @Override
    public void run() {
        while(isPlaying) {

            update ();
            draw();
            sleep();

        }
    }

    private void update () {

        /*background1.x = (int) (background1.x - (10 * screenRatioX));
        background2.x = (int) (background2.x - (10 * screenRatioX));*/

        background1.x -= 2;
        background2.x -= 2;

        if(background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (shooter.isGoingLeft) {
            shooter.x += 40 * screenRatioX;
        }

        if (shooter.isGoingRight) {
            shooter.x -= 40 * screenRatioX;
        }

        if (shooter.x < 0) {
            shooter.x = 0;
        }

        if ( shooter.x > screenX - shooter.width) {
            shooter.x = screenX - shooter.width;
        }

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if(bullet.y > screenY) {
                trash.add(bullet);
            }
            bullet.y += 10;
        }

        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }
    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(shooter.getFireShooter(false), shooter.x, shooter.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if((event.getY() > screenY / 2) && (event.getX() < screenX / 2)) {
                    shooter.isGoingRight = true;
                }
                if((event.getY() > screenY / 2) && (event.getX() > screenX / 2)) {
                    shooter.isGoingLeft = true;
                }
                if(event.getY() < screenY / 2) {
                    shooter.toShoot++;
                }
                break;
            case MotionEvent.ACTION_UP:
                shooter.isGoingLeft = false;
                shooter.isGoingRight = false;
                break;
        }

        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = shooter.x + (shooter.width / 2);
        bullet.y = shooter.y + shooter.height;
        bullets.add(bullet);
    }
}
