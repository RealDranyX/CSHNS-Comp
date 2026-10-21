import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

class ProductArt extends JPanel {
    private final String item;
    ProductArt(String item) { this.item = item; setPreferredSize(new Dimension(150, 130)); setBackground(new Color(239, 234, 222)); }

    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();
        g.setColor(new Color(229, 222, 207)); g.fillOval(w / 2 - 46, h / 2 - 45, 92, 92);
        switch (item) {
            case "Sword": sword(g, w, h); break;
            case "Plate Armor": armor(g, w, h); break;
            case "Chain Mail": mail(g, w, h); break;
            default: pot(g, w, h);
        }
        g.dispose();
    }

    private void sword(Graphics2D g, int w, int h) {
        Path2D blade = new Path2D.Float();
        blade.moveTo(w * .5, h * .16); blade.lineTo(w * .59, h * .68); blade.lineTo(w * .5, h * .78); blade.lineTo(w * .41, h * .68); blade.closePath();
        g.setColor(new Color(125, 133, 133)); g.fill(blade);
        g.setColor(new Color(174, 137, 71)); g.setStroke(new BasicStroke(5)); g.drawLine((int)(w*.32), (int)(h*.68), (int)(w*.68), (int)(h*.68));
        g.setColor(new Color(112, 72, 48)); g.setStroke(new BasicStroke(7)); g.drawLine(w/2, (int)(h*.73), w/2, (int)(h*.9));
    }

    private void armor(Graphics2D g, int w, int h) {
        g.setColor(new Color(130, 139, 139)); g.fillRoundRect((int)(w*.42), (int)(h*.2), (int)(w*.16), (int)(h*.27), 18, 18);
        g.setColor(new Color(98, 108, 108)); g.fillRoundRect((int)(w*.31), (int)(h*.43), (int)(w*.38), (int)(h*.43), 20, 18);
        g.setColor(new Color(181, 146, 77)); g.setStroke(new BasicStroke(3)); g.drawLine(w/2, (int)(h*.47), w/2, (int)(h*.83));
    }

    private void mail(Graphics2D g, int w, int h) {
        int x=(int)(w*.31), y=(int)(h*.27), width=(int)(w*.38);
        g.setColor(new Color(103, 113, 114)); g.fillRoundRect(x, y, width, (int)(h*.54), 18, 18);
        g.setColor(new Color(197, 201, 194)); g.setStroke(new BasicStroke(2));
        for (int r=0; r<5; r++) for (int c=0; c<5; c++) g.drawOval(x+8+c*9+(r%2)*4, y+7+r*10, 8, 8);
    }

    private void pot(Graphics2D g, int w, int h) {
        g.setColor(new Color(99, 108, 108)); g.fillRoundRect((int)(w*.32), (int)(h*.4), (int)(w*.36), (int)(h*.4), 14, 14);
        g.fillRoundRect((int)(w*.25), (int)(h*.44), 12, 28, 8, 8); g.fillRoundRect((int)(w*.68), (int)(h*.44), 12, 28, 8, 8);
        g.setColor(new Color(174, 137, 71)); g.setStroke(new BasicStroke(4)); g.drawLine((int)(w*.29), (int)(h*.4), (int)(w*.71), (int)(h*.4));
    }
}
