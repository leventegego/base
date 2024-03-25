package hu.bme.mit.train.sensor;


import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;


import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.LogEntry;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController mockTC;
    TrainUser mockTU;
    TrainSensorImpl trainSensor;

    @Before
    public void setup()
    {

        // helyettesito objektumok injektalasa
        mockTC = mock(TrainController.class);
        mockTU = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockTC, mockTU);
    }

    @Test
    public void alarmWhenNegativeSpeedLimit()
    {
        // A riasztas beindul, ha a sebesseghatar negativ erteket kap
        trainSensor.overrideSpeedLimit(-1);

        verify(mockTU, times(1)).setAlarmState(true);
    }

    @Test
    public void alarmWhenHighSpeedLimit()
    {
        // A riasztas beindul, ha a sebesseghatar 500--nal nagyobb erteket kap
        trainSensor.overrideSpeedLimit(501);

        verify(mockTU, times(1)).setAlarmState(true);
    }

    @Test
    public void alarmWhenLowSpeedLimit()
    {
        // A riasztas beindul, ha a sebesseghatar a referenciasebesseg fele alatti erteket kap

        // A referencia sebesseg legyen 100
        when(mockTC.getReferenceSpeed()).thenReturn(100);
        trainSensor.overrideSpeedLimit(20);

        verify(mockTU, times(1)).setAlarmState(true);
    }

    @Test
    public void noAlarm()
    {
        // Helyes ertek eseten nincs riasztas

        // A referenciasebesseg legyen 100
        when(mockTC.getReferenceSpeed()).thenReturn(100);
        trainSensor.overrideSpeedLimit(70);

        verify(mockTU, times(1)).setAlarmState(false);
    }

    @Test
    public void logTest()
    {
        // Log tesztelese
        trainSensor.logSpeed(1, 1);
        Table<Integer, Integer, LogEntry> log = trainSensor.getLog();

        assert !log.isEmpty();
    }

    @Test
    public void speedLimitTest()
    {
        // sebesseghatar beallitasanak tesztelese
        trainSensor.overrideSpeedLimit(10);
        assert trainSensor.getSpeedLimit() == 10;
    }
}
