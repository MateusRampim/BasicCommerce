package com.example.demo.webservice;
import com.example.demo.business.models.Cliente;
import com.example.demo.business.models.Usuario;
import com.example.demo.business.repository.ClienteRepository;
import com.example.demo.business.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Cliente usuario) {
        // Verificar se o e-mail já está cadastrado
        if (clienteRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("O e-mail já está cadastrado");
        }


        Cliente novoUsuario = clienteRepository.save(usuario);

        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getUsuarioById(@PathVariable UUID id) {
        Cliente usuario = clienteRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateUsuario(@PathVariable UUID id, @RequestBody Cliente usuarioAtualizado) {
        Cliente usuario = clienteRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os campos do usuário
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());



        Cliente usuarioAtualizadoBanco = clienteRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizadoBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
