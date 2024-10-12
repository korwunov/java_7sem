package org.temperatureObserver;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

import java.util.Random;

public class TemperatureSensor extends Observable<Integer> {
    private final PublishSubject<Integer> subject = PublishSubject.create();

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        subject.subscribe(observer);
    }

    public void sendTemperature() {
        new Thread(() -> {
            while (true) {
                subject.onNext(new Random().nextInt(5, 40));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
