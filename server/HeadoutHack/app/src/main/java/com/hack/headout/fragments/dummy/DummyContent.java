package com.hack.headout.fragments.dummy;

import com.hack.headout.fragments.MyItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private static final int COUNT = 6;
    /**
     * An array of sample (dummy) items.
     */
    public final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    private ItemStatus mStatus;
    private String orderId;
    //private long nodeId;

    public DummyContent(ItemStatus status) {
        this.mStatus = status;

    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public void addDummyItems(long nodeId) {
        long randNm;
        do {
            randNm = new Random().nextLong();
        } while (randNm == 0);

        if (randNm < 0)
            randNm = -randNm;
        orderId = Long.toString(randNm);
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createDummyItem(i, nodeId));
        }
    }

    public void removeItems(String orderId) {

        for (Iterator<DummyItem> iterator = ITEMS.iterator(); iterator.hasNext(); ) {
            DummyItem item = iterator.next();
            if (item.orderId.equals(orderId))
            {
                iterator.remove();
            }
        }

    }

    public boolean removeItems(long nodeId) {

        boolean isFound = false;
        for (Iterator<DummyItem> iterator = ITEMS.iterator(); iterator.hasNext(); ) {
            DummyItem item = iterator.next();
            if (item.nodeId == nodeId)
            {
                iterator.remove();
                isFound = true;
            }
        }
        return isFound;
    }

    private void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    private DummyItem createDummyItem(int position, long nodeId) {
        float price = 0.0f;
        String itemName = "Green Tea";
        switch(position){
            case 1:
                price = 1.50f;
                itemName = "Green Tea";
                break;
            case 2:
                price = 1.25f;
                itemName = "Espresso";
                break;
            case 3:
                itemName = "Hot Chocolate";
                price = 1.25f;
                break;
            case 4:
                itemName = "Cappuccino";
                price = 1.25f;
                break;
        }
        return new DummyItem(String.valueOf(position),itemName, makeDetails(position), orderId,price, nodeId);
    }

    public enum ItemStatus {
        SERVICE_PENDING(0), SERVED(1), SERVICE_DELAYED(2), PAYMENT_PENDING(3), PAID(4), COMPLETED(5);

        private int code;

        ItemStatus(int code) {
            this.code = code;
        }

        int getCode() {
            return code;
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public class DummyItem {

        public final String orderId;
        public final String id;
        public final String itemName;
        public final String details;
        public final int itemQty = 1;
        public final float itemPrice;
        public ItemStatus status;
        public long nodeId;
        public MyItemRecyclerViewAdapter.ItemType type = MyItemRecyclerViewAdapter.ItemType.ORDER_ITEMS;

        public DummyItem(String id, String itemName, String details, String orderId, float itemPrice, long nodeId) {
            this.id = id;
            this.itemName = itemName;
            this.details = details;
            this.status = mStatus;
            this.orderId = orderId;
            this.itemPrice = itemPrice;
            this.nodeId = nodeId;

            if (id.equals("0") || id.equals("6"))
                this.type = MyItemRecyclerViewAdapter.ItemType.ORDER_HEADER;
            if (id.equals("5") || id.equals("11"))
                this.type = MyItemRecyclerViewAdapter.ItemType.ORDER_SUMMARY;
        }


    }
}
