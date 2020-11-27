package chapter02;

/**
 * @author 911
 * @date 2020-11-26 18:13
 */
public class Test02Person {


    private final String name;

    public Test02Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        Test02PersonKoltin2 t = new Test02PersonKoltin2("name",false);
        System.out.println(t.isMarried());
        System.out.println(t.getName());
        t.setMarried(true);
    }
}
