package hu.bme.mit.train.interfaces;

public interface TrainController {

	void followSpeed();

	int getReferenceSpeed();

	void setSpeedLimit(int speedLimit);

	void setJoystickPosition(int joystickPosition);

	int getJoystickPosition();

	void setEmergencyBreak(boolean val);

	void setSensor(TrainSensor sensor);

}
