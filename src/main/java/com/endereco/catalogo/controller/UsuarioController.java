package com.endereco.catalogo.controller;

import com.endereco.catalogo.model.Usuario;
import com.endereco.catalogo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operações com usuários.
 * 
 * Este controlador expõe endpoints HTTP para gerenciar o catálogo de usuários.
 * Fornece operações para criar, ler, atualizar e deletar usuários através
 * de uma API RESTful.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    /**
     * Serviço de usuários injetado automaticamente pelo Spring.
     * Responsável pela lógica de negócio.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para criar um novo usuário.
     * 
     * Recebe um objeto Usuario em formato JSON via POST e o adiciona
     * ao catálogo. A validação é executada automaticamente através
     * das anotações @Valid e das constraints da entidade.
     * 
     * HTTP Method: POST
     * URL: /api/usuarios
     * 
     * @param usuario O usuário a ser criado (validado automaticamente)
     * @return ResponseEntity com status 201 (CREATED) e o usuário criado
     */
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.adicionarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * Endpoint para obter todos os usuários.
     * 
     * Retorna uma lista com todos os usuários cadastrados no catálogo
     * de endereços.
     * 
     * HTTP Method: GET
     * URL: /api/usuarios
     * 
     * @return ResponseEntity com status 200 (OK) e lista de usuários
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> obterTodos() {
        List<Usuario> usuarios = usuarioService.obterTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para obter um usuário específico pelo ID.
     * 
     * Busca um usuário no catálogo através de seu identificador único.
     * 
     * HTTP Method: GET
     * URL: /api/usuarios/{id}
     * 
     * @param id O identificador do usuário a buscar
     * @return ResponseEntity com status 200 (OK) e o usuário encontrado,
     *         ou status 404 (NOT FOUND) se o usuário não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obterUsuarioPorId(id);
        
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para buscar um usuário pelo nome.
     * 
     * Procura um usuário no catálogo através do seu nome.
     * 
     * HTTP Method: GET
     * URL: /api/usuarios/buscar/nome?nome=NomeDoUsuario
     * 
     * @param nome O nome do usuário a buscar
     * @return ResponseEntity com status 200 (OK) e o usuário encontrado,
     *         ou status 404 (NOT FOUND) se não existir
     */
    @GetMapping("/buscar/nome")
    public ResponseEntity<Usuario> obterPorNome(@RequestParam String nome) {
        Usuario usuario = usuarioService.obterUsuarioPorNome(nome);
        
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para atualizar um usuário existente.
     * 
     * Modifica os dados de um usuário identificado pelo ID.
     * Os campos são atualizados com as validações aplicadas.
     * 
     * HTTP Method: PUT
     * URL: /api/usuarios/{id}
     * 
     * @param id O identificador do usuário a atualizar
     * @param usuarioAtualizado Os dados atualizados do usuário
     * @return ResponseEntity com status 200 (OK) e o usuário atualizado,
     *         ou status 404 (NOT FOUND) se o usuário não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuarioAtualizado) {
        
        try {
            Usuario usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para remover um usuário do catálogo.
     * 
     * Deleta um usuário identificado pelo seu ID da base de dados.
     * 
     * HTTP Method: DELETE
     * URL: /api/usuarios/{id}
     * 
     * @param id O identificador do usuário a remover
     * @return ResponseEntity com status 204 (NO CONTENT) se removido com sucesso,
     *         ou status 404 (NOT FOUND) se o usuário não existir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        try {
            usuarioService.removerUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para obter o total de usuários no catálogo.
     * 
     * Retorna a quantidade de usuários cadastrados na base de dados.
     * 
     * HTTP Method: GET
     * URL: /api/usuarios/estatisticas/total
     * 
     * @return ResponseEntity com status 200 (OK) e o total de usuários
     */
    @GetMapping("/estatisticas/total")
    public ResponseEntity<Long> obterTotalUsuarios() {
        long total = usuarioService.contarUsuarios();
        return ResponseEntity.ok(total);
    }
}
