package org.temperatureObserver;

public class Main {
    public static void main(String[] args) {
        TemperatureSensor temperatureSensor = new TemperatureSensor();
        CO2Sensor co2Sensor = new CO2Sensor();
        Alarm alarm = new Alarm();
        temperatureSensor.subscribeActual(alarm);
        co2Sensor.subscribeActual(alarm);
        temperatureSensor.sendTemperature();
        co2Sensor.sendCo2();
    }
}