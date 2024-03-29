import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int tamanyoTablero = 10;

    static char[][] ocultoTablero;
    static char[][] jugadorTablero;

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
            int ejeY = obtenerCoordenadaY();
            System.out.print("Selecciona una columna: ");
            int ejeX = obtenerCoordenadaX();
            compararTableros(ejeY, ejeX);

            intentos--;

            jugando = (intentos >=0);

        }while (jugando);
        if (intentos == 0) System.out.println("Has perdido");
        else System.out.println("Has ganado");
        
    }
    static void seleccionDeDificultad(){
        final int FACIL = 1;
        final int MEDIO = 2;
        final int DIFICIL = 3;
        final int CUSTOM = 4;

        System.out.println("Selecciona una dificultad:\n\n1. Facil\n2. Medio\n3. Dificil\n4. Custom\n");
        int dificultad = inputUsuario();
        tamanyoTablero = 10;
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
        jugadorTablero = new char[tamanyoTablero][tamanyoTablero];
        ocultoTablero  = new char[tamanyoTablero][tamanyoTablero];
        posicionBarcos = new String[barcosCantidad];
        posicionAcorazados = new String[acorazadoCantidad];
        posicionPortaviones = new String[portavionesCantidad];
    }
    static void modoCustom(){
        final char SALIR = 'S';
        boolean valido = false;
        char tipoNavio = LANCHA;
        int cantidadNavio;
        System.out.println("Introduce las dimensiones del tablero (Se espera un número entre 8 y 26):");
        do{
            tamanyoTablero = inputUsuario();
            if (tamanyoTablero > 7 && tamanyoTablero < 27) valido = true;
        }while (!valido);
        //Este límite es la mita del tamaño del tablero por asegurar que no hay ningún problema
        int limiteNavios = (int)(Math.pow(tamanyoTablero,2)/2);
        valido = false;
        System.out.println("Selecciona un número de lanchas");
        while (!valido)
            switch (tipoNavio){
                case LANCHA:
                    System.out.println("Elige el número de lanchas");
                    cantidadNavio = inputUsuario();
                    if (cantidadNavio <= limiteNavios){
                        lanchasCantidad = cantidadNavio;
                        limiteNavios-=cantidadNavio;
                        if (limiteNavios >= 3) tipoNavio = BARCO;
                        else tipoNavio = SALIR;
                    }
                    else System.out.println("Demasiadas lanchas");
                    break;
                case  BARCO:
                    System.out.println("Elige el número de barcos");
                    cantidadNavio = inputUsuario();
                    if (cantidadNavio*3 <= limiteNavios){
                        barcosCantidad = cantidadNavio;
                        limiteNavios-=cantidadNavio*3;
                        if (limiteNavios >= 4) tipoNavio = ACORAZADO;
                        else tipoNavio = SALIR;
                    }
                    else System.out.println("Demasiados barcos");
                    break;
                case ACORAZADO:
                    System.out.println("Elige el número de acorazados");
                    cantidadNavio = inputUsuario();
                    if (cantidadNavio*4 <= limiteNavios){
                        acorazadoCantidad = cantidadNavio;
                        limiteNavios-=cantidadNavio*4;
                        if (limiteNavios >= 5) tipoNavio = PORTAVIONES;
                        else tipoNavio = SALIR;
                    }
                    else System.out.println("Demasiados acorazados");
                    break;
                case PORTAVIONES:
                    System.out.println("Elige el número de portaviones");
                    cantidadNavio = inputUsuario();
                    if (cantidadNavio*5 <= limiteNavios){
                        portavionesCantidad = cantidadNavio;
                        limiteNavios-=cantidadNavio*5;
                        tipoNavio = SALIR;
                    }
                    else System.out.println("Demasiados portaviones");
                    break;
                case SALIR:
                    valido = true;
                    break;
            }
        System.out.println("Selecciona un número de intentos:");
        do {
            intentos = inputUsuario();
            if (intentos >= Math.pow(tamanyoTablero, 2)) System.out.println("Demasiados intentos");
            if (intentos <lanchasCantidad + barcosCantidad*3 +acorazadoCantidad*4 + portavionesCantidad*5) System.out.println("Necesitas más intentos para poder ganar");
            valido = intentos < Math.pow(tamanyoTablero, 2) && intentos > lanchasCantidad + barcosCantidad * 3 + acorazadoCantidad * 4 + portavionesCantidad * 5;
        }while (!valido);
    }
    static void llenarTablero() {
        //primero se llenan los dos tableros de agua
        for (int i = 0; i < ocultoTablero.length; i++){
            for (int j = 0; j < ocultoTablero[i].length; j++){
                ocultoTablero[i][j] = AGUA;
                jugadorTablero[i][j] = AGUA;
            }
        }
        boolean hayNavios;
        char tipoNavio = LANCHA;
        //El siguiente do while coloca cada navío en la tabla comprobando antes que no sobreescribe otro
        //cuando se han colocado todos los navíos el bucle acaba
        do {
            tipoNavio = switch (tipoNavio) {
                case LANCHA -> {
                    do {

                        int ejeX = (int) (Math.random() * tamanyoTablero);
                        int ejeY = (int) (Math.random() * tamanyoTablero);

                        if (posicionValida(LANCHA, ejeY, ejeX)) {
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
        //se muestra el tablero del jugador además del número de la columna y la letra de la fila
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
    static int obtenerCoordenadaX(){
        Scanner input = new Scanner(System.in);
        boolean valido = false;
        int ejeY = 0;

        while (!valido) {
            try {
                ejeY = input.nextInt();
                if (ejeY < tamanyoTablero && ejeY >= 0) valido = true;
                else System.out.println("No es una coordenada valida");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("No es una coordenada valida");
                input.nextLine();
            }
        }return ejeY;
    }
    static int obtenerCoordenadaY(){
        Scanner input = new Scanner(System.in);
        boolean valido = false;
        char ejeX = 0;

        while (!valido) {
            try {
                ejeX = input.next().charAt(0);
                ejeX = Character.toUpperCase(ejeX);
                ejeX -= 65;
                if (ejeX < tamanyoTablero) valido = true;
                else System.out.println("No es una coordenada valida");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("No es una coordenada valida");
                input.nextLine();
            }
        }return ejeX;
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
    //Si el jugador dispara a un navío se busca el índice del navío en el array posición correspondiente
    //Después se sustituye la variable coordenada por el navío que se ha impactado, se recorre el navío
    //en cuestión y se comprueba si en el tablero del jugador encontramos 'X' en cada celda
    //Si cada celda es una 'X' se considera tocado y hundido
    static boolean tocadoYHundido(char tipoNavio, int ejeY, int ejeX){

        boolean hundido = false;
        char ejeYConvertido = convertirAChar(ejeY);
        char ejeXConvertido = convertirAChar(ejeX);
        String coordenada = ejeYConvertido + "" + ejeXConvertido;
        int indice = 0;
        String[] posicionNavio = {};
        int tamanyoNavio = 0;

        switch (tipoNavio) {
            case LANCHA:
                hundido = true;
                break;
            case BARCO:
                posicionNavio = posicionBarcos;
                tamanyoNavio = ESPACIO_BARCO;
                break;
                case ACORAZADO:
                posicionNavio = posicionAcorazados;
                tamanyoNavio = ESPACIO_ACORAZADO;
                break;
                case PORTAVIONES:
                posicionNavio = posicionPortaviones;
                tamanyoNavio = ESPACIO_PORTAVIONES;
                break;
            default:
                break;
        }
        if (tipoNavio != LANCHA) {
                for (int i = 0; i < posicionNavio.length; i++) {
                    if (posicionNavio[i].contains(coordenada)) indice = i;
                }
                for (int i = 0; i <= tamanyoNavio; i++) {
                    coordenada = posicionNavio[indice];
                    for (int j = 0; j < coordenada.length(); j += 2) {
                        ejeY = coordenada.charAt(j) - 65;
                        ejeX = coordenada.charAt(j + 1) - 65;
                        if (jugadorTablero[ejeY][ejeX] != 'X') {
                            hundido = false;
                            break;
                        } else hundido = true;
                    }

                }
        }return hundido;
    }
    static char convertirAChar(int eje){
        char letra = (char) eje;
        letra += 65;
        return letra;
    }
}

