package ejemplo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author tempo001
 */
public class Ejemplo {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ParseException {
        List<ItemPrincipal> productos = new ArrayList<>();
        
        for(int i = 1; i<=100; i++){
            int id = randBetween(1000, 9999);
            int cantidad_detalle = randBetween(1,15);
            productos.add(new ItemPrincipal(id, "producto " + id, generaFecha(), 
                    cantidad_detalle%2==0, generaListaRandom(cantidad_detalle,id),i));
        }
        System.out.println("Items sin ordenar: ");
        for (ItemPrincipal p : productos) {
            System.out.println(p.toString());
        }

        Map mapa = new HashMap();
        List<Detalle> temporal = new ArrayList<>();
        for (ItemPrincipal p : productos) {
            for(Detalle d : p.getItems()){
                temporal.add(d);
            }
        }
        
        System.out.println("Detalle sin ordenar: ");
        for(Detalle d : temporal){
            System.out.println(d.toString());
        }
        
        Comparator<Detalle> comparadorMultiple
                = Comparator.comparing(Detalle::getFecha)
                        .thenComparing(Comparator.comparing(Detalle::getUrgente).reversed()
                        );
        List<Detalle> listaOrdenada = temporal.stream().sorted(comparadorMultiple).collect(Collectors.toList());
        System.out.println("Detalle ordenada: ");
        for (Detalle p : listaOrdenada) {
            System.out.println(p.toString());
        }
        
        for (ItemPrincipal p : productos) {
            p.setOrden(0);
        }
        int orden = 1;
        
        for (ItemPrincipal p : productos) {
            if(p.getOrden() == 0){
                for (Detalle d : listaOrdenada) {
                    if(d.getIdItemPrincipal() == p.getId()){
                        p.setOrden(orden++);
                        break;
                    }
                }
            }
        }
        
        System.out.println("Items ordenados");
        for (ItemPrincipal p : productos) {
            System.out.println(p.toString());
        }
         
    }

    public static LocalDate generaFecha() throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1900, 2010);

        gc.set(GregorianCalendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));

        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // parsing the string to convert it into date
        int idia = gc.get(GregorianCalendar.DAY_OF_MONTH);
        int imes = (gc.get(GregorianCalendar.MONTH) + 1);
        String dia = agregaCero(idia);
        String mes = agregaCero(imes);
        LocalDate local_date = LocalDate.parse(dia + "/" + mes + "/" + gc.get(GregorianCalendar.YEAR), JEFormatter);
        return local_date;
    }

    private static String agregaCero(int valor) {
        String cadena = "";
        if (valor < 10) {
            cadena = "0" + valor;
        } else {
            cadena = String.valueOf(valor);
        }
        return cadena;
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private static List generaListaRandom(int total, int idItemPrincipal) throws ParseException {
        List<Detalle> lista = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            int id = randBetween(1000, 9999);
            int cantidad_detalle = randBetween(1,15);
            lista.add(
                    new Detalle(id, "Item " + id, generaFecha(), cantidad_detalle%2==0, idItemPrincipal)
            );
        }

        return lista;
    }
}

class ItemPrincipal {

    private int id;
    private String nombre;
    private LocalDate fecha;
    private Boolean urgente;
    private List<Detalle> items;
    private int orden;
            
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public List<Detalle> getItems() {
        return items;
    }

    public void setItems(List<Detalle> items) {
        this.items = items;
    }
    
    public ItemPrincipal() {
    }

    public ItemPrincipal(int id, String nombre, LocalDate fecha, Boolean urgente, List<Detalle> items, int orden) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.urgente = urgente;
        this.items = items;
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "ItemPrincipal{" + "id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + ", urgente=" + urgente + ", orden=" + orden + '}';
    }

    

}

class Detalle {

    private int id;
    private String nombre;
    private LocalDate fecha;
    private Boolean urgente;
    private int idItemPrincipal;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    @Override
    public String toString() {
        return "Detalle{" + "id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + ", urgente=" + urgente + ", idItemPrincipal=" + idItemPrincipal + '}';
    }

    public Detalle() {
    }

    public int getIdItemPrincipal() {
        return idItemPrincipal;
    }

    public void setIdItemPrincipal(int idItemPrincipal) {
        this.idItemPrincipal = idItemPrincipal;
    }

    public Detalle(int id, String nombre, LocalDate fecha, Boolean urgente, int idItemPrincipal) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.urgente = urgente;
        this.idItemPrincipal = idItemPrincipal;
    }
    
    
}
