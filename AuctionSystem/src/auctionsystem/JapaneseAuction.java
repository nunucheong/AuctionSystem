package auctionsystem;
import java.util.Date;

/**
 *
 * @author ASUSPC
 */
public class JapaneseAuction extends Auction {
    private double minExceed;
    private boolean dropOut=false;
    
    public JapaneseAuction(){}
    
    public JapaneseAuction(double startPrice, Date startTime, Date endTime, double minExceed){
        super(startPrice, startTime, endTime);
        this.minExceed = minExceed;
        AuctionType = JAPANESE_AUCTION;
    }

    public JapaneseAuction(double startPrice, BiddingStack<Double,Bidder, Date> bidStack, Date startTime, Date endTime, double minExceed){
	super( startPrice, bidStack, startTime, endTime);
	this.minExceed = minExceed;
        AuctionType = JAPANESE_AUCTION;
    }
    
    public void setMinExceed(double min){
        this.minExceed=min;
    }
    
    public double getMinExceed(){
        return this.minExceed;
    }
    
    public void setDropOut(){
        dropOut=true;
    }
    
    @Override
    public void pushBid(Double bid, Date currentTime, Bidder bidder){
        if(currentTime.before(endTime)){
                if(currentTime.after(startTime)){
                    if(bid.compareTo(getHighestBid())>0){
                        if((bid-getHighestBid())>=minExceed){
                            bidStack.push(bid, bidder, currentTime);
                            bidder.addBidFrequency();
                        }
                        else
                            System.out.println("You bid does not reach the minimum amount by which the next bid must exceed the current highest bid.");
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
