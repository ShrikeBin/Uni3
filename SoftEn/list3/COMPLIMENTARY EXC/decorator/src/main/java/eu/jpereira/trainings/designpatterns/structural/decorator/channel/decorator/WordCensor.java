package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;

public class WordCensor extends SocialChannelDecorator {

    @Override
    public void deliverMessage(String message) {
        if (delegate != null) {
            String censoredMessage = censorWords(message);
            delegate.deliverMessage(censoredMessage);
        }
    }

    /**
     * Censor specific words in the message.
     * 
     * @param message The original message.
     * @return The censored message.
     */
    private String censorWords(String message) {
        // Replace "Microsoft" with "###"
        return message.replaceAll("(?i)Microsoft", "###"); // "(?i)" makes it case-insensitive
    }
}
