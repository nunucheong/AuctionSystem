package auctionsystem;
import java.util.Date;
import javafx.util.Pair;

/**
 *
 * @author ASUSPC
 */
public class Auction {
    private double startPrice;
    private double highestBid;
    protected BiddingStack<Double,Bidder> bidStack;
    protected String AuctionType;
    Date startTime;
    Date endTime;
    protected final String ENGLISH_AUCTION = "EnglishAuction";
    protected final String BLIND_AUCTION = "BlindAuction";
    protected final String JAPANESE_AUCTION = "JapaneseAuction";
    protected final String RESERVE_AUCTION = "ReserveAuction";
    protected final String VICKERY_AUCTION = "VickeryAuction";

	public Auction(){
            //not sure about the constructer yet
	}
        
	//constructor 1 : for creation of auction
	public Auction(double startPrice, Date startTime, Date endTime){
            this.startPrice = startPrice;
            this.startTime = startTime;
            this.endTime = endTime;
	}

	//constructor 2 : for reading from text file
	public Auction(double startPrice, BiddingStack<Double,Bidder> bidStack, Date startTime, Date endTime){
            this.startPrice = startPrice;
            this.bidStack = bidStack;
            this.startTime = startTime;
            this.endTime = endTime;
	}
        
	public void setStartPrice(double price){
            this.startPrice = price;
	}

	public double getStartPrice(){
            return this.startPrice;
	}

        public double getHighestBid(){
            Pair<Double,Bidder> hold = this.bidStack.peek();
            return hold.getKey();
	}

	public void pushBid(Double bid, Date currentTime, Bidder bidder){
            if(currentTime.before(endTime)){
                if(currentTime.after(startTime)){
                    if(bid.compareTo(getHighestBid())>0){
                        bidStack.push(bid, bidder);
                    }
                    else
                        System.out.println("Your bidding price is lower than previous bid. ");
                }
                else
                    System.out.println("The auction hasn't started yet. ");
            }
            else
                System.out.println("The auction is ended. ");
	}
}
