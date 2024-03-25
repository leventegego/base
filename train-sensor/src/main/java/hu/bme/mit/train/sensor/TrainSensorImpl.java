package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.LogEntry;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;
import java.time.*; 
import java.time.temporal.*; 


public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	Table<Integer, Integer, LogEntry> logTable = HashBasedTable.create();

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		controller.setSensor(this);
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);

		int reference = controller.getReferenceSpeed();

		double r = (double)speedLimit / (double)reference;

		boolean alarm =
			speedLimit < 0 ||
			speedLimit > 500 ||
			(r < 0.5);

		user.setAlarmState(alarm);

	}

	@Override
	public void logSpeed(int speed, int pos)
	{
		int h = LocalTime.now().getHour();
		int m = LocalTime.now().getMinute();

		LogEntry entry = new LogEntry();
		entry.pos = pos;
		entry.speed = speed;

		logTable.put(h, m, entry);
	}

	@Override
	public Table<Integer, Integer, LogEntry> getLog()
	{
		return logTable;
	}

}
