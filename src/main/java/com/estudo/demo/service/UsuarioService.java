package com.estudo.demo.service;

import com.estudo.demo.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvaUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> obterPorId(Long id);

}
