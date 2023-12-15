import java.util.Arrays;
import java.util.Scanner;

public class Main {

    enum opciones {
        FACIL,
        MEDIO,
        DIFICIL,
        CUSTOM
    }
    static int tamanyoTablero = 10;

    static char[][] ocultoTablero = new char[tamanyoTablero][tamanyoTablero];
    static char[][] jugadorTablero = new char[tamanyoTablero][tamanyoTablero];

    static final char agua ='-';
    static final char lancha ='L';
    static final int barco = 'B';
    static final int crucero = 'C';
    static final int portaviones = 'P';

    static final int tamanyoLancha = 1;
    static final int tamanyoBarco = 3;
    static final int tamanyoCrucero = 4;
    static final int tamanyoPortaviones = 5;

    static int lanchasCantidad;
    static int barcosCantidad;
    static int crucerosCantidad;
    static int portavionesCantidad;

    static int intentos = 50;

    public static void main(String[] args) {
        boolean jugando;
        seleccionDeDificultad();
        llenarTablero();
        //mostrarTableroOculto();
        do{
            System.out.println("Intentos restantes: "+intentos);
            if (inputUsuario() == 8) break;
            mostrarTableroJugador();

            intentos--;
            jugando = (intentos > 0);

        }while (jugando);
        if (intentos == 0) System.out.println("Has perdido");
        else System.out.println("Has ganado");
        

    }
    static int inputUsuario(){

        Scanner input = new Scanner(System.in);
        boolean valido = false;
        int opcion = 0;

        while (!valido) {
            try {
                opcion = input.nextInt();
                valido = true;
            } catch (Exception e) {
                System.out.println("No es un numero valido");
                input.nextLine();
            }
        }
        
        return opcion;
    }
    static void seleccionDeDificultad(){

        System.out.println("Selecciona una dificultad:\n\n1. Facil\n2. Medio\n3. Dificil\n4. Custom\n");
        int opcion = inputUsuario()-1;
        opciones dificultad = opciones.values()[opcion];
        boolean seleccionado = false;

        while (!seleccionado) {
                switch (dificultad) {
                    case FACIL:
                        System.out.println("Modo Facil");
                        lanchasCantidad = 5;
                        barcosCantidad = 3;
                        crucerosCantidad = 1;
                        portavionesCantidad = 1;
                        seleccionado = true;
                        break;
                    case MEDIO:
                        System.out.println("Modo Medio");
                        lanchasCantidad = 2;
                        barcosCantidad = 1;
                        crucerosCantidad = 1;
                        portavionesCantidad = 1;
                        seleccionado = true;
                        break;
                    case DIFICIL:
                        System.out.println("Modo Dificil");
                        lanchasCantidad = 1;
                        barcosCantidad = 1;
                        seleccionado = true;
                        break;
                    case CUSTOM:
                        System.out.println("Modo Custom");
                        seleccionado = true;
                        break;
                    default:
                        System.out.println("No es una opcion valida");
                        break;
            }   
        }
    }
    static void llenarTablero() {

        for (int i = 0; i < ocultoTablero.length; i++){
            for (int j = 0; j < ocultoTablero[i].length; j++){
                ocultoTablero[i][j] = agua;
                jugadorTablero[i][j] = agua;
            }
        }

        boolean hayBarcos;

        char tipoBarco = lancha;

        do {

            if (tipoBarco == lancha) {
                lanchaPosicionAleatoria();
                tipoBarco = barco;
            } else if (tipoBarco == barco) {
                barcoPosicionAleatoria();
                tipoBarco = crucero;
            } else if (tipoBarco == crucero) {
                cruceroPosicionAleatoria();
                tipoBarco = portaviones;
            } else if (tipoBarco == portaviones) {
                portavionesPosicionAleatoria();
            }
            int totalBarcos = lanchasCantidad + barcosCantidad + crucerosCantidad + portavionesCantidad;
            hayBarcos = (totalBarcos > 0);
    
        }while (hayBarcos);
        return;
    }
    static void mostrarTableroJugador() {

        
        for (int i = 0; i < jugadorTablero.length; i++){
            if(i>0)System.out.print(i+"\t");
            else System.out.print("\n");

            if (i == 0) {
                for (int j = 0; j < jugadorTablero[i].length; j++){
                    if (j == 0) System.out.print("\t"+j+"\t");
                    else System.out.print(j+"\t");
                }
            }else for (int j = 0; j < jugadorTablero[i].length; j++){
                    System.out.print(jugadorTablero[i][j] + "\t");
            }System.out.println("\n");
        }return;
    }
    static void lanchaPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

            while (lanchasCantidad > 0) {

                xEjePosicionAleatoria = (int) (Math.random() * 10);
                yEjePosicionAleatoria = (int) (Math.random() * 10);

                if (ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) {
                        ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = lancha;
                        
                }
                lanchasCantidad--;
                        
            }return;

    }
    static void barcoPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (barcosCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = barco;
            barcosCantidad--;
                    
        }return;

    }
    static void cruceroPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (crucerosCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = crucero;
            crucerosCantidad--;
        }return;

    }
    static void portavionesPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (portavionesCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) ocultoTablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = portaviones;
            
            portavionesCantidad--;
                    
            }return;

    }
    static void mostrarTableroOculto() {

        for (int i = 0; i < ocultoTablero.length; i++){
         System.out.println(Arrays.toString(ocultoTablero[i]));
        }return;
    }
}