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
        tipo_simbolo.put("q", "letra");
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
        //comilla doble
        tipo_simbolo.put("\"" ,"comilla_doble");
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
        String buff_2="";
        String buff="";
        String identificador="";
        boolean error= false;
        boolean comment_line= false;
        boolean big_comment = false;
        boolean prev_token=false;
        boolean tab=false;
        int i = 0;
        int columna = 0;
        int fila = 0;

        int state=1;
        while (sc.hasNextLine()) {
            if (error )
                break;
            String[] linea = sc.nextLine().split(" ");

            fila++;
            comment_line = false;
            columna=0;
            for (String lexema :
                    linea) {

                if (error || comment_line)
                    break;
                if(columna!=0&&!tab)
                    columna++;
                if(state!=14 && state!=41 && state!=25)
                    state=1;
                if(state!=41)
                    contenido = "";
                if(state==41)
                    contenido+=" ";
                if(!big_comment)
                    prev_token=false;
                buff_2 = "";
                buff = "";
                String[] cadena = lexema.split("");
                if (state!=41)
                    identificador = "id";
                for (String simbolo :
                        cadena) {


                    int next_state = delta(state, tipo_simbolo.get(simbolo));


                    if(!big_comment)
                        prev_token=false;
                    if(next_state==-2&&columna==0)
                        tab=true;
                    if(next_state!=-2&&tab)
                        tab=false;
                    columna++;



                    state=next_state;
                    if (next_state==1)
                        big_comment=false;
                    if (!big_comment ) {
                        if (next_state != 0) {
                            buff_2 = buff;
                            buff = contenido;
                            contenido += simbolo;

                        }


                        //SIMBOLO FUERA DEL ALFABETO SIN COMENTAR Y FUERA DE COMILLAS
                        if(!(state==38||state==41)) {
                            if (!tipo_simbolo.containsKey(simbolo.concat("a"))) {
                                if (!tipo_simbolo.containsKey(simbolo)) {
                                    if (buff.length() != 0) {
                                        Token t = new Token(columna - buff.length() + 1, fila);
                                        t.setContenido(buff);
                                        t.setIdentificador(identificador);
                                        if (palabras_reservadas.contains(contenido))
                                            t.setPalabra_reservada(true);

                                        System.out.println(t.toString());
                                    }
                                    printError(fila, columna);
                                    error = true;
                                    break;

                                }

                            }
                        }
                        //**IDENTIFICADOR**

                        if (next_state == 2) {
                            identificador = "id";
                        }

                        //**ENTERO**
                        if (next_state == 3) {
                            identificador = "entero";
                        }


                        //**REAL**
                        if (next_state == 5) {
                            identificador = "real";
                        }

                        //**ASIGNACION**
                        if (next_state == 7) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);

                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());
                                identificador = "tk_assig";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_assig";

                        }


                        //**IGUAL**
                        if (next_state == 8) {
                            if (buff.length() != 0 && !buff.equals("=")) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_igual";
                                contenido = simbolo;
                                columna = columna + buff.length();
                                buff = "";

                            }
                            identificador = "tk_igual";

                        }


                        //**MAYOR**
                        if (next_state == 28) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_mayor";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_mayor";

                        }


                        //**MAYOR O IGUAL**
                        if (next_state == 29) {
                            if (buff.length() != 0 && !buff.equals(">")) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_mayor_igual";
                                contenido = simbolo;
                                columna = columna + buff.length();
                                buff = "";

                            }
                            identificador = "tk_mayor_igual";

                        }

                        //**MENOR**
                        if (next_state == 30) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_menor";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_menor";

                        }


                        //**MENOR O IGUAL**
                        if (next_state == 31) {
                            if (buff.length() != 0 && !buff.equals("<")) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_menor_igual";
                                contenido = simbolo;
                                columna = columna + buff.length();
                                buff = "";

                            }
                            identificador = "tk_menor_igual";

                        }

                        //**NEGACION**
                        if (next_state == 32) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_neg";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_neg";

                        }


                        //**DIFERENTE**
                        if (next_state == 33) {
                            if (buff.length() != 0 && !buff.equals("!")) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());
                                identificador = "tk_dif";
                                contenido = simbolo;
                                columna = columna + buff.length();
                                buff = "";

                            }
                            identificador = "tk_dif";

                        }


                        //**ENTERO CON BUFFER Y ERROR**
                        if (next_state == 9) {

                            Token t = new Token(columna - buff.length(), fila);
                            t.setContenido(buff);
                            t.setIdentificador(identificador);
                            if(identificador=="id"||identificador=="entero"||identificador=="real")
                               t.token=false;
                            System.out.println(t.toString());
                            printError(fila, columna);
                            prev_token = true;
                            error = true;
                            break;
                        }

                        //**ENTERO CON BUFFER**
                        if (next_state == 10) {

                            Token t = new Token(columna - buff.length(), fila);
                            t.setContenido(buff);
                            t.setIdentificador(identificador);

                            System.out.println(t.toString());
                            contenido = simbolo;
                            identificador = "entero";
                        }

                        //**IDENTIFICADOR CON BUFFER**
                        if (next_state == 11) {
                            Token t = new Token(columna - buff.length(), fila);
                            t.setContenido(buff);
                            t.setIdentificador(identificador);

                            System.out.println(t.toString());
                            contenido = simbolo;
                            identificador = "id";
                        }


                        //**SUMA**
                        if (next_state == 6) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);

                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());

                            }
                            columna = columna + buff.length();
                            Token t = new Token(columna, fila);
                            t.setContenido("+");
                            identificador = "tk_mas";
                            t.setIdentificador(identificador);

                            System.out.println(t.toString());
                            contenido = "";
                            buff = "";
                            state = 1;
                            prev_token = true;
                        }

                        //**DIVISION**
                        if (next_state == 12) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_division";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_division";

                        }

                        //**COMENTARIO**
                        if (next_state == 13) {
                            if (buff.length() != 0 && !buff.equals("/")) {


                                contenido = simbolo;
                                columna = columna + buff.length();
                                buff = "";
                                prev_token = true;
                                comment_line = true;
                                break;

                            }
                            prev_token = true;
                            comment_line = true;
                            break;


                        }

                        //**COMENTARIO LARGO**
                        if (next_state == 14) {
                            big_comment = true;
                            prev_token = true;

                        }

                        //**PUNTO**
                        if (next_state == 16) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());

                                identificador = "tk_punto";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_punto";

                        }

                        //**SIMBOLO DESPUES DE PUNTO**
                        if (next_state == 17) {
                            if (buff_2.length() != 0) {

                                Token buff_t = new Token(columna - buff_2.length() - 1, fila);
                                buff_t.setContenido(buff_2);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());


                                contenido = simbolo;
                                buff = "";
                                buff_2 = "";
                            }

                            Token t = new Token(columna - 1, fila);
                            t.setContenido(".");
                            t.setIdentificador("tk_punto");

                            System.out.println(t.toString());
                            if (tipo_simbolo.get(simbolo) == "letra") {
                                identificador = "id";
                                state = 2;
                            }
                            if (tipo_simbolo.get(simbolo) == "division") {
                                identificador = "tk_div";
                                state = 12;
                            }
                            if (tipo_simbolo.get(simbolo) == "resta") {
                                identificador = "tk_menos";
                                state = 18;
                            }
                            if (tipo_simbolo.get(simbolo) == "dos_puntos") {
                                identificador = "tk_dosp";
                                state = 19;
                            }
                            if (tipo_simbolo.get(simbolo) == "punto_y_coma") {
                                identificador = "tk_pyc";
                                state = 20;
                            }
                            if (tipo_simbolo.get(simbolo) == "parentesis_izq") {
                                identificador = "tk_par_izq";
                                state = 21;
                            }
                            if (tipo_simbolo.get(simbolo) == "parentesis_der") {
                                identificador = "tk_par_der";
                                state = 22;
                            }
                            if (tipo_simbolo.get(simbolo) == "coma") {
                                identificador = "tk_coma";
                                state = 23;
                            }
                            if (tipo_simbolo.get(simbolo) == "comilla_simple") {
                                identificador = "tk_comilla_sen";
                                state = 24;
                            }
                            if (tipo_simbolo.get(simbolo) == "comilla_doble") {
                                identificador = "tk_comilla_dob";
                                state = 25;
                            }
                            if (tipo_simbolo.get(simbolo) == "modulo") {
                                identificador = "tk_mod";
                                state = 26;
                            }
                            if (tipo_simbolo.get(simbolo) == "multiplicacion") {
                                identificador = "tk_mult";
                                state = 27;
                            }
                            if (tipo_simbolo.get(simbolo) == "mayor") {
                                identificador = "tk_mayor";
                                state = 28;
                            }
                            if (tipo_simbolo.get(simbolo) == "menor") {
                                identificador = "tk_menor";
                                state = 30;
                            }
                            if (tipo_simbolo.get(simbolo) == "admiracion") {
                                identificador = "tk_neg";
                                state = 32;
                            }
                            if (tipo_simbolo.get(simbolo) == "ampersand") {
                                identificador = "tk_y";
                                state = 34;
                            }
                            if (tipo_simbolo.get(simbolo) == "or") {
                                identificador = "tk_o";
                                state = 36;
                            }
                        }

                        //**RESTA**
                        if (next_state == 18) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_menos";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_menos";

                        }

                        //**DOS PUNTOS**
                        if (next_state == 19) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_dosp";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_dosp";
                        }
                        //**PUNTO Y COMA**
                        if (next_state == 20) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_pyc";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_pyc";

                        }

                        //**PARENTESIS DERECHO**
                        if (next_state == 21) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_par_izq";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_par_izq";

                        }

                        //**Y**
                        if (next_state == 34) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_y";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_y";

                        }
                        //**Y**
                        if (next_state == 35) {

                            identificador = "tk_y";

                        }

                        //**Y**
                        if (next_state == 36) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);

                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());
                                identificador = "tk_o";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_o";

                        }
                        //**Y**
                        if (next_state == 37) {

                            identificador = "tk_o";

                        }


                        //**PARENTESIS IZQUIERDO**
                        if (next_state == 22) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_par_der";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_par_der";

                        }
                        //**COMA**
                        if (next_state == 23) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());

                                identificador = "tk_coma";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_coma";

                        }

                        //**COMILLA SENCILLA**
                        if (next_state == 24) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());

                                identificador = "tk_char";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_char";

                        }

                        if (next_state == 39) {


                                Token buff_t = new Token(columna-contenido.length()+1, fila);
                                buff_t.setContenido(contenido);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());

                                identificador = "tk_char";
                                contenido = "";
                                buff = "";
                                prev_token=true;
                                state=1;


                            identificador = "tk_char";

                        }

                        if (next_state == 42) {


                            Token buff_t = new Token(columna-contenido.length()+1, fila);
                            buff_t.setContenido(contenido);
                            buff_t.setIdentificador(identificador);

                            System.out.println(buff_t.toString());

                            identificador = "tk_cadena";
                            contenido = "";
                            buff = "";
                            prev_token=true;
                            state=1;


                            identificador = "tk_cadena";

                        }
                        //**COMILLA DOBLE**
                        if (next_state == 25) {
                            if (buff.length() != 0 ) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);
                                System.out.println(buff_t.toString());

                                identificador = "tk_cadena";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_cadena";

                        }

                        //**MODULO**
                        if (next_state == 26) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_mod";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_mod";

                        }
                        //**MULTIPLICACION**
                        if (next_state == 27) {
                            if (buff.length() != 0) {
                                columna = columna - buff.length();
                                Token buff_t = new Token(columna, fila);
                                buff_t.setContenido(buff);
                                buff_t.setIdentificador(identificador);

                                System.out.println(buff_t.toString());
                                identificador = "tk_mult";
                                columna = columna + buff.length();
                                contenido = simbolo;
                                buff = "";


                            }
                            identificador = "tk_mult";

                        }


                        if (next_state == 0) {
                            columna = columna - contenido.length() + 1;
                            Token t = new Token(columna, fila);
                            t.setContenido(contenido);
                            t.setIdentificador(identificador);
                            if (palabras_reservadas.contains(contenido))
                                t.setPalabra_reservada(true);

                            System.out.println(t.toString());
                            contenido = "";
                            state = 1;
                        }

                        //**ERRORES Y ESTADOS DE NEGACION**
                        if (state == -1 ) {
                            if (buff.length() != 0 && !prev_token && !buff.equals("&") && !buff.equals("|")) {
                                Token t = new Token(columna - contenido.length() + 1, fila);
                                t.setContenido(buff);
                                t.setIdentificador(identificador);
                                if (palabras_reservadas.contains(buff))
                                    t.setPalabra_reservada(true);

                                System.out.println(t.toString());
                                prev_token = true;
                                printError(fila, columna);
                            } else {
                                printError(fila, columna);
                            }
                            error = true;
                            break;
                        }
                    }
                }

                //**ERRORES Y ESTADOS DE NEGACION**
                if(state==-1 || state==24 ){
                    if(buff.length()!=0 && !prev_token && !buff.equals("&") && !buff.equals("|")) {
                        Token t = new Token(columna , fila);
                        t.setContenido(buff);
                        t.setIdentificador(identificador);
                        if (palabras_reservadas.contains(buff))
                            t.setPalabra_reservada(true);

                        System.out.println(t.toString());
                    }
                    if(!error) {
                        contenido = "";
                        printError(fila, columna  + 1);
                    }
                    error=true;
                    break;
                }
                if (state == 38 || state ==40){
                    printError(fila,columna);
                    error=true;
                }
                if( state==34 || state==36){
                    if(buff.length()!=0 && !prev_token ) {
                        Token t = new Token(columna , fila);
                        t.setContenido(buff);
                        t.setIdentificador(identificador);
                        if (palabras_reservadas.contains(buff))
                            t.setPalabra_reservada(true);

                        System.out.println(t.toString());
                    }
                    if(!error) {
                        contenido = "";
                        printError(fila, columna  );
                    }
                    error=true;
                    break;
                }


                //**ESPACIOS VACIOS**
                if (state==-2&&!tab)
                    columna--;


                //**COMENTARIO LARGO**
                if (state==14)
                    big_comment=true;

                if(state==4){
                    Token b = new Token(columna-buff.length(), fila);
                    b.setContenido(buff);
                    b.setIdentificador(identificador);
                    if (palabras_reservadas.contains(buff))
                        b.setPalabra_reservada(true);

                    System.out.println(b.toString());
                    Token t = new Token(columna - buff.length()+1, fila);
                    t.setContenido(".");
                    t.setIdentificador("tk_punto");

                    System.out.println(t.toString());
                    prev_token=true;


                }



            if(!prev_token && state!=-2 && !error && state!=41) {
                        Token t = new Token(columna - contenido.length() + 1, fila);
                t.setContenido(contenido);
                t.setIdentificador(identificador);
                if (palabras_reservadas.contains(contenido))
                    t.setPalabra_reservada(true);

                System.out.println(t.toString());
                }
            }

        }



    }

    public static int delta(int state, String simbolo) {
        if(state==25 ||state==41){
            if (simbolo=="comilla_doble")
                return 42;
            return 41;
        }

        if (state==14){
            if (simbolo=="multiplicacion")
                return 15;
            else
                return 14;
        }
        if (state==24) {
            if (simbolo == "comilla_simple")
                return 39;
            return 38;
        }
        if (state==38) {
            if (simbolo == "comilla_simple")
                return 39;
            return -1;
        }
        try {
            if (simbolo.hashCode() == 0) {
                return state;
            }
        }
        catch(NullPointerException e) {
                //**ESPACIOS VACIOS**
                    return -2;
                }


        switch (state) {
            //**ESTADO INICIAL**
            case 1:
                if (simbolo == "letra")
                    return 2;
                if (simbolo == "num")
                    return 3;
                if (simbolo == "under_score")
                    return -1;
                if (simbolo == "punto")
                    return 16;
                if (simbolo == "suma")
                    return 6;

                if (simbolo == "igual")
                    return 7;
                if (simbolo == "division")
                    return 12;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;
                break;
            //**IDENTIFICADOR**
            case 2:
                if (simbolo == "letra")
                    return 2;
                if (simbolo == "num")
                    return 2;
                if (simbolo == "under_score")
                    return 2;
                if (simbolo == "igual")
                    return 7;
                if (simbolo == "suma")
                    return 6;
                if (simbolo == "punto")
                    return 16;
                if (simbolo == "division")
                    return 12;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;

                break;
            //**ENTERO**
            case 3:
                if (simbolo == "num")
                    return 3;
                if (simbolo == "punto")
                    return 4;
                if (simbolo == "letra")
                    return 11;
                if (simbolo == "suma")
                    return 6;
                if(simbolo=="igual")
                    return 7;
                if(simbolo=="division")
                    return 12;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;

                break;
            //**ENTRE ENTERO Y REAL**
            case 4:
                if (simbolo=="num")
                    return 5;
                else
                    return 17;


            //**REAL**
            case 5:
                if (simbolo == "num")
                    return 5;
                if (simbolo == "suma")
                    return 6;
                if (simbolo == "letra")
                    return 11;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;



                //**TK_ASSIG**
            case 7:
                if (simbolo == "igual")
                    return 8;
                if (simbolo== "num")
                    return 10;
                if (simbolo == "letra")
                    return 11;
                if (simbolo == "punto")
                    return 16;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;


                //**ENTERO BUFFER**
            case 10:
                if(simbolo=="num")
                    return 3;
                if (simbolo == "punto")
                    return 4;
                if(simbolo=="suma")
                    return 6;
                if(simbolo=="letra")
                    return 11;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;
                break;
                //**IDENTIFICADOR BUFFER**
            case 11:
                if (simbolo=="under_score")
                    return 2;
                if (simbolo=="letra")
                    return 2;
                if (simbolo=="num")
                    return 2;
                if (simbolo == "igual")
                    return 7;
                if (simbolo=="punto")
                    return 16;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;
                break;
                //**TK_DIV**
            case 12:
                if (simbolo=="division")
                    return 13;
                if (simbolo=="num")
                    return 10;
                if (simbolo=="punto")
                    return 16;
                if (simbolo=="multiplicacion")
                    return 14;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;
                break;


                //**ESTADOS PARA LOS COMENTARIOS**
            case 13:
                return 13;

            case 15:
                if(simbolo=="division")
                    return 1;
                else
                    return 14;


            //**TK_PUNTO**



                //**CARACTERES ESPECIALES SIN OPCION ADICIONAL**
            case 6:
            case 8:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 42:
                if(simbolo == "num")
                    return 10;
                if(simbolo == "punto")
                    return 16;
                if(simbolo=="letra")
                    return 11;
                if(simbolo=="igual")
                    return 7;
                if (simbolo=="division")
                    return 12;
                if(simbolo=="suma")
                    return 6;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;
                if(simbolo=="or")
                    return 36;
                break;
            //**MAYOR**
            case 28:
                if(simbolo == "num")
                    return 10;
                if(simbolo == "punto")
                    return 16;
                if(simbolo=="letra")
                    return 11;
                if(simbolo=="igual")
                    return 29;
                if (simbolo=="division")
                    return 12;
                if(simbolo=="suma")
                    return 6;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if (simbolo=="menor")
                    return 30;
                if (simbolo=="admiracion")
                    return 32;
                if(simbolo=="ampersand")
                    return 34;

                break;




                //**MENOR**
            case 30:
                if(simbolo == "num")
                    return 10;
                if(simbolo == "punto")
                    return 16;
                if(simbolo=="letra")
                    return 11;
                if(simbolo=="igual")
                    return 31;
                if (simbolo=="division")
                    return 12;
                if(simbolo=="suma")
                    return 6;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if(simbolo=="ampersand")
                    return 34;
            //**NEGACION**
            case 32:
                if(simbolo == "num")
                    return 10;
                if(simbolo == "punto")
                    return 16;
                if(simbolo=="letra")
                    return 11;
                if(simbolo=="igual")
                    return 33;
                if (simbolo=="division")
                    return 12;
                if(simbolo=="suma")
                    return 6;
                if (simbolo == "resta")
                    return 18;
                if (simbolo=="dos_puntos")
                    return 19;
                if (simbolo=="punto_y_coma")
                    return 20;
                if (simbolo=="parentesis_izq")
                    return 21;
                if (simbolo=="parentesis_der")
                    return 22;
                if (simbolo=="coma")
                    return 23;
                if (simbolo=="comilla_simple")
                    return 24;
                if (simbolo=="comilla_doble")
                    return 25;
                if (simbolo=="modulo")
                    return 26;
                if (simbolo=="multiplicacion")
                    return 27;
                if (simbolo=="mayor")
                    return 28;
                if(simbolo=="ampersand")
                    return 34;
                break;

            //**Y**
            case 34:
                if(simbolo=="ampersand")
                    return 35;
                else
                    return -1;
            //**OR**
            case 36:
                if(simbolo=="or")
                    return 37;
                else
                    return -1;

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
    boolean token;

    Token(int columna, int fila){
        this.columna=columna;
        this.fila=fila;
        this.palabra_reservada=false;
        this.token=true;
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
            if(identificador!=("id") && identificador!=("entero") && identificador!=("real")  && identificador!=("tk_char") && identificador!=("tk_cadena")){
                return "<" +
                        identificador +
                        "," +
                        fila +
                        "," +
                        columna+
                        ">" ;
            }

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

