package com.hack.headout.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hack.headout.R;
import com.hack.headout.fragments.dummy.DummyContent;
import com.hack.headout.interfaces.ViewInteractionListener;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;

    private Page currentPage = Page.SERVICE_PENDING;

    private Context mContext;

    private ViewInteractionListener mListener;


    public MyItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, Page currenPage, Context context, ViewInteractionListener listener) {
        mValues = items;
        mListener = listener;

        if (currenPage != null)
            this.currentPage = currenPage;
        this.mContext = context;


    }

    @Override
    public int getItemViewType(int position) {

        if (getItemType(position) == ItemType.ORDER_HEADER)
            return ItemType.ORDER_HEADER.getCode();
        else if (getItemType(position) == ItemType.ORDER_SUMMARY)
        {
            return ItemType.ORDER_SUMMARY.getCode();
        }
        else{
            return ItemType.ORDER_ITEMS.getCode();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == ItemType.ORDER_HEADER.getCode()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_header, parent, false);
            return new OrderHeaderViewHolder(view);
        } else if (viewType == ItemType.ORDER_SUMMARY.getCode()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_summary, parent, false);
            return new OrderSummaryViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_items, parent, false);
            return new ItemsViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemsViewHolder) {
            ItemsViewHolder holders = (ItemsViewHolder) holder;
            holders.mItem = mValues.get(position);
            holders.udpateLayoutStatus();
            holders.itemName.setText(holders.mItem.itemName);
            holders.price.setText(mContext.getResources().getString(R.string.item_price, holders.mItem.itemPrice) );

        } else if (holder instanceof OrderHeaderViewHolder) {
            OrderHeaderViewHolder holders = (OrderHeaderViewHolder) holder;
            holders.mItem = mValues.get(position);
        } else if (holder instanceof OrderSummaryViewHolder) {
            OrderSummaryViewHolder holders = (OrderSummaryViewHolder) holder;
            holders.mItem = mValues.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ItemType getItemType(int position) {
        return mValues.get(position).type;
    }

    public enum ItemType {
        ORDER_HEADER(0), ORDER_ITEMS(1), ORDER_SUMMARY(2);

        private int code;

        ItemType(int code) {
            this.code = code;
        }

        int getCode() {
            return code;
        }
    }


    public enum Page {
        SERVICE_PENDING, PAYMENT_PENDING, ORDER_COMPLETE
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder {

        public TextView totalPrice;
        public RelativeLayout confirm_button;
        public RelativeLayout order_completed_area;
        public DummyContent.DummyItem mItem;
        public TextView confirm_button_text;

        public OrderSummaryViewHolder(View view) {
            super(view);
            totalPrice = (TextView) view.findViewById(R.id.totalPrice);
            confirm_button = (RelativeLayout) view.findViewById(R.id.confirm_button);
            order_completed_area = (RelativeLayout) view.findViewById(R.id.order_completed_area);
            confirm_button_text = (TextView) view.findViewById(R.id.confirm_button_text);
            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (currentPage) {
                        case SERVICE_PENDING:
                            if(mListener != null)
                                mListener.onInvoiceSent(mItem.orderId, mItem.nodeId);
                            break;
                        case PAYMENT_PENDING:
                            break;
                    }
                }
            });

            updateLayout();
        }

        public void updateLayout() {
            if (currentPage == null)
                return;
            switch (currentPage) {
                case SERVICE_PENDING:
                    order_completed_area.setVisibility(View.GONE);
                    confirm_button.setVisibility(View.VISIBLE);
                    confirm_button_text.setText(mContext.getString(R.string.send_invoice_btn_text));
                    break;
                case PAYMENT_PENDING:
                    order_completed_area.setVisibility(View.GONE);
                    confirm_button.setVisibility(View.VISIBLE);
                    confirm_button_text.setText(mContext.getString(R.string.send_reminder_btn_text));
                    break;
                case ORDER_COMPLETE:
                    order_completed_area.setVisibility(View.VISIBLE);
                    confirm_button.setVisibility(View.GONE);
                    break;
            }

        }
    }

    public class OrderHeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView ordeNumber;
        public TextView customerName;
        public TextView seatNumber;
        public DummyContent.DummyItem mItem;

        public OrderHeaderViewHolder(View view) {
            super(view);

            ordeNumber = (TextView) view.findViewById(R.id.orderNumber);
            customerName = (TextView) view.findViewById(R.id.customerName);
            seatNumber = (TextView) view.findViewById(R.id.seatNumber);
        }
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView itemName;
        public final TextView itemQty;
        public final TextView price;
        public final ImageView delayed;
        public final ImageView complete;
        public final LinearLayout serve_button_area;
        public final LinearLayout served_button_area;
        public final LinearLayout somedelay_button_area;
        public final LinearLayout service_area;

        public DummyContent.DummyItem mItem;

        public ItemsViewHolder(View view) {
            super(view);
            mView = view;
            itemName = (TextView) view.findViewById(R.id.itemName);
            itemQty = (TextView) view.findViewById(R.id.itemqty);
            price = (TextView) view.findViewById(R.id.itemprice);

            delayed = (ImageView) view.findViewById(R.id.delay_image);
            complete = (ImageView) view.findViewById(R.id.completed_image);
            serve_button_area = (LinearLayout) view.findViewById(R.id.serve_button_area);
            served_button_area = (LinearLayout) view.findViewById(R.id.served_button_area);
            somedelay_button_area = (LinearLayout) view.findViewById(R.id.somedelay_button_area);
            service_area = (LinearLayout)view.findViewById(R.id.service_area);

            delayed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mItem.status = DummyContent.ItemStatus.SERVICE_DELAYED;
                    udpateLayoutStatus();
                    if(mListener != null)
                        mListener.onItemDelayed(mItem.id,mItem.nodeId);
                }
            });

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItem.status = DummyContent.ItemStatus.SERVED;
                    udpateLayoutStatus();
                    if(mListener != null)
                        mListener.onItemServed(mItem.id,mItem.nodeId);
                }
            });


            udpateLayoutStatus();
        }

        public void udpateLayoutStatus() {

            if (mItem == null || mItem.status == null)
                return;
            switch (mItem.status) {
                case SERVICE_PENDING:
                    service_area.setVisibility(View.VISIBLE);
                    somedelay_button_area.setVisibility(View.GONE);
                    served_button_area.setVisibility(View.GONE);
                    serve_button_area.setVisibility(View.VISIBLE);
                    break;
                case SERVICE_DELAYED:
                    service_area.setVisibility(View.VISIBLE);
                    somedelay_button_area.setVisibility(View.VISIBLE);
                    served_button_area.setVisibility(View.GONE);
                    serve_button_area.setVisibility(View.GONE);
                    break;
                case SERVED:
                    service_area.setVisibility(View.VISIBLE);
                    somedelay_button_area.setVisibility(View.GONE);
                    served_button_area.setVisibility(View.VISIBLE);
                    serve_button_area.setVisibility(View.GONE);
                    break;
                case PAYMENT_PENDING:
                case PAID:
                case COMPLETED:
                    service_area.setVisibility(View.GONE);
                    somedelay_button_area.setVisibility(View.GONE);
                    served_button_area.setVisibility(View.GONE);
                    serve_button_area.setVisibility(View.GONE);
                    break;
            }


        }


    }
}
