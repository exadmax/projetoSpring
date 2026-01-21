package com.endereco.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal de inicialização da aplicação Spring Boot.
 * 
 * Esta classe contém o método main que inicia a aplicação de catálogo
 * de endereços. O Spring Boot automaticamente detectará e configurará
 * os componentes, repositories e serviços baseado nas anotações.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@SpringBootApplication
public class CatalogoEnderecoApplication {

    /**
     * Método principal da aplicação.
     * 
     * Inicia o servidor Spring Boot com a configuração automática.
     * A aplicação estará disponível em: http://localhost:8080
     * 
     * @param args Argumentos de linha de comando (não utilizado)
     */
    public static void main(String[] args) {
        SpringApplication.run(CatalogoEnderecoApplication.class, args);
    }
}
