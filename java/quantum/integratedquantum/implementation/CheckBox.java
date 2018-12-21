package quantum.integratedquantum.implementation;

import android.graphics.Color;

import java.util.List;

import quantum.integratedquantum.app.Assets;

public class CheckBox extends Component {
	public boolean value;
	private int x;
	private int y;
	private int tX;
	private int tY;
	private int size;
	public CheckBox(int x, int y, int size, boolean value) {
		this.x = x-size/2;
		this.y = y-size/2;
		this.size = size;
		this.value = value;
	}
	@Override
	public void update(List<Input.TouchEvent> touchEvents) {
		super.update(touchEvents);
		int n = touchEvents.size();
		for(int i = 0; i < n; i++) {
			Input.TouchEvent event = touchEvents.get(i);
			if (event.x >= x+tX && event.x <= x+tX+size && event.y >= y+tY && event.y <= y+tY+size) {
				if(event.type == Input.TouchEvent.TOUCH_UP)
					value = !value;
			}
		}
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(x, y, size, size, Color.WHITE);
		if(value)
			g.drawImage(Assets.cross, x, y, size+1, size+1);
	}
	public void translate(int x, int y) {
		tX = x;
		tY = y;
	}
}
