package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import static org.junit.Assert.*;
import eu.jpereira.trainings.designpatterns.structural.adapter.DoorTest;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;

/**
 * @author windows
 */
public class ThirdPartyAdapterTest extends DoorTest {

    @Override
    protected Door createDoorUnderTest() {
        return new ThirdPartyDoorAdapter(); // Fixed the typo
    }

    @Override
    protected String getDefaultDoorCode() {
        return ThirdPartyDoor.DEFAULT_CODE;
    }
}
