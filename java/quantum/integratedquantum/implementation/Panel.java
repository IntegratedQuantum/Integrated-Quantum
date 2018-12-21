package quantum.integratedquantum.implementation;

import android.graphics.Bitmap;

import java.util.List;

import quantum.integratedquantum.app.Assets;

public class Panel extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private int borderX;
    private int borderY;
    private int borderWidth;
    private int borderHeight;
    private int lastX;
    private int lastY;
    private Bitmap bg;

    public Panel(int width1, int height1, int x, int y, int width2, int height2, Bitmap bg) {
        this.x = 0;
        this.y = 0;
        if(width1 < 0)
            width1 = 0;
        if(width2 < 0)
            width2 = 0;
        width = -width1;
        height = -height1;
        borderX = x;
        borderY = y;
        borderWidth = width2;
        borderHeight = height2;
        this.bg = bg;
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, borderX, borderY, borderWidth, borderHeight);
        g.translate(borderX + x, borderY + y);
        super.paint(g);
        g.translate(-borderX - x, -borderY - y);
    }
    @Override
    public void update(List<Input.TouchEvent> touchEvents) {
        super.update(touchEvents);
        int n = touchEvents.size();
        for(int i = 0; i < n; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_DRAGGED && event.x > borderX && event.y > borderY && event.x < borderX+borderWidth && event.y < borderY+borderHeight) {
                if(Math.sqrt((event.x-lastX)*(event.x-lastX)+(event.y-lastY)*(event.y-lastY)) < 50) {
                    x += (event.x-lastX);
                    y += (event.y-lastY);
                    if(Assets.doubleSpeed) {
                        x += (event.x-lastX);
                        y += (event.y-lastY);
                    }
                }
                lastX = event.x;
                lastY = event.y;
            }
            else {
                lastX = event.x;
                lastY = event.y;
            }
        }
        if(x > 0)
            x = 0;
        else if(x < width)
            x = width;
        if(y > 0)
            y = 0;
        else if(y < height)
            y = height;
        for(int i = 0; i < size; i++) {
            if(data[i].getClass() == Button.class || data[i].getClass() == CheckBox.class || data[i].getClass() == IntelligentText.class) {
                data[i].maxX = this.borderX;
                data[i].maxY = this.borderY;
            }
            data[i].translate(x+borderX, y+borderY);
        }
    }
    public void changeSize(int x, int y) {
        width = -x;
        height = -y;
    }
}
