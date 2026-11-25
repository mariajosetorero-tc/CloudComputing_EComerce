/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e1_ficheros;

/**
 *
 * @author dam2
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestorIntercambios {//clase para la entrega 2

    public static void exportarXML(List<Producto> productos) throws Exception {

        // creamos el documento xml utilizando DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element raiz = doc.createElement("catalogo"); // creamos la etiqueta principal del catálogo
        doc.appendChild(raiz);

        for (Producto p : productos) {// Recorremos p y creamos sus etiquetas con todos sus datos

            Element producto = doc.createElement("producto");
            producto.setAttribute("id", String.valueOf(p.getId()));

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(p.getNombre());
            producto.appendChild(nombre);

            Element precio = doc.createElement("precio");
            precio.setTextContent(String.valueOf(p.getPrecio()));
            producto.appendChild(precio);

            Element stock = doc.createElement("stock");
            stock.setTextContent(String.valueOf(p.getStock()));
            producto.appendChild(stock);

            Element activo = doc.createElement("activo");
            activo.setTextContent(String.valueOf(p.isActivo()));
            producto.appendChild(activo);

            raiz.appendChild(producto);
        }

        // Configuramos el transformador para guardar el documento XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");// dar formato xml y dar ruta

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("catalogo.xml"));

        // guardamos el documento xml en el archivo
        transformer.transform(source, result);

        System.out.println("Catálogo exportado a XML correctamente.");
    }

    public static void importarXMLSAX(String ruta) throws Exception {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        DefaultHandler manejador = new DefaultHandler() {

            Producto producto;
            String contenido = "";

            @Override
            public void startElement(String uri, String localName,
                    String qName, Attributes atributos) {

                contenido = "";

                if (qName.equals("producto")) {
                    producto = new Producto();
                    producto.setId(Integer.parseInt(atributos.getValue("id")));
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                contenido = contenido + new String(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) {

                if (qName.equals("nombre")) {
                    producto.setNombre(contenido);
                } else if (qName.equals("precio")) {
                    producto.setPrecio(Double.parseDouble(contenido));
                } else if (qName.equals("stock")) {
                    producto.setStock(Integer.parseInt(contenido));
                } else if (qName.equals("activo")) {
                    producto.setActivo(Boolean.parseBoolean(contenido));
                } else if (qName.equals("producto")) {
                    System.out.println(producto);
                }
            }
        };

        parser.parse(new File(ruta), manejador);

        System.out.println("Catálogo importado mediante SAX correctamente.");
    }

    public static void exportarJSON(List<Producto> productos) throws IOException {

        // Creamos el objeto encargado de trabajar con JSON
        ObjectMapper mapper = new ObjectMapper();

        // Damos formato al archivo JSON
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Exportamos los productos
        File archivo = new File("catalogo.json");
        mapper.writeValue(archivo, productos);

        System.out.println("Catálogo exportado a JSON correctamente.");

        Producto[] productosLeidos = mapper.readValue(archivo, Producto[].class);//leemos el json

       
        for (Producto p : productosLeidos) {
            System.out.println(p);
        }
    }
}
