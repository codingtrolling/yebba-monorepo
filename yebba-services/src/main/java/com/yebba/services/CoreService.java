package com.yebba.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.yebba.services.IYebbaServiceInterface;

public class CoreService extends Service {

    // Implementation of the AIDL interface in Java
    private final IYebbaServiceInterface.Stub mBinder = new IYebbaServiceInterface.Stub() {
        @Override
        public String getActiveUser() throws RemoteException {
            return "YEBBA_Explorer_001"; // Placeholder for actual auth logic
        }

        @Override
        public boolean isPremiumMember() throws RemoteException {
            return true;
        }

        @Override
        public void sendEcosystemNotification(String title, String message) throws RemoteException {
            // Logic to trigger a NotificationManager alert goes here
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface so client apps (Chat, Search, Store) can call it
        return mBinder;
    }
}
