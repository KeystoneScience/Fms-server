package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListStructure {
    private List<String> data = new ArrayList<>();

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getRandom(){
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }
}
