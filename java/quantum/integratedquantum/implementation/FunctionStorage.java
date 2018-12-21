package quantum.integratedquantum.implementation;

import android.graphics.Paint;


class FunctionStorage {
    private int n;
    //private double h = 6.626*Math.pow(10, -34);
    //private float c = 299792458;
    //private double k = 1.38064852*Math.pow(10, -23);
    FunctionStorage(int n) {
        this.n = n;
    }
    double getValueOf(double x) {
        double n2 = 0;
        switch (n) {
            case 1:
                n2 = square(x);
                break;
        }
        return n2;
    }
    private double square(double x) {
        return x*x;
    }
    public void paint(Graphics g, FunctionOld f, Paint p) {
        switch (n) {
            case 1:
                g.drawLine(f.nullX+f.x, f.nullY+f.y, (int)(f.scaleX*100), (int)(f.scaleY*396), p);
                break;
        }
    }
}
