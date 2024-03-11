// 10 / 03 / 2024
//Cristian Andres Monterrosa Durango
//Julian Esteban Monsalve Idarraga
package juegocartas;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Carta {
    
    private int indice;
    
    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }
    
    public void mostrar(JPanel pnl, int x, int y) {
        //Generar el nombre del archivo
        String nombreArchivo = "/images/CARTA" + String.valueOf(indice) + ".JPG" ;
        
        //Cargar la imagen en memoria
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreArchivo));
        
        //Crear la instancia del objeto JLabel
        JLabel lbl = new JLabel(imagen);
     
        //Definir las coordenadas de despliegue y las dimensiones
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());

        //Agregar la etiqueta al panel
        pnl.add(lbl);
    }
    
    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.Trébol;
        }
        else if (indice <= 26) {
            return Pinta.Pica;
        }
        else if (indice <= 39) {
            return Pinta.Corazón;
        }
        else {
            return Pinta.Diamante;
        }
    }
    
    public NombreCarta getNombreCarta() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo-1];
    }
    
           
}
