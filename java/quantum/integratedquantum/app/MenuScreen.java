package quantum.integratedquantum.app;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.implementation.App;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Button;
import quantum.integratedquantum.implementation.Icon;
import quantum.integratedquantum.implementation.Label;
import quantum.integratedquantum.implementation.Panel;
import quantum.integratedquantum.implementation.Screen;
import quantum.integratedquantum.implementation.SwitchPanel;

class MenuScreen extends Screen implements ActionListener {
    private Paint paint;
    private String [][] name;
    private String [][] subName;
    MenuScreen(App app, main m) {
        super(app, m);
        add(new Icon(250, 0, 280, 120, Assets.ds));
        name = new String[][]{
            {"Introduction", "Black Body Radiation", "Interference", "Wave Function", "Superposition", "Wave Particle Dualism I", "Wave Particle Dualism II", "Tunnel Effect", "Uncertainty Principle", "Particles", "Light", "Pauli Exclusion", "Spin", "Vacuum Fluctuation", "Quantum Entanglement", "Interpretations"},
            {"Notice", "Differentiation", "Integration", "Imaginary Numbers", "Complex Numbers", "Differential Equations", "Partial Differential Equations"},
            {"Schrödinger Equation", "Basic Calculations I", "Quantum Well", "Quantum Well II", "Basic Calculations II", "Basic Calculations III", "Operators", "Superposition", "Wave Particle Dualism II", "Tunnel Effect", "Uncertainty Principle", "Electronvolt", "Pauli Exclusion", "Spin"},
            {"Numerical Approach I"},
            {"Settings"},
        };
        subName = new String[][]{
            {"", "", "", "", "", "Double Slit Experiment", "Photoelectric Effect", "", "", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", ""},
            {"", "constant Potential", "", "Finite Potential Well", "non-radial symmetric Potential", "radial symmetric Potential", "", "", "Photoelectric Effect", "", "", "", "", ""},
            {"1D Symmetric Potential"},
            {" "},
        };
        String [] name2 = {"Principles of Quantum Physics", "Mathematical Foundation", "Quantum Principle Calculations", "Numerical Solution", "Extras"};
        Panel[] ai = new Panel[5];
        ai[0] = new Panel(0, 1790, 0, 180, 540, 830, Assets.bgP);
        ai[1] = new Panel(0, 350, 0, 180, 540, 830, Assets.bgP);
        ai[2] = new Panel(0, 1470, 0, 180, 540, 830, Assets.bgP);
        ai[3] = new Panel(0, 0, 0, 180, 540, 830, Assets.bgP);
        ai[4] = new Panel(0, 0, 0, 180, 540, 830, Assets.bgP);
        for(int j = 0; j < ai.length; j++) {
            for(int i = 0; i < name[j].length; i++) {
                Button a = new Button(10, 10 + 160 * i, 520, 150, name[j][i], this);
                a.add(new Label(270, 120 + 160 * i, 20, subName[j][i], Paint.Align.CENTER, Color.WHITE));
                ai[j].add(a);
            }
        }
        add(ai[0]);
        n = 0;
        add(new SwitchPanel(0, 130, 540, 50, ai, this, 1, name2));
        paint = new Paint();
        paint.setColor(Color.rgb(150, 150, 255));
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 540, 130, Color.rgb(64, 0, 0));
        g.drawString("Integrated", 120, 50, paint);
        g.drawString("Quantum", 120, 100, paint);
        super.paintOnly(0, g);
    }
    @Override
    public void backButton() {
        super.backButton();
        System.exit(1);
    }
    @Override
    public void actionPerformed(int n) {
        switch(subName[Assets.n][n]) {
            case "":
                app.setScreen(new TextScreen(app, m, name[Assets.n][n], Assets.n));
                break;
            case " ":
                app.setScreen(new SettingsScreen(app, m));
                break;
            default:
                app.setScreen(new TextScreen(app, m, subName[Assets.n][n], Assets.n));
                break;
        }
    }
}
