package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.app.Assets;

public class Button extends Component {
    private int x;
    private int y;
    private int tX;
    private int tY;
    private int oldY;
    private int width;
    private int height;
    private ActionListener listener;
    private boolean pressed;
    private boolean visible = true;

    public Button(int x, int y, int width, int height, String s, ActionListener a) {
        maxX = 0;
        maxY = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        add(new Label(x+width/2, y+height-5-(height-30)/2, 30, s, Paint.Align.CENTER, Color.WHITE));
        pressed = false;
        listener = a;
    }
    public Button(int x, int y, int width, int height, boolean visible, ActionListener a) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pressed = false;
        listener = a;
        this.visible = visible;
    }
    @Override
    public void paint(Graphics g) {
        if(visible) {
            int n = 0;
            if(pressed)
                n = 11;
            g.drawImage(Assets.b, x, y, 5, 5, n, 0, 5, 5);
            g.drawImage(Assets.b, x, y+5, 5, height-10, n, 5, 5, 1);
            g.drawImage(Assets.b, x, y+height-5, 5, 5, n, 6, 5, 5);
            g.drawImage(Assets.b, x+5, y, width-10, 5, n+5, 0, 1, 5);
            g.drawImage(Assets.b, x+5, y+5, width-10, height-10, n+5, 5, 1, 1);
            g.drawImage(Assets.b, x+5, y+height-5, width-10, 5, n+5, 6, 1, 5);
            g.drawImage(Assets.b, x+width-5, y, 5, 5, n+6, 0, 5, 5);
            g.drawImage(Assets.b, x+width-5, y+5, 5, height-10, n+6, 5, 5, 1);
            g.drawImage(Assets.b, x+width-5, y+height-5, 5, 5, n+6, 6, 5, 5);
            super.paint(g);
        }
    }
    @Override
    public void update(List<Input.TouchEvent> touchEvents) {
        super.update(touchEvents);
        int n = touchEvents.size();
        for(int i = 0; i < n; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.x >= x+tX && event.x <= x+tX + width && event.y >= y+tY && event.y < y+tY + height && event.x > maxX && event.y > maxY) {
                if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                    pressed = true;
                    oldY = tY;
                }
                if(pressed) {
                    if(event.type == Input.TouchEvent.TOUCH_UP) {
                        pressed = false;
                        listener.actionPerformed(this.n);
                    }
                    else if(Assets.valueOf(oldY-tY) > height)
                        pressed = false;
                }
            }
            else {
                if(pressed)
                    pressed = false;
            }
        }
    }
    @Override
    public void translate(int x, int y) {
        tX = x;
        tY = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}