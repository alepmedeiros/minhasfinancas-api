package com.estudo.demo.model.repository;

import com.estudo.demo.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarExistenciaEmail(){
        //cenário
        Usuario usuario = criarUsuario();

        entityManager.persist(usuario);

        //ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalesSemUsuarioEmailCadastrado(){
        //cenario

        //acao
        boolean result = repository.existsByEmail("usuario@email.com");
        //verificacao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();
        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    public static Usuario criarUsuario() {
        return Usuario
                .builder()
                    .nome("usuario")
                    .senha("senha")
                    .email("usuario@teste.com")
                .build();
    }

    @Test
    public void deveBuscarUsuarioPorEmail(){
        //cenario
        Usuario usuario = criarUsuario();

        entityManager.persist(usuario);

        //verifica
        Optional<Usuario> result = repository.findByEmail("usuario@teste.com");

        //acao
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExistirNaBase(){
        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@teste.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
