package edu.entra21.fiberguardian.openapi;

import java.util.List;

import org.springframework.http.ResponseEntity;

import edu.entra21.fiberguardian.dto.UsuarioDto;
import edu.entra21.fiberguardian.exception.handler.Problem;
import edu.entra21.fiberguardian.input.UsuarioCompletoComSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioCompletoSemSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioEmailSenhaInput;
import edu.entra21.fiberguardian.input.UsuarioNovaSenhaInput;
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

	@Operation(summary = "Autentica um usuário", description = "Realiza login com email e senha, retornando um cookie de sessão (JSESSIONID) e CSRF token (XSRF-TOKEN)")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, cookies JSESSIONID e XSRF-TOKEN retornados"),
			@ApiResponse(responseCode = "400", description = "Dados de autenticação inválidos", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })

	String autenticar(
			@Parameter(description = "Credenciais do usuário (email e senha)", required = true) UsuarioEmailSenhaInput usuarioInput);

	@Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de usuários cadastrados. Requer autenticação via cookie JSESSIONID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
			@ApiResponse(responseCode = "403", description = "Acesso negado (sessão inválida ou ausente)", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })
	@SecurityRequirement(name = "cookieAuth")

	List<UsuarioDto> listar();

	@Operation(summary = "Busca um usuário por ID", description = "Retorna os detalhes de um usuário específico. Requer autenticação via cookie JSESSIONID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado (sessão inválida ou ausente)", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = Problem.class))) })
	@SecurityRequirement(name = "cookieAuth")

	UsuarioDto buscar(
			@Parameter(description = "ID do usuário a ser buscado", example = "1", required = true) Long usuarioId);

	@Operation(summary = "Cadastra um novo usuário", description = "Cria um novo usuário com os dados fornecidos. Requer autenticação via cookie JSESSIONID e token CSRF no header X-XSRF-TOKEN.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
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

	UsuarioDto atualizar(
			@Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true) Long usuarioId,
			@Parameter(description = "Novos dados do usuário (nome e role)", required = true) UsuarioCompletoSemSenhaInput usuarioInput);

	@Operation(summary = "Inativa um usuário por Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário inativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	ResponseEntity<Void> inativarUsuario(
			@Parameter(description = "Id de um usuário", example = "1", required = true) Long usuarioId);

	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	@Operation(summary = "Ativa um usuário por Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário ativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	ResponseEntity<Void> ativarUsuario(
			@Parameter(description = "Id de um usuário", example = "1", required = true) Long usuarioId);

	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	@Operation(summary = "Altera a senha do usuário pelo ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
			@ApiResponse(responseCode = "403", description = "Sem permissão para alterar a senha") })
	@SecurityRequirement(name = "cookieAuth")
	@SecurityRequirement(name = "csrfToken")

	ResponseEntity<Void> alterarSenha(
			@Parameter(description = "ID do usuário cuja senha será alterada", example = "1", required = true) Long usuarioId,

			@RequestBody(description = "Dados da senha atual e nova senha", required = true) UsuarioNovaSenhaInput usuarioSoSenhaInput);

}