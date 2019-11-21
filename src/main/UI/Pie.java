package ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Pie extends JComponent {

    public Color col = new Color(0,0,0,0);
    public int arc = 0;
    public int startAngle = 0;
    public int defaultSize = 400;
    public int size = defaultSize;
    public int xoffset = 107;
    public int yoffset = 70;

    @Override
    public void paint(Graphics p) {
        p.setColor(col);
        p.fillArc(xoffset, yoffset,size,size,startAngle,arc);
    }
}
