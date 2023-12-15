public class Main {

    static final char agua ='-';
    static final char lancha ='L';
    static final int barco = 'B';
    static final int crucero = 'C';
    static final int portaviones = 'P';

    static final int tamanyoLancha = 1;
    static final int tamanyoBarco = 3;
    static final int tamanyoCrucero = 4;
    static final int tamanyoPortaviones = 5;

    static int lanchasCantidad = 5;
    static int barcosCantidad = 3;
    static int crucerosCantidad = 1;
    static int portavionesCantidad = 1;

    static char[][] tablero = new char[10][10];
    public static void main(String[] args) {

        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[i].length; j++){
                tablero[i][j] = agua;
            }
        }
        llenarTablero();

        for (int i = 0; i < tablero.length; i++){
            if(i>0)System.out.print(i+"\t");
            else System.out.print("\n");

            if (i == 0) {
                for (int j = 0; j < tablero[i].length; j++){
                    if (j == 0) System.out.print("\t"+j+"\t");
                    else System.out.print(j+"\t");
                }
            }else for (int j = 0; j < tablero[i].length; j++){
                    System.out.print(tablero[i][j] + "\t");
            }System.out.println("\n");
        }
    }
    
    
    static void llenarTablero() {
        boolean hayBarcos;
        int totalBarcos = lanchasCantidad + barcosCantidad + crucerosCantidad + portavionesCantidad;
        char tipoBarco = lancha;

        do {

            if (tipoBarco == lancha) {
                lanchaPosicionAleatoria();
                totalBarcos--;
                tipoBarco = barco;
            } else if (tipoBarco == barco) {
                barcoPosicionAleatoria();
                totalBarcos--;
                tipoBarco = crucero;
            } else if (tipoBarco == crucero) {
                cruceroPosicionAleatoria();
                totalBarcos--;
                tipoBarco = portaviones;
            } else if (tipoBarco == portaviones) {
                portavionesPosicionAleatoria();
                totalBarcos--;
            }
            hayBarcos = (totalBarcos > 0);
    
        }while (hayBarcos);
        return;
        
    }
    static void lanchaPosicionAleatoria(){

    int xEjePosicionAleatoria = 0;
    int yEjePosicionAleatoria = 0;

        while (lanchasCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) {
                    tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = lancha;
                    
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

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = barco;
            barcosCantidad--;
                    
        }return;

    }
    static void cruceroPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (crucerosCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = crucero;
            
            crucerosCantidad--;
        }return;

    }
    static void portavionesPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (portavionesCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = portaviones;
            
            portavionesCantidad--;
                    
            }return;

    }
}