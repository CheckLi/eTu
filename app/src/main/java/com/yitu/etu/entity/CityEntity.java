package com.yitu.etu.entity;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/25.
 */
public class CityEntity {
    private String name;
    private List<String> area;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
    public List<String> getArea() {
        return area;
    }
}
