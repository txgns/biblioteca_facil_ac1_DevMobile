package com.example.ac1.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac1.R;
import com.example.ac1.adapter.LivroAdapter;
import com.example.ac1.data.LivroManager;
import com.example.ac1.databinding.FragmentGalleryBinding;
import com.example.ac1.model.Livro;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private RecyclerView recyclerViewLivros;
    private Button buttonMarcarLidos;
    private TextView textEmpty;
    private LivroAdapter livroAdapter;
    private LivroManager livroManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar componentes
        livroManager = LivroManager.getInstance();
        recyclerViewLivros = root.findViewById(R.id.recyclerViewLivros);
        buttonMarcarLidos = root.findViewById(R.id.buttonMarcarLidos);
        textEmpty = root.findViewById(R.id.textEmpty);

        // Configurar RecyclerView
        recyclerViewLivros.setLayoutManager(new LinearLayoutManager(getContext()));
        livroAdapter = new LivroAdapter(livroManager.getTodosLivros());
        recyclerViewLivros.setAdapter(livroAdapter);

        // Configurar listener do botão
        buttonMarcarLidos.setOnClickListener(v -> marcarLivrosComoLidos());

        // Atualizar lista quando o fragmento for exibido
        atualizarLista();

        return root;
    }





    @Override
    public void onResume() {
        super.onResume();
        // Atualizar lista sempre que o fragmento for exibido
        atualizarLista();
    }

    private void atualizarLista() {
        List<Livro> livros = livroManager.getTodosLivros();
        livroAdapter.atualizarLista(livros);
        
        if (livros.isEmpty()) {
            recyclerViewLivros.setVisibility(View.GONE);
            textEmpty.setVisibility(View.VISIBLE);
            buttonMarcarLidos.setVisibility(View.GONE);
        } else {
            recyclerViewLivros.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.GONE);
            buttonMarcarLidos.setVisibility(View.VISIBLE);
        }
    }

    private void marcarLivrosComoLidos() {
        List<Livro> livrosSelecionados = livroAdapter.getLivrosSelecionados();
        
        if (livrosSelecionados.isEmpty()) {
            Toast.makeText(getContext(), "Selecione pelo menos um livro!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Marcar livros como lidos
        for (Livro livro : livrosSelecionados) {
            livroManager.marcarComoLido(livro.getId());
        }

        // Limpar seleções e atualizar lista
        livroAdapter.limparSelecoes();
        atualizarLista();

        String mensagem = livrosSelecionados.size() == 1 ? 
                "1 livro marcado como lido!" : 
                livrosSelecionados.size() + " livros marcados como lidos!";
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}