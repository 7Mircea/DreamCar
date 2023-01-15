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
        BidComparator comparator = new BidComparator();

        Optional<Bid> bestBid = bidList.stream().min(comparator);
        if (bestBid.isPresent())
            return bestBid.get().getUser().getEmail();
        return "";
    }

    public String sayHello() {
        return "Hello from control: " + System.currentTimeMillis();
    }

    public void checkExpiredAuctions() {
        List<Auction> auctions = auctionDao.getAllAuctions();
        auctions.stream().parallel().forEach(this::forEachAuction);
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
        if (date != null) {
            if (currentDate.before(date)) {
                auction.setStatus(Boolean.FALSE);

                String mail = getMailForMostAffordableBid(auction.getBidList());
                if (!mail.isEmpty())
                    sendMail(mail);
            }
        }

    }

    private void sendMail(String mail) {
        System.out.println("The mail has been sent to " + mail);
    }
}
