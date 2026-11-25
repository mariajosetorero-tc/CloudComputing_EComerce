package bloque2;

public class Pedido {

    private int id;
    private String origen;
    private String destino;

    public Pedido(int id, String origen, String destino) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }
}
