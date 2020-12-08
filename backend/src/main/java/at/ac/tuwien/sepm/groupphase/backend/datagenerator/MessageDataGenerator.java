package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

@Profile("generateData")
@Component
public class MessageDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_MESSAGES_TO_GENERATE = 5;
    private static final int NUMBER_OF_InterestAreas_TO_GENERATE = 10;
    private static final String TEST_NEWS_TITLE = "Title";
    private static final String TEST_NEWS_SUMMARY = "Summary of the message";
    private static final String TEST_NEWS_TEXT = "This is the text of the message";

    private final MessageRepository messageRepository;
    private final InterestAreaRepository interestAreaRepository;

    public MessageDataGenerator(MessageRepository messageRepository, InterestAreaRepository interestAreaRepository) {
        this.messageRepository = messageRepository;
        this.interestAreaRepository = interestAreaRepository;
    }

    @PostConstruct
    private void generateMessage() {
        if (messageRepository.findAll().size() > 0) {
            LOGGER.debug("message already generated");
        } else {
            LOGGER.debug("generating {} message entries", NUMBER_OF_MESSAGES_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_MESSAGES_TO_GENERATE; i++) {
                Message message = Message.MessageBuilder.aMessage()
                    .withTitle(TEST_NEWS_TITLE + " " + i)
                    .withSummary(TEST_NEWS_SUMMARY + " " + i)
                    .withText(TEST_NEWS_TEXT + " " + i)
                    .withPublishedAt(LocalDateTime.now().minusMonths(i))
                    .build();
                LOGGER.debug("saving message {}", message);
                messageRepository.save(message);
            }
        }
    }

    @PostConstruct
    private void generateInterestAreas() {
        if (interestAreaRepository.findAll().size() > 0) {
            LOGGER.debug("interestAreas already generated");
        } else {
            LOGGER.debug("generating {} interestAreas entries", NUMBER_OF_InterestAreas_TO_GENERATE);
            InterestArea interestArea1 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Promo")
                .withDescription("Flyer verteilen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea1);
            interestAreaRepository.save(interestArea1);
            InterestArea interestArea2 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Gastro")
                .withDescription("Gäste bedienen und Tische abräumen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea2);
            interestAreaRepository.save(interestArea2);
            InterestArea interestArea3 = InterestArea.InterestAreaBuilder.aInterest()
                .withArea("Körperliches")
                .withDescription("schwere Lasten tragen")
                .build();
            LOGGER.debug("saving interestArea {}", interestArea3);
            interestAreaRepository.save(interestArea3);
        }
    }
}
