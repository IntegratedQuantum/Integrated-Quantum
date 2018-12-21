package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.app.Assets;

public class SwitchPanel extends Component implements ActionListener {
    private Panel[] subPanel;
    private Screen screen;
    private int i;
    private int x;
    private int y;
    private int width;
    private int height;
    private Paint paint;
    private int color;
    private String [] name;
    public SwitchPanel(int x, int y, int width, int height, Panel[] subPanels, Screen screen, int i, String[] title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = Color.rgb(64, 25, 25);
        this.subPanel = subPanels;
        this.screen = screen;
        this.i = i;
        paint = new Paint();
        paint.setColor(Color.rgb(150, 150, 255));
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        name = title;
        add(new Button(x, y, height, height, false, this));
        add(new Button(x+width-height, y, height, height, false, this));
        add(new Label(x, y+height-12, height, "←", Paint.Align.LEFT, Color.rgb(150, 150, 255)));
        add(new Label(x+width, y+height-12, height, "→", Paint.Align.RIGHT, Color.rgb(150, 150, 255)));
        screen.set(i, subPanel[Assets.n]);
    }

    public void actionPerformed(int n) {
        if(n == 0) {
            if(Assets.n > 0)
                Assets.n--;
            else
                Assets.n = name.length-1;
        }
        else {
            if(Assets.n < name.length-1)
                Assets.n++;
            else
                Assets.n = 0;
        }
        screen.set(i, subPanel[Assets.n]);
        screen.n = Assets.n;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(x, y, width, height, color);
        g.drawString(name[Assets.n], x+width/2, y+height-20, paint);
        super.paintOnly(2, g);
        super.paintOnly(3, g);
    }
}
