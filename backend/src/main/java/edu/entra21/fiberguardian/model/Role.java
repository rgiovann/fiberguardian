package edu.entra21.fiberguardian.model;

public enum Role {
	ADMIN, USUARIO, LABORATORIO, ENGENHARIA, ENG_LAB;

	public String getAuthority() {
		return "ROLE_" + this.name();
	}
}
