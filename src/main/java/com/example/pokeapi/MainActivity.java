package com.example.pokeapi;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.pokeapi.POJO.PokemonItem;
import com.example.pokeapi.POJO.Result;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pokeapi.PokemonActivity.POSITION_STAT_ATTACK;
import static com.example.pokeapi.PokemonActivity.POSITION_STAT_DEFENSE;
import static com.example.pokeapi.PokemonActivity.POSITION_STAT_HP;


public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    final private int OFFSET = 30;
    final private int MAX_RANDOM_OFFSET = 934;

    private LinearLayoutManager layoutManager;
    private ClientPokeapi clientPokeapi;
    private RecyclerView recyclerView;
    private ServicePokeapi apiService;
    private Adapter adapter;

    private ArrayList<PokemonItem> pokemonItems;

    private int count;
    private int offset;
    private int maxIndexPokemonStat;

    private boolean checkBoxAttack;
    private boolean checkBoxDefense;
    private boolean checkBoxHp;
    private boolean checkBoxClick;

    CheckBox attackView;
    CheckBox defenseView;
    CheckBox hpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        loadListOfPokemon(offset);
        pagination();
    }

    private void initialization() {
        clientPokeapi = new ClientPokeapi();
        apiService = clientPokeapi.getClient().create(ServicePokeapi.class);

        adapter = new Adapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.listOfPokemon);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);

        pokemonItems = new ArrayList<>();
        offset = 0;
        count = 0;
        maxIndexPokemonStat = 0;

        checkBoxAttack = false;
        checkBoxDefense = false;
        checkBoxHp = false;
        checkBoxClick = false;

        attackView = findViewById(R.id.maxCheckBoxAttack);
        defenseView = findViewById(R.id.maxCheckBoxDefense);
        hpView = findViewById(R.id.maxCheckBoxHp);

    }

    private void loadListOfPokemon(int offset) {
        Toast toast = Toast.makeText(getApplicationContext(), "Loading the list...", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Call<PokemonItem> call = apiService.getItems(30, offset);
        call.enqueue(new Callback<PokemonItem>() {
            @Override
            public void onResponse(Call<PokemonItem> call, Response<PokemonItem> response) {
                List<Result> items = response.body().getResults();
                for (int i = 0; i < items.size(); i++) {
                    loadPokemonItem(items.get(i));
                    //A delay for receiving a response from the server
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPokemonItem(Result item) {
        Call<PokemonItem> call = apiService.getPokemonItem(item.getName());
        call.enqueue(new Callback<PokemonItem>() {
            @Override
            public void onResponse(Call<PokemonItem> call, Response<PokemonItem> response) {
                PokemonItem pokemonItem = response.body();
                addPokemonToList(pokemonItem);
            }

            @Override
            public void onFailure(Call<PokemonItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addPokemonToList(PokemonItem pokemonItem) {
        pokemonItems.add(pokemonItem);
        count += 1;
        if (count == OFFSET) {
            adapter.setData(pokemonItems, getApplicationContext());
            count = 0;
        }
    }

    private void pagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        offset += OFFSET;
                        loadListOfPokemon(offset);
                    }
                }
            }
        });
    }

    @Override
    public void openPokemonActivity(int position) {
        Intent intent = new Intent(MainActivity.this, PokemonActivity.class);
        intent.putExtra(PokemonItem.class.getSimpleName(), pokemonItems.get(position));
        startActivity(intent);
    }

    public void loadRandomPosition(View view) {
        pokemonItems.clear();
        adapter.background = false;
        checkBoxClick = false;

        attackView.setChecked(false);
        defenseView.setChecked(false);
        hpView.setChecked(false);

        checkBoxAttack = false;
        checkBoxDefense = false;
        checkBoxHp = false;

        offset = (int) (Math.random() * MAX_RANDOM_OFFSET);
        loadListOfPokemon(offset);

        Toast toast = Toast.makeText(getApplicationContext(), "Lading list from " + offset + " position", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void setPokemonWithMaxStat(View view) {

        if (checkBoxClick) {
            pokemonItems.add(maxIndexPokemonStat + 1, pokemonItems.get(0));
            pokemonItems.remove(0);
            adapter.setData(pokemonItems, getApplicationContext());
            adapter.changeBackground();
        }

        checkBoxClick = true;

        if (view.getId() == R.id.maxCheckBoxAttack) {
            checkBoxAttack = !checkBoxAttack;
        }
        if (view.getId() == R.id.maxCheckBoxDefense) {
            checkBoxDefense = !checkBoxDefense;
        }
        if (view.getId() == R.id.maxCheckBoxHp) {
            checkBoxHp = !checkBoxHp;
        }

        if (checkBoxAttack || checkBoxDefense || checkBoxHp) {

            maxIndexPokemonStat = findMaxStatIndex(checkBoxAttack, checkBoxDefense, checkBoxHp);
            pokemonItems.add(0, pokemonItems.get(maxIndexPokemonStat));
            pokemonItems.remove(maxIndexPokemonStat + 1);
            adapter.setData(pokemonItems, getApplicationContext());
            adapter.changeBackground();
            recyclerView.smoothScrollToPosition(0);
        } else {
            checkBoxClick = false;
            recyclerView.smoothScrollToPosition(0);
        }
    }

    private int findMaxStatIndex(boolean checkBoxAttack, boolean checkBoxDefense, boolean checkBoxHp) {
        int maxIndexPokemonStat = 0;
        int maxSumPokemonStat = findSumPokemonStat(maxIndexPokemonStat, checkBoxAttack, checkBoxDefense, checkBoxHp);

        for (int currentIndex = 1; currentIndex < pokemonItems.size(); currentIndex++) {
            int currentSumPokemonStat = findSumPokemonStat(currentIndex, checkBoxAttack, checkBoxDefense, checkBoxHp);

            if (currentSumPokemonStat > maxSumPokemonStat) {
                maxSumPokemonStat = currentSumPokemonStat;
                maxIndexPokemonStat = currentIndex;
            }
        }
        return maxIndexPokemonStat;
    }

    private int findSumPokemonStat(int position, boolean checkBoxAttack, boolean checkBoxDefense, boolean checkBoxHp) {
        int pokemonStat = 0;
        int pokemonAttack = Integer.parseInt(pokemonItems.get(position).getStat().get(POSITION_STAT_ATTACK).getBaseStat());
        int pokemonDefense = Integer.parseInt(pokemonItems.get(position).getStat().get(POSITION_STAT_DEFENSE).getBaseStat());
        int pokemonHp = Integer.parseInt(pokemonItems.get(position).getStat().get(POSITION_STAT_HP).getBaseStat());

        if (checkBoxAttack) {
            pokemonStat += pokemonAttack;
        }
        if (checkBoxDefense) {
            pokemonStat += pokemonDefense;
        }
        if (checkBoxHp) {
            pokemonStat += pokemonHp;
        }
        return pokemonStat;
    }
}
