import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static final int FACIL = 1;
    static final int MEDIO = 2;
    static final int DIFICIL = 3;
    static final int CUSTOM = 4;

    static int tamanyoTablero = 10;

    static char[][] ocultoTablero = new char[tamanyoTablero][tamanyoTablero];
    static char[][] jugadorTablero = new char[tamanyoTablero][tamanyoTablero];

    static final char agua ='-';
    static final char lancha ='L';
    static final char barco = 'B';
    static final char acorazado = 'Z';
    static final char portaviones = 'P';

    static final int espacioBarco = 2;
    static final int espacioCrucero = 3;
    static final int espacioPortaviones = 4;

    static int lanchasCantidad;
    static int barcosCantidad;
    static int crucerosCantidad;
    static int portavionesCantidad;

    static int intentos = 50;

    public static void main(String[] args) {
        boolean jugando;

        seleccionDeDificultad();
        llenarTablero();

        int ejeX = 0;
        int ejeY = 0;

        do{
            mostrarTableroOculto();
            mostrarTableroJugador();
            System.out.println("Intentos restantes: "+intentos);

            System.out.println("Introduce la coordenada X");
            ejeX = obtenerCoordenadaX();
            System.out.println("Introduce la coordenada Y");
            ejeY = obtenerCoordenadaY();

            compararTableros(ejeY, ejeX);

            intentos--;
            jugando = (intentos > 0);

        }while (jugando);
        if (intentos == 0) System.out.println("Has perdido");
        else System.out.println("Has ganado");
        

    }
    static int obtenerCoordenadaX(){
        Scanner input = new Scanner(System.in);
        boolean valido = false;
        int ejeX = 0;

        while (!valido) {
            try {
                ejeX = input.nextInt();
                if (ejeX < tamanyoTablero && ejeX >= 0) valido = true;
                else System.out.println("No es una coordenada valida");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("No es una coordenada valida");
                input.nextLine();
            }
        }return ejeX;
    }
    static int obtenerCoordenadaY(){
        Scanner input = new Scanner(System.in);
        boolean valido = false;
        char ejeY = 0;

        while (!valido) {
            try {
                ejeY = input.next().charAt(0);
                ejeY = Character.toUpperCase(ejeY);
                ejeY -= 65;
                if (ejeY < tamanyoTablero && ejeY >= 0) valido = true;
                else System.out.println("No es una coordenada valida");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("No es una coordenada valida");
                input.nextLine();
            }
        }return ejeY;
    }
    static void compararTableros(int ejeY, int ejeX){

        if (ocultoTablero[ejeY][ejeX] == agua) jugadorTablero[ejeY][ejeX] = 'A';
        else jugadorTablero[ejeY][ejeX] = 'X';
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
        int dificultad = inputUsuario();
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
                        dificultad = inputUsuario();
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

            switch (tipoBarco) {
                case lancha:
                    lanchaPosicionAleatoria();
                    tipoBarco = barco;
                    break;
                case barco:
                barcoPosicionAleatoria();
                tipoBarco = crucero;
                    break;
                case crucero:
                cruceroPosicionAleatoria();
                tipoBarco = portaviones;
                    break;
                case portaviones:
                    portavionesPosicionAleatoria();
                    tipoBarco = lancha;
                    break;
            }
            int totalBarcos = lanchasCantidad + barcosCantidad + crucerosCantidad + portavionesCantidad;
            hayBarcos = (totalBarcos > 0);
    
        }while (hayBarcos);
        return;
    }
    static void mostrarTableroJugador() {

        System.out.print("\t");
            for (int i = 0; i < jugadorTablero.length; i++){
            System.out.print(i+"\t");
        }
        System.out.print("\n");

        for (int i = 0; i < jugadorTablero.length; i++){
            System.out.print((char)(i+65)+"\t");
            for (int j = 0; j < jugadorTablero[i].length; j++){
                System.out.print(jugadorTablero[i][j]+"\t");
            }
            System.out.println();
        }return;
    }
    static void lanchaPosicionAleatoria(){

        int ejeX = 0;
        int ejeY = 0;

            while (lanchasCantidad > 0) {

                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);

                if (posicionValida(lancha, ejeX, ejeY)) {
                    ocultoTablero[ejeX][ejeY] = lancha;
                    lanchasCantidad--;
                }
                 
            }return;

    }
    static void barcoPosicionAleatoria(){
        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        
        while (barcosCantidad > 0) {

            if (ejeX+espacioBarco >= tamanyoTablero) ejeX -= espacioBarco;

            if (posicionValida(barco, ejeY, ejeX)) {

                for (int i = 0; i < 3; i++){
                    ocultoTablero[ejeY][ejeX] = barco;
                    ejeX++;
                    
                }
                barcosCantidad--;
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);

            }
            else{
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            
                    
        }return;

    }
    static void cruceroPosicionAleatoria(){

        int ejeX = 0;
        int ejeY = 0;


        while (crucerosCantidad > 0) {
            if (ejeX+espacioCrucero >= tamanyoTablero) ejeX -= espacioCrucero;

            if (posicionValida(acorazado, ejeY, ejeX)) {
                for (int i = 0; i < 4; i++){
                    ocultoTablero[ejeY][ejeX] = acorazado;
                    ejeX++;
                }
                crucerosCantidad--;
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            else{
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
        }return;

    }
    static void portavionesPosicionAleatoria(){

        int ejeX = 0;
        int ejeY = 0;

        while (portavionesCantidad > 0) {

           if (ejeY+espacioPortaviones >= tamanyoTablero) ejeY -= espacioPortaviones;

            if (posicionValida(portaviones, ejeY, ejeX)) {
                for (int i = 0; i < 5; i++){
                    ocultoTablero[ejeY][ejeX] = portaviones;
                    ejeY++;
                }
                portavionesCantidad--;
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            else{
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            }return;

    }
    static void mostrarTableroOculto() {

        for (int i = 0; i < ocultoTablero.length; i++){
         System.out.println(Arrays.toString(ocultoTablero[i]));
        }return;
    }
    static boolean posicionValida(char tipoBarco,int y, int x){

        switch (tipoBarco) {
            case lancha:
                if (ocultoTablero[y][x] == agua) return true;
                break;
            case barco:
                if (ocultoTablero[y][x] == agua && ocultoTablero[y][x+1] == agua && ocultoTablero[y][x+2] == agua) return true;
                break;
            case acorazado:
                if (ocultoTablero[y][x] == agua && ocultoTablero[y][x+1] == agua && ocultoTablero[y][x+2] == agua && ocultoTablero[y][x+3] == agua) return true;
                break;
            case portaviones:
                if (ocultoTablero[y][x] == agua && ocultoTablero[y+1][x] == agua && ocultoTablero[y+2][x] == agua && ocultoTablero[y+3][x] == agua && ocultoTablero[y+4][x] == agua) return true;
                break;
                
        }
        return false;
    }
}
