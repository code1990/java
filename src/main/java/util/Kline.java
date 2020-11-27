package util;

/**
 * @author 911
 * @date 2020-11-21 21:41
 */
public class Kline {

    private Double high;
    private Double low;
    private Double open;
    private Double close;

    public Kline() {
    }

    public Kline(Double open, Double high, Double low, Double close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public boolean isRed() {
        return this.close >= this.open;
    }

    public boolean isGreen() {
        return this.close < this.open;
    }

    public double getUpLine() {
        if (isRed()) {
            return this.high - this.close;
        } else {
            return this.high - this.open;
        }
    }

    public double getDownLine() {
        if (isRed()) {
            return this.open - this.low;
        } else {
            return this.close - this.low;
        }
    }

    public double getBody() {
        if (isRed()) {
            return this.close - this.open;
        } else {
            return this.open - this.close;
        }
    }

    public double getHalf() {
        if (isRed()) {
            return this.open + 0.5 * getBody();
        } else {
            return this.close + 0.5 * getBody();
        }
    }

    public double getLineLength() {
        return this.high - this.open;
    }

    @Override
    public String toString() {
        return "Kline{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                '}';
    }


}
