package quantum.integratedquantum.app;

import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.Hyperlinking;
import quantum.integratedquantum.app.file.SimpleFileReader;
import quantum.integratedquantum.implementation.App;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Button;
import quantum.integratedquantum.implementation.Icon;
import quantum.integratedquantum.implementation.Label;
import quantum.integratedquantum.implementation.Panel;
import quantum.integratedquantum.implementation.Screen;
import quantum.integratedquantum.implementation.Animation;
import quantum.integratedquantum.implementation.Formula;
import quantum.integratedquantum.implementation.Function;
import quantum.integratedquantum.implementation.IntelligentText;

class TextScreen extends Screen implements ActionListener, Hyperlinking {
    private TextScreen previous;
    TextScreen(App app, main m, String name, int n) {
        super(app, m);
        add(new Icon(130, 0, 280, 120, Assets.ds));
        add(new Label(270, 75, 30, name, Paint.Align.CENTER, Color.rgb(150, 150, 255)));
        add(new Label(0, 90, 100, "←", Paint.Align.LEFT, Color.rgb(150, 150, 255)));
        Panel p = new Panel(0, 1000, 0, 130, 540, 830, Assets.bgP);
        String text = SimpleFileReader.readFile(n+"/"+name+".txt", app);
        int y = 0;
        StringBuilder txt;
        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '[') {
                if(text.charAt(i+1) == 't') {
                    txt = new StringBuilder(" ");
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        if(text.charAt(j) == '&') {
                            j++;
                            txt.append(String.valueOf(getGreekChar(text.charAt(j))));
                            continue;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    txt.append('\\');
                    IntelligentText iT = new IntelligentText(0, y, txt.toString(), this);
                    y += 30*iT.rows()+30;
                    p.add(iT);
                }
                else if(text.charAt(i+1) == 'p') {
                    txt = new StringBuilder();
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    Icon aI = new Icon(0, y, app.getGraphics().newImage(txt.toString(), Config.ARGB_8888));
                    y += aI.getSize();
                    p.add(aI);
                }
                else if(text.charAt(i+1) == 'f') {
                    txt = new StringBuilder();
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        if(text.charAt(j) == '&') {
                            j++;
                            txt.append(String.valueOf(getGreekChar(text.charAt(j))));
                            continue;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    Formula f = new Formula(0, y, txt.toString());
                    y += 60;
                    p.add(f);
                }
                else if(text.charAt(i+1) == 'a') {
                    txt = new StringBuilder();
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    Animation a = new Animation(y, txt.toString(), app);
                    y += 30+a.getHeight();
                    p.add(a);
                }
                else if(text.charAt(i+1) == 'd') {
                    txt = new StringBuilder();
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    p.add(Function.getFunction(20, y, p, Integer.parseInt(txt.toString())));
                    y += Function.getHeight(Integer.parseInt(txt.toString()));
                }
                else if(text.charAt(i+1) == 'r' && Assets.requirements) {
                    txt = new StringBuilder(" ");
                    for(int j = i+3; j < text.length(); j++) {
                        if(text.charAt(j) == '[') {
                            i = j-1;
                            break;
                        }
                        if(text.charAt(j) == '&') {
                            j++;
                            txt.append(String.valueOf(getGreekChar(text.charAt(j))));
                            continue;
                        }
                        txt.append(String.valueOf(text.charAt(j)));
                    }
                    txt.append('\\');
                    IntelligentText iT = new IntelligentText(0, y, txt.toString(), this);
                    y += 30*iT.rows()+30;
                    p.add(iT);
                }
            }
        }
        if(y < 830)
            y = 830;
        p.changeSize(0, y-830);
        add(p);
        add(new Button(0, 0, 130, 130, false, this));
    }

    private char getGreekChar(char c) {
        switch(c) {
            case 'a':
                return 'α'; //alpha
            case 'b':
                return 'β'; //beta
            case 'c':
                return 'χ'; //chi
            case 'd':
                return 'δ'; //delta
            case 'e':
                return 'ε'; //epsilon
            case 'f':
                return 'φ'; //phi
            case 'g':
                return 'γ'; //gamma
            case 'h':
                return 'θ'; //theta
            case 'i':
                return 'η'; //eta
            case 'j':
                return 'ι'; //iota
            case 'k':
                return 'κ'; //kappa
            case 'l':
                return 'λ'; //lambda
            case 'm':
                return 'µ'; //mu
            case 'n':
                return 'ν'; //nu
            case 'o':
                return 'ο'; //omicron
            case 'p':
                return 'π'; //pi
            case 'q':
                return 'ω'; //omega
            case 'r':
                return 'ρ'; //rho
            case 's':
                return 'σ'; //sigma
            case 't':
                return 'τ'; //tau
            case 'u':
                return 'υ'; //upsilon
            case 'v':
                return 'φ'; //phi
            case 'w':
                return 'ω'; //omega
            case 'x':
                return 'ξ'; //xi
            case 'y':
                return 'ψ'; //psi
            case 'z':
                return 'ζ'; //zeta
            case 'A':
                return 'Α'; //Alpha
            case 'B':
                return 'Β'; //Beta
            case 'C':
                return 'Χ'; //Chi
            case 'D':
                return 'Δ'; //Delta
            case 'E':
                return 'Ε'; //Epsilon
            case 'F':
                return 'Φ'; //Phi
            case 'G':
                return 'Γ'; //Gamma
            case 'H':
                return 'Θ'; //Theta
            case 'I':
                return 'Η'; //Eta
            case 'J':
                return 'Ι'; //Iota
            case 'K':
                return 'Κ'; //Kappa
            case 'L':
                return 'Λ'; //Lambda
            case 'M':
                return 'Μ'; //Mu
            case 'N':
                return 'Ν'; //Nu
            case 'O':
                return 'Ο'; //Omicron
            case 'P':
                return 'Π'; //Pi
            case 'Q':
                return 'Ω'; //Omega
            case 'R':
                return 'Ρ'; //Rho
            case 'S':
                return 'Σ'; //Sigma
            case 'T':
                return 'Τ'; //Tau
            case 'U':
                return 'Υ'; //Upsilon
            case 'V':
                return 'Φ'; //Phi
            case 'W':
                return 'Ω'; //Omega
            case 'X':
                return 'Ξ'; //Xi
            case 'Y':
                return 'Ψ'; //Psi
            case 'Z':
                return 'Ζ'; //Zeta
        }
        return c;
    }

    @Override
    public void paint() {
        Graphics g = app.getGraphics();
        super.paintOnly(3, g);
        g.fillRect(0, 0, 540, 130, Color.rgb(64, 0, 0));
        super.paintExcept(3, g);
    }
    @Override
    public void backButton() {
        super.backButton();
        if(previous == null)
            app.setScreen(new MenuScreen(app, m));
        else
            app.setScreen(previous);
    }

    @Override
    public void actionPerformed(int n) {
        if(previous == null)
            app.setScreen(new MenuScreen(app, m));
        else
            app.setScreen(previous);
    }
    @Override
    public void openLink(String s, int n) {
        TextScreen t = new TextScreen(app, m, s, n);
        t.previous = this;
        app.setScreen(t);
    }
}
