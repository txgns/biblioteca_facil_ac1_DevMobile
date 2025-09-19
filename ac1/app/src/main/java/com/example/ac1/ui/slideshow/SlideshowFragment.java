package com.example.ac1.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac1.R;
import com.example.ac1.adapter.LivroLidoAdapter;
import com.example.ac1.data.LivroManager;
import com.example.ac1.databinding.FragmentSlideshowBinding;
import com.example.ac1.model.Livro;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private RecyclerView recyclerViewLivrosLidos;
    private TextView textEmptyLidos;
    private LivroLidoAdapter livroLidoAdapter;
    private LivroManager livroManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar componentes
        livroManager = LivroManager.getInstance();
        recyclerViewLivrosLidos = root.findViewById(R.id.recyclerViewLivrosLidos);
        textEmptyLidos = root.findViewById(R.id.textEmptyLidos);

        // Configurar RecyclerView
        recyclerViewLivrosLidos.setLayoutManager(new LinearLayoutManager(getContext()));
        livroLidoAdapter = new LivroLidoAdapter(livroManager.getLivrosLidos());
        recyclerViewLivrosLidos.setAdapter(livroLidoAdapter);

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
        List<Livro> livrosLidos = livroManager.getLivrosLidos();
        livroLidoAdapter.atualizarLista(livrosLidos);
        
        if (livrosLidos.isEmpty()) {
            recyclerViewLivrosLidos.setVisibility(View.GONE);
            textEmptyLidos.setVisibility(View.VISIBLE);
        } else {
            recyclerViewLivrosLidos.setVisibility(View.VISIBLE);
            textEmptyLidos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}