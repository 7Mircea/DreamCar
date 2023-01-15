package util;

import data.model.Bid;

import java.util.Comparator;

public class BidComparator  {

    public static int compare(Bid o1, Bid o2) {
        return o1.getPrice() - o2.getPrice();
    }
}
