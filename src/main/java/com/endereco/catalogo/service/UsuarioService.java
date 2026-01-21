package com.endereco.catalogo.service;

import com.endereco.catalogo.model.Usuario;
import com.endereco.catalogo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações com usuários.
 * 
 * Esta classe implementa a lógica de negócio relacionada aos usuários.
 * Ela funciona como intermediária entre o controlador REST e o repositório,
 * concentrando as regras de negócio e validações específicas da aplicação.
 * 
 * @author Treinamento Spring Boot
 * @version 1.0
 */
@Service
public class UsuarioService {

    /**
     * Repositório para acesso aos dados de usuários.
     * Injetado automaticamente pelo Spring.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Adiciona um novo usuário ao catálogo.
     * 
     * Este método recebe um objeto Usuario com dados validados
     * e o persiste no banco de dados H2. As validações de campo
     * são executadas automaticamente pelas anotações da entidade.
     * 
     * @param usuario O usuário a ser adicionado
     * @return O usuário salvo com o ID gerado pelo banco
     * @throws IllegalArgumentException Se dados obrigatórios forem nulos
     */
    public Usuario adicionarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório");
        }
        if (usuario.getIdade() == null) {
            throw new IllegalArgumentException("A idade do usuário é obrigatória");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtém todos os usuários do catálogo.
     * 
     * Este método retorna uma lista com todos os usuários
     * armazenados na base de dados.
     * 
     * @return Lista com todos os usuários
     */
    public List<Usuario> obterTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo seu identificador único.
     * 
     * Este método procura no banco de dados por um usuário
     * com o ID especificado.
     * 
     * @param id O identificador do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca um usuário pelo nome.
     * 
     * Este método procura na base de dados um usuário
     * com o nome exato especificado.
     * 
     * @param nome O nome do usuário a buscar
     * @return O usuário encontrado, ou null se não existir
     */
    public Usuario obterUsuarioPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * 
     * Este método encontra o usuário pelo ID e atualiza seus dados.
     * As validações de campo são executadas automaticamente.
     * 
     * @param id O identificador do usuário a atualizar
     * @param usuarioAtualizado O objeto com os dados atualizados
     * @return O usuário atualizado
     * @throws IllegalArgumentException Se o usuário não for encontrado
     */
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado");
        }
        
        Usuario usuario = usuarioExistente.get();
        
        // Atualiza apenas os campos fornecidos
        if (usuarioAtualizado.getNome() != null && !usuarioAtualizado.getNome().trim().isEmpty()) {
            usuario.setNome(usuarioAtualizado.getNome());
        }
        
        if (usuarioAtualizado.getIdade() != null) {
            usuario.setIdade(usuarioAtualizado.getIdade());
        }
        
        if (usuarioAtualizado.getEndereco() != null) {
            usuario.setEndereco(usuarioAtualizado.getEndereco());
        }
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Remove um usuário do catálogo.
     * 
     * Este método deleta um usuário da base de dados
     * pelo seu identificador único.
     * 
     * @param id O identificador do usuário a remover
     * @throws IllegalArgumentException Se o usuário não for encontrado
     */
    public void removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Obtém a quantidade total de usuários no catálogo.
     * 
     * Este método retorna o número de usuários armazenados
     * na base de dados.
     * 
     * @return O total de usuários
     */
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
}
