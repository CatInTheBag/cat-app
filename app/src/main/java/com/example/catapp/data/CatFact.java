package com.example.catapp.data;

public class CatFact {

    private String header;
    private String fact;
    private int length;

    public CatFact(String header, String fact, int length){
        this.header = header;
        this.fact = fact;
        this.length = length;
    }

    public String getHeader() {
        return header;
    }

    public String getFact(){
        return fact;
    }
}

