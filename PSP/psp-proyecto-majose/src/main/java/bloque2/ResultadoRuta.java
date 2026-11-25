
package bloque2;


public class ResultadoRuta {
 private int idPedido;
    private double distancia;
    private double coste;

    public ResultadoRuta(int idPedido, double distancia, double coste) {
        this.idPedido = idPedido;
        this.distancia = distancia;
        this.coste = coste;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getCoste() {
        return coste;
    }

    @Override
    public String toString() {
        return "ResultadoRuta{" + "idPedido=" + idPedido + ", distancia=" + distancia + ", coste=" + coste + '}';
    }

   
}
