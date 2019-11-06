package com.example.pokeapi;

import com.example.pokeapi.POJO.PokemonItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicePokeapi {

    @GET("api/v2/pokemon/")
    Call<PokemonItem> getItems(@Query("limit") int limit, @Query("offset") int offset);

    @GET("api/v2/pokemon/{name}/")
    Call<PokemonItem> getPokemonItem(@Path("name") String pokemonName);
}
