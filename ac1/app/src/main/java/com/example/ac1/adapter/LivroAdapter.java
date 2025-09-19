package com.example.ac1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac1.R;
import com.example.ac1.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {
    private List<Livro> livros;
    private List<Livro> livrosSelecionados;
    private OnLivroClickListener listener;

    public interface OnLivroClickListener {
        void onLivroClick(Livro livro);
    }

    public LivroAdapter(List<Livro> livros) {
        this.livros = livros != null ? livros : new ArrayList<>();
        this.livrosSelecionados = new ArrayList<>();
    }

    public void setOnLivroClickListener(OnLivroClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_livro, parent, false);
        return new LivroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        Livro livro = livros.get(position);
        holder.bind(livro);
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public void atualizarLista(List<Livro> novosLivros) {
        this.livros = novosLivros != null ? novosLivros : new ArrayList<>();
        this.livrosSelecionados.clear();
        notifyDataSetChanged();
    }

    public List<Livro> getLivrosSelecionados() {
        return new ArrayList<>(livrosSelecionados);
    }

    public void limparSelecoes() {
        livrosSelecionados.clear();
        notifyDataSetChanged();
    }

    class LivroViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitulo;
        private TextView textAutor;
        private TextView textStatus;
        private CheckBox checkBoxSelecionar;

        public LivroViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textAutor = itemView.findViewById(R.id.textAutor);
            textStatus = itemView.findViewById(R.id.textStatus);
            checkBoxSelecionar = itemView.findViewById(R.id.checkBoxSelecionar);

            checkBoxSelecionar.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Livro livro = livros.get(getBindingAdapterPosition());
                if (isChecked) {
                    if (!livrosSelecionados.contains(livro)) {
                        livrosSelecionados.add(livro);
                    }
                } else {
                    livrosSelecionados.remove(livro);
                }
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLivroClick(livros.get(getBindingAdapterPosition()));
                }
            });
        }

        public void bind(Livro livro) {
            textTitulo.setText(livro.getTitulo());
            textAutor.setText(livro.getAutor());
            textStatus.setText(livro.isLido() ? "Lido" : "Não lido");
            textStatus.setTextColor(itemView.getContext().getColor(
                    livro.isLido() ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
            checkBoxSelecionar.setChecked(livrosSelecionados.contains(livro));
        }
    }
}
