package quantum.integratedquantum.implementation;

import android.graphics.Bitmap;

import quantum.integratedquantum.app.Assets;
import quantum.integratedquantum.app.file.SimpleFileReader;

public class Animation extends Component {
    private Bitmap[] i;
    private int time;
    private long currentTime;
    private int stadium;
    private int y;
    public Animation(int y, String text, App app) {
        String special = SimpleFileReader.readFile(Assets.n+"/"+text+".txt", app);
        int length = 0;
        StringBuilder current = new StringBuilder();
        for(int i = 0; i < special.length(); i++) {
            if(special.charAt(i) == "\\".charAt(0)) {
                length = Integer.parseInt(current.toString());
                time = Integer.parseInt(special.substring(i+1));
                break;
            }
            else
                current.append(String.valueOf(special.charAt(i)));
        }
        stadium = 0;
        i = new Bitmap[length];
        for(int i = 0; i < length; i++)
            this.i[i] = app.getGraphics().newImage(Assets.n+"/"+text+(i+1)+".png", Bitmap.Config.ARGB_8888);
        currentTime = System.currentTimeMillis()+time;
        this.y = y;
    }
    public int getHeight() {return i[0].getHeight();}
    @Override
    public void paint(Graphics g) {
        if(System.currentTimeMillis() > currentTime) {
            currentTime += time;
            stadium++;
            if(stadium == i.length)
                stadium = 0;
        }
        g.drawImage(i[stadium], 0, y);
    }
}
