package biddingstack;
import java.util.Date;

/**
 *
 * @author ASUSPC
 */
public class BlindAuction extends Auction {
    private int bidCounter=1;
    
    public BlindAuction(double startPrice, Date startTime, Date endTime){
        super(startPrice, startTime, endTime);
    }
    
    public BlindAuction(double startPrice, BiddingStack<Double,Bidder> bidStack, Date startTime, Date endTime){
	super( startPrice, bidStack, startTime, endTime);
    }
    
    @Override
    public void pushBid(Double bid, Date currentTime, Bidder bidder){
        if(bidCounter==1){
            if(currentTime.equals(endTime)){
                bidStack.push(bid, bidder);
            }
        }
    }
}
