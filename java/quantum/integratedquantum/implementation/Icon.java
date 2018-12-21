package quantum.integratedquantum.implementation;

import android.graphics.Bitmap;

public class Icon extends Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap icon;

    public Icon(int x, int y, Bitmap i) {
        this. x = x;
        this.y = y;
        icon = i;
        width = i.getWidth();
        height = i.getHeight();
    }
    public Icon(int x, int y, int width, int height, Bitmap i) {
        this. x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        icon = i;
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(icon, x, y, width, height);
        super.paint(g);
    }
    public int getSize() {
        return height;
    }
}
