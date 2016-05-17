package auctionsystem;
import java.util.Date;

/**
 *
 * @author ASUSPC
 */
public class ReserveAuction extends Auction {
    private double reservePrice;

    public ReserveAuction(){}
    
    public ReserveAuction(double startPrice, Date startTime, Date endTime, double reservePrice){
        super(startPrice, startTime, endTime);
        this.reservePrice = reservePrice;
        AuctionType = RESERVE_AUCTION;
    }
    
     public ReserveAuction(double startPrice, BiddingStack<Double,Bidder, Date> bidStack, Date startTime, Date endTime, double reservePrice){
	super( startPrice, bidStack, startTime, endTime);
	this.reservePrice = reservePrice;
        AuctionType = RESERVE_AUCTION;
    }
    
    public void setReservePrice(double reserve){
        this.reservePrice = reserve;
    }
    
    public double getReservePrice(){
        return this.reservePrice;
    }
    
    public boolean checkPrice(Date currentTime){
        if(bidStack.peek().getKey()>this.reservePrice)
                return true;
        
        else return false;
    }

}
