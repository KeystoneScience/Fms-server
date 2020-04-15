package model;

import java.util.ArrayList;
import java.util.List;

import model.Person;

public class Family {
    Person fam;
    String relation;

    public Person getFam() {
        return fam;
    }

    public void setFam(Person fam) {
        this.fam = fam;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Family(Person fam, String relation) {
        this.fam = fam;
        this.relation = relation;
    }
}
