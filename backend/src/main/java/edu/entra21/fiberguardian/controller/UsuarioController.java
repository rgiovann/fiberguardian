package edu.entra21.fiberguardian.controller;

import java.util.List;

import edu.entra21.fiberguardian.input.*;
import edu.entra21.fiberguardian.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import edu.entra21.fiberguardian.jacksonview.UsuarioView;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.openapi.UsuarioControllerOpenApi;
import edu.entra21.fiberguardian.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuarios")
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
			UsuarioNovoInputDisassembler UsuarioCriarUsuarioInputDisassembler ) {

		this.usuarioService = usuarioService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.usuarioListagemDtoAssembler = usuarioListagemDtoAssembler;
		this.UsuarioCriarUsuarioInputDisassembler = UsuarioCriarUsuarioInputDisassembler;

	}

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
		Usuario atualizado = usuarioService.alterarDadosUsuario(emailAutenticado,
				input.getNome(),
				input.getSetor(),
				input.getTurno());

		UsuarioDto dto = usuarioDtoAssembler.toDto(atualizado);
		return ResponseEntity.ok(dto);
	}

	@Override
	@PutMapping(path = "/me/senha", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> alterarSenha(@RequestBody @Valid UsuarioAlteraSenhaInput input,
			Authentication authentication) {

		String emailAutenticado = authentication.getName();
		usuarioService.atualizarSenha(emailAutenticado, input.getNovaSenha(), input.getSenhaAtual());
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativarUsuario(@RequestBody @Valid UsuarioAlteraStatusInput input,
			Authentication authentication) {
		usuarioService.inativarUsuario(authentication.getName(), input.getEmail());
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarUsuario(@RequestBody @Valid UsuarioAlteraStatusInput input,
			Authentication authentication) {
		usuarioService.ativarUsuario(authentication.getName(), input.getEmail());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/validar-admin")
	public ResponseEntity<Void> validarAdmin(
			@RequestBody @Valid UsuarioEmailSenhaInput input,
			HttpServletRequest request,
			HttpServletResponse response
	) {
		Authentication auth = usuarioService.autenticarSupervisor(input.getEmail(), input.getSenha());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);

		// Salva diretamente na sessão HTTP
		HttpSession session = request.getSession(true); // true para criar se não existir
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

		return ResponseEntity.ok().build();
	}

	// reforca que só usuario admin autenticado pode fazer reset de senha
	@PostMapping("/reset-senha")
	public ResponseEntity<Void> resetSenha(@RequestBody UsuarioResetSenhaInput input) {

		// lógica para resetar a senha de outro usuário
		usuarioService.resetarSenha(input.getEmail(),input.getSenha(),input.getRepeteSenha());
		return ResponseEntity.ok().build();

	}

}
