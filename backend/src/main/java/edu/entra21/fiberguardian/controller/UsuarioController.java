package edu.entra21.fiberguardian.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.entra21.fiberguardian.assembler.UsuarioCriarUsuarioInputDisassembler;
import edu.entra21.fiberguardian.assembler.UsuarioDtoAssembler;
import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoSemSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioNovaSenhaInput;
import edu.entra21.fiberguardian.openapi.UsuarioControllerOpenApi;
import edu.entra21.fiberguardian.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	private final UsuarioService usuarioService;
	private final UsuarioDtoAssembler usuarioDtoAssembler;
	private final UsuarioCriarUsuarioInputDisassembler UsuarioCriarUsuarioInputDisassembler;

	public UsuarioController(UsuarioService usuarioService, UsuarioDtoAssembler usuarioDtoAssembler,
			UsuarioCriarUsuarioInputDisassembler UsuarioCriarUsuarioInputDisassembler) {

		this.usuarioService = usuarioService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.UsuarioCriarUsuarioInputDisassembler = UsuarioCriarUsuarioInputDisassembler;
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsuarioDto> listar() {
		// TODO Auto-generated method stub
		return usuarioDtoAssembler.toCollectionDto(usuarioService.listar());
	}

	@Override
	public UsuarioDto buscar(Long usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDto adicionar(UsuarioCompletoComSenhaInput usuarioInput) {
		return null;
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

	@Override
	public ResponseEntity<Void> alterarSenha(Long usuarioId, UsuarioNovaSenhaInput usuarioSoSenhaInput) {
		return null;
	}

//	@Override
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<UsuarioDto> listar() {
//
//		return usuarioDtoAssembler.toCollectionModel(usuarioService.listar());
//
//	}

}
