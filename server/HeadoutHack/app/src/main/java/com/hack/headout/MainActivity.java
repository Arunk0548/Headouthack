package com.hack.headout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hack.headout.fragments.OrderCompletedFragment;
import com.hack.headout.fragments.PaymentPendingFragment;
import com.hack.headout.fragments.RootFragment;
import com.hack.headout.fragments.ServicePendingFragment;
import com.hack.headout.interfaces.IMainActivity;
import com.hack.headout.presenters.MainActivityPresenter;

public class MainActivity extends FragmentActivity implements IMainActivity, RootFragment.RootFragmentListener {

    private static int TOTAL_ORDER_PENDING = 0;
    private static int TOTAL_PAYMENT_PENDING = 0;
    private static int TOTAL_ORDER_COMPLETED = 0;
    private final String TAB_SERVICE_PENDING = "TAB_SERVICE_PENDING";
    private final String TAB_PAYMENT_PENDING = "TAB_PAYMENT_PENDING";
    private final String TAB_COMPLETED_ORDERS = "TAB_COMPLETED_ORDERS";
    private FragmentTabHost mTabHost;
    private String[] tabs_text;

    private MainActivityPresenter mPresenter;

    private TextView total_guests_connected;
    private TextView total_order_received;
    private LinearLayout announcement;
    private LinearLayout order_status_area;
    private LinearLayout logout_area;
    private LinearLayout announcement_content_area;
    private ImageView deal3_imageview;

    private ServicePendingFragment mServicePendingFragment;
    private PaymentPendingFragment mPaymentPendingFragment;
    private OrderCompletedFragment mOrderCompletedFragment;

    private long pendingNodeId;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.announcement_area:
                    announcement_content_area.setVisibility(View.VISIBLE);
                    mTabHost.setVisibility(View.GONE);
                    break;
                case R.id.order_status_area:
                    announcement_content_area.setVisibility(View.GONE);
                    mTabHost.setVisibility(View.VISIBLE);
                    break;
                case R.id.logout_area:
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.deal3_imageview:
                    mPresenter.onOfferAnnounce();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabs_text = getResources().getStringArray(R.array.tabs_text);
        total_guests_connected = (TextView) findViewById(R.id.total_guests_connected);
        total_order_received = (TextView) findViewById(R.id.total_order_received);
        announcement = (LinearLayout) findViewById(R.id.announcement_area);
        order_status_area = (LinearLayout) findViewById(R.id.order_status_area);
        logout_area = (LinearLayout) findViewById(R.id.logout_area);
        announcement_content_area = (LinearLayout) findViewById(R.id.announcement_content_area);
        deal3_imageview = (ImageView) (ImageView) findViewById(R.id.deal3_imageview);

        announcement_content_area.setVisibility(View.GONE);

        announcement.setOnClickListener(onClickListener);
        order_status_area.setOnClickListener(onClickListener);
        logout_area.setOnClickListener(onClickListener);
        deal3_imageview.setOnClickListener(onClickListener);


        setUpTabs();

        mPresenter = new MainActivityPresenter(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.onStop();

    }

