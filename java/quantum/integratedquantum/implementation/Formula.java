package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import static quantum.integratedquantum.implementation.IntelligentText.Type.integral;
import static quantum.integratedquantum.implementation.IntelligentText.Type.italic;
import static quantum.integratedquantum.implementation.IntelligentText.Type.link;
import static quantum.integratedquantum.implementation.IntelligentText.Type.normal;
import static quantum.integratedquantum.implementation.IntelligentText.Type.operator;

public class Formula extends Component {
    private Paint paint;
    int x;
    int y;
    private List<Word> words;
    public Formula(int x, int y, String text) {
        paint = new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.WHITE);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        this.x = x;
        this.y = y+20;
        words = new ArrayList<>();
        IntelligentText.Type mode = normal;
        StringBuilder current = new StringBuilder();
        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '{') {
                words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                StringBuilder frac = new StringBuilder();
                i++;
                boolean second = false;
                for(; i < text.length(); i++) {
                    if(text.charAt(i) == '}') {
                        if(second)
                            break;
                        second = true;
                    }
                    frac.append(text.charAt(i));
                }
                words.add(new Fraction(frac.toString(), paint));
                current = new StringBuilder();
                mode = normal;
            }
            else if(text.charAt(i) == '√') {
                words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                StringBuilder root = new StringBuilder();
                i++;
                for(; i < text.length(); i++) {
                    if(text.charAt(i) == '√')
                        break;
                    root.append(text.charAt(i));
                }
                words.add(new Root(root.toString(), paint));
                current = new StringBuilder();
                mode = normal;
            }
            else {
                if(mode == normal) {
                    if(text.charAt(i) == '^') {
                        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        current = new StringBuilder();
                        mode = IntelligentText.Type.superScript;
                    }
                    else if(text.charAt(i) == '_') {
                        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        current = new StringBuilder();
                        mode = IntelligentText.Type.subScript;
                    }
                    else if(text.charAt(i) == '∫') {
                        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        words.add(new Word("∫", integral, measureText(paint, "∫", integral)));
                        current = new StringBuilder();
                    }
                    else if(text.charAt(i) == '³') {
                        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        i++;
                        words.add(new Word(String.valueOf(text.charAt(i)), operator, measureText(paint, String.valueOf(text.charAt(i)), operator)));
                        current = new StringBuilder();
                    }
                    else
                        current.append(String.valueOf(text.charAt(i)));
                }
                else {
                    if(text.charAt(i) == ' ') {
                        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        current = new StringBuilder();
                        mode = normal;
                    }
                    else
                        current.append(String.valueOf(text.charAt(i)));
                }
            }
        }
        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
    }
    private int measureText(Paint p, String current, IntelligentText.Type mode) {
        if(mode == normal || mode == link || mode == italic || mode == operator)
            return (int)p.measureText(current);
        if(mode == integral)
            paint.setTextSize(40);
        p.setTextSize(10);
        int ret = (int)p.measureText(current);
        p.setTextSize(20);
        return ret;
    }
    @Override
    public void paint(Graphics g) {
        paint.setTextAlign(Paint.Align.LEFT);
        int shift = 10;
        for(int i = 0; i < words.size(); i++) {
            words.get(i).paint(g, x + shift, y, paint);
            shift += words.get(i).size();
        }
    }
}
