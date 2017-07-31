import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jssc.SerialPort;
import static jssc.SerialPort.MASK_RXCHAR;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *A simple Java app that controls the LEDs and resets alarm through MQTT.
 * 
 * @author Ashwin Rana 
 * @version 25.01.2016
 */


public class LEDcontrol extends Application {
    
    SerialPort mbedPort = null;
    ObservableList<String> portList;
    
    Button onButton;
    Button offButton;
    Button reset;
    Button request;
    public  String led;
    public static String mqtt;
    Label labValue;
    final int NUM_OF_POINT = 100;

     
    private void detectPort(){         
        portList = FXCollections.observableArrayList();
        String[] serialPortNames = SerialPortList.getPortNames();
        for(String devices: serialPortNames){
            System.out.println(devices);
            portList.add(devices);
        }
    }
    
    @Override
    public void start(Stage primaryStage) {        
        labValue = new Label();
        
        detectPort();
        final ComboBox comboBoxPorts = new ComboBox(portList);
        comboBoxPorts.valueProperty()
                .addListener(new ChangeListener<String>() 
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldVal, String newVal) {
                System.out.println(newVal);
                disconnectmbed();
                connectmbed(newVal);   
            }
        }
        );  
        
        //Button to control led on mbed 
        onButton = new Button("On");
        onButton.setOnAction((ActionEvent event) -> 
        {
            led = "On";
            updateLed();
        }
        );
        
        offButton = new Button("Off");
        offButton.setOnAction((ActionEvent event) -> 
        {
            led = "Off";
            updateLed();
        }
        );
        
        reset = new Button("Reset");
        reset.setOnAction((ActionEvent event) -> 
        {
            led = "Reset";
            updateLed();
        }
        );
        
        request = new Button("Request");
        request.setOnAction((ActionEvent event) -> 
        {
            requestMessage();
        }
        );
        
        //GUI
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                comboBoxPorts,  
                onButton, 
                offButton,
                reset,
                request);
                
                StackPane root = new StackPane();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 500, 400);
        
        primaryStage.setTitle("LED Controller");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void updateLed(){
        MQTTrequest.request();  

        switch (led){
            case "On":
            try{if(mbedPort != null){
                    mbedPort.writeByte((byte)MQTTrequest.message());
                    System.out.println("LED on");
                }else{
                    System.out.println("mbedPort not connected!");
                } }catch (SerialPortException ex) {
                    Logger.getLogger(LEDcontrol.class.getName())
                    .log(Level.SEVERE, null, ex);break;
                }       
            case "Off":
            try{if(mbedPort != null){
                    mbedPort.writeByte((byte)MQTTrequest.message());
                    System.out.println("LED OFF");
                }else{
                    System.out.println("mbedPort not connected!");
                } }catch (SerialPortException ex) {
                    Logger.getLogger(LEDcontrol.class.getName())
                    .log(Level.SEVERE, null, ex);break;
                }    
            case "Reset":
            try{if(mbedPort != null){
                    mbedPort.writeByte((byte)MQTTrequest.message());
                    System.out.println("Alarm Reset!");
                }else{
                    System.out.println("mbedPort not connected!");
                } }catch (SerialPortException ex) {
                    Logger.getLogger(LEDcontrol.class.getName())
                    .log(Level.SEVERE, null, ex);break;
                }
            }
    }
    
    private void requestMessage(){
      MQTTrequest.request();  

    }
   
    public static String floatString(){
        return mqtt;
    }
    
    public boolean connectmbed(String port){      
        System.out.println("connectmbed");
        boolean success = false;
        SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_38400,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
               
            mbedPort = serialPort;
            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(LEDcontrol.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex.toString());
        }
        return success;
    }
    
    public void disconnectmbed(){      
        System.out.println("disconnectmbed()");
        if(mbedPort != null){
            try {
                mbedPort.removeEventListener();
                if(mbedPort.isOpened()){
                    mbedPort.closePort();
                } 
                mbedPort = null;
            } catch (SerialPortException ex) {
                Logger.getLogger(LEDcontrol.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void stop() throws Exception {
        disconnectmbed();
        super.stop();
    }
            
    public static void main(String[] args) {
        launch(args);
    }
    
}