    private void setUpTabs() {
        //setup tab
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


        TabHost.TabSpec tab1 = mTabHost.newTabSpec(TAB_SERVICE_PENDING);
        TabHost.TabSpec tab2 = mTabHost.newTabSpec(TAB_PAYMENT_PENDING);
        TabHost.TabSpec tab3 = mTabHost.newTabSpec(TAB_COMPLETED_ORDERS);


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        View view = createTabView(0);
        tab1.setIndicator(view);

        view = createTabView(1);
        tab2.setIndicator(view);

        view = createTabView(2);
        tab3.setIndicator(view);


        /** Add the tabs  to the TabHost to display. */
        mTabHost.addTab(tab1, ServicePendingFragment.class, null);
        mTabHost.addTab(tab2, PaymentPendingFragment.class, null);
        mTabHost.addTab(tab3, OrderCompletedFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = mTabHost.getCurrentTab();

                switch (tab) {
                    case 0:
                        applyTabBgRes(R.drawable.servicepending_tab_indicator_holo);
                        break;
                    case 1:
                        applyTabBgRes(R.drawable.paymentpending_tab_indicator_holo);
                        break;
                    case 2:
                        applyTabBgRes(R.drawable.ordercompleted_tab_indicator_holo);
                        break;
                }

            }
        });

        applyTabBgRes(R.drawable.servicepending_tab_indicator_holo);
    }

    private void applyTabBgRes(int resourceId) {
        TabWidget widget = mTabHost.getTabWidget();
        for (int tabIndex = 0; tabIndex < widget.getChildCount(); tabIndex++) {
            widget.getChildAt(tabIndex).setBackgroundResource(resourceId);
        }
    }

    private void updateTotalCount() {
        TabWidget widget = mTabHost.getTabWidget();

        for (int tabIndex = 0; tabIndex < widget.getChildCount(); tabIndex++) {
            {
                View view = widget.getChildAt(tabIndex);

                TextView itemCount = (TextView) view.findViewById(R.id.item_count);
                switch (tabIndex) {
                    case 0:
                        itemCount.setText("" + TOTAL_ORDER_PENDING);
                        break;
                    case 1:
                        itemCount.setText("" + TOTAL_PAYMENT_PENDING);
                        break;
                    case 2:
                        itemCount.setText("" + TOTAL_ORDER_COMPLETED);
                        break;
                }
            }
        }
    }

    @Override
    public void onNotificationAnimateIndicator(int tabIndex) {
        TabWidget widget = mTabHost.getTabWidget();
        if (tabIndex >= widget.getChildCount())
            return;

        View view = widget.getChildAt(tabIndex);

        ImageView imageView = (ImageView) view.findViewById(R.id.transition_indicator_image);

        animateView(imageView);

    }

    @Override
    public void onPaymentReceived(long nodeId) {

        if (mOrderCompletedFragment != null) {
            mOrderCompletedFragment.onPaymentReceived(nodeId);
        } else {
            pendingNodeId = nodeId;
            mTabHost.setCurrentTab(2);
        }
        if(mPaymentPendingFragment != null)
        {
            mPaymentPendingFragment.onPaymentReceived(nodeId);
        }

        TOTAL_PAYMENT_PENDING--;
        if(TOTAL_PAYMENT_PENDING < 0)
            TOTAL_PAYMENT_PENDING = 0;
        TOTAL_ORDER_COMPLETED++;
        updateTotalCount();
        setTotalOrderReceived();

    }

    @Override
    public void onInvoiceSent(String orderId, long nodeId) {

        if (mPaymentPendingFragment != null) {

            mPaymentPendingFragment.onInvoiceSent(orderId, nodeId);
        } else {
            pendingNodeId = nodeId;
            mTabHost.setCurrentTab(1);


        }
        TOTAL_ORDER_PENDING--;
        if(TOTAL_ORDER_PENDING < 0)
            TOTAL_ORDER_PENDING = 0;
        TOTAL_PAYMENT_PENDING++;
        updateTotalCount();

        mPresenter.onInvoiceSent(orderId,nodeId);
    }

    private void animateView(final View view) {
        if (view == null)
            return;

        view.setVisibility(View.VISIBLE);
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(300);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(8);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
        view.startAnimation(mAnimation);

    }


    private View createTabView(int tabIndex) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_header_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.tab_contenttext_txtView);
        tv.setText(tabs_text[tabIndex]);
        TextView itemCount = (TextView) view.findViewById(R.id.item_count);

        switch (tabIndex) {
            case 0:
                setSateListTextColor(itemCount, R.color.item_count_service_pending);
                break;
            case 1:
                setSateListTextColor(itemCount, R.color.item_count_payment_pending);
                break;
            case 2:
                setSateListTextColor(itemCount, R.color.item_count_order_complete);
                break;
        }

        return view;
    }

    private void setSateListTextColor(TextView txt, int resouceId) {
        XmlResourceParser xrp = getResources().getXml(resouceId);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            txt.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    public void setTotalOrderReceived()
    {
        if(TOTAL_ORDER_COMPLETED <10)
            total_order_received.setText("0" + TOTAL_ORDER_COMPLETED);
        else
            total_order_received.setText( TOTAL_ORDER_COMPLETED);
    }

    @Override
    public void onPeerRefresh(int peersCount) {

        if (peersCount < 10)
            total_guests_connected.setText("0" + peersCount);
        else
            total_guests_connected.setText(peersCount);
    }

    @Override
    public void onOrderReceived(long nodeId) {
        if (mServicePendingFragment != null)
            mServicePendingFragment.onOrderReceived(nodeId);
        TOTAL_ORDER_PENDING++;
        updateTotalCount();

    }

    @Override
    public void onItemServed(String itemId, long nodeId) {
        mPresenter.onItemServed(itemId, nodeId);
    }

    @Override
    public void onItemDelayed(String itemId, long nodeId) {
        mPresenter.onItemDelayed(itemId, nodeId);
    }

    @Override
    public boolean onNodeDisconnected(long nodeId) {
        if(mServicePendingFragment != null) {
            if (mServicePendingFragment.onNodeDisconnected(nodeId)) {
                TOTAL_ORDER_PENDING--;
                if (TOTAL_ORDER_PENDING < 0)
                    TOTAL_ORDER_PENDING = 0;
            }
        }
        if(mPaymentPendingFragment!= null) {
            if (mPaymentPendingFragment.onNodeDisconnected(nodeId)) {
                TOTAL_PAYMENT_PENDING--;
                if(TOTAL_PAYMENT_PENDING < 0)
                    TOTAL_PAYMENT_PENDING = 0;
            }
        }

        updateTotalCount();
        return false;
    }

    @Override
    public void onFragmentAttached(RootFragment fragment) {

        if (fragment instanceof ServicePendingFragment) {
            if (mServicePendingFragment != null) {

            } else
                mServicePendingFragment = (ServicePendingFragment) fragment;
        } else if (fragment instanceof PaymentPendingFragment) {
            if (mPaymentPendingFragment != null) {


            } else {
                mPaymentPendingFragment = (PaymentPendingFragment) fragment;

            }
        } else if (fragment instanceof OrderCompletedFragment) {
            if (mOrderCompletedFragment != null) {

            } else {
                mOrderCompletedFragment = (OrderCompletedFragment) fragment;


            }
        }
    }

    @Override
    public void onFragmentViewCreated(RootFragment fragment) {
        if (fragment instanceof PaymentPendingFragment) {
            if (pendingNodeId > 0)
                mPaymentPendingFragment.onInvoiceSent("", pendingNodeId);
            pendingNodeId = 0;
        } else if (fragment instanceof OrderCompletedFragment) {
            if (pendingNodeId > 0)
                mOrderCompletedFragment.onPaymentReceived(pendingNodeId);
            pendingNodeId = 0;
        }
    }
}
