package quantum.integratedquantum.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

import quantum.integratedquantum.implementation.App;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Screen;

class LoadingScreen extends Screen {
    LoadingScreen(App app, main m) {
        super(app, m);
    }
    @Override
    public void update() {
        m.loadData();
        Graphics g = app.getGraphics();
        Assets.b = g.newImage("b.png", Config.ARGB_8888);
        Assets.bgP = g.newImage("bgP.png", Config.RGB_565);
        Assets.ds = g.newImage("ds.png", Config.ARGB_8888);
        Assets.Q = g.newImage("quantum.png", Config.ARGB_4444);
        Assets.integral = g.newImage("integral.png", Config.ARGB_8888);
        Assets.cross = g.newImage("cross.png", Config.ARGB_4444);
        SharedPreferences sp = m.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Assets.size = sp.getInt("size", Assets.size);
        app.setScreen(new MenuScreen(app, m));
    }
    @Override
    public void paint() {
        Graphics g = app.getGraphics();
        g.fillRect(0, 0, 540, 960, Color.BLACK);
    }
    @Override
    public void pause() {
    }
    @Override
    public void backButton() {
    }
}
