package com.hack.headout.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hack.headout.presenters.MainActivityPresenter;
import com.hack.headout.service.model.Message;
import com.hack.headout.service.model.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;

public class Node implements TransportListener
{
	private boolean running;
	private MainActivityPresenter mPresenter;
	private long nodeId;
	private Transport transport;

	private ArrayList<Link> links = new ArrayList<>();
	private int framesCount = 0;

	public Node(MainActivityPresenter activity)
	{
		this.mPresenter = activity;

		do
		{
			nodeId = new Random().nextLong();
		} while (nodeId == 0);

		if(nodeId < 0)
			nodeId = -nodeId;

		configureLogging();

		EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);
		//kinds = EnumSet.of(TransportKind.WIFI);
		//kinds = EnumSet.of(TransportKind.BLUETOOTH);

		this.transport = Underdark.configureTransport(
				234235,
				nodeId,
				this,
				null,
				mPresenter.getActivity().getApplicationContext(),
				kinds
		);
	}

	private void configureLogging()
	{
		/*NSLoggerAdapter adapter = (NSLoggerAdapter)
				StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(Node.class.getName());
		adapter.logger = new NSLogger(mPresenter.getApplicationContext());
		adapter.logger.connect("192.168.43.155", 50000);

		Underdark.configureLogging(true);*/
	}

	public void start()
	{
		if(running)
			return;

		running = true;
		transport.start();
	}

	public void stop()
	{
		if(!running)
			return;

		running = false;
		transport.stop();
	}

	public ArrayList<Link> getLinks()
	{
		return links;
	}

	public int getFramesCount()
	{
		return framesCount;
	}

	public void broadcastFrame(byte[] frameData)
	{
		if(links.isEmpty())
			return;

		//++framesCount;
		//mPresenter.refreshFrames();

		for(Link link : links) {
			link.sendFrame(frameData);

		}
	}

	//region TransportListener
	@Override
	public void transportNeedsActivity(Transport transport, ActivityCallback callback)
	{
		callback.accept(mPresenter.getActivity());
	}

	@Override
	public void transportLinkConnected(Transport transport, Link link)
	{
		links.add(link);
		mPresenter.refreshPeers();
	}

	@Override
	public void transportLinkDisconnected(Transport transport, Link link)
	{
		mPresenter.onNodeDisconnected(link.getNodeId());

		links.remove(link);
		mPresenter.refreshPeers();

		if(links.isEmpty())
		{
			framesCount = 0;
			mPresenter.refreshFrames();
		}
	}

	@Override
	public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] frameData)
	{

		String toString = new String(frameData);
		System.out.println("Received data : " + toString);

		Gson gson = new Gson();
		Messages messages = gson.fromJson(toString,Messages.class);

		if(messages == null && messages.getItems()==  null )
			return;

		Message message = messages.getItems();

		if(!message.getDestinationId().equals("Master"))
			return;

		if(String.valueOf(message.getMessageType()).equals(String.valueOf(Message.MessageType.ORDER.getCode())))
		{
			mPresenter.onOrderReceived(link.getNodeId());
		}
		if(String.valueOf(message.getMessageType()).equals(String.valueOf(Message.MessageType.MAKE_PAYMENT.getCode())))
		{
			System.out.println("payment request received");
			mPresenter.onPaymentReceived(link.getNodeId());
		}

	}
	//endregion
} // Node
