package com.apps.myfirstapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    int x;
    int y;
    Bitmap bullet;

    Bullet (Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

         /*width = bullet.getWidth() / 4;
        height = bullet.getHeight() / 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;*/

        int width = 100;
        int height = 100;

        bullet = Bitmap.createScaledBitmap(bullet, bullet.getWidth(), bullet.getHeight(), false);

    }
}
