import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import spark.Spark;
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

        get("/home", (req, res) -> "Hello, World");

        Twilio.init("AC52873427123d39917e51227e318e0106","07ff2b7a945809225c7f9110d045b6ad");
        post("/sms", (req, res) -> {
            String body = req.queryParams("Body");
            String to = "whatsapp:"+req.queryParams("To");
            String from = "whatsapp:+14155238886";

            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(to),
                            new com.twilio.type.PhoneNumber(from),
                            body)
                    .create();

            return message.getSid();
        });

    }
}
