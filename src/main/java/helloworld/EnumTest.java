package helloworld;

import java.text.SimpleDateFormat;

/**
 * @author issuser
 * @date 2019-08-21 23:23
 * 枚举的最大作用就是限制一个对象的产生格式
 */
public enum EnumTest {
    String(0, ""), Short(1, "0"), Integer(2, "0"), Long(3, "0"), Float(4, "0.0"),
    Double(5, "0.0"), BigInteger(6, "0"), BigDecimal(7, "0.0"), Date(8, new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
    // 成员变量私有化
    private final int index;
    private final String desc;

    // 构造方法私有化
    private EnumTest(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    //覆盖方法
    @Override
    public String toString() {
        return this.index + "_" + this.desc;
    }

    public int getIndex() {
        return this.index;
    }

    public String getDesc() {
        return this.desc;
    }

    public static Integer getValue(String desc) {
        EnumTest[] enumTests = values();
        for (EnumTest type : enumTests) {
            if (type.desc == desc) {
                return type.index;
            }
        }
        return null;
    }

    public static String getDesc(Integer value) {
        EnumTest[] enumTests = values();
        for (EnumTest type : enumTests) {
            if (type.index == value) {
                return type.desc;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "String";
        System.out.println(EnumTest.valueOf(str).index);
        System.out.println(EnumTest.valueOf(str).desc);
        System.out.println(EnumTest.getDesc(8));
        System.out.println(EnumTest.getValue("0.0"));
        String[] array = new String[]{"String", "Short", "Date"};
        for (int i = 0; i < array.length; i++) {
            switch (EnumTest.valueOf(array[i])) {
                case String:
                    System.out.println("String");
                    break;
                case Short:
                    System.out.println("short");
                    break;
                case Date:
                    System.out.println("date");
                    break;
                default:
                    break;
            }
        }

    }
}
