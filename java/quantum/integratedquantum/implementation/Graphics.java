package quantum.integratedquantum.implementation;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

import quantum.integratedquantum.app.Assets;

public class Graphics {
    private AssetManager assets;
    private Canvas canvas;
    private Paint paint;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    Graphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }
    public Bitmap newImage(String fileName, Config format) {
        Options options = new Options();
        options.inPreferredConfig = format;
        InputStream in = null;
        Bitmap bitmap;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"+fileName+"'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"+fileName+"'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {}
            }
        }
        return bitmap;
    }
    void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.STROKE);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }
    public void drawString(String text, int x, int y, Paint paint) {
        canvas.drawText(text, x, y, paint);
    }
    void drawImage(Bitmap image, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        dstRect.left = x1;
        dstRect.top = y1;
        dstRect.right = x1+x2;
        dstRect.bottom = y1+y2;

        srcRect.left = x3;
        srcRect.top = y3;
        srcRect.right = x3+x4;
        srcRect.bottom = y3+y4;
        canvas.drawBitmap(image, srcRect, dstRect,
                null);
    }
    void drawImage(Bitmap image, int x, int y) {
        canvas.drawBitmap(image, x, y, null);
    }
    void drawImage(Bitmap image, int x, int y, int width, int height) {
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x+width;
        dstRect.bottom = y+height;

        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = image.getWidth();
        srcRect.bottom = image.getHeight();
        canvas.drawBitmap(image, srcRect, dstRect, null);
    }
    public void fillRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }
    public void translate(int x, int y) {
        canvas.translate(x, y);
    }
    public void drawLine(int x, int y, int x1, int y1, Paint p) {
        canvas.drawLine(x, y, x1, y1, p);
    }
    void drawString(String text, int x, int y, Paint paint, IntelligentText.Type type) {
        if(type == IntelligentText.Type.normal)
            canvas.drawText(text, x, y, paint);
        else if(type == IntelligentText.Type.superScript) {
            paint.setTextSize(paint.getTextSize()/2);
            canvas.drawText(text, x, y-10, paint);
            paint.setTextSize(paint.getTextSize()*2);
        }
        else if(type == IntelligentText.Type.subScript) {
            paint.setTextSize(paint.getTextSize()/2);
            canvas.drawText(text, x, y+5, paint);
            paint.setTextSize(paint.getTextSize()*2);
        }
        else if(type == IntelligentText.Type.link) {
            paint.setColor(Color.rgb(255, 128, 100));
            canvas.drawText(text, x, y, paint);
            paint.setColor(Color.WHITE);
        }
        else if(type == IntelligentText.Type.integral)
            drawImage(Assets.integral, x-8, y-27);
        else if(type == IntelligentText.Type.operator) {
            this.drawString(text, x, y, paint);
            x += (int)(paint.measureText(text)/2);
            this.drawLine(x-4, y-17, x, y-21, paint);
            this.drawLine(x+4, y-17, x, y-21, paint);
        }
        else {
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            this.drawString(text, x, y, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        }
    }
    public void rotate(float degrees) {
        canvas.rotate(degrees);
    }
}
