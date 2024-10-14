package org.task2;

import io.reactivex.Observable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> list = Observable.range(0, 1000).map(i -> (int) (Math.random() * 1000))
                .filter(number -> number > 500);
        list.forEach(System.out::println);
        System.out.println("____________________");

        Observable<Integer> list1 = Observable.range(0, 1000).map(i -> (int) (Math.random() * 1000));
        Observable<Integer> list2 = Observable.range(0, 1000).map(i -> (int) (Math.random() * 1000));
        Observable<Integer> mergedLists = Observable.concat(list1, list2);
        mergedLists.forEach(System.out::println);
        System.out.println("____________________");

        list = Observable.range(0, 10).map(i -> (int) (Math.random() * 100));
        Observable<Integer> result = list.take(5);
        result.forEach(System.out::println);
    }
}