import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    protected int x, y, width, height;
    protected boolean isDead = false;

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
    public boolean isDead() { return isDead; }
    public void setDead(boolean dead) { this.isDead = dead;}
    public abstract void draw(Graphics g);
}