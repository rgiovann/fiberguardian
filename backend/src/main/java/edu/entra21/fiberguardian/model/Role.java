package edu.entra21.fiberguardian.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {
	ADMIN, USUARIO, LABORATORIO, ENGENHARIA, ENG_LAB;

	public String getAuthority() {
		return "ROLE_" + this.name();
	}

}
