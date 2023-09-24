package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {
	@NotBlank
	private String calle;
	@NotBlank
	private String distrito;
	@NotBlank
	private String ciudad;
	@NotBlank
	private String numero;
	@NotBlank
	private String complemento;
	

	public Direccion(DatosDireccion direccion) {
		this.calle = direccion.calle();
		this.distrito = direccion.distrito();
		this.ciudad = direccion.ciudad();
		this.numero = direccion.numero();
		this.complemento = direccion.complemento();
	
	}


    public Direccion actualizarDatos(DatosDireccion direccion) {
		this.calle = direccion.calle();
		this.distrito = direccion.distrito();
		this.ciudad = direccion.ciudad();
		this.numero = direccion.numero();
		this.complemento = direccion.complemento();
		return this;
    }
}
