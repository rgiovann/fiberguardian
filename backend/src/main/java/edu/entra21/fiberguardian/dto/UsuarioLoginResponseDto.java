package edu.entra21.fiberguardian.dto;

import edu.entra21.fiberguardian.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginResponseDto {

    private Long id;
    private String email;
    private Role role;
    private String nome;
}
