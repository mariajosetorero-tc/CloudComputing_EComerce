package e1_ficheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionManager {

    public Properties cargarConfiguracion() throws IOException {

        File carpeta = new File("data/e_commerce");
        carpeta.mkdirs();//crea una carpeta por si no exite

        File archivo = new File(carpeta, "config.properties");

        Properties propiedades = new Properties();//guarda la configuracion en parejas clave-valor

        if (!archivo.exists()) {
            propiedades.setProperty("app.nombre", "E_commerce Store");
            propiedades.setProperty("app.iva", "21.0");
            propiedades.setProperty(
                    "path.catálogo",
                    "./data/ecomarket/productos.dat"
            );//set propierties añade una propiedad 

            try (FileOutputStream salida = new FileOutputStream(archivo)) {
                propiedades.store(salida, "Configuracion");//store guarda las propiedades del archivo
            }
        }

        try (FileInputStream entrada = new FileInputStream(archivo)) {
            propiedades.load(entrada);//lee el archivo
        }

        return propiedades;
    }
}
