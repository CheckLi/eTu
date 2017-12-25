package com.yitu.etu.entity;


import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/25.
 */
public class AreaEntity {
    private String name;
    private List<CityEntity> city;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCity(List<CityEntity> city) {
        this.city = city;
    }
    public List<CityEntity> getCity() {
        return city;
    }

}
