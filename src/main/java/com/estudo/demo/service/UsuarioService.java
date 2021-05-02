package com.estudo.demo.service;

import com.estudo.demo.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvaUsuario(Usuario usuario);

    void validarEmail(String email);

}
