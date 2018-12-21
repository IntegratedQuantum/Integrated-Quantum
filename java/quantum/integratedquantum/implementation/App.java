package quantum.integratedquantum.implementation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public abstract class App extends Activity implements Runnable {
    private SurfaceView view;
    Graphics graphics;
    Input input;
    Screen screen;
    PowerManager.WakeLock wakeLock;
    public AssetManager assets;
    Bitmap frameBuffer;
    Thread renderThread = null;
    volatile boolean running = false;
    @Override
    public void run() {
        SurfaceHolder holder = view.getHolder();
        Rect dstRect = new Rect();
        while(running) {
            if(!holder.getSurface().isValid())
                continue;
            screen.update();
            screen.paint();
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        frameBuffer = Bitmap.createBitmap(540, 960, Config.RGB_565);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        double scaleX = 540.0/size.x;
        double scaleY = 960.0/size.y;
        view = new SurfaceView(this);
        assets = getAssets();
        graphics = new Graphics(assets, frameBuffer);
        input = new Input(view, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(view);
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        assert powerManager != null;
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Integrated Quantum");
    }
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire(10*60*1000L /*10 minutes*/);
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        running = false;
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException ignored) {}
        }
        screen.pause();
    }
    @Override
    public void onBackPressed() {
        screen.backButton();
    }

    public Input getInput() {
        return input;
    }
    public Graphics getGraphics() {
        return graphics;
    }

    public void setScreen(Screen screen) {
        /*if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");*/
        screen.update();
        this.screen = screen;
    }
    public AssetManager getAsset() {return assets;}
    
    public abstract Screen getInitScreen();
}
