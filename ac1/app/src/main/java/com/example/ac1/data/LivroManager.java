package com.example.ac1.data;

import com.example.ac1.model.Livro;
import java.util.ArrayList;
import java.util.List;

public class LivroManager {
    private static LivroManager instance;
    private List<Livro> livros;
    private int nextId = 1;

    private LivroManager() {
        livros = new ArrayList<>();
    }

    public static LivroManager getInstance() {
        if (instance == null) {
            instance = new LivroManager();
        }
        return instance;
    }

    public void adicionarLivro(Livro livro) {
        livro.setId(nextId++);
        livros.add(livro);
    }

    public List<Livro> getTodosLivros() {
        return new ArrayList<>(livros);
    }

    public List<Livro> getLivrosNaoLidos() {
        List<Livro> naoLidos = new ArrayList<>();
        for (Livro livro : livros) {
            if (!livro.isLido()) {
                naoLidos.add(livro);
            }
        }
        return naoLidos;
    }

    public List<Livro> getLivrosLidos() {
        List<Livro> lidos = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.isLido()) {
                lidos.add(livro);
            }
        }
        return lidos;
    }

    public void marcarComoLido(int livroId) {
        for (Livro livro : livros) {
            if (livro.getId() == livroId) {
                livro.setLido(true);
                break;
            }
        }
    }

    public void marcarComoNaoLido(int livroId) {
        for (Livro livro : livros) {
            if (livro.getId() == livroId) {
                livro.setLido(false);
                break;
            }
        }
    }

    public void removerLivro(int livroId) {
        livros.removeIf(livro -> livro.getId() == livroId);
    }
}
