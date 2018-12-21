package quantum.integratedquantum.implementation.functions;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Component;

public class Tunnel extends Component {
    private int y;
    private int width;
    private int height;
    private int nullX;
    private int [] valueR;
    private int [] valueI;
    private Paint p;
    private double scaleX;
    private double scaleY;

    public Tunnel(int y) {
        this.y = y;
        this.width = 540;
        this.height = 300;
        double minX = -0.002;
        double maxX = 0.004;
        valueR = new int[width];
        valueI = new int[width];
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setAntiAlias(true);
        scaleX = (maxX-minX)/(double)(width);
        scaleY = 36.0/(double)(height);
        nullX = -(int)(minX/scaleX);
        init();
    }
    @Override
    public void paint(Graphics g) {
        g.fillRect(nullX, y, (int)(0.001/scaleX), height, Color.DKGRAY);
        for(int i = 0; i < width-1; i++) {
            g.drawLine(i, valueR[i]-height/2+40, i+1, valueR[i+1]-height/2+40, p);
            p.setColor(Color.rgb(0, 0, 200));
            g.drawLine(i, valueI[i]-height/2+40, i+1, valueI[i+1]-height/2+40, p);
            p.setColor(Color.rgb(0, 200, 0));
        }
        super.paint(g);
    }
    private void init() {
        for(int i = 0; (i-nullX)*scaleX < 0; i++)
            valueR[i] = height+y-40-(int)(function1R((i-nullX)*scaleX)/ scaleY);
        for(int i = nullX; (i-nullX)*scaleX < 0.001; i++)
            valueR[i] = height+y-40-(int)(function2R((i-nullX)*scaleX)/ scaleY);
        for(int i = nullX+(int)(0.001/scaleX); (i-nullX)*scaleX < 0.004; i++)
            valueR[i] = height+y-40-(int)(function3R((i-nullX)*scaleX)/ scaleY);
        for(int i = 0; (i-nullX)*scaleX < 0; i++)
            valueI[i] = height+y-40-(int)(function1I((i-nullX)*scaleX)/ scaleY);
        for(int i = nullX; (i-nullX)*scaleX < 0.001; i++)
            valueI[i] = height+y-40-(int)(function2I((i-nullX)*scaleX)/ scaleY);
        for(int i = nullX+(int)(0.001/scaleX); (i-nullX)*scaleX < 0.004; i++)
            valueI[i] = height+y-40-(int)(function3I((i-nullX)*scaleX)/ scaleY);
    }
    private double AI = 12.18888109;
    private double AR = 0.6744985555;
    private double BI = 0.6767578244;
    private double BR = -12.22970847;
    private double ER = 1;
    private double k = 3196.87347263;
    private double function1R(double x) {
        return AR*Math.cos(k*x)+BR*Math.cos(-k*x)+AI*Math.sin(k*x)+BI*Math.sin(-k*x);
    }
    private double function1I(double x) {
        return AI*Math.cos(k*x)+BI*Math.cos(-k*x)-AR*Math.sin(k*x)-BR*Math.sin(-k*x);
    }
    private double function2R(double x) {
        return -0.02154332878*Math.exp(k*x)-11.53366659*Math.exp(-k*x);
    }
    private double function2I(double x) {
        return -0.01928405564*Math.exp(k*x)+12.88492297*Math.exp(-k*x);
    }
    private double function3R(double x) {
        return ER*Math.cos(k*x);
    }
    private double function3I(double x) {
        return ER*Math.sin(k*x);
    }
}
