package edu.entra21.fiberguardian.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.entra21.fiberguardian.assembler.UsuarioCriarUsuarioInputDisassembler;
import edu.entra21.fiberguardian.assembler.UsuarioDtoAssembler;
import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.input.UsuarioInput.AlterarSenha;
import edu.entra21.fiberguardian.input.UsuarioInput.AlterarUsuario;
import edu.entra21.fiberguardian.input.UsuarioInput.CriarUsuario;
import edu.entra21.fiberguardian.input.UsuarioInput.HabilitarDesabilitarUsuario;
import edu.entra21.fiberguardian.input.UsuarioInput.UsuarioAutenticado;
import edu.entra21.fiberguardian.openapi.UsuarioControllerOpenApi;
import edu.entra21.fiberguardian.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	private final CadastroUsuarioService usuarioService;
	private final UsuarioDtoAssembler usuarioDtoListaUsuarioDtoAssembler;
	private final UsuarioCriarUsuarioInputDisassembler usuarioCriarUsuarioInputAssembler;

	public UsuarioController(CadastroUsuarioService usuarioService,
			UsuarioDtoAssembler usuarioDtoAssembler,
			UsuarioCriarUsuarioInputDisassembler usuarioInputDisassembler) {

		this.usuarioService = usuarioService;
		this.usuarioDtoAssembler = usuarioDtoAssembler;
		this.usuarioInputDisassembler = usuarioInputDisassembler;
	}

	@Override
	public String autenticar(UsuarioAutenticado usuarioInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsuarioDto> listar() {
		// TODO Auto-generated method stub
		return usuarioDtoAssembler.toCollectionModel(usuarioService.listar());
	}

	@Override
	public UsuarioDto buscar(Long usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDto adicionar(CriarUsuario usuarioInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDto atualizar(Long usuarioId, AlterarUsuario usuarioInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void habilitarDesabilitar(Long usuarioId, HabilitarDesabilitarUsuario usuarioInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterarSenha(Long usuarioId, AlterarSenha senha) {
		// TODO Auto-generated method stub

	}

//	@Override
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<UsuarioDto> listar() {
//
//		return usuarioDtoAssembler.toCollectionModel(usuarioService.listar());
//
//	}

}
