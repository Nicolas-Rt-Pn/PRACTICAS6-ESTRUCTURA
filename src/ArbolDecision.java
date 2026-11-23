import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ArbolDecision {
    private BinaryNode<String> raiz;

    // Crea un árbol que solo conoce un animal
    public ArbolDecision(String animal) {
        raiz = new BinaryNode<>(animal);
    }

    // Crea un árbol a partir de una raíz ya construida (lo usan cargar y los tests)
    protected ArbolDecision(BinaryNode<String> raiz) {
        this.raiz = raiz;
    }

    public BinaryNode<String> getRaiz() {
        return raiz;
    }

    // Métodos a implementar

    //Ejercicio1
    public boolean esExtendido() {

        if(raiz == null) return true;
        else if(raiz.isLeaf()) return true;
        else if(raiz.left()!=null && raiz.right()!=null){
            return esExtendido(raiz.left()) && esExtendido(raiz.right());
        }
        else return false;
    }

    private boolean esExtendido(BinaryNode<String> r){
        if(r.isLeaf()) return true;
        else if(r.left()!=null && r.right()!=null){
            return esExtendido(r.left()) && esExtendido(r.right());
        }
        else return false;
    }


    //Ejercicio2
    public int numAnimales() {

        if(raiz==null) return 0;
        else if(raiz.isLeaf()) return 1;
        else return numAnimales(raiz.left()) + numAnimales(raiz.right());
    }
    private int numAnimales(BinaryNode<String> r){
        if(r.isLeaf()) return 1;
        else return numAnimales(r.left()) + numAnimales(r.right());
    }


    //Ejercicio3
    public List<String> animales() {
        List<String> l = new ArrayList<>();
        if(raiz == null) return l;
        else if(raiz.isLeaf()) {
            l.add(raiz.getData());
            return l;
        }
        else {
            agregar(raiz.left(), l);
            agregar(raiz.right(), l);
        }
        return l;
    }

    private void agregar(BinaryNode<String> r, List<String> l){
        if(r == null) return;
        else if(r.isLeaf()) l.add(r.getData());
        else{
            agregar(r.left(), l);
            agregar(r.right(), l);
        }
    }



    //Ejercicio 4
    public List<String> camino (String elem) {
        List<String> l = new LinkedList<>();
        if(raiz == null) return l;
        else if(raiz.isLeaf()){
            if(raiz.getData().equals(elem)){
                l.add(elem);
                return l;
            }
            else return l;
        }
        else if (raiz.getData().equals(elem)) {
            l.add(elem);
        }
        else{
            if(build(raiz.left(), l, elem) || build(raiz.left(), l, elem)) l.add(0, raiz.getData());
        }
        return l;
    }
    private boolean build(BinaryNode<String> r, List<String> l, String e){
        if(r == null) return false;
        else if(r.getData().equals(e)) {
            l.add(0, r.getData());
            return true;
        }
        else if(build(r.left(), l, e) || build(r.left(), l, e)) l.add(0, r.getData());
        return false;
    }


    //Ejercicio 5

    public void aprender (BinaryNode<String> hoja, String animalNuevo, String pregunta, boolean respuestaNuevo) {
        throw new UnsupportedOperationException("Falta implementar Ejercicio 5");
    }

    //Ejercicio 6
    public List<String> describir(String animal) {
        throw new UnsupportedOperationException("Falta implementar Ejercicio 6");
    }


    //Ejercicio 7
    public String preguntaDiferenciadora(String animal1, String animal2) {
        throw new UnsupportedOperationException("Falta implementar Ejercicio 7");
    }


    public void guardar(String nombreFichero) throws IOException {
        if (raiz == null) {
            throw new IllegalStateException("No se puede guardar un árbol vacío");
        }
        try (PrintWriter out = new PrintWriter(nombreFichero, StandardCharsets.UTF_8.name())) {
            guardar(raiz, out);
            if (out.checkError()) {
                throw new IOException("Error al escribir en " + nombreFichero);
            }
        }
    }

    //Ejercicio 8
    private void guardar(BinaryNode<String> r, PrintWriter out) {
        throw new UnsupportedOperationException("Falta implementar Ejercicio 8");
    }

    // ---------- cargar ----------

    public static ArbolDecision cargar(String nombreFichero) throws IOException {
        try (Scanner sc = new Scanner(new File(nombreFichero), StandardCharsets.UTF_8.name())) {
            if (!sc.hasNextLine()) {
                throw new IOException("El fichero está vacío");
            }
            BinaryNode<String> raiz = leerNodo(sc);
            while (sc.hasNextLine()) {
                if (!sc.nextLine().trim().isEmpty()) {
                    throw new IOException("Sobran líneas después de completar el árbol");
                }
            }
            return new ArbolDecision(raiz);
        }
    }

    // Lee un subárbol completo: una hoja, o una pregunta seguida de sus dos subárboles
    private static BinaryNode<String> leerNodo(Scanner sc) throws IOException {
        if (!sc.hasNextLine()) {
            throw new IOException("El fichero termina antes de completar el árbol");
        }
        String linea = sc.nextLine();
        if (linea.startsWith("A:")) {
            return new BinaryNode<>(linea.substring(2));
        }
        if (linea.startsWith("P:")) {
            BinaryNode<String> nodo = new BinaryNode<>(linea.substring(2));
            nodo.setLeft(leerNodo(sc));
            nodo.setRight(leerNodo(sc));
            return nodo;
        }
        throw new IOException("Línea con formato incorrecto: " + linea);
    }
}
