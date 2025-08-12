package service;

import edu.entra21.fiberguardian.exception.exception.FornecedorNaoEncontradoException;
import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.repository.FornecedorRepository;
import edu.entra21.fiberguardian.service.FornecedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setCnpj("1234567890001");
        fornecedor.setNomeFornecedor("Fornecedor Teste");
    }

    @Test
    void salvar_devePersistirFornecedor() {
        when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);

        Fornecedor salvo = fornecedorService.salvar(fornecedor);

        assertNotNull(salvo);
        assertEquals("Fornecedor Teste", salvo.getNomeFornecedor());
        assertEquals("1234567890001", salvo.getCnpj());

        verify(fornecedorRepository).save(fornecedor);
    }

    @Test
    void buscarPorCNPJObrigatorio_quandoExiste() {
        when(fornecedorRepository.findByCnpj("1234567890001"))
                .thenReturn(Optional.of(fornecedor));

        Fornecedor encontrado = fornecedorService.buscarPorCNPJObrigatorio("1234567890001");

        assertEquals(fornecedor, encontrado);
    }

    @Test
    void buscarPorCNPJObrigatorio_quandoNaoExiste() {
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.empty());

        assertThrows(FornecedorNaoEncontradoException.class,
                () -> fornecedorService.buscarPorCNPJObrigatorio("123"));
    }

    @Test
    void buscarPorCNPJOpcional_quandoExiste() {
        String cnpjValido = "1234567890001"; // CNPJ completo

        when(fornecedorRepository.findByCnpj(cnpjValido))
                .thenReturn(Optional.of(fornecedor));

        Optional<Fornecedor> opt = fornecedorService.buscarPorCNPJOpcional(cnpjValido);

        assertTrue(opt.isPresent(), "Deveria encontrar fornecedor com CNPJ exato");
    }

    @Test
    void buscarPorCNPJOpcional_quandoNaoExiste() {
        String cnpjInexistente = "12389823"; // CNPJ completo

        when(fornecedorRepository.findByCnpj(cnpjInexistente))
                .thenReturn(Optional.empty());

        Optional<Fornecedor> opt = fornecedorService.buscarPorCNPJOpcional(cnpjInexistente);

        assertFalse(opt.isPresent(), "Não deveria encontrar fornecedor para CNPJ inexistente");
    }

    @Test
    void listar_deveRetornarTodos() {
        when(fornecedorRepository.findAll()).thenReturn(List.of(fornecedor));

        List<Fornecedor> lista = fornecedorService.listar();

        assertEquals(1, lista.size());
    }

    @Test
    void listarFiltroPorNome_quandoNomeNuloOuVazio() {
        when(fornecedorRepository.findAll()).thenReturn(List.of(fornecedor));

        assertEquals(1, fornecedorService.listarFiltroPorNome(null).size());
        assertEquals(1, fornecedorService.listarFiltroPorNome(" ").size());
    }

    @Test
    void listarFiltroPorNome_quandoNomeInformado() {
        when(fornecedorRepository.findByNomeFornecedorContainingIgnoreCase("teste"))
                .thenReturn(List.of(fornecedor));

        List<Fornecedor> lista = fornecedorService.listarFiltroPorNome("teste");

        assertEquals(1, lista.size());
    }

    @Test
    void excluir_quandoSucesso() {
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.of(fornecedor));

        fornecedorService.excluir("123");

        verify(fornecedorRepository).deleteByCnpj("123");
        verify(fornecedorRepository).flush();
    }

    @Test
    void excluir_quandoEmUso() {
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.of(fornecedor));
        doThrow(DataIntegrityViolationException.class)
                .when(fornecedorRepository).flush();

        assertThrows(EntidadeEmUsoException.class,
                () -> fornecedorService.excluir("123"));
    }

    @Test
    void validarInsercao_quandoCnpjDuplicado() {
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.of(fornecedor));

        assertThrows(NegocioException.class,
                () -> fornecedorService.validarInsercao("123", "Outro Nome"));
    }

    @Test
    void validarInsercao_quandoNomeDuplicado() {
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.empty());
        when(fornecedorRepository.findByNomeFornecedorIgnoreCase("Fornecedor Teste"))
                .thenReturn(Optional.of(fornecedor));

        assertThrows(NegocioException.class,
                () -> fornecedorService.validarInsercao("123", "Fornecedor Teste"));
    }

    @Test
    void validarAlteracao_quandoCnpjDuplicado() {
        when(fornecedorRepository.findByCnpj("atual")).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.findByCnpj("novo")).thenReturn(Optional.of(new Fornecedor()));

        assertThrows(NegocioException.class,
                () -> fornecedorService.validarAlteracao("atual", "novo", "Fornecedor Teste"));
    }

    @Test
    void validarAlteracao_quandoNomeDuplicado() {
        when(fornecedorRepository.findByCnpj("atual")).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.findByCnpj("123")).thenReturn(Optional.empty());
        when(fornecedorRepository.findByNomeFornecedorIgnoreCase("Outro Nome"))
                .thenReturn(Optional.of(new Fornecedor()));

        assertThrows(NegocioException.class,
                () -> fornecedorService.validarAlteracao("atual", "123", "Outro Nome"));
    }

    @Test
    void validarAlteracao_quandoNaoHaDuplicidade() {
        when(fornecedorRepository.findByCnpj("atual")).thenReturn(Optional.of(fornecedor));
        when(fornecedorRepository.findByCnpj("novo")).thenReturn(Optional.empty());
        when(fornecedorRepository.findByNomeFornecedorIgnoreCase("Novo Nome"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                fornecedorService.validarAlteracao("atual", "novo", "Novo Nome"));
    }
}
