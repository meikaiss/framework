package com.android.framework.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by meikai on 16/3/29.
 */
public class ServerService extends Service {

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private Binder binder = new IBookManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        bookList.add(new Book(1, "Android开发艺术探索"));
        bookList.add(new Book(2, "Android内核剖析"));
        bookList.add(new Book(3, "Android设计模式"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}
