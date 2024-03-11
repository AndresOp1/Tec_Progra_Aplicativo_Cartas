// 10 / 03 / 2024
//Cristian Andres Monterrosa Durango
//Julian Esteban Monsalve Idarraga

package juegocartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int totalCartas = 10;
    private int margen = 20;
    private int distancia = 36;

    private Carta[] cartas = new Carta[totalCartas];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < totalCartas; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public int totalCartas() {
        for (int i = 0; i < totalCartas; i++) {
            cartas[i] = new Carta(r);
        }
        return totalCartas;
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = 347;

        for (Carta c : cartas) {
            c.mostrar(pnl, x, margen);
            x -= distancia;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[13];
        for (Carta c : cartas) {
            contadores[c.getNombreCarta().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int c : contadores) {
            if (c >= 2) {
                totalGrupos++;
            }
        }

        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }

        return mensaje;
    }

    public String getEscaleras() {

        StringBuilder mensaje = new StringBuilder("Escaleras Encontradas: \n");
        boolean escalerasEncontradas = false;

        // recorrer sobre cada posible pinta
        for (Pinta pinta : Pinta.values()) {
            List<Integer> valoresC = new ArrayList<>();

            // Obtenemos los valores de las cartas con la pinta actual
            for (Carta c : cartas) {
                if (c.getPinta() == pinta) {
                    valoresC.add(c.getNombreCarta().ordinal());
                }
            }

            // Si hay al menos dos cartas con la misma pinta, buscamos escaleras
            if (valoresC.size() >= 2) {
                Collections.sort(valoresC);

                // recorremos sobre los valores de las cartas para buscar escaleras
                for (int i = 0; i < valoresC.size() - 1; i++) {
                    int valorActual = valoresC.get(i);
                    int contador = 1;

                    // Comprobamos si la siguiente carta forma parte de la escalera
                    while (i + contador < valoresC.size() && valoresC.get(i + contador) == valorActual + contador) {
                        contador++;
                    }

                    // Si se encuentra una escalera, agregarla al mensaje
                    if (contador >= 2) {
                        escalerasEncontradas = true;
                        mensaje.append("Escalera de ").append(NombreCarta.values()[valorActual]);
                        mensaje.append(" a ").append(NombreCarta.values()[valorActual + contador - 1]);
                        mensaje.append(" de ").append(pinta).append("\n");
                        // Saltamos al siguiente grupo de cartas después de la escalera encontrada
                        i += contador - 1;
                    }
                }
            }
        }

        // Si no se encontraron escaleras, agregamos un mensaje indicándolo
        if (!escalerasEncontradas) {
            mensaje.append("No se encontraron escaleras.");
        }

        return mensaje.toString();
    }

    public int calcularPuntaje() {
        int puntaje = 0;
        String grupos = getGrupos();
        String escaleras = getEscaleras();

        for (Carta c : cartas) {
            String nombreCarta = c.getNombreCarta().toString();

            // Verificar si la carta no está en ningún grupo ni en ninguna escalera
            if (!grupos.contains(nombreCarta) && !escaleras.contains(nombreCarta)) {
                // Asignar puntaje según el valor de la carta
                if (c.getNombreCarta().ordinal() > 9 || c.getNombreCarta().ordinal() < 1) {
                    puntaje += 10;  // "Ace", "Jack", "Queen" o "King" valen 10 puntos
                } else {
                    puntaje += c.getNombreCarta().ordinal() + 1;  // El valor es su ordinal + 1 (que es el valor de la carta)
                }
            }
        }

        return puntaje;
    }

//profe este metodo no sirve para nada jajaja, nos costo mucho hacerlo y no quisimos borrarlo jeje
    public List<Carta> getNoEscalera() {
        List<Carta> cartaNoEsc = new ArrayList<>();

        for (Pinta pinta : Pinta.values()) {
            List<Integer> valoresC = new ArrayList<>();

            for (Carta c : cartas) {
                if (c.getPinta() == pinta) {
                    valoresC.add(c.getNombreCarta().ordinal());
                }
            }
            if (valoresC.size() >= 2) {
                Collections.sort(valoresC);
                for (int i = 0; i < valoresC.size() - 1; i++) {
                    int valorActual = valoresC.get(i);
                    int contador = 1;

                    while (i + contador < valoresC.size() && valoresC.get(+contador) == valorActual + contador) {
                        contador++;
                    }
                    if (contador >= 2) {
                        for (int x = i; x < i + contador; x++) {
                            cartaNoEsc.add(cartas[x]);
                        }
                    }
                }
            }
        }
        return cartaNoEsc;
    }
//
//    public int getConteo(Jugador jugador, String grupos, String escaleras) {
//        int acomulador = 1;
//        if (grupos.equals("se hay grupos") && escaleras.equals("no hay escaleras")) {
//
//            for (Carta carta : jugador.cartas) {
//                acomulador += carta.getNombreCarta().ordinal();
//            }
//            return acomulador;
//
//        } else {
//            return 0;
//        }
//
//    }

}
