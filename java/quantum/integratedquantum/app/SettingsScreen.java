package quantum.integratedquantum.app;

import android.graphics.Color;
import android.graphics.Paint;

import quantum.integratedquantum.ActionListener;
import quantum.integratedquantum.implementation.App;
import quantum.integratedquantum.implementation.Graphics;
import quantum.integratedquantum.implementation.Button;
import quantum.integratedquantum.implementation.CheckBox;
import quantum.integratedquantum.implementation.Icon;
import quantum.integratedquantum.implementation.Label;
import quantum.integratedquantum.implementation.Panel;
import quantum.integratedquantum.implementation.Screen;

public class SettingsScreen extends Screen implements ActionListener {
	private CheckBox[] ch;
	SettingsScreen(App app, main m) {
		super(app, m);
		ch = new CheckBox[2];
		ch[0] = new CheckBox(500, 45, 30, Assets.doubleSpeed);
		ch[1] = new CheckBox(500, 105, 30, !Assets.requirements);
		add(new Icon(130, 0, 280, 120, Assets.ds));
		add(new Label(270, 75, 30, "Settings", Paint.Align.CENTER, Color.rgb(150, 150, 255)));
		add(new Label(0, 90, 100, "←", Paint.Align.LEFT, Color.rgb(150, 150, 255)));
		add(new Button(0, 0, 130, 130, false, this));
		Panel p = new Panel(0, 0, 0, 130, 540, 830, Assets.bgP);
		add(p);
		p.add(new Label(20, 50, 30, "double scroll-speed", Paint.Align.LEFT));
		p.add(new Label(20, 110, 30, "disable Requirements", Paint.Align.LEFT));
		for(CheckBox aCh : ch) {
			p.add(aCh);
		}
		add(new Button(70, 800, 400, 100, "Apply Settings", this));
	}
	@Override
	public void paint() {
		Graphics g = app.getGraphics();
		g.fillRect(0, 0, 540, 960, Color.rgb(50, 50, 50));
		g.fillRect(0, 0, 540, 130, Color.rgb(64, 0, 0));
		super.paint(g);
	}
	@Override
	public void backButton() {
		super.backButton();
		app.setScreen(new MenuScreen(app, m));
	}
	@Override
	public void actionPerformed(int n) {
		switch(n) {
			case 0:
				app.setScreen(new MenuScreen(app, m));
				break;
			case 1:
				apply();
				m.saveData();
				app.setScreen(new MenuScreen(app, m));
				break;
		}
	}
	private void apply() {
		Assets.doubleSpeed = ch[0].value;
		Assets.requirements = !ch[1].value;
	}
}
