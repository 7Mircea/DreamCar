package service;

import data.dao.AuctionDao;
import data.model.Auction;
import data.model.Bid;
import util.BidComparator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static util.Constants.DATE_FORMAT;

@Stateless
public class HelloService {

    @Inject
    private AuctionDao auctionDao;

    /**
     * @param bidList the list of bids
     * @return email for the most affordable bid. If there's no such bid, empty string is returned
     */
    public static String getMailForMostAffordableBid(List<Bid> bidList) {
        Optional<Bid> bestBid = bidList.stream().min(BidComparator::compare);
        if (bestBid.isPresent())
            return bestBid.get().getUser().getEmail();
        return "";
    }

    public String sayHello() {
        return "Hello from control: " + System.currentTimeMillis();
    }

    public void checkAuctions() {
        List<Auction> auctions = auctionDao.getAllAuctions();
        auctions.stream().parallel().filter(Auction::getStatus).forEach(this::forEachAuction);
    }

    public void forEachAuction(Auction auction) {
        String dueDate = auction.getDueDate();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date;
        Date currentDate = new Date();
        try {
            date = formatter.parse(dueDate);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<Bid> bidList = auction.getBidList();
        if (date != null) {
            if (currentDate.after(date)||isABBidThatMatchesTheTargetPrice(bidList, auction.getMaxWantedPrice())) {
                auction.setStatus(Boolean.FALSE);

                String mail = getMailForMostAffordableBid(bidList);
                if (!mail.isEmpty())
                    sendMail(mail);
            }
        }

    }

    private boolean isABBidThatMatchesTheTargetPrice(List<Bid> bidList, Integer maxPrice) {
        Optional<Bid> minBid = bidList.stream().filter(bid -> bid.getPrice() <= maxPrice).min(BidComparator::compare);
        return minBid.isPresent();
    }

    private void sendMail(String mail) {
        System.out.println("The mail has been sent to " + mail);
    }
}
