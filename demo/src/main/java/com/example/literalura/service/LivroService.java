package com.example.literalura.service;

import com.example.literalura.model.LivrosSalvarBanco;
import com.example.literalura.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivrosRepository livrosRepository;

    @Autowired
    public LivroService(LivrosRepository livrosRepository) {
        this.livrosRepository = livrosRepository;
    }

    public void salvarLivro(LivrosSalvarBanco livro) {
        String titulo = livro.getTitulo();
        if (!livrosRepository.existsByTitulo(titulo)) {
            livrosRepository.save(livro);
        }
    }

    public List<LivrosSalvarBanco> listarTodosLivros() {
        return livrosRepository.findAllFetchIdiomas();
    }

    public List<String> listarTodosAutores() {
        return livrosRepository.findAllUniqueAuthors();
    }

    public List<LivrosSalvarBanco> listarAutoresVivosEmAno(Integer ano) {
        return livrosRepository.findAuthorsAliveInYear(ano);
    }

    public List<LivrosSalvarBanco> buscarPorIdioma(String idioma) {
        return livrosRepository.findByIdiomasContaining(idioma);
    }
}