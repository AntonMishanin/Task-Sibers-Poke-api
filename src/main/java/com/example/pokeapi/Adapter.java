package com.example.pokeapi;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokeapi.POJO.PokemonItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.pokeapi.PokemonActivity.POSITION_STAT_ATTACK;
import static com.example.pokeapi.PokemonActivity.POSITION_STAT_DEFENSE;
import static com.example.pokeapi.PokemonActivity.POSITION_STAT_HP;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<PokemonItem> pokemonItems = new ArrayList<>();
    private OnItemClickListener listener;
    boolean background = false;

    public interface OnItemClickListener {
        void openPokemonActivity(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<PokemonItem> repos, Context context) {
        pokemonItems.clear();
        pokemonItems.addAll(repos);
        this.context = context;
        notifyDataSetChanged();
    }

    void changeBackground() {
        background = !background;
        notifyDataSetChanged();
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(pokemonItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return pokemonItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView pokemonName;
        TextView pokemonAttack;
        TextView pokemonDefense;
        TextView pokemonHp;
        ImageView imageViewPokemon;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonAttack = itemView.findViewById(R.id.textViewAttack);
            pokemonDefense = itemView.findViewById(R.id.textViewDefense);
            pokemonHp = itemView.findViewById(R.id.textViewHp);
            imageViewPokemon = itemView.findViewById(R.id.imageViewPokemon);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.openPokemonActivity(position);
                        }
                    }
                }
            });
        }

        void bind(PokemonItem pokemonItem, int position) {
            pokemonName.setText(pokemonItem.getName());
            pokemonAttack.setText(pokemonItem.getStat().get(POSITION_STAT_ATTACK).getBaseStat());
            pokemonDefense.setText(pokemonItem.getStat().get(POSITION_STAT_DEFENSE).getBaseStat());
            pokemonHp.setText(pokemonItem.getStat().get(POSITION_STAT_HP).getBaseStat());

            Picasso.with(context)
                    .load(pokemonItem.getSprites().getImagePokemon())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageViewPokemon);

            if (background && position == 0) {
                linearLayout.setBackgroundColor(Color.parseColor(context.getString(R.string.color_strongest_pokemon)));
            }else {
                linearLayout.setBackgroundColor(Color.parseColor(context.getString(R.string.white)));
            }
        }
    }
}
