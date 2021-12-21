import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.twiml.Sms;
import com.twilio.type.PhoneNumber;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class SMSBackend {
    public static void main(String[] args) {

        //Heroku assigns different port each time, hence reading it from process.
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        Spark.port(port);

        get("/welcome", (req, res) -> "Hello, World");

       // Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        Twilio.init("AC52873427123d39917e51227e318e0106","89c3946ce0d6e8fd454af8eddcda664a");
        post("/sms", (req, res) -> {
            String body = "whatsapp:"+ req.queryParams("Body");
            String to = "whatsapp:"+ req.queryParams("To");
            String from = "whatsapp:+14155238886";

            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:+22961877882"),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            "body").create();


            return message.getSid();
        });

    }
}
