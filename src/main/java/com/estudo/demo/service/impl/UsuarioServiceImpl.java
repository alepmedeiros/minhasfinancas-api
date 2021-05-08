package com.estudo.demo.service.impl;

import com.estudo.demo.model.entity.Usuario;
import com.estudo.demo.exception.ErrorAutenticacao;
import com.estudo.demo.exception.RegraNegocioException;
import com.estudo.demo.model.repository.UsuarioRepository;
import com.estudo.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        //refatorar pois isso é muito feio
        if (!usuario.isPresent()){
            throw new ErrorAutenticacao("Usuário não encontrado com o e-mail informado");
        }

        if (!usuario.get().getSenha().equals(senha)){
            throw new ErrorAutenticacao("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvaUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe =  repository.existsByEmail(email);
        if (existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}
