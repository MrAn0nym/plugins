package xyz.wingio.plugins.discovery.util;

import android.util.Base64;
import android.graphics.*;

import com.aliucord.*;
import com.discord.models.user.User;

import java.io.*;
import java.lang.*;

public class IconUtils {
    private User user;

    public IconUtils(User user) {
        this.user = user;
    }

    public String toBase64() {
        return this.toBase64("https://cdn.discordapp.com/avatars/" + user.getId() + "/" + user.getAvatar() + ".png");
    }

    public static String toBase64(String url) {
        try {
            var res = new Http.Request(url).execute();
            try (var baos = new ByteArrayOutputStream()) {
                res.pipe(baos);
                var b64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                return String.format("%s", b64);
            }
        } catch (IOException ex) {return null;}
    }

    public static Bitmap makeCircle(Bitmap bitmap) {
        float r = 0;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }
        return makeRound(bitmap, r);
    }

    public static Bitmap makeRound(Bitmap bitmap, float radius) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = radius;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    
}