package org.temperatureObserver;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

import java.util.Random;

public class CO2Sensor extends Observable<Integer> {
    private final PublishSubject<Integer> subject = PublishSubject.create();

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        subject.subscribe(observer);
    }

    public void sendCo2() {
        new Thread(() -> {
            while (true) {
                subject.onNext(new Random().nextInt(41, 100));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
