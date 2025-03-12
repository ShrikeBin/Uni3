package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelBuilder;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelProperties;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelPropertyKey;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.spy.TestSpySocialChannel;

/**
 * Tests chaining multiple decorators, including WordCensor, MessageTruncator, and URLAppender.
 */
public class ChainCensorDecoratorTest extends AbstractSocialChanneldDecoratorTest {

    @Test
    public void testChainWordCensorAndURLAppender() {
        SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

        SocialChannelProperties props = new SocialChannelProperties()
                .putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);

        SocialChannel channel = builder
                .with(new WordCensor())
                .with(new URLAppender("http://example.com"))
                .getDecoratedChannel(props);

        channel.deliverMessage("Microsoft is a major tech company.");
        TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);

        assertEquals("### is a major tech company. http://example.com", spyChannel.lastMessagePublished());
    }

    @Test
    public void testChainWordCensorAndMessageTruncator() {
        SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

        SocialChannelProperties props = new SocialChannelProperties()
                .putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);

        SocialChannel channel = builder
                .with(new WordCensor())
                .with(new MessageTruncator(20))
                .getDecoratedChannel(props);

        channel.deliverMessage("Microsoft builds amazing products.");
        TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);

        assertTrue(spyChannel.lastMessagePublished().startsWith("### builds amaz"));
    }

    @Test
    public void testChainAllDecorators() {
        SocialChannelBuilder builder = createTestSpySocialChannelBuilder();

        SocialChannelProperties props = new SocialChannelProperties()
                .putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);

        SocialChannel channel = builder
                .with(new WordCensor())
                .with(new MessageTruncator(30))
                .with(new URLAppender("http://example.com"))
                .getDecoratedChannel(props);

        // Deliver the message
        channel.deliverMessage("Microsoft is building the future of technology.");
        TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);

        assertTrue(spyChannel.lastMessagePublished().startsWith("### is building the "));
    }
}
