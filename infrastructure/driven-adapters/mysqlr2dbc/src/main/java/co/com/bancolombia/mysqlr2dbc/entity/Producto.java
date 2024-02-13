package co.com.bancolombia.mysqlr2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
public class Producto {
    @Id
    private Long idproducto;
    private String nombre;
    private Integer precio ;

    public Producto() {
    }

    public Producto(Long idproducto, String nombre, Integer precio) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
