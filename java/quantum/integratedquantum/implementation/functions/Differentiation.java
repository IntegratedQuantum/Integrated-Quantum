package quantum.integratedquantum.implementation.functions;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Component;

public class Differentiation extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private int nullX;
    private int [] value;
    private int [] value2;
    private Paint p;
    private double scaleX;
    private double scaleY;

    public Differentiation(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 460;
        this.height = 540;
        int minX = -1;
        int maxX = 1;
        value = new int[width-40];
        value2 = new int[width-40];
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setAntiAlias(true);
        scaleX = ((double) maxX -(double) minX)/(double)(width-40);
        scaleY = 2.0/(double)(height-40);
        nullX = -(int)(minX/scaleX);
        init();
    }
    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < width-41; i++) {
            g.drawLine(i+x+40, value[i], i+1+x+40, value[i+1], p);
            if(value2[i] < height+y-40) {
                p.setColor(Color.rgb(0, 0, 200));
                g.drawLine(i + x + 40, value2[i], i + 1 + x + 40, value2[i + 1], p);
                p.setColor(Color.rgb(0, 200, 0));
            }
        }
        super.paint(g);
    }
    private void init() {
        for(int i = 0; i < width-40; i++)
            value[i] = height+y-40-(int)(function((i-nullX)*scaleX)/scaleY);
        for(int i = 0; i < width-40; i++)
            value2[i] = height+y-40-(int)(function2((i-nullX)*scaleX)/scaleY);
    }
    private double function(double x) {
        return 2*x*x;
    }
    private double function2(double x) {
        return 2*x-0.5;
    }
}

