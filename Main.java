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
            mostrarTableroOculto();
            mostrarTableroJugador();
            System.out.println(Arrays.toString(posicionBarcos));
            if (naviosRestantes == 0) break;
            System.out.println("\n\t\t\t---INTENTOS RESTANTES: "+intentos+"---");


            System.out.print("Selecciona una fila: ");
            int ejeX = obtenerCoordenadaX();
            System.out.print("Selecciona una columna: ");
            int ejeY = obtenerCoordenadaY();

            compararTableros(ejeX, ejeY);

            
            intentos--;
            
            jugando = (intentos >= 0);

        }while (jugando);
        if (intentos == -1) System.out.println("Has perdido");
        else System.out.println("Has ganado");
        
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
                        modoFacil();
                        seleccionado = true;
                        break;
                    case MEDIO:
                        System.out.println("Modo Medio");
                        lanchasCantidad = 2; barcosCantidad = 1; acorazadoCantidad = 1; portavionesCantidad = 1;
                        naviosRestantes = lanchasCantidad + barcosCantidad*3 + acorazadoCantidad*4 + portavionesCantidad*5;
                        seleccionado = true;
                        break;
                    case DIFICIL:
                        System.out.println("Modo Dificil");
                        lanchasCantidad = 1; barcosCantidad = 1;
                        naviosRestantes = lanchasCantidad + barcosCantidad*3 + acorazadoCantidad*4 + portavionesCantidad*5;
                        seleccionado = true;
                        break;
                    case CUSTOM:
                        System.out.println("Modo Custom");
                        naviosRestantes = lanchasCantidad + barcosCantidad*3 + acorazadoCantidad*4 + portavionesCantidad*5;
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
                ocultoTablero[i][j] = AGUA;
                jugadorTablero[i][j] = AGUA;
            }
        }
        boolean hayNavios;
        char tipoNavio = LANCHA;

        do {
            switch (tipoNavio) {
                case LANCHA:
                    lanchaPosicionAleatoria();
                    tipoNavio = BARCO;
                    break;
                case BARCO:
                barcoPosicionAleatoria();
                tipoNavio = ACORAZADO;
                    break;
                case ACORAZADO:
                AcorazadoPosicionAleatoria();
                tipoNavio = PORTAVIONES;
                    break;
                case PORTAVIONES:
                    portavionesPosicionAleatoria();
                    tipoNavio = LANCHA;
                    break;
            }
            int totalNavios = lanchasCantidad + barcosCantidad + acorazadoCantidad + portavionesCantidad;
            hayNavios = (totalNavios > 0);
    
        }while (hayNavios);

    }
    static void modoFacil(){
        lanchasCantidad = 5; 
        barcosCantidad = 3; 
        acorazadoCantidad = 1; 
        portavionesCantidad = 1;
        naviosRestantes = lanchasCantidad + barcosCantidad*3 + acorazadoCantidad*4 + portavionesCantidad*5;

        posicionBarcos = new String[barcosCantidad];
        posicionAcorazados = new String[acorazadoCantidad];
        posicionPortaviones = new String[portavionesCantidad];
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
    static void lanchaPosicionAleatoria(){

            do{

                int ejeX = (int) (Math.random() * tamanyoTablero);
                int ejeY = (int) (Math.random() * tamanyoTablero);

                if (posicionValida(LANCHA, ejeX, ejeY)) {
                    ocultoTablero[ejeX][ejeY] = LANCHA;
                    lanchasCantidad--;
                }

            }while  (lanchasCantidad > 0);

    }
    static void barcoPosicionAleatoria(){
        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;
        while (barcosCantidad > 0) {
            
            if (ejeX+ESPACIO_BARCO >= tamanyoTablero) ejeX -= ESPACIO_BARCO;

            if (posicionValida(BARCO, ejeY, ejeX)) {
                
                for (int i = 0; i < 3; i++){
                    ocultoTablero[ejeY][ejeX] = BARCO;
                    char ejeYConvertido = convertirAChar(ejeY);
                    char ejeXConvertido = convertirAChar(ejeX);
                    ejeX++;
                    if (posicionBarcos[indice] == null) posicionBarcos[indice] = ejeYConvertido + "" + ejeXConvertido;
                    else posicionBarcos[indice] += ejeYConvertido + "" + ejeXConvertido;
                }
                
                indice++;
                barcosCantidad--;
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);

            }
            else{
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
        }

    }
    static void AcorazadoPosicionAleatoria(){

        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;


        while (acorazadoCantidad > 0) {
            if (ejeX+ESPACIO_ACORAZADO >= tamanyoTablero) ejeX -= ESPACIO_ACORAZADO;

            if (posicionValida(ACORAZADO, ejeY, ejeX)) {
                for (int i = 0; i < 4; i++){
                    ocultoTablero[ejeY][ejeX] = ACORAZADO;
                    char ejeYConvertido = convertirAChar(ejeY);
                    char ejeXConvertido = convertirAChar(ejeX);
                    ejeX++;
                    if (posicionAcorazados[indice] == null) posicionAcorazados[indice] = ejeYConvertido + "" + ejeXConvertido;
                    else posicionAcorazados[indice] += ejeYConvertido + "" + ejeXConvertido;
                }

                indice++;
                acorazadoCantidad--;
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
            else{
                ejeX = (int) (Math.random() * tamanyoTablero);
                ejeY = (int) (Math.random() * tamanyoTablero);
            }
        }

    }
    static void portavionesPosicionAleatoria(){

        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;

        while (portavionesCantidad > 0) {
           if (ejeY+ESPACIO_PORTAVIONES >= tamanyoTablero) {
               ejeY -= ESPACIO_PORTAVIONES;
               if (posicionValida(PORTAVIONES, ejeY, ejeX)) {
                   for (int i = 0; i < 5; i++){
                       ocultoTablero[ejeY][ejeX] = PORTAVIONES;
                       char ejeYConvertido = convertirAChar(ejeY);
                       char ejeXConvertido = convertirAChar(ejeX);
                       ejeY++;
                       if (posicionPortaviones[indice] == null) posicionPortaviones[indice] = ejeYConvertido + "" + ejeXConvertido;
                       else posicionPortaviones[indice] += ejeYConvertido + "" + ejeXConvertido;
                   }
                   indice++;
                   portavionesCantidad--;
                   ejeX = (int) (Math.random() * tamanyoTablero);
                   ejeY = (int) (Math.random() * tamanyoTablero);
               }
               else{
                   ejeX = (int) (Math.random() * tamanyoTablero);
                   ejeY = (int) (Math.random() * tamanyoTablero);
               }
           } else {
               if (posicionValida(PORTAVIONES, ejeY, ejeX)) {
                   for (int i = 0; i < 5; i++){
                       ocultoTablero[ejeY][ejeX] = PORTAVIONES;
                       char ejeYConvertido = convertirAChar(ejeY);
                       char ejeXConvertido = convertirAChar(ejeX);
                       ejeY++;
                       if (posicionPortaviones[indice] == null) posicionPortaviones[indice] = ejeYConvertido + "" + ejeXConvertido;
                       else posicionPortaviones[indice] += ejeYConvertido + "" + ejeXConvertido;
                   }
                   indice++;
                   portavionesCantidad--;
               }
               ejeX = (int) (Math.random() * tamanyoTablero);
               ejeY = (int) (Math.random() * tamanyoTablero);
           }

        }

    }
    static void mostrarTableroOculto() {

        for (int i = 0; i < ocultoTablero.length; i++){
         System.out.println(Arrays.toString(ocultoTablero[i]));
        }
    }
    static boolean posicionValida(char tipoBarco,int y, int x){

        switch (tipoBarco) {
            case LANCHA:
                if (ocultoTablero[y][x] == AGUA) return true;
                break;
            case BARCO:
                if (ocultoTablero[y][x] == AGUA && ocultoTablero[y][x+1] == AGUA && ocultoTablero[y][x+2] == AGUA) return true;
                break;
            case ACORAZADO:
                if (ocultoTablero[y][x] == AGUA && ocultoTablero[y][x+1] == AGUA && ocultoTablero[y][x+2] == AGUA && ocultoTablero[y][x+3] == AGUA) return true;
                break;
            case PORTAVIONES:
                if (ocultoTablero[y][x] == AGUA && ocultoTablero[y+1][x] == AGUA && ocultoTablero[y+2][x] == AGUA && ocultoTablero[y+3][x] == AGUA && ocultoTablero[y+4][x] == AGUA) return true;
                break;
                
        }
        return false;
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

