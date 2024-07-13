package com.example.literalura.repository;

import com.example.literalura.model.LivrosSalvarBanco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivrosRepository extends JpaRepository<LivrosSalvarBanco, Long> {

    boolean existsByTitulo(String titulo);

    @Query("SELECT DISTINCT l FROM LivrosSalvarBanco l LEFT JOIN FETCH l.idiomas")
    List<LivrosSalvarBanco> findAllFetchIdiomas();

    @Query("SELECT DISTINCT l.nomeAutor FROM LivrosSalvarBanco l")
    List<String> findAllUniqueAuthors();

    @Query("SELECT l FROM LivrosSalvarBanco l WHERE l.anoDeMorte > :ano AND (l.anoDeNascimento IS NULL OR l.anoDeNascimento <= :ano)")
    List<LivrosSalvarBanco> findAuthorsAliveInYear(@Param("ano") Integer ano);

    List<LivrosSalvarBanco> findByIdiomasContaining(String idioma);
}