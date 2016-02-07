package com.hack.headout.presenters;

import android.app.Activity;

import com.hack.headout.interfaces.IMainActivity;
import com.hack.headout.service.Node;
import com.hack.headout.service.model.Message;

/**
 * Created by Arun Kumar on 2/6/2016.
 */
public class MainActivityPresenter {

    private Activity mActivity;
    private IMainActivity mView;

    private Node node;
    private static boolean started = false;

    public MainActivityPresenter(Activity mActivity, IMainActivity mView) {

        setActivity(mActivity);
        setView(mView);

        node = new Node(this);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public IMainActivity getView() {
        return mView;
    }

    public void setView(IMainActivity mView) {
        this.mView = mView;
    }

    public void onStart(){
        node.start();
    }

    public void onStop()
    {

        if(node != null)
            node.stop();
    }

    public void refreshPeers()
    {
        getView().onPeerRefresh(node.getLinks().size());
    }

    public void refreshFrames()
    {
    }

    public void onOrderReceived(long nodeId)
    {
        getView().onOrderReceived(nodeId);
    }

    public void onPaymentReceived(long nodeId)
    {
        getView().onPaymentReceived(nodeId);
        if(node != null)
            node.broadcastFrame(getMessage("",nodeId, Message.MessageType.PAYMENT_RECEIVED.getCode()).toByeArray());
    }

    public Message getMessage(String msg, long nodeId, int messageType)
    {
        Message message = new Message();
        message.setDestinationId(String.valueOf(nodeId));
        message.setSourceId("Master");
        message.setMessageType(String.valueOf(messageType));
        message.setMessage(msg);

        return message;

    }

    public void onInvoiceSent(String orderId, long nodeId)
    {
        if(node != null)
            node.broadcastFrame(getMessage("",nodeId, Message.MessageType.SEND_INVOICE.getCode()).toByeArray());
    }

    public void onNodeDisconnected(long nodeId){
        getView().onNodeDisconnected(nodeId);
    }

    public void onItemServed(String itemId, long nodeId) {

        if(node != null)
            node.broadcastFrame(getMessage(itemId,nodeId, Message.MessageType.ORDER_SERVED.getCode()).toByeArray());
    }

    public void onItemDelayed(String itemId, long nodeId) {
        if(node != null)
            node.broadcastFrame(getMessage(itemId,nodeId, Message.MessageType.ORDER_DELAYED.getCode()).toByeArray());
    }

    public void onOfferAnnounce(){
        if(node != null)
            node.broadcastFrame(getMessage("",0, Message.MessageType.OFFER_ANNOUNCE.getCode()).toByeArray());
    }
}
