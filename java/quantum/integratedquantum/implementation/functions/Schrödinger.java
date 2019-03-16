package quantum.integratedquantum.implementation.functions;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.implementation.Button;
import quantum.integratedquantum.implementation.CheckBox;
import quantum.integratedquantum.implementation.Complex;
import quantum.integratedquantum.implementation.Component;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Label;

public class Schrödinger extends Component implements ActionListener {
    private int y;
    private int l;
    private int minX;
    private int maxX;
    private int averageX;
    private float minY;
    private float maxY;
    private float ΔY;
    private float averageY;
    private boolean sym;
    private static int width = 460;
    private static int height = 540;
    private Paint p;
    private float[] PsiSquared;
    private float[] Psi;
    private Complex[] A;
    private Complex[] B;
    private Complex[] k;
    private float scaleX = (float)(width/2000.0);
    private float scaleY = 1;
    private double m = 9.10938188e-31;//kg    //.0016678204759907602;  //¿?
    private double ħ = 1.0545716346179718e-34;//Js
    private static double scale = 10e-12;
    private CheckBox ch1, ch2, ch3;
    private int energylevel = 1;
    //double m = 510998.946131;           //eV/c²
    //static double ħ = 6.58211951440e-16;       //eVs
    static double e = 1.602176620898e-19;      //C
    private double E;
    private static Complex one = new Complex(1, 0);


    // Rounds a float to 3 digits.
    private static float round(float in) {
        in *= 1000;
        in = (int)in;
        in /= 1000;
        return in;
    }

    // Calculates Ψ from the constants A, B and k.
    private void schröd(int x) {
        Complex exp = k[x].times(x*scale);
        Psi[x] = (float)(A[x].times(exp.ePow()).plus(B[x].times(exp.negate().ePow()))).r;
    }

    // Calculates the constants A and B based on the previous constant.
    private void A(int x) {
        A[x] = A[x-1].times(k[x-1].minus(k[x]).times(x*scale).ePow()).times(one.plus(k[x-1].divide(k[x]))).plus(B[x-1].times(k[x-1].plus(k[x]).negate().times(x*scale).ePow()).times(one.minus(k[x-1].divide(k[x])))).divide(new Complex(2, 0));
    }
    private void B(int x) {
        B[x] = (A[x-1].times(k[x-1].times(x*scale).ePow()).plus(B[x-1].times(k[x-1].negate().times(x*scale).ePow())).minus(A[x].times(k[x].times(x*scale).ePow()))).times(k[x].times(x*scale).ePow());
    }

    // Calculates k for the specific energy at a given point.
    private void k(int x) {
        k[x] = Complex.sqrt(-2*m*(E-V(x))).divide(new Complex (ħ, 0));
    }

    // Calculates the potential energy based on a given formula.
    private static double V(double x) {
        // x is in nm.
        x *= scale;
        return x*x/e/10000000000000000000.0;
        //return Math.log(x)*x/1000000000000000000000000000000000000000000000000000000000000000000000000.0;
        //return Math.log(x+1)/100000000000000000000000000000000000000000000000000000000000000000000.0;
        /*x *= scale;
        double V = 0.00000000000000000000000000000004;
        for(int i = 0; i < 22; i++) {
            V -= e*e/value(x-i*1000/21*scale+500*scale)/22;
        }
        if(V == Double.NaN || V == -1/0.0 || V < 0)
            V = 0;
        return V*6.2415091e18;*/
    }

    // Normalizes Ψ and initializes a few parameters that are needed to display the function.
    private void norm() {
        int i = 0;
        for(; i < l; i++) {
            if(Psi[i] != Psi[i])
                Psi[i] = 0;
        }
        i--;
        int x;
        for(;; i--) {
            if(Math.signum(Psi[i]-Psi[i-1]) == Math.signum(Psi[i-2]-Psi[i-1]) || (Psi[i] == Psi[i-1])) {
                x = i-1;
                break;
            }
        }
        PsiSquared = new float[Psi.length];
        float base = Psi[x]/Psi[x-1];
        for(i = x+1; i < l; i++) {
            Psi[i] = base*Psi[i-1];
        }
        float sum = 0;
        for(i = 0; i < l; i++) {
            sum += Psi[i]*Psi[i];
        }
        System.out.println(sum);
        sum = 1/(float)Math.sqrt(sum);
        float max = 0;
        float min = 0;
        for(i = 0; i < l; i++) {
            Psi[i] = Psi[i]*sum;
            if(Psi[i] <= min)
                min = Psi[i];
            if(Psi[i] >= max)
                max = Psi[i];
            PsiSquared[i] = Psi[i]*Psi[i];
        }
        minY = round(min);
        maxY = round(max);
        if(!sym) {
            if(minY <= -maxY)
                maxY = -minY;
            else
                minY = -maxY;
        }
        averageY = round(((minY+maxY)/2));
        minX = -l;
        maxX = l;
        averageX = 0;
        scaleY = round((height/(maxY-minY)));
        ΔY = round(((maxY)*scaleY));
        scaleX = (float)(width/(2.0*l));
    }

