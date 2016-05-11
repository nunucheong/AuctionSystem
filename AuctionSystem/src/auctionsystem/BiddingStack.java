package biddingstack;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author ASUSPC
 * @param <E>
 * @param <F>
 */
public class BiddingStack<E,F>{
    ArrayList<E>bidPriceList=new ArrayList<>();
    ArrayList<F>bidderList=new ArrayList<>();
    
    public BiddingStack(){}
    
    public int getSize(ArrayList list){ //change from getSize() to getSize(ArrayList list)
        return list.size();
    }
    
    public Pair<E,F> peek(){
        E bidPrice = bidPriceList.get(getSize(bidPriceList)-1);
        F bidder = bidderList.get(getSize(bidderList)-1);
        return new Pair<>(bidPrice,bidder);
    } 
    
    public Pair<E,F> pop(){
        E bidPrice = bidPriceList.get(getSize(bidPriceList)-1);
        F bidder = bidderList.get(getSize(bidderList)-1);
        bidPriceList.remove(getSize(bidPriceList)-1);
        bidderList.remove(getSize(bidderList)-1);
        return new Pair<>(bidPrice,bidder);
    }
    
    public void push(E e, F f){
        bidPriceList.add(e);
        bidderList.add(f);
    }
    
    public boolean isEmpty(ArrayList list){ //change from isEmpty() to isEmpty(ArrayList list)
        return list.isEmpty();
    }
    
    @Override
    public String toString(){
        return "Bid Price List: "+bidPriceList.toString()+"\nBidder List: "+bidderList.toString();
    }
    
    
}