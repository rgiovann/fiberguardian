package edu.entra21.fiberguardian.openapi;

import edu.entra21.fiberguardian.input.UsuarioAlteraStatusInput;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import edu.entra21.fiberguardian.dto.PageDto;
import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.dto.UsuarioListagemDto;
import edu.entra21.fiberguardian.exception.handler.Problem;
import edu.entra21.fiberguardian.input.UsuarioAlteraSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoSemSenhaInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public interface UsuarioControllerOpenApi {

	public PageDto<UsuarioListagemDto> listarPaginado(Pageable listarPaginado);

	@Operation(summary = "Lista Usuarios", description = "Retorna Retorna uma lista paginada de usuarios. Requer autenticação via cookie JSESSIONID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado (sessão inválida ou ausente)", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })
	@SecurityRequirement(name = "cookieAuth")

	UsuarioDto buscarNome(
			@Parameter(description = "Dados do usuario autenticado", example = "email@dominio.com", required = true) Authentication emailAutenticado);

	@Operation(summary = "Busca o nome do usuário autenticado", description = "Busca o nome do usuário autenticado. Requer autenticação via cookie JSESSIONID e token CSRF no header X-XSRF-TOKEN.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Nome do usuário autenticado realizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados do usuário inválidos", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado (sessão inválida ou CSRF inválido)", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })
	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	UsuarioDto adicionar(
			@Parameter(description = "Dados para criação de um novo usuário", required = true) UsuarioCompletoComSenhaInput usuarioInput);

	@Operation(summary = "Atualiza um usuário por ID", description = "Atualiza os dados de um usuário existente, exceto o email. Requer autenticação via cookie JSESSIONID e token CSRF no header X-XSRF-TOKEN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados do usuário inválidos", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado (sessão inválida ou CSRF inválido)", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })
	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

//	UsuarioDto atualizar(
//			@Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true) Long usuarioId,
//			@Parameter(description = "Novos dados do usuário (nome e role)", required = true) UsuarioCompletoSemSenhaInput usuarioInput);
//
//	@Operation(summary = "Inativa um usuário por Id")
//	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário inativado com sucesso"),
//			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })

	ResponseEntity<Void> inativarUsuario(
			@Parameter(description = "Email usuario", example = "usuario@email.com", required = true) UsuarioAlteraStatusInput input, Authentication authentication);

	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	@Operation(summary = "Ativa um usuário por Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário ativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })

	ResponseEntity<Void> ativarUsuario(
			@Parameter(description = "Emaild usuário", example = "usuario@email.com", required = true) UsuarioAlteraStatusInput input,
			Authentication authentication);

	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	@Operation(summary = "Altera a senha do usuário  autenticado")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
			@ApiResponse(responseCode = "403", description = "Sem permissão para alterar a senha") })
	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	ResponseEntity<String> alterarSenha(

			@RequestBody(description = "Dados da senha atual e nova senha", required = true) UsuarioAlteraSenhaInput usuarioSoSenhaInput,
			Authentication usuario);

}