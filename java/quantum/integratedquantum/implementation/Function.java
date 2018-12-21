package quantum.integratedquantum.implementation;

import quantum.integratedquantum.implementation.functions.BlackBody;
import quantum.integratedquantum.implementation.functions.Differentiation;
import quantum.integratedquantum.implementation.functions.Tunnel;

public class Function {
    private static int[] heights = {700, 500, 300};
    public static Component getFunction(int x, int y, Component parent, int n) {
        switch(n) {
            case 0:
                return new BlackBody(parent, x, y);
            case 1:
                return new Differentiation(x, y);
            case 2:
                return new Tunnel(y);
        }
        return null;
    }
    public static int getHeight(int n) {
        return heights[n];
    }
}
