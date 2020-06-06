package base.course4;

public class TestData {
    public static void main(String[] args) {

        int i =3;
//        System.out.println(i+=i);//6
        int k =3;
//        System.out.println(k-=k);//0
        int j =3;
//        System.out.println(j*=j);//9
        int m =3;
//        System.out.println(m/=m);//1

        System.out.println(i);//3
        //i++ 先赋值 后加一
        //如下所示是赋值过程的结果
        System.out.println(i++);//i 先赋值 i后加1
        //如下所示是+1过程
        System.out.println(i);//4
        System.out.println("i--------------------");
        System.out.println(k);//3
        //i++  先加一 后赋值
        //如下所示是赋值过程
        System.out.println(++k);//4
        //如下所示是+1过程
        System.out.println(k);//4
        System.out.println("k--------------------");
        System.out.println(j);//3
        System.out.println(j--);//3
        System.out.println(j);//3
        System.out.println("j--------------------");
        System.out.println(m);//2
        System.out.println(--m);//2
        System.out.println(m);//3
        System.out.println("m--------------------");
        /**
         * 3
         * 3
         * 4
         * i--------------------
         * 3
         * 4
         * 4
         * k--------------------
         * 3
         * 3
         * 2
         * j--------------------
         * 3
         * 2
         * 2
         * m--------------------
         *
         *
         */
    }
}
