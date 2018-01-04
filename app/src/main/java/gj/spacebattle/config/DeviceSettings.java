package gj.spacebattle.config;

import android.hardware.SensorManager;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by gilsonsantos on 03/01/18.
 */

public class DeviceSettings {

    private static SensorManager sensormanager;

    public static void setSensorManager(SensorManager sensorManagerRef) {
        sensormanager = sensorManagerRef;
    }

    public static SensorManager getSensormanager() {
        return sensormanager;
    }


    public static CGPoint screenResolution(CGPoint gcPoint) {
        return gcPoint;
    }
    public static float screenWidth() {
        return winSize().width;
    }
    public static float screenHeight() {
        return winSize().height;
    }
    public static CGSize winSize() {
        return CCDirector.sharedDirector().winSize();
    }
}