    // Finds the nth energy level in the given potential function.
    private void rebuild(int n, int l) {
        this.l = l;
        Psi = new float[l];
        A = new Complex[l];
        B = new Complex[l];
        k = new Complex[l];
        sym = (n&1) == 1;
        if(sym) {
            A[0] = new Complex(1, 0);
            B[0] = new Complex(1, 0);
        }
        else {
            A[0] = new Complex(0, 1);
            B[0] = new Complex(0, -1);
        }
        E = 1.0e-200;
        double factor = 1000;
        boolean changed = false;
        l /= 20;
        scale *= 20;
        while(true) {
            E *= factor;
            k(0);
            schröd(0);
            for (int i = 1; i < l; i++) {
                k(i);
                A(i);
                B(i);
                schröd(i);
            }
            int nulls = sym ? 0:1;
            for(int j = 1; j < l; j++) {
                if(Psi[j]*Psi[j-1] < 0) {
                    nulls += 2;
                }
            }
            if(!changed && factor < 1.1) {
                l = this.l;
                scale /= 20;
                changed = true;
            }
            if(factor == 1)
                break;
            if(nulls-n > 0) {
                E /= factor;
                factor = 1+(factor-1)*0.11;
            }
        }
        norm();
    }

    // Initializes the needed objects, and calculates the first function.
    public Schrödinger(int y) {
        this.y = y;
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(20);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        rebuild(1, 500);
        ch1 = new CheckBox(40, y+height+40, 30, true);         // Ψ
        ch2 = new CheckBox(40, y+height+100, 30, false);       //|Ψ|²
        ch3 = new CheckBox(40, y+height+160, 30, false);       // Hide labeling
        add(ch1);
        add(ch2);
        add(ch3);
        add(new Label(100, y+height+45, 30, "Show Ψ", Paint.Align.LEFT));
        add(new Label(100, y+height+105, 30, "Show |Ψ|²", Paint.Align.LEFT));
        add(new Label(100, y+height+165, 30, "Hide labeling", Paint.Align.LEFT));
        add(new Label(40, y+height+230, 20, "Choose an energy level:", Paint.Align.LEFT));
        add(new Button(310, y+height+190, 40, 30, "+", this));
        add(new Button(310, y+height+220, 40, 30, "–", this));
        add(new Button(30, y+height+280, 310, 60, "Reload wave function", this));
        //add(ch3);
    }

    @Override
    public void paint(Graphics g) {
        g.fillRect(40+width/2, y, 1, height, Color.BLUE);
        g.fillRect(40, y+(int)ΔY, width-40, 1, Color.BLUE);
        if(ch1.value) {
            for(int i = 0; i < l-1; i++) {
                g.drawLine(width/2+scaleX*i+40, y-scaleY*Psi[i]+ΔY, width/2+scaleX*(i+1)+40, y-scaleY*Psi[i+1]+ΔY, p);
                if(sym)
                    g.drawLine(width/2-scaleX*i+40, y-scaleY*Psi[i]+ΔY, width/2-scaleX*(i+1)+40, y-scaleY*Psi[i+1]+ΔY, p);
                else
                    g.drawLine(width/2-scaleX*i+40, y+scaleY*Psi[i]+ΔY, width/2-scaleX*(i+1)+40, y+scaleY*Psi[i+1]+ΔY, p);
            }
        }
        p.setColor(Color.rgb(255, 127, 0));
        if(ch2.value) {
            for(int i = 0; i < l-1; i++) {
                g.drawLine(width/2+scaleX*i+40, y-scaleY*PsiSquared[i]+ΔY, width/2+scaleX*(i+1)+40, y-scaleY*PsiSquared[i+1]+ΔY, p);
                g.drawLine(width/2-scaleX*i+40, y-scaleY*PsiSquared[i]+ΔY, width/2-scaleX*(i+1)+40, y-scaleY*PsiSquared[i+1]+ΔY, p);
            }
        }
        p.setColor(Color.WHITE);
        p.setTextSize(20);
        p.setTextAlign(Paint.Align.RIGHT);
        g.drawString(String.valueOf(energylevel), 300, y+height+230, p);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(20);
        p.setColor(Color.YELLOW);
        if(!ch3.value) {
            g.drawString(String.valueOf(minY), 40+width/2, y+height, p);
            g.drawString(String.valueOf(minX), 40, y+(int)ΔY+16, p);
            g.drawString(String.valueOf(maxY), 40+width/2, y, p);
            g.drawString(String.valueOf(maxX), 40+width, y+(int)ΔY+16, p);
            g.drawString(String.valueOf(averageY), 40+width/2, y+height/2, p);
            g.drawString(String.valueOf(averageX), 40+width/2, y+(int)ΔY+16, p);
        }
        super.paint(g);
    }

    // WIP
    @Override
    public void actionPerformed(int n) {
        switch(n) {
            case 0:
                energylevel++;
                if(energylevel == 81)
                    energylevel = 80;
                break;
            case 1:
                energylevel--;
                if(energylevel == 0)
                    energylevel = 1;
                break;
            case 2:
                rebuild(energylevel, l);
                break;
        }
    }
}
