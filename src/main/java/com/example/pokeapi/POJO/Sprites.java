package com.example.pokeapi.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sprites implements Serializable {

    @SerializedName("front_default")
    @Expose
    private String imagePokemon;

    public String getImagePokemon() {
        return imagePokemon;
    }

    public void setImagePokemon(String next) {
        this.imagePokemon = imagePokemon;
    }
}
