public abstract class Plant extends Entity {
    protected int hp;
    protected int cost; // THÊM THUỘC TÍNH NÀY

    // Thêm hàm để GameManager có thể lấy giá tiền
    public int getCost() {
        return cost;
    }

    public void takeDamage(int amount) {
        this.hp -= amount;
        if (this.hp <= 0) {
            this.isDead = true;
        }
    }

    public abstract void update(GameManager game);
}