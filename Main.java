import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static final int FACIL = 1;
    static final int MEDIO = 2;
    static final int DIFICIL = 3;
    static final int CUSTOM = 4;

    static int tamanyoTablero = 26;

    static char[][] ocultoTablero = new char[tamanyoTablero][tamanyoTablero];
    static char[][] jugadorTablero = new char[tamanyoTablero][tamanyoTablero];

    static final char agua ='-';
    static final char lancha ='L';
    static final char barco = 'B';
    static final char acorazado = 'Z';
    static final char portaviones = 'P';

    static final int espacioBarco = 2;
    static final int espacioAcorazado = 3;
    static final int espacioPortaviones = 4;

    static int lanchasCantidad;
    static int barcosCantidad;
    static int acorazadoCantidad;
    static int portavionesCantidad;
    static int cantidadInicialBarcos;
    static int cantidadInicialAcorazados;
    static int cantidadInicialPortaviones;

    static String[] posicionBarcos;
    static String[] posicionAcorazados;
    static String[] posicionPortaviones;

    static int naviosRestantes;

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
            if (naviosRestantes == 0) break;
            System.out.println("\n\t\t\t---INTENTOS RESTANTES: "+intentos+"---");


            System.out.print("Selecciona una fila: ");
            ejeY = obtenerCoordenadaY();
            System.out.print("Selecciona una columna: ");
            ejeX = obtenerCoordenadaX();

            compararTableros(ejeY, ejeX);

            
            intentos--;
            
            jugando = (intentos >= 0);

        }while (jugando);
        if (intentos == -1) System.out.println("Has perdido");
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
        if (jugadorTablero[ejeY][ejeX] != agua) {
            System.out.println("Ya has disparado ahi");
            intentos++;
            return;
        }
        if (ocultoTablero[ejeY][ejeX] == agua) {
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
                ocultoTablero[i][j] = agua;
                jugadorTablero[i][j] = agua;
            }
        }
        boolean hayNavios;
        char tipoBarco = lancha;

        do {

            switch (tipoBarco) {
                case lancha:
                    lanchaPosicionAleatoria();
                    tipoBarco = barco;
                    break;
                case barco:
                barcoPosicionAleatoria();
                tipoBarco = acorazado;
                    break;
                case acorazado:
                AcorazadoPosicionAleatoria();
                tipoBarco = portaviones;
                    break;
                case portaviones:
                    portavionesPosicionAleatoria();
                    tipoBarco = lancha;
                    break;
            }
            int totalNavios = lanchasCantidad + barcosCantidad + acorazadoCantidad + portavionesCantidad;
            hayNavios = (totalNavios > 0);
    
        }while (hayNavios);
        return;
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
        int indice = 0;
        while (barcosCantidad > 0) {
            
            if (ejeX+espacioBarco >= tamanyoTablero) ejeX -= espacioBarco;

            if (posicionValida(barco, ejeY, ejeX)) {
                
                for (int i = 0; i < 3; i++){
                    ocultoTablero[ejeY][ejeX] = barco;
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
        }System.out.println(Arrays.toString(posicionBarcos));

    }
    static void AcorazadoPosicionAleatoria(){

        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;


        while (acorazadoCantidad > 0) {
            if (ejeX+espacioAcorazado >= tamanyoTablero) ejeX -= espacioAcorazado;

            if (posicionValida(acorazado, ejeY, ejeX)) {
                for (int i = 0; i < 4; i++){
                    ocultoTablero[ejeY][ejeX] = acorazado;
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
        }return;

    }
    static void portavionesPosicionAleatoria(){

        int ejeX = (int) (Math.random() * tamanyoTablero);
        int ejeY = (int) (Math.random() * tamanyoTablero);
        int indice = 0;

        while (portavionesCantidad > 0) {
           if (ejeY+espacioPortaviones >= tamanyoTablero) ejeY -= espacioPortaviones;

            if (posicionValida(portaviones, ejeY, ejeX)) {
                for (int i = 0; i < 5; i++){
                    ocultoTablero[ejeY][ejeX] = portaviones;
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
    static boolean tocadoYHundido(char tipoBarco, int ejeY, int ejeX){
        boolean hundido = false;
        char ejeYConvertido = convertirAChar(ejeY);
        char ejeXConvertido = convertirAChar(ejeX);
        String coordenada = ejeYConvertido + "" + ejeXConvertido;
        int indice = 0;

        switch (tipoBarco) {
            case lancha:
                hundido = true;
                break;
            case barco:
                for (int i = 0; i < posicionBarcos.length; i++) {
                    if (posicionBarcos[i].contains(coordenada)) indice = i;   
                }
                for (int i = 0; i <= espacioBarco ; i++) {
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
                case acorazado:
                for (int i = 0; i < posicionAcorazados.length; i++) {
                    if (posicionAcorazados[i].contains(coordenada)) indice = i;   
                }
                for (int i = 0; i <= espacioAcorazado ; i++) {
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
                case portaviones:

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

