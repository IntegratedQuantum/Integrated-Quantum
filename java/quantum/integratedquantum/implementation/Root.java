package quantum.integratedquantum.implementation;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import static quantum.integratedquantum.implementation.IntelligentText.Type.normal;

public class Root extends Word {
    private List<Word> words;
    Root(String text, Paint paint) {
        words = new ArrayList<>();
        words.add(new Word("√", IntelligentText.Type.bigger, measureText(paint, "√", IntelligentText.Type.bigger)));
        IntelligentText.Type mode = IntelligentText.Type.normal;
        StringBuilder current = new StringBuilder();
        for(int i = 0; i < text.length(); i++) {
            if (mode == normal) {
                if (text.charAt(i) == '^') {
                    words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                    current = new StringBuilder();
                    mode = IntelligentText.Type.superScript;
                } else if (text.charAt(i) == '_') {
                    words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                    current = new StringBuilder();
                    mode = IntelligentText.Type.subScript;
                } else
                    current.append(text.charAt(i));
            } else {
                if (text.charAt(i) == ' ') {
                    words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                    current = new StringBuilder();
                    mode = normal;
                } else
                    current.append(text.charAt(i));
            }
        }
        words.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
        for(int i = 0; i < words.size(); i++)
            size += words.get(i).size();
    }
    private int measureText(Paint p, String current, IntelligentText.Type mode) {
        if(mode == IntelligentText.Type.normal || mode == IntelligentText.Type.link || mode == IntelligentText.Type.italic)
            return (int)p.measureText(current);
        if(mode == IntelligentText.Type.bigger)
            p.setTextSize(25);
        else
            p.setTextSize(10);
        int ret = (int)p.measureText(current);
        p.setTextSize(20);
        return ret;
    }
    public void paint(Graphics g, int x, int y, Paint p) {
        int shift = 0;
        for (int i = 0; i < words.size(); i++) {
            words.get(i).paint(g, x+shift, y, p);
            shift += words.get(i).size();
        }
        g.drawLine(x+12, y-17, x+size, y-17, p);
    }
}
