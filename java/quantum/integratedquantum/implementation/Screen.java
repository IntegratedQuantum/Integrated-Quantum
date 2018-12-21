package quantum.integratedquantum.implementation;

import android.graphics.Color;

import java.util.List;

import quantum.integratedquantum.app.main;

public abstract class Screen extends Component {
    protected main m;
    private List<Input.TouchEvent> touchEvents;
    protected App app;
    public int n;
    protected Screen(App app, main m) {
        this.app = app;
        touchEvents = app.getInput().getTouchEvents();
        this.m = m;
    }
    public void update() {
        touchEvents = app.getInput().getTouchEvents();
        super.update(touchEvents);
    }
    public void paint() {
        Graphics g = app.getGraphics();
        g.fillRect(0, 0, 540, 960, Color.rgb(50, 50, 50));
        g.fillRect(0, 0, 540, 130, Color.rgb(64, 0, 0));
        paint(g);
    }
    public void pause() {
        m.saveData();
    }

    public void backButton() {
        m.saveData();
    }
}
