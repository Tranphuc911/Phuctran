public abstract class Zombie extends Entity {
    protected int hp, speed;
    protected boolean isAttacking = false;

    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
    }

    public abstract void update();
    
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) this.isDead = true;
    }
}