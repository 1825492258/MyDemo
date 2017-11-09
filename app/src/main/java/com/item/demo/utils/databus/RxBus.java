package com.item.demo.utils.databus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuzongjie on 2017/11/1.
 * 用于发送消息
 */

public class RxBus {

    private static volatile RxBus instance;
    // 订阅者集合
    private Set<Object> subscibers;

    /**
     * 注册
     */
    public synchronized void register(Object subscriber) {
        subscibers.add(subscriber);
    }

    /**
     * 解除注册
     */
    public synchronized void unRegister(Object subscriber) {
        subscibers.remove(subscriber);
    }

    private RxBus() {
        subscibers = new CopyOnWriteArraySet<>();
    }

    public static synchronized RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 包装处理过程
     *
     * @param func
     */
    public void chainProcess(Function func) {
        Observable.just("")
                .subscribeOn(Schedulers.io()) // 指定处理过程在 IO 线程
                .map(func)   // 包装处理过程
                .observeOn(AndroidSchedulers.mainThread())  // 指定事件消费在 Main 线程
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o == null) {
                            return;
                        }
                        send(o);
                    }
                });
    }

    /**
     * 反射获取对象方法列表，判断
     * 1.是否被注释修饰
     * 2.参数是否和data类型一致
     *
     * @param target
     * @param data
     */
    private void callMethodByAnnotiation(Object target, Object data) {
        Method[] methodArray = target.getClass().getDeclaredMethods();
        for (int i = 0; i < methodArray.length; i++) {
            try {
                if (methodArray[i].isAnnotationPresent(RegisterBus.class)) {
                    // 被 @RegisterBus 修饰的方法
                    Class paramType = methodArray[i].getParameterTypes()[0];
                    if (data.getClass().getName().equals(paramType.getName())) {
                        // 参数类型和 data 一样，调用此方法
                        methodArray[i].invoke(target, new Object[]{data});
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void send(Object data) {
        for (Object subscriber : subscibers) {
            callMethodByAnnotiation(subscriber, data);
        }
    }
}
