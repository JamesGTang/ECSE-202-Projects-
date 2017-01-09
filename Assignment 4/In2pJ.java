// this program uses java generic. it will only work with JDK 5+

// the class In2Pj will be iplemented as default class
// package com.jamestang;


import java.util.ArrayList;

interface precedence{
    int precedence_check(String str);
}
interface Node<T>{

}

public class In2pJ {

    public static void main(String[] args) {
        // use final declaration
        // for the length of the input to improve performace

        final int input_length = args.length;
        Stacklinkedlist<String> operator_stack = new Stacklinkedlist<String>();
        QueueAsLinkedList<String> output_queue = new QueueAsLinkedList<String>();
        QueueAsLinkedList<String> input_queue = new QueueAsLinkedList<String>();
        precendence_check p = new precendence_check();

        // if array list is allowed, the marker array will be implemented as arraylist
        // which allows for dynamic allocation of array
        // ArrayList<Integer> list=new ArrayList<Integer>();

        // initialize an array to hold the value of operator precedence
        // so that the precedence check dosent have to performed twice
        int marker[]=new int[100];

        /* test the operator stack pop() and push function
        for (int i=0;i<args.length;i++){
            operator_stack.push(args[i]);
        }
        while(!operator_stack.isEmpty()){
            System.out.print(operator_stack.pop());
        }*/

        //System.out.print(operator_stack.isEmpty());
        for (int i = 0; i < input_length; i++) {
            input_queue.enqueue(args[i]);
        }

        // shuanting yard algorithm
        for (int i = 0; i < input_length;i++){

            // here precedence check is only performed once
            int pos=0;
            int val=p.precedence_check(args[i]);

            if (val==0){
                // its a number
                // it is a number, it is directly put in the output
                output_queue.enqueue(args[i]);
            }else{
                if(operator_stack.isEmpty()){

                    operator_stack.push(args[i]);
                    marker[pos]=val;
                    pos++;
                    //System.out.println(pos);

                }else if(val==2){
                    operator_stack.push(args[i]);
                    marker[pos]=val;
                    pos++;
                }else if(val==3){
                    if(marker[pos]!=2){
                        // pop an operator from stack into the output queue
                        output_queue.enqueue(operator_stack.pop());
                        marker[pos]=val;
                    }else{
                        // pop operator from stack and discard
                        operator_stack.pop();
                        marker[pos]=val;
                    }
                }else if(val<=marker[pos]){
                    //System.out.print(marker[i]+" "+marker[i]);
                    // if operator on top has higher precedence
                    // pop operator from stack to output queue
                    output_queue.enqueue(operator_stack.pop());
                    operator_stack.push(args[i]);
                    marker[pos]=val;

                }else if(val>marker[pos]){
                    //System.out.println(marker[pos]);
                    //System.out.println(marker[pos]);
                    // if operator on top has lower precedence
                    // push operator in diurectly
                    operator_stack.push(args[i]);
                    marker[pos++]=val;
                }
            }
        }
        // enqueue all the left over operators in stack

        while(!operator_stack.isEmpty()){
            output_queue.enqueue(operator_stack.pop());
        }
        System.out.print("Postfix: ");
        // print the output queue using linked list
        while (!output_queue.isEmpty()){
            System.out.print(output_queue.dequeue()+" ");
        }
        System.out.println();

        // to test the input function
        /* test the precedence function
        System.out.println("\n"+"------------");
        while(!input_queue.isEmpty()){
            String str1=input_queue.dequeue();
            int k=p.precedence_check(str1);
            System.out.print(k+" ");
            //System.out.print(input_queue.dequeue());

        }
        */
    }}



class QueueAsLinkedList<T> implements Node<T> {

    private Node<T> front;
    private Node<T> rear;
    private int size;
    // constructor of class
    public QueueAsLinkedList() {
    }
    // node implemntation for linkedlist
    private class Node<T> {
        T item;
        Node<T> next;

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
    // enqueue function to add element to queue
    public void enqueue(T item) {
        Node<T> node = new Node<T>(item, null);
        if (rear == null) {
            rear = node;
            front = node;
        } else {
            rear.next = node;
        }

        rear = node;
        size++;
    }
    // dequeue function to extract value from a queue LIFO
    public T dequeue() {
        if (front == null) {
            // throw error, nothing in queue
        }
        T item = front.item;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

class Stacklinkedlist<T>{
    private Node<T> first;
    int tos=0;
    int size=0;
    private class Node<T> {
        T item;
        Node<T> next;
        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
    public Stacklinkedlist(){
    }
    // push function to add element to stack
    public void push(T item){
        Node<T> temp=first;
        first =new Node<T>(item,null);

        first.next=temp;
        tos++;
        size++;
    }
    // pop function to extract value from a stack FIFO
    public T pop(){
        T item;
        if (tos==0) {
            item=null;
        }else{
            item=first.item;
            first=first.next;
            tos--;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            //System.out.print(size);
            return false;
        }
    }
}


class precendence_check implements precedence{
    int marker=0;
    // function to assgin a value to the operator
    // if it is a number the value will be 0
    public int precedence_check(String str){
        char c[]=str.toCharArray();
        //System.out.println(c[0]);
        if(c.length==1){
        switch(c[0]){
                // precedence are assigned a marker value from
                // 15 to 1
            case '(': marker=2; break;
            case ')': marker=3;  break;
            case '.': marker=15; break;
            case '/': marker=13; break;
                // command line will not pass asterik
                // therefore x is used
            case 'x': marker=13; break;
            case '+': marker=12; break;
            case '-': marker=12; break;
            case '>': marker=11; break;
            case '<': marker=11; break;
                // bitwise operator
            case '&': marker=8; break;
            case '^': marker=7; break;
            case '|': marker=6; break;
            case ',': marker=5; break;
            default: marker=0;
        }}else if(c.length==2){
            // throw exception
            // the operator is not correct
            marker=0;
        }else return marker;
        // marer contains the value of the check
        return marker;
    }
}