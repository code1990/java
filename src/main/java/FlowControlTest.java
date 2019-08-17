/**
 * @author issuser
 * @date 2019-08-17 10:49
 */
public class FlowControlTest {
    public static void main(String[] args) {
        /*程序语言分为3种结构 1.顺序结构 2.分支结构 3.循环结构*/
        /*1.顺序结构从上到下依次执行*/
        /*2.分支 if(true)  if(true)else{} if((true)else if(true){} else{} switch case*/
        int index = 0;
        boolean flag = (index % 2 == 0 ? true : false);
        if (flag) {
            System.out.println(index + "是偶数");
        }

        int score = (int) (Math.random() * 100);
        if (score >= 60) {
            System.out.println("成绩及格");
        } else {
            System.out.println("成绩不及格");
        }

        int year = 2020;
        if (year % 4 == 0 && !(year % 10 == 0)) {
            System.out.println("是闰年");
        } else if (year % 400 == 0) {
            System.out.println("是闰年");
        } else {
            System.out.println("不是闰年");
        }

        /*当if else的分支条件大于等于4个 采取switch case语句*/
        int level = score / 10;
        switch (level) {
            case 9:
                System.out.println("成绩优秀");
                break;
            case 8:
                System.out.println("成绩良好");
                break;
            case 7:
                System.out.println("成绩一般");
                break;
            case 6:
                System.out.println("成绩及格");
                break;
            default:
                System.out.println("成绩不及格");
                break;
        }

        /*3.while(true){} do{}while()*/
        int sum = 0;
        int k = 0;
        while (k <= 5) {
            sum += sum;
            k++;
        }
        System.out.println(sum);
        sum = 0;
        k = 0;
        do {
            sum += sum;
            k++;
        } while (k <= 5);

        /*4 for循环 foreach循环*/
        int sum2 = 0;
        for (int i = 0; i <= 5; i++) {
            sum2 += sum2;
        }
        System.out.println(sum2);
        for (char c : "Hello".toCharArray()) {
            System.out.println(c);
        }
        /*5.循环控制 break 结束循环 continue结束本次循环 继续下次循环*/
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0) {
                break;
            } else {
                System.out.println(i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0) {
                continue;
            } else {
                System.out.println(i);
            }
        }
    }
}
