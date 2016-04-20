package hobot;
import static spark.Spark.*;
import com.typesafe.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


public class Hobot {

    private static class ShutDownHook extends Thread {
        public void run() {
            Messenger.getInstance().shutDown();

        }
    }

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("Hobot");
        logger.info("Hobot is getting ready.");

        ShutDownHook jvmShutdownHook = new ShutDownHook();
        Runtime.getRuntime().addShutdownHook(jvmShutdownHook);

        logger.info ("Hobot is reading wishlist");
        Config conf = ConfigFactory.load();
        Messenger.setUsername(conf.getString("credentials.username"));
        Messenger.setPassword(conf.getString("credentials.password"));

        logger.debug("Getting chats from wishlist.");
        ConfigObject chatsList = conf.getObject("chats");
        for (Map.Entry<String, ConfigValue> chatEntry: chatsList.entrySet()) {
            logger.debug("Putting chat "
                    + chatEntry.getKey()
                    + " to bag of presents"
            );

            Messenger.getInstance().putChatIntoBag(
                    chatEntry.getKey(),
                    chatEntry.getValue().unwrapped().toString()
            );
        }
        logger.info("All Chats were put to bag.");

        logger.info("Igningting Spark listeners");
        logger.debug("Starting GET /config listener");
        get("/config", (request, response) -> {
            return Messenger.getInstance().getChatsKeys().toString();
        });

        logger.debug("Starting POST /send/:chat listener");
        post("send/:chat", (request, response) -> {
            if (Messenger.getInstance().getChatsKeys().contains(request.params(":chat"))) {
                Messenger.getInstance().getChat(request.params(":chat")).sendMessage(request.body());
                return "OK";
            }
            else return "NOK";
        });
    }
}