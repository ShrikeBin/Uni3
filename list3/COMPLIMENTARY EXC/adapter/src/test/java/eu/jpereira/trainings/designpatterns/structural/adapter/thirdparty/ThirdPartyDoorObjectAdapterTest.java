package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.DoorTest;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;

/**
 * @author windows
 */
public class ThirdPartyDoorObjectAdapterTest extends DoorTest {

    @Override
    protected Door createDoorUnderTest() {
        return new ThirdPartyDoorObjectAdapter(); // Ensure a default constructor exists in the adapter class
    }

    @Override
    protected String getDefaultDoorCode() {
        return ThirdPartyDoor.DEFAULT_CODE;
    }
}
