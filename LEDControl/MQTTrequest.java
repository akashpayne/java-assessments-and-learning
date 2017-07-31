import org.eclipse.paho.client.mqttv3.*;
/**
 * Mqtt request handler, that gets message from the doughnut client and passes it on to the led controller.
 * 
 * @author Ashwin Rana 
 * @version 25.01.2016
 */
public class MQTTrequest
{
    public static void request() {
       String topic        = "unikent/users/at435/"+"doorSensor"; 

        String content      = LEDcontrol.floatString() ; 
        // QoS supported at level 0 and 1
        int qos             = 1;
 
        String broker       = "tcp://doughnut.kent.ac.uk:1883"; 
        
        try
            {MqttClient mainclient = new MqttClient("tcp://doughnut.kent.ac.uk:1883", "pahomqttpublish1");
                mainclient.connect();
                MqttMessage message = new MqttMessage();
                message.setPayload("A single message".getBytes());
                mainclient.publish("at435/doorSensor", message);
                mainclient.disconnect();
            } 
             catch (MqttException e){
             e.printStackTrace();
        }
  
    }
    
    public static byte message(){
        return message();
    }
}
