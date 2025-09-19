package com.example.ac1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac1.R;
import com.example.ac1.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroLidoAdapter extends RecyclerView.Adapter<LivroLidoAdapter.LivroLidoViewHolder> {
    private List<Livro> livrosLidos;

    public LivroLidoAdapter(List<Livro> livrosLidos) {
        this.livrosLidos = livrosLidos != null ? livrosLidos : new ArrayList<>();
    }

    @NonNull
    @Override
    public LivroLidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_livro_lido, parent, false);
        return new LivroLidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroLidoViewHolder holder, int position) {
        Livro livro = livrosLidos.get(position);
        holder.bind(livro);
    }

    @Override
    public int getItemCount() {
        return livrosLidos.size();
    }

    public void atualizarLista(List<Livro> novosLivrosLidos) {
        this.livrosLidos = novosLivrosLidos != null ? novosLivrosLidos : new ArrayList<>();
        notifyDataSetChanged();
    }

    class LivroLidoViewHolder extends RecyclerView.ViewHolder {
        private TextView textTituloLido;
        private TextView textAutorLido;
        private TextView textStatusLido;

        public LivroLidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textTituloLido = itemView.findViewById(R.id.textTituloLido);
            textAutorLido = itemView.findViewById(R.id.textAutorLido);
            textStatusLido = itemView.findViewById(R.id.textStatusLido);
        }

        public void bind(Livro livro) {
            textTituloLido.setText(livro.getTitulo());
            textAutorLido.setText(livro.getAutor());
            textStatusLido.setText("✓ LIDO");
        }
    }
}
