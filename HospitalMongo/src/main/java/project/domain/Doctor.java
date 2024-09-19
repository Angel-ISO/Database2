package project.domain;

public class Doctor {
    private String id;
    private String nombre;
    private String especialidad;
    private int añosexperiencia;

    public Doctor() {}

    public Doctor(String id, String nombre, String especialidad, int añosexperiencia) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.añosexperiencia = añosexperiencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getAñosexperiencia() {
        return añosexperiencia;
    }

    public void setAñosexperiencia(int añosexperiencia) {
        this.añosexperiencia = añosexperiencia;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", añosexperiencia=" + añosexperiencia +
                '}';
    }

}
