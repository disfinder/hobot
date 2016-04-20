/*
    Example is here
    https://gist.github.com/samczsun/507ad7f91bc11cb5d487
    Skype4J Library is required

 */

package hobot;

import com.samczsun.skype4j.*;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.SkypeException;
import com.samczsun.skype4j.internal.SkypeImpl;
import java.util.HashMap;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messenger {

    private static Messenger instance;

    private Messenger(String username, String password) throws SkypeException{
        logger = LoggerFactory.getLogger("Messenger");
        logger.debug("Lazy. Trying log into Skype, username: "
                + username
        );
        skypeInstance = (SkypeImpl) new SkypeBuilder(username, password).withAllResources().build();
        skypeInstance.login();
        chatBag = new HashMap<String, Chat>();
        logger.debug("Skype login success. Messenger instance created ");

    }

    protected static Messenger getInstance() {
        if (instance == null) {
            try {
                instance = new Messenger(username, password);
            } catch (SkypeException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    private  static String username;
    private  static String password;

    protected static void setUsername(String username) {
        Messenger.username = username;
    }
    protected static void setPassword(String password) {
        Messenger.password = password;
    }

    private Logger logger;
    private SkypeImpl skypeInstance;
    private HashMap<String, Chat> chatBag;

    public void putChatIntoBag(String key, String chatName) {
        Chat chat = null;
        try {
            logger.debug("Trying getOrLoadChat " + chatName);
            chat =  skypeInstance.getOrLoadChat(chatName);

        } catch (ConnectionException connectionException) {
            logger.error("Looks like getOrLoadChat failed.");
            connectionException.printStackTrace();
        } catch (ChatNotFoundException chatNotFoundException) {
            logger.warn("Chat not found/No such chat: "
                    + key
                    + " "
                    + chatName
            );
        }
        chatBag.put(key, chat);
        logger.info("Chat /send/" + key + " put to bag");
    }

    protected Set<String> getChatsKeys() {
        return chatBag.keySet();
    }

    protected Chat getChat (String key) {
        return chatBag.get(key);

    }

    protected void shutDown() {
        logger.debug("Trying to shutdown Messenger. Loging out Skype");
        if (skypeInstance != null) {
            try
            {
                skypeInstance.logout();
                logger.info("Skype logged out");
            }
            catch(SkypeException e) {
                logger.error("Graceful skype logout failed");
                logger.error("Messenger will be killed dirty anyway");
                e.printStackTrace();
            }
            finally {
                instance = null;
                logger.info("Messenger instance nulled");
            }
        }

    }

}
