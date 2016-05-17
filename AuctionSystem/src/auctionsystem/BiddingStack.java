package auctionsystem;

import java.util.ArrayList;

/**
 *
 * @author ASUSPC
 * @param <E>
 * @param <F>
 */
public class BiddingStack<E,F,T>{
    ArrayList<E>bidPriceList=new ArrayList<>();
    ArrayList<F>bidderList=new ArrayList<>();
    ArrayList<T>bidTimeList = new ArrayList<>();
    
    public BiddingStack(){}
    
    public int getSize(ArrayList list){ //change from getSize() to getSize(ArrayList list)
        return list.size();
    }
    
    public Triplet<E,F,T> peek(){
        E bidPrice = bidPriceList.get(getSize(bidPriceList)-1);
        F bidder = bidderList.get(getSize(bidderList)-1);
        T bidTime = bidTimeList.get(getSize(bidTimeList)-1);
        return new Triplet<>(bidPrice,bidder,bidTime);
    } 
    
    public Triplet<E,F,T> pop(){
        E bidPrice = bidPriceList.get(getSize(bidPriceList)-1);
        F bidder = bidderList.get(getSize(bidderList)-1);
        T bidTime = bidTimeList.get(getSize(bidTimeList)-1);
        bidPriceList.remove(getSize(bidPriceList)-1);
        bidderList.remove(getSize(bidderList)-1);
        bidTimeList.remove(getSize(bidTimeList)-1);
        return new Triplet<>(bidPrice,bidder,bidTime);
    }
    
    public void push(E e, F f, T t){
        bidPriceList.add(e);
        bidderList.add(f);
        bidTimeList.add(t);
    }
    
    public boolean isEmpty(ArrayList list){ //change from isEmpty() to isEmpty(ArrayList list)
        return list.isEmpty();
    }
    
    @Override
    public String toString(){
        return "Bid Price List: "+bidPriceList.toString()+"\nBidder List: "+bidderList.toString() + "\nBidding Time List: " + bidTimeList.toString();
    }
    
    
}