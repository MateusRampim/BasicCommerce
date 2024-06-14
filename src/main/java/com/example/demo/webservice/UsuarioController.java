package com.example.demo.webservice;
import com.example.demo.business.models.Usuario;
import com.example.demo.business.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        // Verificar se o e-mail já está cadastrado
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("O e-mail já está cadastrado");
        }

        // Hash da senha (Você pode implementar uma lógica mais segura para hash)
        String senhaHash = hashSenha(usuario.getSenha());
        usuario.setSenha(senhaHash);

        // Salvar o usuário no banco de dados
        Usuario novoUsuario = usuarioRepository.save(usuario);

        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
    @GetMapping()
    public ResponseEntity<List<Usuario> >getUsuarios() {
        List<Usuario> usuario = usuarioRepository.findAll();
        if (usuario .isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os campos do usuário
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());

        // Verifica se a senha foi fornecida e a atualiza, se necessário
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            String senhaCriptografada = hashSenha(usuarioAtualizado.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        Usuario usuarioAtualizadoBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizadoBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String hashSenha(String senha) {
        try {
            // Obtém uma instância do MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Calcula o hash da senha
            byte[] hash = digest.digest(senha.getBytes());

            // Converte o hash em uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Tratar a exceção, caso o algoritmo não seja suportado
            e.printStackTrace();
            return null;
        }

    }
}
