/**
 * @author issuser
 * @date 2019-08-24 11:12
 */
public class StreamEntity {

    private double price;
    private Integer amount;

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
}
