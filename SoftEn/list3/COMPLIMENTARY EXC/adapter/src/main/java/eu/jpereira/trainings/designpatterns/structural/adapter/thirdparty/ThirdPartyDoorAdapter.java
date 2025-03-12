package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorAdapter extends ThirdPartyDoor implements Door {

    public ThirdPartyDoorAdapter() 
    {
    }

    @Override
    public void open(String code) throws IncorrectDoorCodeException {
        try {
            this.unlock(code);
            this.setState(ThirdPartyDoor.DoorState.OPEN);
        } catch (CannotUnlockDoorException | CannotChangeStateOfLockedDoor e) {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public void close() {
        try {
            this.setState(ThirdPartyDoor.DoorState.CLOSED);
        } catch (CannotChangeStateOfLockedDoor e) {
            // Log or handle the exception (optional)
        }
    }

    @Override
    public boolean isOpen() {
        return this.getState() == ThirdPartyDoor.DoorState.OPEN;
    }

    @Override
    public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
            throws IncorrectDoorCodeException, CodeMismatchException {
        if (!newCode.equals(newCodeConfirmation)) {
            throw new CodeMismatchException();
        }
        try {
            this.unlock(oldCode);
            this.setNewLockCode(newCode);
        } catch (CannotUnlockDoorException | CannotChangeCodeForUnlockedDoor e) {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public boolean testCode(String code) {
        try {
            this.unlock(code);
            return true;
        } catch (CannotUnlockDoorException e) {
            return false;
        }
    }
}
