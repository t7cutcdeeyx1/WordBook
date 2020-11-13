package com.example.wordbook;

import org.litepal.crud.DataSupport;

public class Data1 extends DataSupport {
    int id;
    String name;
    String jieshi;
    String lijv;

    public Data1(){
        super();
    }

    public Data1(String name, String jieshi, String lijv){
        this.name = name;
        this.jieshi = jieshi;
        this.lijv = lijv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJieshi() {
        return jieshi;
    }

    public void setJieshi(String jieshi) {
        this.jieshi = jieshi;
    }

    public String getLijv() {
        return lijv;
    }

    public void setLijv(String lijv) {
        this.lijv = lijv;
    }
}
