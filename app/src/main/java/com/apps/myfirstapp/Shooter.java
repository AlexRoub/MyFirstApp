package com.apps.myfirstapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.apps.myfirstapp.GameView.screenRatioX;
import static com.apps.myfirstapp.GameView.screenRatioY;

public class Shooter {

    int x;
    int y;
    int width;
    int height;
    int toShoot = 0;
    int shootCounter = 1;
    Bitmap shooter1;
    Bitmap shooter2;
    Bitmap bullet;
    boolean isGoingLeft = false;
    boolean isGoingRight = false;
    private GameView gameView;

    Shooter (GameView gameView, int screenX, Resources res) {

        this.gameView = gameView;

        shooter1 = BitmapFactory.decodeResource(res, R.drawable.shooting_gun);
        shooter2 = BitmapFactory.decodeResource(res, R.drawable.shooting_gun_fire);

        /*width = shooter1.getWidth() / 4;
        height = shooter1.getHeight() / 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;*/

        width = 350;
        height = 350;

        shooter1 = Bitmap.createScaledBitmap(shooter1, width, height, false);
        shooter2 = Bitmap.createScaledBitmap(shooter2, width, height, false);

        x = screenX / 2;
        y = (int) (1224 * screenRatioY);
    }

    Bitmap getFireShooter (boolean shoot) {
        if(toShoot != 0){
            if (shootCounter == 1 ) {
                shootCounter++;
                return shooter2;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();
        }
        return  shooter1;
    }
}
