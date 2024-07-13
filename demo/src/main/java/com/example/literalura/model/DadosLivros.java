package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("results") List<DadosLivro> results) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DadosLivro(
            @JsonAlias("title") String titulo,
            @JsonAlias("authors") List<Autor> autores,
            @JsonAlias("languages") List<String> idiomas) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Autor(
            @JsonAlias("name") String nome,
            @JsonAlias("birth_year") Integer anoDeNascimento,
            @JsonAlias("death_year") Integer anoDeMorte) {
    }
}