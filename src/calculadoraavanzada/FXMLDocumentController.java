package calculadoraavanzada;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    private Float dato, segundoDato, resultado;
    private int operation = -1, operacionInicial = 0, numOpe = 0, soloUnSigno = 1,
            signoRestaUnaVez = 1, charTexto, soloUnaComa = 1;
    private boolean nuevaOpe, numeroIntroducido = false;
    private String texto;
    @FXML
    private Button bt_num5, bt_num2,bt_num3,bt_num4,bt_num7,bt_num8,bt_num6,
            bt_num9,bt_num0,bt_Sumar,bt_Restar,bt_Multiplicar,bt_Dividir,
            bt_Igual, bt_Clear,bt_num1,bt_coma,bt_nuevoHistorial,bt_Delete;
    @FXML
    private TextField tf_pantalla, tf_pantalla2;
    @FXML
    private CheckBox cb_verHistorial;
    @FXML
    private TextArea ta_historial;


    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (nuevaOpe == true) {
            tf_pantalla.setText("");
            tf_pantalla2.setText("");
            soloUnaComa = 1;
        }
        nuevaOpe = false;

        if (event.getSource() == bt_num1) {
            tf_pantalla.setText(tf_pantalla.getText() + "1");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num2) {
            tf_pantalla.setText(tf_pantalla.getText() + "2");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num3) {
            tf_pantalla.setText(tf_pantalla.getText() + "3");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num4) {
            tf_pantalla.setText(tf_pantalla.getText() + "4");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num5) {
            tf_pantalla.setText(tf_pantalla.getText() + "5");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num6) {
            tf_pantalla.setText(tf_pantalla.getText() + "6");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num7) {
            tf_pantalla.setText(tf_pantalla.getText() + "7");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num8) {
            tf_pantalla.setText(tf_pantalla.getText() + "8");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num9) {
            tf_pantalla.setText(tf_pantalla.getText() + "9");
            numeroIntroducido = true;

        } else if (event.getSource() == bt_num0) {
            texto = tf_pantalla.getText();

            if (texto.isEmpty() || texto.startsWith("0") == false || texto.length() > 1) {
                tf_pantalla.setText(tf_pantalla.getText() + "0");
                numeroIntroducido = true;
            }

        } else if (event.getSource() == bt_Clear) {
            tf_pantalla.setText("");
            tf_pantalla2.setText("");
            numeroIntroducido = false;
            soloUnSigno = 1;
            signoRestaUnaVez = 1;
            soloUnaComa = 1;

        } else if (event.getSource() == bt_Delete) {

            if (tf_pantalla.getText().isEmpty() == false) {
                charTexto = tf_pantalla.lengthProperty().intValue();
                tf_pantalla.deleteText(charTexto - 1, charTexto);
            }
            
        } else if (event.getSource() == bt_coma) {
            texto = tf_pantalla.getText();

            if (soloUnaComa == 1 && texto.contains(".") == false) {
                soloUnaComa++;
                if (tf_pantalla.getText().isEmpty() == true) {
                    tf_pantalla.setText(tf_pantalla.getText() + "0.");
                } else {
                    tf_pantalla.setText(tf_pantalla.getText() + ".");
                }
            }else{
                soloUnaComa = 1;
            }
            numeroIntroducido = true;

        } else if (event.getSource() == bt_Sumar) {
            if (numeroIntroducido == true && soloUnSigno == 1) {
                soloUnSigno++;
                soloUnaComa = 1;
                sumar();
            }
        } else if (event.getSource() == bt_Restar) {
            texto = tf_pantalla2.getText();
            if (numeroIntroducido == true) {
                signoRestaUnaVez = 1;
                soloUnaComa = 1;
            }
            if (signoRestaUnaVez == 1) { // Para que solo te deje poner el menos una vez
                signoRestaUnaVez++;
                restar();
            }
        } else if (event.getSource() == bt_Multiplicar) {
            if (numeroIntroducido == true && soloUnSigno == 1) {
                soloUnSigno++;
                soloUnaComa = 1;
                multiplicar();
            }

        } else if (event.getSource() == bt_Dividir) {
            if (numeroIntroducido == true && soloUnSigno == 1) {
                soloUnSigno++;
                soloUnaComa = 1;
                dividir();
            }

        } else if (event.getSource() == bt_Igual) {
            // AQUÍ RECOGEMOS EL DATO UNO SEA DE UN NUMERO SOLO, O DEL RESULTADO
            // DE LAS OPERACIONES ANTERIORES, Y LO OPERAMOS CON EL ULTIMO DATO INTRODUCIDO
            if (operacionInicial != 0) { // si ya se ha introducido algun numero
                numOpe++;
                segundoDato = Float.parseFloat(tf_pantalla.getText());
                tf_pantalla2.setText(tf_pantalla2.getText() + tf_pantalla.getText());
                tf_pantalla.setText("");
                switch (operation) {
                    case 1: // SUMA
                        resultado = this.dato + segundoDato;
                        break;
                    case 2: // RESTA
                        resultado = this.dato - segundoDato;
                        break;
                    case 3: // MULTIPLICA
                        resultado = this.dato * segundoDato;
                        break;
                    case 4: // DIVIDE
                        try {
                            resultado = this.dato / segundoDato;
                        } catch (Exception e) {
                            tf_pantalla.setText("ERROR");
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
                tf_pantalla.setText(String.valueOf(resultado));
                this.dato = 0f; // el dato vuelva a cero para nuevas operaciones
                this.operacionInicial = 0; // se vuelve a indicar que va a realizarse una primera operacion
                this.ta_historial.appendText("Operacion: " + numOpe + ": " + tf_pantalla2.getText()
                        + " = " + resultado + "\n"); //Historial
                nuevaOpe = true;
                numeroIntroducido = false;
                soloUnSigno = 1;
                signoRestaUnaVez = 1;
            }
        }

    }
    

    private void sumar() {
        soloUnSigno = 1; // deja volver a poner un signo
        numeroIntroducido = false; // avisa de que tiene que introducir un numero para mostrr el signo

        this.tf_pantalla2.setText(tf_pantalla2.getText() + tf_pantalla.getText() + " + "); // Pinta por la pantalla 2 el texto que ya hay en la dos + el de la 1 + el simbolo
        if (operacionInicial != 0) { // Entra por aquí a partir de la primera operacion
            this.dato = datoTemporal(); // La variable dato es el resultado de la el primer numero mas el segundo, del resultado de esos mas el tercer, etc
        }
        this.operation = 1; // SUMA
        if (operacionInicial == 0) { // la primera operacion que debe hacer
            operacionInicial++;
            this.dato = Float.parseFloat(tf_pantalla.getText()); // La variable dato es igual al primer nuemro que metemos
            tf_pantalla.setText("");
        }
    }

    private void restar() {
        soloUnSigno = 1;

        this.tf_pantalla2.setText(tf_pantalla2.getText() + tf_pantalla.getText() + " - ");
        if (operacionInicial != 0) {
            this.dato = datoTemporal();
        }
        this.operation = 2; // RESTA
        if (operacionInicial == 0) {
            operacionInicial++;
            if (numeroIntroducido == true) { // para que lo covnierta a negativo
                this.dato = Float.parseFloat(tf_pantalla.getText());
            } else {
                this.dato = 0f;
            }
            tf_pantalla.setText("");
        }
        numeroIntroducido = false;

    }

    private void multiplicar() {
        soloUnSigno = 1;
        numeroIntroducido = false;
        this.tf_pantalla2.setText(tf_pantalla2.getText() + tf_pantalla.getText() + " * ");
        if (operacionInicial != 0) {
            this.dato = datoTemporal();
        }
        this.operation = 3; // RESTA
        if (operacionInicial == 0) {
            operacionInicial++;
            this.dato = Float.parseFloat(tf_pantalla.getText());
            tf_pantalla.setText("");
        }
    }

    private void dividir() {
        soloUnSigno = 1;
        numeroIntroducido = false;
        this.tf_pantalla2.setText(tf_pantalla2.getText() + tf_pantalla.getText() + " / ");
        if (operacionInicial != 0) {
            this.dato = datoTemporal();
        }
        this.operation = 4; // RESTA
        if (operacionInicial == 0) {
            operacionInicial++;
            this.dato = Float.parseFloat(tf_pantalla.getText());
            tf_pantalla.setText("");
        }
    }

    private Float datoTemporal() {
        // ESTE METODO TRABAJA CON LOS DATOS ANTES DE DARLE AL IGUAL
        // Y CAMBIA EL VALOR DE LA VARIABLE DATO AL RESULTADO DE ESTA OPERACION
        // ASÍ PODEMOS USAR EL DATO UNO OTRA VEZ PARA SUMERLO CON EL DATO 2 
        // QUE ES EL NUEVO DATO QUE INTRODUCIREMOS Y AL QUE LO SUMAREMOS CON 
        // EL RESULTADO DE LAS ANTERIORES OPERACIONES
        segundoDato = Float.parseFloat(tf_pantalla.getText()); // la variable segundo dato va recogiendo cada numero que añadimos despues del primer numero
        tf_pantalla.setText("");
        switch (operation) {
            case 1: // SUMA
                resultado = this.dato + segundoDato;
                break;
            case 2: // RESTA
                resultado = this.dato - segundoDato;
                break;
            case 3: // MULTIPLICA
                resultado = this.dato * segundoDato;
                break;
            case 4: // DIVIDE
                try {
                    resultado = this.dato / segundoDato;
                } catch (Exception e) {
                    tf_pantalla.setText("ERROR");
                }
                break;
            default:
                throw new AssertionError();
        }
        return resultado;
    }

    @FXML
    private void historialAction(ActionEvent event) throws IOException, Exception {

        if (cb_verHistorial.isSelected()) {
            ta_historial.setVisible(true);
        } else {
            ta_historial.setVisible(false);
        }

        if (event.getSource() == this.bt_nuevoHistorial) {
            Alert dialogoAlerta = new Alert(AlertType.CONFIRMATION);
            dialogoAlerta.setTitle("Nuevo Historial");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("¿Está seguro de abrir un nuevo historial? Perderá el "
                    + "historial actual");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = dialogoAlerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.ta_historial.setText("");
                numOpe =0;
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
