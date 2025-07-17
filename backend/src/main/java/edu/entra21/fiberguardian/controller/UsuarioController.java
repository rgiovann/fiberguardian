package edu.entra21.fiberguardian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import edu.entra21.fiberguardian.assembler.UsuarioDtoAssembler;
import edu.entra21.fiberguardian.assembler.UsuarioListagemDtoAssembler;
import edu.entra21.fiberguardian.assembler.UsuarioNovoInputDisassembler;
import edu.entra21.fiberguardian.dto.PageDto;
import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.dto.UsuarioListagemDto;
import edu.entra21.fiberguardian.input.UsuarioAlteraNomeInput;
import edu.entra21.fiberguardian.input.UsuarioAlteraSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoSemSenhaInput;
import edu.entra21.fiberguardian.jacksonview.UsuarioView;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.openapi.UsuarioControllerOpenApi;
import edu.entra21.fiberguardian.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	private final UsuarioService usuarioService;
	private final UsuarioDtoAssembler usuarioDtoAssembler;
	private final UsuarioListagemDtoAssembler usuarioListagemDtoAssembler;
	private final UsuarioNovoInputDisassembler UsuarioCriarUsuarioInputDisassembler;
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	private static final int TAMANHO_PAGINA_PADRAO = 10;
	private static final String CAMPO_ORDEM_PADRAO = "nome";

	public UsuarioController(UsuarioService usuarioService, UsuarioDtoAssembler usuarioDtoAssembler,
			UsuarioListagemDtoAssembler usuarioListagemDtoAssembler,
			UsuarioNovoInputDisassembler UsuarioCriarUsuarioInputDisassembler) {

		this.usuarioService = usuarioService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.usuarioListagemDtoAssembler = usuarioListagemDtoAssembler;
		this.UsuarioCriarUsuarioInputDisassembler = UsuarioCriarUsuarioInputDisassembler;
	}
	/*
	 * @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) public
	 * Page<UsuarioListagemDto> listarPaginado(
	 * 
	 * @PageableDefault(size = TAMANHO_PAGINA_PADRAO, sort = CAMPO_ORDEM_PADRAO,
	 * direction = Sort.Direction.ASC) Pageable pageable) { Page<Usuario> pagina =
	 * usuarioService.listarPaginado(pageable); List<UsuarioListagemDto> dtos =
	 * usuarioListagemDtoAssembler.toCollectionDto(pagina.getContent());
	 * Page<UsuarioListagemDto> paginaDto = new PageImpl<>(dtos, pageable,
	 * pagina.getTotalElements()); return paginaDto; }
	 */

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageDto<UsuarioListagemDto> listarPaginado(
			@PageableDefault(size = TAMANHO_PAGINA_PADRAO, sort = CAMPO_ORDEM_PADRAO, direction = Sort.Direction.ASC) Pageable pageable) {

		Page<Usuario> pagina = usuarioService.listarPaginado(pageable);
		List<UsuarioListagemDto> dtos = usuarioListagemDtoAssembler.toCollectionDto(pagina.getContent());

		PageDto<UsuarioListagemDto> dtoPaged = new PageDto<UsuarioListagemDto>();
		dtoPaged.setContent(dtos);
		dtoPaged.setPageNumber(pagina.getNumber());
		dtoPaged.setPageSize(pagina.getSize());
		dtoPaged.setTotalElements(pagina.getTotalElements());
		dtoPaged.setTotalPages(pagina.getTotalPages());
		dtoPaged.setLast(pagina.isLast());

		return dtoPaged;
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionar(@RequestBody @Valid UsuarioCompletoComSenhaInput usuarioNomeInput) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("Usuário autenticado: " + auth.getName());
		logger.debug("Authorities: " + auth.getAuthorities());

		usuarioService.existeEmailCadastrado(usuarioNomeInput.getEmail());

		return usuarioDtoAssembler.toDto(
				usuarioService.cadastrarNovoUsuario(UsuarioCriarUsuarioInputDisassembler.toEntity(usuarioNomeInput)));

	}

	@GetMapping(path = "/me/nome")
	@JsonView(UsuarioView.Autenticado.class)
	public UsuarioDto buscarNome(Authentication authentication) {
		String emailAutenticado = authentication.getName();
		return usuarioDtoAssembler.toDto(usuarioService.buscarPorEmailObrigatorio(emailAutenticado));
	}

	/*
	 * TODO: depois atualizar interface OpenAPI
	 */
	@PutMapping(path = "/me/nome", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(UsuarioView.SomenteNome.class)
	public ResponseEntity<UsuarioDto> alterarNome(@RequestBody @Valid UsuarioAlteraNomeInput input,
			Authentication authentication) {

		String emailAutenticado = authentication.getName();

		Usuario usuario = usuarioService.buscarPorEmailObrigatorio(emailAutenticado);
		Usuario atualizado = usuarioService.alterarNomeUsuario(input.getNome(), usuario);
		UsuarioDto dto = usuarioDtoAssembler.toDto(atualizado);

		return ResponseEntity.ok(dto);
	}

	@Override
	@PutMapping(path = "/me/senha", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> alterarSenha(@RequestBody @Valid UsuarioAlteraSenhaInput input,
			Authentication authentication) {

		String emailAutenticado = authentication.getName();

		Usuario usuario = usuarioService.buscarPorEmailObrigatorio(emailAutenticado);
		usuarioService.atualizarSenha(usuario, input.getNovaSenha(), input.getSenhaAtual());
		return ResponseEntity.noContent().build();
	}

	@Override
	public UsuarioDto atualizar(Long usuarioId, UsuarioCompletoSemSenhaInput usuarioInput) {
		return null;
	}

	@Override
	public ResponseEntity<Void> inativarUsuario(Long usuarioId) {
		return null;
	}

	@Override
	public ResponseEntity<Void> ativarUsuario(Long usuarioId) {
		return null;
	}

}
