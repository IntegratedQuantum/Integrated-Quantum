package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;

public class FunctionOld extends Component implements ActionListener {
    private boolean interactive;
    double scaleX;
    private int additionalLength;
    int x;
    int y;
    private int width;
    private int height;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int averageX;
    private int averageY;
    int nullX;
    int nullY;
    private int argument;
    private int [] argv;
    private int [] value;
    private String xAxis;
    private String yAxis;
    private String additional;
    private FunctionStorage function;
    private Paint p;
    double scaleY;

    public FunctionOld(int x, int y, int width, int height, String s, Panel sup) {
        int n = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        int mode = 0;
        String current = "";
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ';') {
                switch(mode) {
                    case 0:
                        minX = Integer.parseInt(current);
                        break;
                    case 1:
                        maxX = Integer.parseInt(current);
                        break;
                    case 2:
                        xAxis = current;
                        break;
                    case 3:
                        yAxis = current;
                        break;
                    case 4:
                        additional = current;
                        break;
                    case 5:
                        try {
                            additionalLength = Integer.parseInt(current);
                        }catch(Exception ignored){}
                        break;
                    case 6:
                        n = Integer.parseInt(current);
                        break;
                    case 7:
                        argument = Integer.parseInt(current);
                }
                current = "";
                mode ++;
            }
            else {
                try {
                    current += String.valueOf(s.charAt(i));
                } catch (Exception ignored) {}
            }
        }
        interactive = additionalLength != 0;
        value = new int[width-40];
        function = new FunctionStorage(n);
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(20);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        if(interactive) {
            argv = new int[additionalLength];
            y += 120;
            for (int i = 0; i < additionalLength; i++) {
                try {
                    argv[i] = Integer.parseInt(String.valueOf(("" + argument).charAt(("" + argument).length() - additionalLength + i)));
                } catch (Exception e) {
                    argv[i] = 0;
                }
                sup.add(new Button(x + 100 + i * 40, y - 120, 40, 40, "↑", this));
                sup.add(new Button(x + 100 + i * 40, y - 40, 40, 40, "↓", this));
            }
        }
        scaleX = (maxX-minX)/(double)(width-40);
        nullX = x+width-(int)(minX/scaleX);
        averageX = minX/2+maxX/2;
        update();
    }
    private void update() {
        minY = 0;
        maxY = 0;
        if(interactive) {
            StringBuilder n = new StringBuilder();
            for (int i = 0; i < additionalLength; i++)
                n.append(argv[i]);
            argument = Integer.parseInt(n.toString());
        }
        for(int i = 0; i < width-40; i++) {
            if(function.getValueOf(i*scaleX+minX) > maxY)
                maxY = (int)function.getValueOf(i*scaleX+minX)+1;
            else if(function.getValueOf(i*scaleX+minX) < minY && function.getValueOf(i*scaleX+minX) > -1000000000)
                minY = (int)function.getValueOf(i*scaleX+minX);
        }
        scaleY = (double) (maxY - minY) / (double) (height - 40);
        nullY = y+(int)(maxY/ scaleY)+8;
        averageY = minY/2+maxY/2;
        int c = height+y-40;
        for(int i = 0; i < width-40; i++)
            value[i] = c-(int)(function.getValueOf(i*scaleX+minX)/ scaleY);
    }
    @Override
    public void paint(Graphics g) {
        g.fillRect(nullX, y, 1, height-40, Color.BLUE);
        g.fillRect(x+40, nullY-8, width-40, 1, Color.BLUE);
        for(int i = 0; i < width-41; i++) {
            g.drawLine(i+x+40, value[i], i+1+x+40, value[i+1], p);
        }
        if(interactive) {
            p.setColor(Color.WHITE);
            p.setTextSize(40);
            for(int i = 0; i < additionalLength; i++)
                g.drawString(argv[i]+"", x+120+i*40, y-47, p);
            p.setTextAlign(Paint.Align.LEFT);
            g.drawString(additional, x+100+additionalLength*40, y-47, p);
            p.setTextAlign(Paint.Align.CENTER);
            p.setTextSize(20);
            p.setColor(Color.YELLOW);
        }
        g.drawString(minY+"", nullX, y+height-32, p);
        g.drawString(minX+"", x+40, nullY, p);
        g.drawString(maxY+"", nullX, y, p);
        g.drawString(maxX+"", x+width, nullY, p);
        g.drawString(averageY+"", nullX, y+height/2-16, p);
        g.drawString(averageX+"", x+width/2+20, nullY, p);
        g.drawString(xAxis, x+width/2+20, y+height, p);
        g.translate(x, y+height/2-20);
        g.rotate(-90);
        g.drawString(yAxis, 0, 0, p);
        g.rotate(90);
        g.translate(-x, -y-height/2+20);
        function.paint(g, this, p);
        super.paint(g);
    }

    @Override
    public void actionPerformed(int n) {
        int n2 = n/2;
        if(n2*2 != n)
            argv[n2]--;
        else
            argv[n2]++;
        if(argv[n2] < 0)
            argv[n2] = 9;
        else if(argv[n2] > 9)
            argv[n2] = 0;
        update();
    }
    public int getLength() {
        if(interactive)
            return 660;
        return 540;
    }
}
