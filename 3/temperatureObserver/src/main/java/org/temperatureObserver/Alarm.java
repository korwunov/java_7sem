package org.temperatureObserver;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class Alarm implements Observer<Integer> {
    private final int TEMPERATURE_LIMIT = 25;
    private final int CO2_LIMIT = 70;

    public int temperature = 0;
    public int co2 = 0;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("Новый subscriber");
    }

    @Override
    public void onNext(Integer value) {
        if (value <= 30) {
            temperature = value;
        }
        else {
            co2 = value;
        }
        check();
    }

    public void check() {
        if (temperature >= TEMPERATURE_LIMIT) {
            System.out.println("ТРЕВОГА!!! ТЕМПЕРАТУРА ВЫШЕ ЛИМИТА, ЗНАЧЕНИЕ " + temperature);
        } else {
            System.out.println("Температура в норме, значение " + temperature);
        }

        if (co2 >= CO2_LIMIT) {
            System.out.println("ТРЕВОГА!!! CO2 ВЫШЕ ЛИМИТА, ЗНАЧЕНИЕ " + co2);
        } else {
            System.out.println("CO2 в норме, значение " + co2);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Наблюдение завершено");
    }
}
