package run;

import entities.Cargo;
import entities.Empleado;
import repository.dao.DCargo;
import repository.dao.DEmpleado;

public class Main {
    public static void main(String[] args) {
        DCargo dCargo = new DCargo();
        DEmpleado dEmp = new DEmpleado();

        // Cargos
        Cargo dev = new Cargo(); dev.setNombre("Desarrollador"); dev.setSalario(1200); dev = dCargo.create(dev);
        Cargo qa  = new Cargo(); qa.setNombre("QA");            qa.setSalario(1000);  qa  = dCargo.create(qa);

        // Empleados
        Empleado e1 = new Empleado();
        e1.setNombre("Ana"); e1.setApellido("Gómez"); e1.setCargo(dev);
        e1 = dEmp.create(e1);

        Empleado e2 = new Empleado();
        e2.setNombre("Luis"); e2.setApellido("Martínez"); e2.setCargo(qa);
        e2 = dEmp.create(e2);

        System.out.println("== Empleados inicial ==");
        dEmp.getAll().forEach(System.out::println);

        // Cambiar cargo
        dEmp.changeCargo(e2.getId(), dev.getId());

        System.out.println("== Empleados post cambio ==");
        dEmp.getAll().forEach(System.out::println);

        // Actualizar nombre/apellido
        dEmp.findById(e1.getId()).ifPresent(emp -> {
            emp.setApellido("G. López");
            dEmp.update(emp);
        });

        // Búsqueda por nombre/apellido
        dEmp.searchByNombreApellido("ana").forEach(System.out::println);

        // Eliminar
        dEmp.delete(e2.getId());

        System.out.println("== Empleados final ==");
        dEmp.getAll().forEach(System.out::println);
    }
}
