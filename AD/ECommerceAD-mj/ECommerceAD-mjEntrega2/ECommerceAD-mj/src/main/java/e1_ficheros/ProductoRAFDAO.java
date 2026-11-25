package e1_ficheros;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ProductoRAFDAO {

     private final String ruta;
    private final int TAM = 77;

    public ProductoRAFDAO(String ruta) {
        this.ruta = ruta;
        new File(ruta).getParentFile().mkdirs();
    }

    

    public boolean insertar(Producto p) throws IOException {

        if (p.getId() < 1) {
            return false;
        }

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {

            long pos = (long) (p.getId() - 1) * TAM;

            if (pos < raf.length()) {
                raf.seek(pos);
                int idExistente = raf.readInt();

                if (idExistente != 0) {
                    return false;
                }
            }

            while (raf.length() < pos) {
                long hueco = raf.length();
                int id = (int) (hueco / TAM) + 1;

                raf.seek(hueco);
                escribir(raf, new Producto(id, "", 0, 0, false));
            }

            raf.seek(pos);
            escribir(raf, p);

            return true;
        }
    }

    private void escribir(RandomAccessFile raf, Producto p)
            throws IOException {

        raf.writeInt(p.getId());

        String nombre = p.getNombre();

        for (int i = 0; i < 30; i++) {
            if (i < nombre.length()) {
                raf.writeChar(nombre.charAt(i));
            } else {
                raf.writeChar(' ');
            }
        }

        raf.writeDouble(p.getPrecio());
        raf.writeInt(p.getStock());
        raf.writeBoolean(p.isActivo());
    }

    private Producto leer(RandomAccessFile raf) throws IOException {

        int id = raf.readInt();
        String nombre = "";

        for (int i = 0; i < 30; i++) {
            nombre += raf.readChar();
        }

        double precio = raf.readDouble();
        int stock = raf.readInt();
        boolean activo = raf.readBoolean();

        return new Producto(id, nombre.trim(), precio, stock, activo);
    }

    public Producto buscarPorId(int id) throws IOException {

        if (id < 1) {
            return null;
        }

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {

            long pos = (long) (id - 1) * TAM;

            if (pos + TAM > raf.length()) {
                return null;
            }

            raf.seek(pos);
            Producto p = leer(raf);

            if (p.getId() != id) {
                return null;
            }

            return p;
        }
    }

    public boolean modificarStock(int id, int nuevoStock)
            throws IOException {

        Producto p = buscarPorId(id);

        if (p == null || !p.isActivo() || nuevoStock < 0) {
            return false;
        }

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek((long) (id - 1) * TAM + 72);
            raf.writeInt(nuevoStock);
            return true;
        }
    }

    public boolean borradoLogico(int id) throws IOException {

        Producto p = buscarPorId(id);

        if (p == null || !p.isActivo()) {
            return false;
        }

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek((long) (id - 1) * TAM + 76);
            raf.writeBoolean(false);
            return true;
        }
    }

    public long numeroRegistros() throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {
            return raf.length() / TAM;
        }
    }

    public Producto leerPorIndice(int indice) throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r")) {

            long pos = (long) indice * TAM;

            if (pos < 0 || pos + TAM > raf.length()) {
                return null;
            }

            raf.seek(pos);
            return leer(raf);
        }
    }
}
