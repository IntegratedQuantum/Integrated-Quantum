package quantum.integratedquantum.implementation;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import static quantum.integratedquantum.implementation.IntelligentText.Type.normal;

public class Fraction extends Word {
    private List<Word> top;
    private List<Word> bot;
    private int topSize;
    private int botSize;
    Fraction(String text, Paint paint) {
        top = new ArrayList<>();
        bot = new ArrayList<>();

        IntelligentText.Type mode = IntelligentText.Type.normal;
        StringBuilder current = new StringBuilder();
        int stadium = 0;
        for(int i = 0; i < text.length(); i++) {
            switch (stadium) {
                case 0:
                    if (text.charAt(i) == '}') {
                        stadium++;
                        i += 2;
                        top.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                        current = new StringBuilder();
                        mode = normal;
                    } else {
                        if (mode == normal) {
                            if (text.charAt(i) == '^') {
                                top.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                                current = new StringBuilder();
                                mode = IntelligentText.Type.superScript;
                            } else if (text.charAt(i) == '_') {
                                top.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                                current = new StringBuilder();
                                mode = IntelligentText.Type.subScript;
                            } else
                                current.append(text.charAt(i));
                        } else {
                            if (text.charAt(i) == ' ') {
                                top.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                                current = new StringBuilder();
                                mode = normal;
                            } else
                                current.append(text.charAt(i));
                        }
                    }
                    break;
                case 1:
                    if (mode == normal) {
                        if (text.charAt(i) == '^') {
                            bot.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                            current = new StringBuilder();
                            mode = IntelligentText.Type.superScript;
                        } else if (text.charAt(i) == '_') {
                            bot.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                            current = new StringBuilder();
                            mode = IntelligentText.Type.subScript;
                        } else
                            current.append(text.charAt(i));
                    } else {
                        if (text.charAt(i) == ' ') {
                            bot.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
                            current = new StringBuilder();
                            mode = normal;
                        } else
                            current.append(text.charAt(i));
                    }
                    break;
            }
        }
        bot.add(new Word(current.toString(), mode, measureText(paint, current.toString(), mode)));
        for(int i = 0; i < top.size(); i++)
            topSize += top.get(i).size();
        for(int i = 0; i < bot.size(); i++)
            botSize += bot.get(i).size();



        size = topSize >= botSize ? topSize : botSize;
    }
    private int measureText(Paint p, String current, IntelligentText.Type mode) {
        if(mode == IntelligentText.Type.normal || mode == IntelligentText.Type.link || mode == IntelligentText.Type.italic)
            return (int)p.measureText(current);
        p.setTextSize(10);
        int ret = (int)p.measureText(current);
        p.setTextSize(20);
        return ret;
    }
    public void paint(Graphics g, int x, int y, Paint p) {
        int shift = 0;
        for (int i = 0; i < top.size(); i++) {
            top.get(i).paint(g, x+shift-topSize/2+size/2, y-14, p);
            shift += top.get(i).size();
        }
        shift = 0;
        for (int i = 0; i < bot.size(); i++) {
            bot.get(i).paint(g, x+shift-botSize/2+size/2, y+13, p);
            shift += bot.get(i).size();
        }
        g.drawLine(x, y-7, x+size, y-7, p);
    }
}
