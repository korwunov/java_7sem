package org.fileObserver;

import io.reactivex.Observable;

public class FileQueue {
    private final int capacity;
    private final Observable<File> fileObservable;

    public FileQueue(int capacity) {
        this.capacity = capacity;
        this.fileObservable = new FileGenerator().generateFile().replay(capacity).autoConnect();
    }

    public Observable<File> getFileObservable() {
        return fileObservable;
    }
}
