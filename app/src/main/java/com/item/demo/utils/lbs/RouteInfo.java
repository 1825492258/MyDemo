package com.item.demo.utils.lbs;

/**
 * Created by wuzongjie on 2017/11/3.
 */

public class RouteInfo {
    // 两点之间的距离
    private float distance;
    // 预计价格
    private float taxiCost;
    // 预计行车时间
    private int duration;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getTaxiCost() {
        return taxiCost;
    }

    public void setTaxiCost(float taxiCost) {
        this.taxiCost = taxiCost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "distance=" + distance +
                ", taxiCost=" + taxiCost +
                ", duration=" + duration +
                '}';
    }
}
