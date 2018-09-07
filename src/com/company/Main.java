package com.company;
import java.util.*;

public class Main {
    ArrayList<String> palabras_reservadas;
    HashMap<String, String> tipo_simbolo;

    public static void main(String[] args) {
        ArrayList<String> palabras_reservadas = new ArrayList<String>(
                Arrays.asList("funcion_principal", "fin_principal", "funcion", "entero", "hacer", "retornar", "imprimir", "leer", "real", "caracter", "booleano",
                        "cadena", "si", "entonces", "si_no", "fin_si", "mientras", "fin_mientras", "para", "fin_para", "seleccionar", "entre", "caso", "romper"
                        , "fin_seleccionar", "estructura", "fin_estructura", "funcion", "fin_funcion", "verdadero", "falso"));
        HashMap<String, String> tipo_simbolo = new HashMap<>();
        tipo_simbolo.put("a", "letra");
        tipo_simbolo.put("b", "letra");
        tipo_simbolo.put("c", "letra");
        tipo_simbolo.put("d", "letra");
        tipo_simbolo.put("e", "letra");
        tipo_simbolo.put("f", "letra");
        tipo_simbolo.put("g", "letra");
        tipo_simbolo.put("h", "letra");
        tipo_simbolo.put("i", "letra");
        tipo_simbolo.put("j", "letra");
        tipo_simbolo.put("k", "letra");
        tipo_simbolo.put("l", "letra");
        tipo_simbolo.put("m", "letra");
        tipo_simbolo.put("n", "letra");
        tipo_simbolo.put("o", "letra");
        tipo_simbolo.put("p", "letra");
        tipo_simbolo.put("k", "letra");
        tipo_simbolo.put("r", "letra");
        tipo_simbolo.put("s", "letra");
        tipo_simbolo.put("t", "letra");
        tipo_simbolo.put("u", "letra");
        tipo_simbolo.put("v", "letra");
        tipo_simbolo.put("w", "letra");
        tipo_simbolo.put("x", "letra");
        tipo_simbolo.put("y", "letra");
        tipo_simbolo.put("z", "letra");
        tipo_simbolo.put("+", "suma");
        tipo_simbolo.put("-", "resta");
        tipo_simbolo.put("*", "multiplicacion");
        tipo_simbolo.put("/", "division");
        tipo_simbolo.put("%", "modulo");
        tipo_simbolo.put("=", "igual");
        tipo_simbolo.put("<", "menor");
        tipo_simbolo.put(">", "mayor");
        tipo_simbolo.put("&", "ampersand");
        tipo_simbolo.put("|", "or");
        tipo_simbolo.put("!", "admiracion");
        tipo_simbolo.put(":", "dos_puntos");
        tipo_simbolo.put(".", "punto");
        tipo_simbolo.put(",", "coma");
        tipo_simbolo.put(";", "punto_y_coma");
        tipo_simbolo.put("'", "comilla_simple");
        //comilla doble tipo_simbolo.put(' ,"letra");
        tipo_simbolo.put("(", "parentesis_izq");
        tipo_simbolo.put(")", "parentesis_der");
        tipo_simbolo.put("_", "under_score");

        tipo_simbolo.put("0", "num");
        tipo_simbolo.put("1", "num");
        tipo_simbolo.put("2", "num");
        tipo_simbolo.put("3", "num");
        tipo_simbolo.put("4", "num");
        tipo_simbolo.put("5", "num");
        tipo_simbolo.put("6", "num");
        tipo_simbolo.put("7", "num");
        tipo_simbolo.put("8", "num");
        tipo_simbolo.put("9", "num");
        Scanner sc = new Scanner(System.in);
        String contenido = "";
        boolean error= false;
        int i = 0;
        int columna = 0;
        int fila = 0;
        int state=1;
        while (sc.hasNextLine()) {
            if (error)
                break;
            String[] linea = sc.nextLine().split(" ");
            fila++;
            columna=0;
            for (String lexema :
                    linea) {
                if (error)
                    break;
                state=1;
                if(columna!=0)
                    columna++;
                contenido = "";
                String[] cadena = lexema.split("");
                String identificador = "ID";
                for (String simbolo :
                        cadena) {
                    columna++;
                    int next_state = delta(state, tipo_simbolo.get(simbolo));
                    state=next_state;
                    if (next_state != 0 ) {
                        contenido += simbolo;
                    }
                    if(next_state==2){
                        identificador="ID";
                    }
                    if(next_state==3){
                        identificador="entero";
                    }
                    if(next_state==5){
                        identificador="real";
                    }

                    if (next_state == 0) {
                        columna=columna-contenido.length()+1;
                        Token t = new Token(columna, fila);
                        t.setContenido(contenido);
                        t.setIdentificador(identificador);
                        if (palabras_reservadas.contains(contenido))
                            t.setPalabra_reservada(true);
                        System.out.println(t.toString());
                    }

                }
                if(state==-1||state==4){
                    printError(fila,columna-contenido.length()+1);
                    error=true;
                    break;
                }
                Token t = new Token(columna-contenido.length()+1, fila);
                t.setContenido(contenido);
                t.setIdentificador(identificador);
                if (palabras_reservadas.contains(contenido))
                    t.setPalabra_reservada(true);
                System.out.println(t.toString());
            }

        }



    }

    public static int delta(int state, String simbolo) {
        switch (state) {
            case 1:
                if (simbolo == "letra")
                    return 2;
                if (simbolo == "num")
                    return 3;
                if (simbolo == "under_score")
                    return -1;

                break;
            case 2:
                if (simbolo == "letra")
                    return 2;
                if (simbolo == "num")
                    return 2;
                if (simbolo == "under_score")
                    return 2;
                if (simbolo == "otra cosa")
                    return 0;
                if (simbolo == "punto")
                    return -1;
                break;

            case 3:
                if (simbolo == "num")
                    return 3;
                if (simbolo == "punto")
                    return 4;
                if (simbolo == "letra")
                    return -1;
                break;
            case 4:
                if (simbolo == "num")
                    return 5;
                if (simbolo == "letra")
                    return -1;
            case 5:
                if (simbolo == "num")
                    return 5;
            default:
                return -1;
        }

        return -1;
    }
    public static void printError(int fila, int columna){
            System.out.println(">>> Error lexico (linea: "+ fila+", posicion: "+columna+")");
    }

    // write your code here


}
    class Token {
    String identificador;
    String contenido;
    int fila; //fila donde empieza el token
    int columna;//columna donde termina el token
    boolean palabra_reservada;      //V si es palabra reservada, F si no

    Token(int columna, int fila){
        this.columna=columna;
        this.fila=fila;
        this.palabra_reservada=false;
    }

        public String getIdentificador() {
            return identificador;
        }

        public void setIdentificador(String identificador) {
            this.identificador = identificador;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public boolean isPalabra_reservada() {
            return palabra_reservada;
        }

        public void setPalabra_reservada(boolean palabra_reservada) {
            this.palabra_reservada = palabra_reservada;
        }

        @Override
    public String toString() {
        if(this.palabra_reservada) {
            return "<" +
                     contenido +
                    ","  +
                    fila +
                    "," +
                    columna +
                    ">" ;
        }else{
            return "<" +
                    identificador +
                    ","  +
                    contenido +
                    "," +
                    fila +
                    "," +
                    columna+
                    ">" ;
        }

    }
}
