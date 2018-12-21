package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;


public class Label extends Component {
    private int x;
    private int y;
    private String text;
    private Paint paint;

    public Label(int x, int y, int size, String s, Paint.Align a) {
        this. x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(a);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        this.text = s;
    }
    public Label(int x, int y, int size, String s, Paint.Align a, int color) {
        this. x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(color);
        paint.setTextAlign(a);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        this.text = s;
    }
    @Override
    public void paint(Graphics g) {
        g.drawString(text, x, y, paint);
        super.paint(g);
    }
}
