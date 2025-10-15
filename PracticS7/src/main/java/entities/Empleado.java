package entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name="empleados")
@Getter
@Setter
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name="nombre", length=50, nullable=false)
    private String nombre;
    @Column (name="apellido", length=50, nullable=false)
    private String apellido;
    @ManyToOne
    @JoinColumn(name="cargo_id", nullable = false)
    private Cargo cargo;

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cargo=" + cargo +
                '}';
    }
}
