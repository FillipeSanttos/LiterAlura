package com.example.literalura;

import com.example.literalura.principal.Principal;
import com.example.literalura.repository.LivrosRepository;
import com.example.literalura.service.ConverteDados;
import com.example.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraAplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraAplication.class, args);
    }

    @Autowired
    private ConverteDados converteDados;

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivrosRepository livroRepository;

    @Override
    public void run(String... args) throws Exception {

		Principal iniciarMenu = new Principal(converteDados,livroService,livroRepository);
		iniciarMenu.executarMenu();
    }
}