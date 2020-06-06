package base.course4;

/**
 * 三种基本结构
 * 1.顺序执行  按照代码的先后顺序 往后执行
 * 2.分支  if（true）{代码块1} else{代码块2}  如果条件为真 执行代码块1 否则执行代码块2
 * 3循环 重复的事情不断做 遇到指定条件则调出循环 不再执行
 * 死循环 一致执行 直到程序终止 内存耗尽 需要避免死循环
 * 循环涉及到2个关键字 break 直接跳出当前循环 终止循环
 * continue 结束本次循环 继续下次循环 类似于暂停
 */
public class JavaStructure {

    /**
     * int i =9; i+= i;(i =i +i) i =18
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        // 计算10人的阶乘
        int number = 10;

        // 0和1的阶乘都为1
        if (number == 0 || number == 1) {
            System.out.print(1);
        }else {
            //循环累乘 10
            int count = 1;
            for (int i = number; i > 0; i--) {
               // count *= i;
                count = count * i;
            }
            System.out.println(count);
        }
        System.out.println();

        // 打印九九乘法表
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + (i * j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        //打印金子塔
        for (int i = 1; i <= 5; i++) {
            for (int j = 5 - i; j > 0; j--) {
                System.out.print(" ");
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }



        //分支语句
        int i = 5;
        //switch 开关
        switch(i){
            //如果i == 0 打印0 跳出循环
            case 0:
                System.out.println("0");break;
            case 1:
                System.out.println("1");break;
            case 2:
                System.out.println("2");break;
//                上面的全部没有匹配上 打印 default 跳出循环
            default:
                System.out.println("default");break;
        }


    }
}
