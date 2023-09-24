package med.voll.api.domain.usuarios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepositorio extends CrudRepository<Usuario,Long> {

    UserDetails findByUsuario(String username);

}
