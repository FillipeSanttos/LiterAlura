package com.example.literalura.principal;


import com.example.literalura.model.DadosLivros;
import com.example.literalura.model.LivrosSalvarBanco;
import com.example.literalura.repository.LivrosRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConverteDados;
import com.example.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class Principal {

    @Autowired
    private ConverteDados converteDados;
    @Autowired
    private LivroService livroService;
    @Autowired
    private LivrosRepository livroRepository;

    @Autowired
    public Principal(ConverteDados converteDados, LivroService livroService, LivrosRepository livroRepository) {
        this.converteDados = converteDados;
        this.livroService = livroService;
        this.livroRepository = livroRepository;
    }

    Scanner leitorDeDados = new Scanner(System.in);
    ConsumoAPI consumoAPI = new ConsumoAPI();
    int opcao = -1;

    public void executarMenu() throws IOException, InterruptedException {

        while (opcao != 0) {
            var menu = """
                    ********** LITERALURA ************
                                        
                    1 - Buscar LIVRO pelo TÍTULO	
                    2 - Listar LIVROS registrados
                    3 - Listar AUTORES registrados
                    4 - Listar AUTORES vivos em determinado ANO
                    5 - Listar LIVROS em um determinado idioma
                    0 - Sair
                    					
                    Digite uma opção:
                    """;

            System.out.println(menu);
            opcao = leitorDeDados.nextInt();
            leitorDeDados.nextLine();


            switch (opcao) {
                case 1:

                    System.out.println("Escreva o nome do livro:");
                    var nomedolivro = leitorDeDados.nextLine();
                    var informacoeslivro = consumoAPI.obterDados(nomedolivro);
                    DadosLivros resultado = converteDados.obterDados(informacoeslivro, DadosLivros.class);

                    for (DadosLivros.DadosLivro dadosLivro : resultado.results()) {
                        for (DadosLivros.Autor autor : dadosLivro.autores()) {
                            LivrosSalvarBanco livro = new LivrosSalvarBanco();
                            livro.setTitulo(dadosLivro.titulo());
                            livro.setNomeAutor(autor.nome());
                            livro.setAnoDeNascimento(autor.anoDeNascimento());
                            livro.setAnoDeMorte(autor.anoDeMorte());
                            livro.setIdiomas(dadosLivro.idiomas());
                            livroService.salvarLivro(livro);


                            System.out.println("""
                                    ********** TÍTULO ESCOLHIDO ************
                                    Título: %s
                                    Autor: %s
                                    Idiomas publicados: %s
                                    ************************************************
                                    """.formatted(livro.getTitulo(), livro.getNomeAutor(), livro.getIdiomas()));

                        }
                    }
                    break;

                case 2:

                    List<LivrosSalvarBanco> todosLivros = livroService.listarTodosLivros();

                    for (LivrosSalvarBanco livro2 : todosLivros) {
                        System.out.println("Título: " + livro2.getTitulo());
                        System.out.println("Nome do Autor: " + livro2.getNomeAutor());
                        System.out.println("Idioma: " + livro2.getIdiomas());
                        System.out.println("-------------------------");
                    }
                    break;


                case 3:
                    List<String> autores = livroService.listarTodosAutores();
                    System.out.println("*********** AUTORES CADASTRADOS ***********");
                    for (String autor : autores) {
                        System.out.println(autor);
                    }
                    System.out.println("*******************************************");
                    System.out.println("");
                    break;

                case 4:

                    System.out.print("Digite um ano para listar quais autores estavam vivos: ");
                    int ano = leitorDeDados.nextInt();

                    List<LivrosSalvarBanco> autoresVivos = livroService.listarAutoresVivosEmAno(ano);
                    System.out.println("*************** AUTORES VIVOS NO ANO " + ano + ": ***************");
                    Map<String, Set<String>> autoresLivrosMap = new HashMap<>();
                    Map<String, String> autoresInfoMap = new HashMap<>();

                    for (LivrosSalvarBanco livro : autoresVivos) {
                        String autorNome = livro.getNomeAutor();
                        String autorInfo = autorNome + " (" + (livro.getAnoDeNascimento() != null ? livro.getAnoDeNascimento() : "N/A") + " - " + (livro.getAnoDeMorte() != null ? livro.getAnoDeMorte() : "N/A") + ")";

                        if (!autoresLivrosMap.containsKey(autorNome)) {
                            autoresLivrosMap.put(autorNome, new HashSet<>());
                            autoresInfoMap.put(autorNome, autorInfo);
                        }
                        autoresLivrosMap.get(autorNome).add(livro.getTitulo());
                    }

                    for (String autor : autoresLivrosMap.keySet()) {
                        String autorInfo = autoresInfoMap.get(autor);
                        String titulos = String.join(", ", autoresLivrosMap.get(autor));
                        System.out.println(autorInfo);
                        System.out.println("Títulos: " + titulos);
                        System.out.println();
                    }
                    break;

                case 5:

                    System.out.println("""

                            Insira o idioma para realizar a busca:
                            es - espanhol
                            en - inglês
                            fr - francês
                            pt - português

                            """);

                    String idiomaBusca = leitorDeDados.nextLine();

                    List<LivrosSalvarBanco> livrosEncontrados = livroService.buscarPorIdioma(idiomaBusca);

                    for (LivrosSalvarBanco livro : livrosEncontrados) {
                        System.out.println("""
                                ------ LIVRO -------------------
                                Título: %s
                                Autor: %s
                                Idioma: %s
                                """.formatted(livro.getTitulo(), livro.getNomeAutor(), idiomaBusca));
                    }

                    break;

                case 0:
                    System.out.println("Obrigado por acessar o LiterAlura!");
                    break;


                default:
                    System.out.println("Opção inválida!");
                    System.out.println("");
            }
        }
    }
}