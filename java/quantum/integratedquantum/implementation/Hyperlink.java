package quantum.integratedquantum.implementation;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.Hyperlinking;

class Hyperlink extends Component implements ActionListener {
	private String source;
	private int n;
	private Hyperlinking h;
	Hyperlink(String source, String dir, Hyperlinking hyper, int x1, int y1, int x2, int y2) {
		this.source = source;
		n = Integer.parseInt(dir);
		h = hyper;
		if(y1 == y2)
			add(new Button(x1, y1, x2, 30, false, this));
		else {
			add(new Button(x1, y1, 520-x1, 30, false, this));
			for(int i = 1; i+1 < (y2-y1)/30; i++)
				add(new Button(20, y1+30*i, 500, 30, false, this));
			add(new Button(20, y2, x2, 30, false, this));
		}
	}
	@Override
	public void actionPerformed(int n) {
		h.openLink(source, this.n);
	}
	@Override
	public void translate(int x, int y) {
		super.translate(x, y);
		for(int i = 0; i < size; i++) {
			if(data[i].getClass() == Button.class) {
				data[i].maxX = maxX;
				data[i].maxY = maxY;
			}
		}
	}
}