package com.hack.headout.interfaces;

/**
 * Created by Arun Kumar on 2/7/2016.
 */
public interface ViewInteractionListener {

    void onInvoiceSent(String orderId, long nodeId);
    void onItemServed(String itemId, long nodeId);
    void onItemDelayed(String itemId, long nodeId);
    boolean onNodeDisconnected(long nodeId);
}
