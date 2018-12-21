package quantum.integratedquantum.app;

import android.content.Context;
import android.content.SharedPreferences;

import quantum.integratedquantum.implementation.App;

public class main extends App {
    @Override
    public LoadingScreen getInitScreen() {
        return new LoadingScreen(this, this);
    }
    public void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Assets.doubleSpeed = sp.getBoolean("doubleSpeed", Assets.doubleSpeed);
        Assets.requirements = sp.getBoolean("requirements", Assets.requirements);
    }
    public void saveData() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("doubleSpeed", Assets.doubleSpeed);
        editor.putBoolean("requirements", Assets.requirements);
        editor.apply();
    }
}
