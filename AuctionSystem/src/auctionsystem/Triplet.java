/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionsystem;

/**
 *
 * @author User
 */
public class Triplet<E,F,T> {
    private E key;
    private F value;
    private T thirdVal;
    
    public Triplet(E e, F f, T t){
        this.key = e;
        this.value = f;
        this.thirdVal = t;
    }
    
    public void setKey(E e){
        this.key = e;
    }
    
    public void setValue(F f){
        this.value = f;
    }
    
    public void setThird(T t){
        this.thirdVal = t;
    }
    
    public E getKey(){
        return key;
    }
    
    public F getValue(){
        return value;
    }
    
    public T getThird(){
        return thirdVal;
    }
}
