// IYebbaServiceInterface.aidl
package com.yebba.services;

interface IYebbaServiceInterface {
    // Get the current globally logged-in user
    String getActiveUser();
    
    // Check if the user has "Premium" status across all YEBBA apps
    boolean isPremiumMember();
    
    // Send a system-wide notification from any YEBBA app
    void sendEcosystemNotification(String title, String message);
}
