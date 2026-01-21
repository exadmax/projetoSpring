package com.endereco.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidade Usuario representa um usuário no catálogo de endereços.
 * 
 * Esta classe mapeia a tabela de usuários no banco de dados H2 e contém
 * as informações necessárias para gerenciar usuários e seus endereços.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único do usuário no banco de dados.
     * Gerado automaticamente pela estratégia de identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário com validações de obrigatoriedade e tamanho máximo.
     * Campo obrigatório com limite de 100 caracteres.
     */
    @NotBlank(message = "O nome não pode estar vazio")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    /**
     * Idade do usuário com validação de faixa numérica.
     * Campo obrigatório com valores entre 1 e 99.
     */
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 1, message = "A idade deve ser no mínimo 1")
    @Max(value = 99, message = "A idade deve ser no máximo 99")
    @Column(nullable = false)
    private Integer idade;

    /**
     * Endereço do usuário em formato livre.
     * Campo opcional que permite armazenar informações completas de endereço.
     */
    @Column(length = 500)
    private String endereco;

    /**
     * Construtor padrão necessário para JPA.
     */
    public Usuario() {
    }

    /**
     * Construtor completo da entidade Usuario.
     * 
     * @param nome O nome do usuário (obrigatório)
     * @param idade A idade do usuário (obrigatório)
     * @param endereco O endereço do usuário (opcional)
     */
    public Usuario(String nome, Integer idade, String endereco) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único do usuário.
     * 
     * @return O ID do usuário
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único do usuário.
     * 
     * @param id O ID a ser definido
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome do usuário.
     * 
     * @return O nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário com validação de tamanho.
     * 
     * @param nome O nome a ser definido (máximo 100 caracteres)
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a idade do usuário.
     * 
     * @return A idade do usuário
     */
    public Integer getIdade() {
        return idade;
    }

    /**
     * Define a idade do usuário com validação de faixa.
     * 
     * @param idade A idade a ser definida (entre 1 e 99)
     */
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    /**
     * Obtém o endereço do usuário.
     * 
     * @return O endereço do usuário
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do usuário.
     * 
     * @param endereco O endereço a ser definido
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Representação textual da entidade Usuario.
     * 
     * @return String com os dados do usuário
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
