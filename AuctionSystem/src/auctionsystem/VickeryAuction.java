package auctionsystem;

import java.util.Date;
import javafx.util.Pair;

/**
 *
 * @author ASUSPC
 */
public class VickeryAuction extends Auction {
    private int bidCounter=1;
    
    public VickeryAuction(){}
    
    public VickeryAuction(double startPrice, Date startTime, Date endTime){
        super(startPrice, startTime, endTime);
    }
    
    public VickeryAuction(double startPrice, BiddingStack<Double,Bidder> bidStack, Date startTime, Date endTime){
	super( startPrice, bidStack, startTime, endTime);
    }
    
    public void setBidCount(int bidCounter){
        this.bidCounter = bidCounter;
    }
    
    @Override
    public void pushBid(Double bid, Date currentTime, Bidder bidder){
        if(bidCounter==1){
            if(currentTime.equals(endTime)){
                bidStack.push(bid, bidder);
            }
        }
    }
    
    public double getSecondHighestBid(){
        Pair<Double, Bidder> hold = this.bidStack.pop();
        Pair<Double, Bidder> secondBid = this.bidStack.peek();
        this.bidStack.push(hold.getKey(), hold.getValue());
        return secondBid.getKey();
    }
    
}
