import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int tamanyoTablero = 10;

    static char[][] ocultoTablero = new char[tamanyoTablero][tamanyoTablero];
    static char[][] jugadorTablero = new char[tamanyoTablero][tamanyoTablero];

    static final char AGUA ='-';
    static final char LANCHA ='L';
    static final char BARCO = 'B';
    static final char ACORAZADO = 'Z';
    static final char PORTAVIONES = 'P';

    static final int ESPACIO_BARCO = 2;
    static final int ESPACIO_ACORAZADO = 3;
    static final int ESPACIO_PORTAVIONES = 4;

    static int lanchasCantidad;
    static int barcosCantidad;
    static int acorazadoCantidad;
    static int portavionesCantidad;
    static String[] posicionBarcos;
    static String[] posicionAcorazados;
    static String[] posicionPortaviones;

    static int naviosRestantes;

    static int intentos = 50;

    public static void main(String[] args) {
        boolean jugando;

        seleccionDeDificultad();
        llenarTablero();

        do{
            //mostrarTableroOculto();
            mostrarTableroJugador();

            if (naviosRestantes == 0) break;
            System.out.println("\n\t\t\t---INTENTOS RESTANTES: "+intentos+"---");


            System.out.print("Selecciona una fila: ");
            int ejeX = obtenerCoordenadaX();
            System.out.print("Selecciona una columna: ");
            int ejeY = obtenerCoordenadaY();
            compararTableros(ejeX, ejeY);

            intentos--;

            jugando = (intentos >=0);

        }while (jugando);
        if (intentos == 0) System.out.println("Has perdido");
        else System.out.println("Has ganado");
        
    }
    static void llenarTablero() {
        for (int i = 0; i < ocultoTablero.length; i++){
            for (int j = 0; j < ocultoTablero[i].length; j++){
                ocultoTablero[i][j] = AGUA;
                jugadorTablero[i][j] = AGUA;
            }
        }
        boolean hayNavios;
        char tipoNavio = LANCHA;

        do {
            tipoNavio = switch (tipoNavio) {
                case LANCHA -> {
                    do {

                        int ejeX = (int) (Math.random() * tamanyoTablero);
                        int ejeY = (int) (Math.random() * tamanyoTablero);

                        if (posicionValida(LANCHA, ejeX, ejeY)) {
                            ocultoTablero[ejeX][ejeY] = LANCHA;
                            lanchasCantidad--;
                        }

                    } while (lanchasCantidad > 0);
                    yield BARCO;
                }
                case BARCO -> {
                    colocarNavio(BARCO, ESPACIO_BARCO, posicionBarcos, barcosCantidad);
                    yield ACORAZADO;
                }
                case ACORAZADO -> {
                    colocarNavio(ACORAZADO, ESPACIO_ACORAZADO, posicionAcorazados, acorazadoCantidad);
                    yield PORTAVIONES;
                }
                case PORTAVIONES -> {
                    colocarNavio(PORTAVIONES, ESPACIO_PORTAVIONES, posicionPortaviones, portavionesCantidad);
                    yield LANCHA;
                }
                default -> tipoNavio;
            };
            int totalNavios = lanchasCantidad + barcosCantidad + acorazadoCantidad + portavionesCantidad;
            hayNavios = (totalNavios > 0);

        }while (hayNavios);

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
        final int FACIL = 1;
        final int MEDIO = 2;
        final int DIFICIL = 3;
        final int CUSTOM = 4;

        System.out.println("Selecciona una dificultad:\n\n1. Facil\n2. Medio\n3. Dificil\n4. Custom\n");
        int dificultad = inputUsuario();
        boolean seleccionado = false;

        while (!seleccionado) {
            switch (dificultad) {
                case FACIL:
                    System.out.println("Modo Facil");
                    lanchasCantidad = 5; barcosCantidad = 3; acorazadoCantidad = 1; portavionesCantidad = 1;
                    seleccionado = true;
                    break;
                case MEDIO:
                    System.out.println("Modo Medio");
                    lanchasCantidad = 2; barcosCantidad = 1; acorazadoCantidad = 1; portavionesCantidad = 1;
                    seleccionado = true;
                    break;
                case DIFICIL:
                    System.out.println("Modo Dificil");
                    lanchasCantidad = 1; barcosCantidad = 1;
                    seleccionado = true;
                    break;
                case CUSTOM:
                    System.out.println("Modo Custom");
                    modoCustom();
                    seleccionado = true;
                    break;
                default:
                    System.out.println("No es una opcion valida");
                    dificultad = inputUsuario();
                    break;
            }
        }naviosRestantes = lanchasCantidad + barcosCantidad*3 + acorazadoCantidad*4 + portavionesCantidad*5;
        posicionBarcos = new String[barcosCantidad];
        posicionAcorazados = new String[acorazadoCantidad];
        posicionPortaviones = new String[portavionesCantidad];
    }
    static void modoCustom(){

    }
    static void colocarNavio (char tipoNavio, int tamanyoNavio, String[] posicionNavio, int navioCantidad){
        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;

        if (tipoNavio != PORTAVIONES) {
            while (navioCantidad > 0) {

                if (ejeX + tamanyoNavio >= tamanyoTablero) ejeX -= tamanyoNavio;

                if (posicionValida(tipoNavio, ejeY, ejeX)) {

                    for (int i = 0; i <= tamanyoNavio; i++) {
                        ocultoTablero[ejeY][ejeX] = tipoNavio;
                        char ejeYConvertido = convertirAChar(ejeY);
                        char ejeXConvertido = convertirAChar(ejeX);
                        ejeX++;
                        if (posicionNavio[indice] == null) posicionNavio[indice] = ejeYConvertido + "" + ejeXConvertido;
                        else posicionNavio[indice] += ejeYConvertido + "" + ejeXConvertido;
                    }

                    indice++;
                    navioCantidad--;

                }
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            switch (tipoNavio) {
                case BARCO:
                    barcosCantidad = navioCantidad;
                    posicionBarcos = posicionNavio;
                    break;
                case ACORAZADO:
                    acorazadoCantidad = navioCantidad;
                    posicionAcorazados = posicionNavio;
                    break;
            }
        }else{
            while (navioCantidad > 0) {

                if (ejeY + tamanyoNavio >= tamanyoTablero) ejeY -= tamanyoNavio;

                if (posicionValida(tipoNavio, ejeY, ejeX)) {

                    for (int i = 0; i <= tamanyoNavio; i++) {
                        ocultoTablero[ejeY][ejeX] = tipoNavio;
                        char ejeYConvertido = convertirAChar(ejeY);
                        char ejeXConvertido = convertirAChar(ejeX);
                        ejeY++;
                        if (posicionNavio[indice] == null) posicionNavio[indice] = ejeYConvertido + "" + ejeXConvertido;
                        else posicionNavio[indice] += ejeYConvertido + "" + ejeXConvertido;
                    }

                    indice++;
                    navioCantidad--;

                }
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
                portavionesCantidad = navioCantidad;
                posicionPortaviones = posicionNavio;
            }
        }
    }
    static boolean posicionValida(char tipoNavio,int y, int x){
        switch (tipoNavio) {
            case LANCHA:
                if (ocultoTablero[y][x] == AGUA) return true;
                break;
            case PORTAVIONES:
                for (int i = 0; i <ESPACIO_BARCO; i++) if (ocultoTablero[y+i][x] != AGUA) return false;
                return true;
            default:
                for (int i = 0; i <ESPACIO_BARCO; i++) if (ocultoTablero[y][x+i] != AGUA) return false;
                return true;
        }
        return false;
    }
    static void mostrarTableroOculto() {
        for (char[] chars : ocultoTablero) {
            System.out.println(Arrays.toString(chars));
        }
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
        }
    }
    static int obtenerCoordenadaY(){
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
    static int obtenerCoordenadaX(){
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
        if (jugadorTablero[ejeY][ejeX] != AGUA) {
            System.out.println("Ya has disparado ahi");
            intentos++;
            return;
        }
        if (ocultoTablero[ejeY][ejeX] == AGUA) {
            jugadorTablero[ejeY][ejeX] = 'A';
            System.out.println("\n\nAGUA");
        }
        else{
            jugadorTablero[ejeY][ejeX] = 'X';
            naviosRestantes--;
            if (tocadoYHundido(ocultoTablero[ejeY][ejeX], ejeY, ejeX)) System.out.println("\n\nTOCADO Y HUNDIDO");
            else System.out.println("\n\nTOCADO");
        } 
    }
    static boolean tocadoYHundido(char tipoNavio, int ejeY, int ejeX){
        boolean hundido = false;
        char ejeYConvertido = convertirAChar(ejeY);
        char ejeXConvertido = convertirAChar(ejeX);
        String coordenada = ejeYConvertido + "" + ejeXConvertido;
        int indice = 0;

        switch (tipoNavio) {
            case LANCHA:
                hundido = true;
                break;
            case BARCO:
                for (int i = 0; i < posicionBarcos.length; i++) {
                    if (posicionBarcos[i].contains(coordenada)) indice = i;   
                }
                for (int i = 0; i <= ESPACIO_BARCO ; i++) {
                    coordenada = posicionBarcos[indice];
                    for (int j = 0; j < coordenada.length(); j+=2) {
                        ejeY = coordenada.charAt(j)-65;
                        ejeX = coordenada.charAt(j+1)-65;
                        if (jugadorTablero[ejeY][ejeX] != 'X') {
                            hundido = false;
                            break;                            
                        }
                        else hundido = true;
                    }

                }
                break;
                case ACORAZADO:
                for (int i = 0; i < posicionAcorazados.length; i++) {
                    if (posicionAcorazados[i].contains(coordenada)) indice = i;   
                }
                for (int i = 0; i <= ESPACIO_ACORAZADO ; i++) {
                    coordenada = posicionAcorazados[indice];
                    for (int j = 0; j < coordenada.length(); j+=2) {
                        ejeY = coordenada.charAt(j)-65;
                        ejeX = coordenada.charAt(j+1)-65;
                        if (jugadorTablero[ejeY][ejeX] != 'X') {
                            hundido = false;
                            break;                            
                        }
                        else hundido = true;
                    }
                    
                }

                break;
                case PORTAVIONES:
                for (int i = 0; i < posicionPortaviones.length; i++) {
                    if (posicionPortaviones[i].contains(coordenada)) indice = i;   
                }
                for (int i = 0; i <= ESPACIO_PORTAVIONES ; i++) {
                    coordenada = posicionPortaviones[indice];
                    for (int j = 0; j < coordenada.length(); j+=2) {
                        ejeY = coordenada.charAt(j)-65;
                        ejeX = coordenada.charAt(j+1)-65;
                        if (jugadorTablero[ejeY][ejeX] != 'X') {
                            hundido = false;
                            break;                            
                        }
                        else hundido = true;
                    }

                }
                break;
            default:
                break;
        }return hundido;
    }
    static char convertirAChar(int eje){
        char letra = (char) eje;
        letra += 65;
        return letra;
    }
}

