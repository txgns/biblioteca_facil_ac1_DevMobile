package com.example.ac1.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ac1.R;
import com.example.ac1.data.LivroManager;
import com.example.ac1.databinding.FragmentHomeBinding;
import com.example.ac1.model.Livro;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EditText editTextTitulo;
    private EditText editTextAutor;
    private CheckBox checkBoxLido;
    private Button buttonCadastrar;
    private LivroManager livroManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar componentes
        livroManager = LivroManager.getInstance();
        editTextTitulo = root.findViewById(R.id.editTextTitulo);
        editTextAutor = root.findViewById(R.id.editTextAutor);
        checkBoxLido = root.findViewById(R.id.checkBoxLido);
        buttonCadastrar = root.findViewById(R.id.buttonCadastrar);

        // Configurar listener do botão
        buttonCadastrar.setOnClickListener(v -> cadastrarLivro());

        return root;
    }

    private void cadastrarLivro() {
        String titulo = editTextTitulo.getText().toString().trim();
        String autor = editTextAutor.getText().toString().trim();
        boolean lido = checkBoxLido.isChecked();

        // Validação dos campos obrigatórios
        if (TextUtils.isEmpty(titulo)) {
            editTextTitulo.setError("Título é obrigatório");
            editTextTitulo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(autor)) {
            editTextAutor.setError("Autor é obrigatório");
            editTextAutor.requestFocus();
            return;
        }

        // Criar e adicionar o livro
        Livro livro = new Livro(titulo, autor, lido);
        livroManager.adicionarLivro(livro);

        // Limpar campos
        editTextTitulo.setText("");
        editTextAutor.setText("");
        checkBoxLido.setChecked(false);

        // Mostrar mensagem de sucesso
        String mensagem = lido ? "Livro cadastrado como lido!" : "Livro cadastrado com sucesso!";
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}