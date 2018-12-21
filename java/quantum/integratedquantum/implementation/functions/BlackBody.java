package quantum.integratedquantum.implementation.functions;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Button;
import quantum.integratedquantum.implementation.Component;

public class BlackBody extends Component implements ActionListener {
    private int additionalLength;
    private int x;
    private int y;
    private int width;
    private int height;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private double averageX;
    private double averageY;
    private int nullX;
    private int nullY;
    private int argument;
    private int [] argv;
    private int [] value;
    private String xAxis;
    private String yAxis;
    private String additional;
    private Paint p;
    private double scaleX;

    public BlackBody(Component parent, int x, int y) {
        this.x = x;
        y += 120;
        this.y = y;
        this.width = 460;
        this.height = 540;
        minX = 0;
        maxX = 2000;
        xAxis = "wavelength(nm)";
        yAxis = "L(λ, T)";
        additional = "K";
        additionalLength = 4;
        argument = 5000;
        value = new int[width-40];
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(20);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        argv = new int[additionalLength];
        for (int i = 0; i < additionalLength; i++) {
            try {
                argv[i] = Integer.parseInt(String.valueOf((""+argument).charAt((""+argument).length()-additionalLength+i)));
            } catch (Exception e) {
                argv[i] = 0;
            }
            parent.add(new Button(x + 100 + i * 40, y - 120, 40, 40, "↑", this));
            parent.add(new Button(x + 100 + i * 40, y - 40, 40, 40, "↓", this));
        }
        scaleX = (maxX-minX)/(double)(width-40);
        nullX = x+40+(int)(minX/scaleX);
        averageX = minX/2.0+maxX/2.0;
        update();
    }
    @Override
    public void paint(Graphics g) {
        g.fillRect(nullX, y, 1, height-40, Color.BLUE);
        g.fillRect(x+40, nullY-8, width-40, 1, Color.BLUE);
        for(int i = 0; i < width-41; i++)
            g.drawLine(i+x+40, value[i], i+1+x+40, value[i+1], p);
        p.setColor(Color.WHITE);
        p.setTextSize(40);
        for(int i = 0; i < additionalLength; i++)
            g.drawString(argv[i]+"", x+120+i*40, y-47, p);
        p.setTextAlign(Paint.Align.LEFT);
        g.drawString(additional, x+100+additionalLength*40, y-47, p);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(20);
        p.setColor(Color.YELLOW);
        g.drawString(minY+"", nullX, y+height-32, p);
        g.drawString(minX+"", x+40, nullY, p);
        g.drawString(maxY+"", nullX, y, p);
        g.drawString(maxX+"", x+width, nullY, p);
        g.drawString(averageY+"", nullX, y+height/2-16, p);
        g.drawString(averageX+"", x+width/2+20, nullY, p);
        g.drawString(xAxis, x+width/2+20, y+height, p);
        g.translate(x+10, y+120);
        g.rotate(-90);
        g.drawString(yAxis, 0, 0, p);
        g.rotate(90);
        g.translate(-x-10, -y-120);
        super.paint(g);
    }
    @Override
    public void actionPerformed(int n) {
        int n2 = n/2;
        if(n2<<1 != n)
            argv[n2]--;
        else
            argv[n2]++;
        if(argv[n2] < 0)
            argv[n2] = 9;
        else if(argv[n2] > 9)
            argv[n2] = 0;
        update();
    }
    private void update() {
        minY = 0;
        maxY = 0;
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < additionalLength; i++)
            n.append(argv[i]);
        argument = Integer.parseInt(n.toString());
        for(int i = 0; i < width-40; i++) {
            if(function(i*scaleX+minX, argument) > maxY)
                maxY = (int)function(i*scaleX+minX, argument)+1;
            else if(function(i*scaleX+minX, argument) < minY && function(i*scaleX+minX, argument) > -1000000000)
                minY = (int)function(i*scaleX+minX, argument);
        }
        double scaleY = (double) (maxY - minY) / (double) (height - 40);
        nullY = y+(int)(maxY/ scaleY)+8;
        averageY = minY/2.0+maxY/2.0;
        for(int i = 0; i < width-40; i++)
            value[i] = height+y-40-(int)(function(i*scaleX+minX, argument)/ scaleY);
    }
    public double function(double x, double y) {
        x /= 1000000000;
        double c = 299792458;
        double h = 6.62607004e-34;
        double k = 1.38064852e-23;
        return 8*Math.PI*h* c /(x*x*x*x*x*Math.expm1(h* c /(x*k*y)));
    }
}
