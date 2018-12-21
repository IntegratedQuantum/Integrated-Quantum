package quantum.integratedquantum.implementation;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import quantum.integratedquantum.Hyperlinking;

public class IntelligentText extends Component {
	public enum Type {
        normal,
        superScript,
        subScript,
        italic,
        integral,
        operator,
        link,
        bigger,
    }
    private int x;
    private int y;
    private List <Integer> enter;
    private List <Word> words;
    private Paint paint;
    private int rows;
    private Type mode;
    public IntelligentText(int x, int y, String text, Hyperlinking link) {
        enter = new ArrayList<>();
        words = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.WHITE);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        this.x = x;
        this.y = y;
        int length = 500;
        String current = "   ";
        int total = 0;
        int last = 0;
        float n = 0;
        StringBuilder sourceName = new StringBuilder();
        StringBuilder dirName = new StringBuilder();
        int hyperx1 = 0;
        int hypery1 = 0;
        for(int i = 0; i < text.length(); i++) {
            if(mode == Type.normal) {
                if(text.charAt(i) == '\\') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    enter.add(words.size()-last);
                    last = words.size();
                    current = "  ";
                    total = i;
                    n = 0;
                    rows++;
                }
                else if(text.charAt(i) == '^') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    mode = Type.superScript;
                    current = "";
                }
                else if(text.charAt(i) == '_') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    mode = Type.subScript;
                    current = "";
                }
                else if(text.charAt(i) == '²') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    mode = Type.italic;
                    current = "";
                }
                else if(text.charAt(i) == '³') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    i++;
                    words.add(new Word(String.valueOf(text.charAt(i)), Type.operator, (int)paint.measureText(String.valueOf(text.charAt(i)))));
                    current = "";
                }
                else if(text.charAt(i) == 'Ħ') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    current = "";
                    i++;
                    for(; text.charAt(i) != 'Ħ'; i++)
                        sourceName.append(text.charAt(i));
                    i++;
                    for(; text.charAt(i) != 'Ħ'; i++)
                        dirName.append(text.charAt(i));
                    mode = Type.link;
                    hyperx1 = (int)n+x+10;
                    hypery1 = rows*30+y;
                }
                else if(i-total != 0 || text.charAt(i) != ' ') {
                    current += String.valueOf(text.charAt(i));
                    n += paint.measureText(String.valueOf(text.charAt(i)));
                    if(text.charAt(i) == " ".charAt(0) && i-total > 1 && n > 320) {
                        words.add(new Word(current, mode, (int)paint.measureText(current)));
                        current = "";
                    }
                    if(n > length) {
                        total = i;
                        n = paint.measureText(current);
                        rows++;
                        enter.add(words.size()-last);
                        last = words.size();
                    }
                }
            }
            else if(mode == Type.link) {
                if(text.charAt(i) == 'Ħ') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    mode = Type.normal;
                    System.out.println(current);
                    this.add(new Hyperlink(sourceName.toString(), dirName.toString(), link, hyperx1, hypery1, (int)n+x, rows*30+y));
                    sourceName = new StringBuilder();
                    dirName = new StringBuilder();
                    current = "";
                }
                else {
                    current += String.valueOf(text.charAt(i));
                    n += paint.measureText(String.valueOf(text.charAt(i)));
                    if(text.charAt(i) == " ".charAt(0) && i-total > 1 && n > 320) {
                        words.add(new Word(current, mode, (int)paint.measureText(current)));
                        current = "";
                    }
                    if(n > length) {
                        if((int)n+x == hyperx1 && rows*30+y == hypery1) {
                            hyperx1 = x+10;
                            hypery1 += 30;
                        }
                        total = i;
                        n = paint.measureText(current);
                        rows++;
                        enter.add(words.size()-last);
                        last = words.size();
                    }
                }
            }
            else if(mode == Type.italic) {
                if(text.charAt(i) == '²') {
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    current = "";
                    mode = Type.normal;
                }
                else if(!(i-total == 1 && text.charAt(i) == ' ')) {
                    current += String.valueOf(text.charAt(i));
                    n += paint.measureText(String.valueOf(text.charAt(i)));
                    if(n > length && text.charAt(i) == " ".charAt(0)) {
                        words.add(new Word(current, mode, (int)paint.measureText(current)));
                        enter.add(words.size()-last);
                        last = words.size();
                        current = "";
                        total = i;
                        n = 0;
                        rows++;
                    }
                }
            }
            else {
                if(text.charAt(i) == ' ') {
                    paint.setTextSize(10);
                    words.add(new Word(current, mode, (int)paint.measureText(current)));
                    paint.setTextSize(20);
                    current = "";
                    mode = Type.normal;
                }
                else
                    current += String.valueOf(text.charAt(i));
            }
        }
    }
    public int rows() {
        return rows;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int total = 0;
        for(int i = 0; i < rows; i++) {
            int shift = 10;
            for(int j = 0; j < enter.get(i); j++) {
                words.get(total).paint(g, x+shift, y+30+i*30, paint);
                shift += words.get(total).size();
                total++;
            }
        }
    }
    @Override
    public void translate(int x, int y) {
        super.translate(x, y);
        for(int i = 0; i < size; i++) {
            if(data[i].getClass() == Hyperlink.class) {
                data[i].maxX = maxX;
                data[i].maxY = maxY;
            }
        }
    }
}
