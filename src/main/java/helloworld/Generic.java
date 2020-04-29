package helloworld;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author issuser
 * @date 2019-08-21 23:44
 */
public class Generic implements GenericInterface {
    /*继承泛型类实现泛型接口*/

    @Override
    public void print(Object o) {

    }
}

/*泛型接口*/
interface GenericInterface<T> {
    /*泛型方法*/
    public void print(T t);
}

/*泛型类*/
class Point<T> {
    private T x;
    private T y;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

/*泛型上限*/
/*泛型限制向下造型*/
/* A<? extends List> a =null*/
/*泛型限制向上造型*/
/* A<? super List> a =null*/
class Message<T extends Number> {
    private T msg;

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    /*?表示任意类型的对象 ?解决参数传参的问题*/
    public void fun(Message<?> t) {
        System.out.println(t.getMsg());
    }
}