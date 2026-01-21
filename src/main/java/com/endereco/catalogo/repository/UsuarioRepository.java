package com.endereco.catalogo.repository;

import com.endereco.catalogo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Usuario.
 * 
 * Este repositório fornece operações de persistência para usuários
 * utilizando Spring Data JPA. Oferece métodos padrão de CRUD
 * (Create, Read, Update, Delete) e permite a criação de consultas personalizadas.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca um usuário pelo nome.
     * 
     * Este método permite encontrar usuários através do seu nome
     * na base de dados.
     * 
     * @param nome O nome do usuário a ser buscado
     * @return O usuário encontrado, ou null se não existir
     */
    Usuario findByNome(String nome);
}
