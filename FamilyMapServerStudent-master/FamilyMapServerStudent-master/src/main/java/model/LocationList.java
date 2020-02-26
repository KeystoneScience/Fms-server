package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationList {
    private List<Location> data = new ArrayList<>();

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }

    public Location getRandom(){
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }
}
