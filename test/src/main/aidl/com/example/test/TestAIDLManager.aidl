// TestAIDLManager.aidl
package com.example.test;
import com.example.test.ClientAIDLCallback;
// Declare any non-default types here with import statements

interface TestAIDLManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerClientCallback(ClientAIDLCallback clientAIDLCallback);
    void trigBroadcastCallback();
}
