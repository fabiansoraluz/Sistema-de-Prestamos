package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisprestamo.entity.Enlace;
import com.sisprestamo.entity.Grupo;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//Buscar usuario por username y password
	@Query("SELECT u FROM Usuario u WHERE u.username = :#{#usu.username} and u.password = :#{#usu.password}")
	public abstract Usuario login(@Param(value = "usu") Usuario bean);
	
	//Obtener roles y enlaces
	@Query("SELECT re.enlace FROM RolHasEnlace re WHERE re.rol.id = :var_idRol")
	public abstract List<Enlace> traerEnlacesDeUsuario(@Param("var_idRol") int idRol);

	//Obtener roles de usuario
	@Query("SELECT u.rol FROM Usuario u WHERE u.id = :var_idUsuario")
	List<Rol> traerRolesDeUsuario(@Param("var_idUsuario") long idUsuario);
	
	//Obtener usuario por correo
	@Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.persona p LEFT JOIN FETCH u.rol r WHERE u.correo = :correo")
    public Usuario findByCorreo(@Param("correo") String correo);
	
	//Buscar grupo por idUsuario
	@Query("SELECT g.id FROM Grupo g WHERE g.jefe.id = :idUsuario")
	Grupo findIdsByJefeId(@Param("idUsuario") Long idUsuario);

	//traer idUsuario mediante idPersona
	@Query("SELECT u.id FROM Usuario u WHERE u.persona.id = :idPersona")
    public Long findIdUsuarioByIdPersona(@Param("idPersona") Long idPersona);
	
	 @Query("SELECT u.persona.ubigeo.id FROM Usuario u WHERE u.id = :idUsuario")
	 public String BuscarUbigeoPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
	//Buscar usuario por username
	public abstract Usuario findByUsername(String username);
	
	//Validar usuario por correo
	public boolean existsByCorreo(String correo);
	
	//Validar usuario por username
	public boolean existsByUsername(String username);
	
	//Listar jefes prestamistas
	@Query("SELECT U FROM Usuario U WHERE U.rol.id=2")
	public List<Usuario> listarJefes();
}
