package auctionsystem;

import javafx.util.Pair;

public class ItemLinkedList<T,E> {
    private Node head;
    private Node tail;
    private int entry = 0;
    
    public ItemLinkedList(){}
    
    public void addFirst(T date, E item){
        Node newNode = new Node(date,item,head);
        head = newNode;
        entry++;
        if (tail == null)
            tail = head;
    }
    
    public int getEntry(){
        return entry;
    }
    
    public void addLast(T date, E item){
        Node newNode = new Node(date, item);
        if (tail == null){
            tail = head = newNode;
        } 
        else{
            tail.next = newNode;
            tail = newNode;
            entry++;
        }
    }
    
    //this needs improvisation
    public void add(int index, T date, E item){
        if(index == 0){
            addFirst(date,item);
        }
        else if (index >= entry){
            addLast(date,item);
        }
        else{
            Node current = head;
            for (int i = 1; i < index; i++){
                current = current.next;
            }
            Node temp = current.next;
            current.next = new Node(date, item);
            current.next.next = temp;
            entry++;
        }
    }
    
    public Pair<T,E> removeFirst(){
        if (head == null){
            return null;
        }
        else {
            Node temp = head;
            head = head.next;
            entry--;
            return new Pair<>(temp.date,temp.item);
        }
    }
    
    public Pair<T,E> removeLast(){
        if (tail == null){
            return null;
        }
        else {
            Node current = head;
            for (int i = 1; i < entry-1; i++)
                current = current.next;
            Node temp = current.next;
            current.next = null;
            tail = current;
            entry--;
            return new Pair<>(temp.date,temp.item);
        }
    }
    
    public Pair<T,E> remove(int index){
        Pair<T,E> hold ;
        if (index <= 0 || index >= entry) 
            hold =  null;
        else if (index == 1)
            hold = removeFirst();
        else if (index == entry-1)
            hold = removeLast();
        else{
            Node current = head;
            for (int i = 1; i < index-1; i++){
                current = current.next;
            }
            Node temp = current.next;
            current.next = temp.next;
            hold = new Pair<>(temp.date,temp.item);
            entry--;
        }
        return hold;
    }
    
    //might need to reconsider things
    public boolean containsItem(E item){
        boolean found = false;
        Node current = head;
        while (!found && current != null){
            if(current.item.equals(item))
                found = true;
            current = current.next;
        }
        return found;
    }
    
    //returns the item based on the user's choice
    public Pair<T,E> getItem(int index){
        Pair<T,E> result = null;
        if (index < 0 || index >= entry)
            result = null;
        else if (index == 0)
            result = getFirst();
        else if (index == entry-1)
            result = getLast();
        else{
            Node node = head;
            for(int i = 0; i < index; i++){
                node = node.next;
            }
            result = new Pair<>(node.date, node.item);
        }
        return result;
    }
    
    public Pair<T,E> getFirst(){
        Pair<T,E> result = null;
        if (head != null){
            result = new Pair<>(head.date, head.item);
        }
        return result;
    }
    
    public Pair<T,E> getLast(){
        Pair<T,E> result = null;
        if (tail != null){
            result = new Pair<>(tail.date, tail.item);
        }
        return result;
    }
    
    //not sure the use of this 
    public int indexOfItem(String name){
        int index = 0;
        boolean found = false;
        Node current = head;
        while(!found && current!=null){            
            if (name.equals(current.item.toString()))
                found = true;
            else
                index++;
            current = current.next;
        }
        if (found)
            return index;
        else
            return -1;
    }
    
    public void print(){
        if (head == null || tail == null){
            System.out.println("Linked list is empty.");
        }
        else{
            Node current = head;
            while(current!= null){
                System.out.print(current.date.toString() + " " + current.item.toString());
                current = current.next;
            }
        }
        System.out.println(" ");
    }
    
    private class Node{
        private T date;
        private E item;
        private Node next;
        
        private Node (T date, E item){
            this(date, item, null);
        }
        
        private Node (T date, E item, Node nextNode){
            this.date = date;
            this.item = item;
            next = nextNode;
        }
        
        private T getDate(){
            return date;
        }
        
        private E getItem(){
            return item;
        }
        
        private void setDate(T newDate){
            date = newDate;
        }
        
        private void setItem(E newItem){
            item = newItem;
        }
        
        private Node getNextNode(){
            return next;
        }
        
        private void setNextNode(Node nextNode){
            next = nextNode;
        }
    }
}
