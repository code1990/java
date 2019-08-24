import java.io.Serializable;

/**
 * @author issuser
 * @date 2019-08-24 11:12
 * Serializable Cloneable 都是一种标识 表示一种能力
 */
public class StreamEntity implements Serializable,Cloneable {

    private double price;
    private Integer amount;
    private transient String title;

    public StreamEntity(double price, Integer amount, String title) {
        this.price = price;
        this.amount = amount;
        this.title = title;
    }

    public StreamEntity(double price, Integer amount) {
        this.price = price;
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "StreamEntity{" +
                "price=" + price +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                '}';
    }

    /*对象克隆 重写clone才能实现深度克隆引用对象 */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
