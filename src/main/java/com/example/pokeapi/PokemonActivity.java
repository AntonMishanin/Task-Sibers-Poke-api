package com.example.pokeapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeapi.POJO.PokemonItem;
import com.squareup.picasso.Picasso;


public class PokemonActivity extends AppCompatActivity {

    static final int POSITION_STAT_DEFENSE = 3;
    static final int POSITION_STAT_ATTACK = 4;
    static final int POSITION_STAT_HP = 5;

    PokemonItem pokemonItem;

    ImageView pokemonImageView;
    TextView pokemonHeightView;
    TextView pokemonWeightView;
    TextView pokemonTypeView;
    TextView pokemonAttackView;
    TextView pokemonDefenseView;
    TextView pokemonHpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_layout);

        initialization();
        getPokemonFromMainActivity();
        setDataInView(pokemonItem);
    }

    private void initialization() {
        pokemonImageView = findViewById(R.id.imageViewPokemon);
        pokemonHeightView = findViewById(R.id.pokemonHeight);
        pokemonWeightView = findViewById(R.id.pokemonWeight);
        pokemonTypeView = findViewById(R.id.pokemonType);
        pokemonAttackView = findViewById(R.id.attack);
        pokemonDefenseView = findViewById(R.id.defense);
        pokemonHpView = findViewById(R.id.hp);
    }


    private void getPokemonFromMainActivity() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            pokemonItem = (PokemonItem) arguments.getSerializable(PokemonItem.class.getSimpleName());
        }
    }

    private void setDataInView(PokemonItem pokemonItem) {
        pokemonHeightView.setText(pokemonItem.getHeight());
        pokemonWeightView.setText(pokemonItem.getWeight());
        pokemonAttackView.setText(pokemonItem.getStat().get(POSITION_STAT_ATTACK).getBaseStat());
        pokemonDefenseView.setText(pokemonItem.getStat().get(POSITION_STAT_DEFENSE).getBaseStat());
        pokemonHpView.setText(pokemonItem.getStat().get(POSITION_STAT_HP).getBaseStat());
        pokemonTypeView.setText(pokemonTypesToString());

        Picasso.with(PokemonActivity.this)
                .load(pokemonItem.getSprites().getImagePokemon())
                .placeholder(R.drawable.ic_launcher_background)
                .into(pokemonImageView);
    }

    private String pokemonTypesToString(){
        String pokemonTypes = "";
        for (int i = 0; i < pokemonItem.getTypes().size(); i++) {
            pokemonTypes += pokemonItem.getTypes().get(i).getType().getName();
            if (i + 1 < pokemonItem.getTypes().size()) {
                pokemonTypes += ", ";
            }
        }
        return pokemonTypes;
    }
}
