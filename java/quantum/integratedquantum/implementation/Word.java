package quantum.integratedquantum.implementation;

import android.graphics.Paint;

class Word {
    private String value;
    private IntelligentText.Type type;
    int size;
    Word(String str, IntelligentText.Type t, int s) {
        value = str;
        type = t;
        size = s;
    }
    Word() {}
    public void paint(Graphics g, int x, int y, Paint p) {
        g.drawString(value, x, y, p, type);
    }
    public int size() {
        return size;
    }
}
