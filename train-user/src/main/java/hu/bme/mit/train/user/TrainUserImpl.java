package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

// branch a + branch-b


public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private boolean emergencyBreak;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);
	}

	@Override
	public boolean getEmergencyBreak() {
		return emergencyBreak;
	}

	@Override
	public void setEmergencyBreak(boolean val)
	{
		this.emergencyBreak = val;
		controller.setEmergencyBreak(val);
	}


	boolean alarmState = false;

	@Override
	public void setAlarmState(boolean alarmState) { alarmState = true; }
	@Override
	public boolean getAlarmState() { return alarmState; }


}
