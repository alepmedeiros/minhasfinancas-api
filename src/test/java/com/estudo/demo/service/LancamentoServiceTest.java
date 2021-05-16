package com.estudo.demo.service;

import com.estudo.demo.exception.RegraNegocioException;
import com.estudo.demo.model.entity.Lancamento;
import com.estudo.demo.model.entity.Usuario;
import com.estudo.demo.model.enums.StatusLancamento;
import com.estudo.demo.model.repository.LancamentoRepository;
import com.estudo.demo.model.repository.LancamentoRepositoryTest;
import com.estudo.demo.service.impl.LancamentoServiceImpl;
import jdk.nashorn.internal.runtime.options.Option;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.callback.LanguageCallback;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImpl service;

    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento(){
        //cenario
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        //execucao
        Lancamento lancamento = service.salvar(lancamentoASalvar);

        //verificacao
        Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.EFETIVADO);
    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao(){
        //cenario
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

        //execucao e verificacao
        Assertions.catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);
        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveAtualizarUmLancamento(){
        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        //execucao
        service.salvar(lancamentoASalvar);

        //verificacao
        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
    }

    @Test
    public void deveLancaErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

        //execucao e verificacao
        Assertions.catchThrowableOfType(() -> service.atualizar(lancamento), NullPointerException.class);
        Mockito.verify(repository, Mockito.never()).save(lancamento);
    }

    @Test
    public void deveDeletarUmLancamento(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.getId(1l);

        //execucao
        service.deletar(lancamento);

        //verificacao
        Mockito.verify(repository).delete(lancamento);
    }

    @Test
    public void deveLancaErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

        //execucao
        Assertions.catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);

        //verificacao
        Mockito.verify(repository, Mockito.never()).delete(lancamento);
    }

    @Test
    public void deveFiltrarLancamentos(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        List<Lancamento> lista = Arrays.asList(lancamento);
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        //execucao
        List<Lancamento> resultado = service.buscar(lancamento);

        //verificacao
        Assertions.assertThat(resultado)
                .isNotEmpty()
                .hasSize(1)
                .contains(lancamento);
    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        //execucao
        service.atualizarStatus(lancamento, novoStatus);

        //verificacao
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamento);
    }

    @Test
    public void deveObterUmLancamentoPorID(){
        //cenario
        Long id = 1l;

        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

        //execucao
        Optional<Lancamento> resultado = service.obterPorId(id);

        //verificacao
        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioQuandoOLancamentoNaoExiste(){
        //cenario
        Long id = 1l;

        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execucao
        Optional<Lancamento> resultado = service.obterPorId(id);

        //verificacao
        Assertions.assertThat(resultado.isPresent()).isFalse();
    }

    @Test
    public void deveLancarErrosAoValidatUmLancamento(){
        Lancamento lancamento = new Lancamento();

        Throwable erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma descrição válida");

        lancamento.setDescricao("");

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma descrição válida");

        lancamento.setDescricao("Salario");

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um mês válido");

        lancamento.setAno(0);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um mês válido");

        lancamento.setAno(13);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um mês válido");

        lancamento.setMes(1);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um ano válido");

        lancamento.setAno(202);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um ano válido");

        lancamento.setAno(2021);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um usuário válido");

        lancamento.setUsuario(new Usuario());

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um usuário válido");

        lancamento.getUsuario().setId(1l);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um valor válido");

        lancamento.setValor(BigDecimal.ZERO);

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um valor válido");

        lancamento.setValor(BigDecimal.valueOf(1));

        erro = Assertions.catchThrowable(() -> service.validar(lancamento));
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um tipo de lançamento");

    }



}
