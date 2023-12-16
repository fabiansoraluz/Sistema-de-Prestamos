package com.sisprestamo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sisprestamo.entity.Enlace;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private int idUsuario;
	private String login;
	private String password;
	private String nombreUsuario;
	private static String nombreCompleto;
	private Collection<? extends GrantedAuthority> authorities;
	private List<Enlace> enlaces;
	
	public static UsuarioPrincipal build(Usuario usuario, List<Rol> roles, List<Enlace> enlaces) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Rol x : roles) {
			authorities.add(new SimpleGrantedAuthority(x.getNombre()));
		}
		
		nombreCompleto = usuario.getUsername();
		
		return new UsuarioPrincipal((int) usuario.getId(), usuario.getUsername(), usuario.getPassword(), usuario.getUsername(), authorities, enlaces);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
}
