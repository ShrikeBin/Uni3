package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelBuilder;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelProperties;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelPropertyKey;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.spy.TestSpySocialChannel;

/**
 * @author jpereira
 */
public class WordCensorTest extends AbstractSocialChanneldDecoratorTest {

    @Test
    public void testCensorWordMicrosoft() {
        SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

        SocialChannelProperties props = new SocialChannelProperties()
                .putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);
        SocialChannel channel = builder.with(new WordCensor()).getDecoratedChannel(props);

        channel.deliverMessage("Microsoft is a tech giant.");
        
        TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);
        assertEquals("### is a tech giant.", spyChannel.lastMessagePublished());
    }

    @Test
    public void testCensorWordCaseInsensitive() {
        SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

        SocialChannelProperties props = new SocialChannelProperties()
                .putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);
        SocialChannel channel = builder.with(new WordCensor()).getDecoratedChannel(props);

        channel.deliverMessage("MICROSOFT creates Windows OS.");
        
        TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);
        assertEquals("### creates Windows OS.", spyChannel.lastMessagePublished());
    }
}
