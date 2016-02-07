package com.hack.headout.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hack.headout.R;
import com.hack.headout.fragments.dummy.DummyContent;
import com.hack.headout.interfaces.IMainActivity;
import com.hack.headout.interfaces.ViewInteractionListener;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PaymentPendingFragment extends RootFragment implements ViewInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private View mMainView;

    private DummyContent content = new DummyContent(DummyContent.ItemStatus.PAYMENT_PENDING);
    private MyItemRecyclerViewAdapter mAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PaymentPendingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PaymentPendingFragment newInstance(int columnCount) {
        PaymentPendingFragment fragment = new PaymentPendingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mMainView == null) {
            mMainView = inflater.inflate(R.layout.fragment_item_list, container, false);

            // Set the adapter
            if (mMainView instanceof RecyclerView) {
                Context context = mMainView.getContext();
                RecyclerView recyclerView = (RecyclerView) mMainView;
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                mAdapter = new MyItemRecyclerViewAdapter(content.ITEMS, MyItemRecyclerViewAdapter.Page.PAYMENT_PENDING, getContext(), this);
                recyclerView.setAdapter(mAdapter);
            }
        } else {
            // Cached rootView need to determine whether the parent has been added, if there is
            // parent needs to be deleted from parent, or else will happen this rootview has a parent error.
            ViewGroup parent = (ViewGroup) mMainView.getParent();
            if (parent != null) {
                parent.removeView(mMainView);
            }
        }
        return mMainView;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onInvoiceSent(String orderId,long nodeId) {
        content.addDummyItems(nodeId);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemServed(String itemId, long nodeId) {

    }

    @Override
    public void onItemDelayed(String itemId, long nodeId) {

    }

    @Override
    public boolean onNodeDisconnected(long nodeId) {
        boolean result = content.removeItems(nodeId);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public void onPaymentReceived(long nodeId)
    {
        content.removeItems(nodeId);
        mAdapter.notifyDataSetChanged();
        ((IMainActivity) getActivity()).onNotificationAnimateIndicator(2);
    }
}
