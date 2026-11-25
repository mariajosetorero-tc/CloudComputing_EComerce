package e4_hibernate;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fecha;

    private double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detalles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "detalle_pedido",
            joinColumns = @JoinColumn(name = "pedido_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "producto_id", insertable = false, updatable = false)
    )
    private List<Producto> productos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Cliente cliente, double total) {
        this.cliente = cliente;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}