package quantum.integratedquantum.implementation;

import java.util.Arrays;
import java.util.List;

public class Component {
	Component[] data = new Component[10];
	protected int size;
    int n;
    int maxY;
    int maxX;
    public void add(Component c) {
        if(c != this) {
            int n = 0;
            for(int i = 0; i < size; i++) {
                if(data[i].getClass() == Button.class)
                    n++;
            }
            c.setInt(n);
			if(size == data.length)
				data = Arrays.copyOf(data, data.length << 1);
			data[size] = c;
			size++;
        }
    }
    void set(int n, Component c) {
        c.setInt(data[n].getInt());
        data[n] = c;
    }
    public void paint(Graphics g) {
        for(int i = 0; i < size; i++)
            data[i].paint(g);
    }
	protected void paintOnly(int n, Graphics g) {
		data[n].paint(g);
	}
	protected void paintExcept(int n, Graphics g) {
		for(int i = 0; i < size; i++) {
			if(i != n)
				data[i].paint(g);
		}
	}
    public void setInt(int n) {
        this.n = n;
    }
    public int getInt() {
        return n;
    }
    public void update(List<Input.TouchEvent> touchEvents) {
        for(int i = 0; i < size; i++)
			data[i].update(touchEvents);
    }
    public void translate(int x, int y) {
		for(int i = 0; i < size; i++)
			data[i].translate(x, y);
    }
}
