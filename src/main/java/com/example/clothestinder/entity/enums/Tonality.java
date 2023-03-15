package com.example.clothestinder.entity.enums;

public enum Tonality { //todo
    CMINOR("Cminor"),
    DMINOR("Dminor"),
    EMINOR("Eminor");

    private String tonality;

    Tonality(String tonality) {
        this.tonality = tonality;
    }

    public String getCode() {
        return tonality;
    }

}
