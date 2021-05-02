package com.estudo.demo.service;

import com.estudo.demo.model.repository.UsuarioRepository;
import com.estudo.demo.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

//    @Test(expected = Test.None.class)
//    public void deveSalvalUmUsuario(){
//        //cenario
//        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
//        Usuario usuario = Usuario.builder()
//                                .id(1l)
//                                .nome("nome")
//                                .email("email@email.com")
//                                .senha("senha")
//                            .build();
//        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
//
//        //acao
//        Usuario usuarioSalvo = service.salvaUsuario(new Usuario());
//
//        //verificacao
//        Assertions.assertThat(usuarioSalvo).isNotNull();
//        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
//        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
//        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email");
//        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
//    }
//
//    @Test(expected = RegraNegocioException.class)
//    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado(){
//        //cenario
//        String email = "email@email.com";
//        Usuario usuario = Usuario.builder().email(email).build();
//        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
//
//        //acao
//        service.salvaUsuario(usuario);
//
//        //verificacao
//        Mockito.verify(repository, Mockito.never()).save(usuario);
//    }
//
//    @Test(expected = Test.None.class)
//    public void deveAutenticarUmUsuarioComSucesso(){
//        //cenario
//        String email = "teste@teste.com";
//        String senha = "senha";
//
//        Usuario usuario = Usuario
//                            .builder()
//                                .email(email)
//                                .senha(senha)
//                            .build();
//         Mockito
//                 .when(repository.findByEmail(email))
//                 .thenReturn(Optional.of(usuario));
//
//        //acao
//        Usuario result = service.autenticar(email, senha);
//
//        //verificacao
//        Assertions.assertThat(result).isNotNull();
//    }
//
//    @Test
//    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado(){
//        //cenario
//        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
//
//        //acao
//        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com","senha"));
//
//        //verificacao
//        Assertions.assertThat(exception).isInstanceOf(ErrorAutenticacao.class).hasMessage("Email não encontrado");
//    }
//
//    @Test
//    public void deveLancarErroQuandoSenhaNaoBater(){
//        //cenario
//        String senha = "senha";
//
//        Usuario usuario = Usuario.builder()
//                                .email("email@email.com")
//                                .senha(senha)
//                            .build();
//
//        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
//
//        //acao
//        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com","123"));
//
//        Assertions.assertThat(exception).isInstaceOf(ErrorAutenticacao.class).hasMessage("Senha invalida");
//    }
//
//    //este unitario
//    @Test(expected = Test.None.class)
//    public void deveValidarEmail(){
//        //cenario
//        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
//
//        //acao
//        service.validarEmail("teste@teste.com");
//    }
//
//    //teste interacao
//    @Test(expected = RegraNegocioException.class)
//    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
//        //cenario
//        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
//
//        //acao
//        service.validarEmail("usuario@usuario.com");
//    }
}
