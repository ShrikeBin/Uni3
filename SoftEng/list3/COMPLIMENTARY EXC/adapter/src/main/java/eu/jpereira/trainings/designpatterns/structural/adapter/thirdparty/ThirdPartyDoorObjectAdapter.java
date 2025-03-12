package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorObjectAdapter implements Door {

    private final ThirdPartyDoor thirdPartyDoor;
    
    public ThirdPartyDoorObjectAdapter() {
        this.thirdPartyDoor = new ThirdPartyDoor();
    }

    public ThirdPartyDoorObjectAdapter(ThirdPartyDoor thirdPartyDoor) {
        this.thirdPartyDoor = thirdPartyDoor;
    }

    @Override
    public void open(String code) throws IncorrectDoorCodeException {
        try {
            thirdPartyDoor.unlock(code);
            thirdPartyDoor.setState(ThirdPartyDoor.DoorState.OPEN);
        } catch (CannotUnlockDoorException | CannotChangeStateOfLockedDoor e) {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public void close() {
        try {
            thirdPartyDoor.setState(ThirdPartyDoor.DoorState.CLOSED);
        } catch (CannotChangeStateOfLockedDoor e) {
            // Log or handle the exception (optional)
        }
    }

    @Override
    public boolean isOpen() {
        return thirdPartyDoor.getState() == ThirdPartyDoor.DoorState.OPEN;
    }

    @Override
    public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
            throws IncorrectDoorCodeException, CodeMismatchException {
        if (!newCode.equals(newCodeConfirmation)) {
            throw new CodeMismatchException();
        }
        try {
            thirdPartyDoor.unlock(oldCode);
            thirdPartyDoor.setNewLockCode(newCode);
        } catch (CannotUnlockDoorException | CannotChangeCodeForUnlockedDoor e) {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public boolean testCode(String code) {
        try {
            thirdPartyDoor.unlock(code);
            return true;
        } catch (CannotUnlockDoorException e) {
            return false;
        }
    }
}
