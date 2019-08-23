/**
 * @author issuser
 * @date 2019-08-23 17:42
 */
public class ReflectEntityTest {


    private byte aByte;
    private char aChar;
    private int anInt;
    private short aShort;
    private long aLong;
    private double aDouble;
    private float aFloat;
    private boolean aBoolean;
    private String string;
    public String publicString;

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }

    public byte getaByte() {
        return aByte;
    }

    public void setaByte(byte aByte) {
        this.aByte = aByte;
    }

    public char getaChar() {
        return aChar;
    }

    public void setaChar(char aChar) {
        this.aChar = aChar;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public short getaShort() {
        return aShort;
    }

    public void setaShort(short aShort) {
        this.aShort = aShort;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public ReflectEntityTest() {
    }

    public ReflectEntityTest(String string) {
        this.string = string;
    }

    public ReflectEntityTest(byte aByte, char aChar, int anInt, short aShort, long aLong, double aDouble, float aFloat, boolean aBoolean, String string) {
        this.aByte = aByte;
        this.aChar = aChar;
        this.anInt = anInt;
        this.aShort = aShort;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
        this.aBoolean = aBoolean;
        this.string = string;
    }

    @Override
    public String toString() {
        return "ReflectEntityTest{" +
                "aByte=" + aByte +
                ", aChar=" + aChar +
                ", anInt=" + anInt +
                ", aShort=" + aShort +
                ", aLong=" + aLong +
                ", aDouble=" + aDouble +
                ", aFloat=" + aFloat +
                ", aBoolean=" + aBoolean +
                ", string='" + string + '\'' +
                '}';
    }
}
