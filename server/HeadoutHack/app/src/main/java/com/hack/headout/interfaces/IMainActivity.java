package com.hack.headout.interfaces;

/**
 * Created by Arun Kumar on 2/6/2016.
 */
public interface IMainActivity {

    void onPeerRefresh(int peersCount);

    void onOrderReceived(long nodeId);

    void onNotificationAnimateIndicator(int tabIndex);

    void onInvoiceSent(String orderId, long nodeId);

    void onPaymentReceived(long nodeId);

    void onItemServed(String itemId, long nodeId);

    void onItemDelayed(String itemId, long nodeId);

    boolean onNodeDisconnected(long nodeId);
}
