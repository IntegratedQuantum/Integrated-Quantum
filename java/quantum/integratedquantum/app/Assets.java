package quantum.integratedquantum.app;

import android.graphics.Bitmap;

public class Assets {
    public static Bitmap b;
    static Bitmap bgP;
    static Bitmap ds;
    static Bitmap Q;
    public static Bitmap integral;
    public static Bitmap cross;
    public static int n;
    public static int size;
    //settings:
    public static boolean doubleSpeed = true;
    public static boolean requirements = true;
    
    public static int valueOf(int n) {
        if(n < 0)
            n *= -1;
        return n;
    }
}
