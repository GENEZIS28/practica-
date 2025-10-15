package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cargos")
@Getter
@Setter
public class Cargo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="nombre_cargo",length = 100, nullable=false)
    private String nombre;

    @Column (name="salario", nullable = false)
    private double salario;

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", salario=" + salario +
                '}';
    }
}
