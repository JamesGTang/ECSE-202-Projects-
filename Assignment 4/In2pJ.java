// this program uses java generic. it will only work with JDK 5+
// the class In2Pj will be iplemented as default class

import java.util.ArrayList;

interface precedence{
    int precedence_check(String str);
}
interface Node<T>{

}

public class In2pJ {

    public static void main(String[] args) {

        final int input_length = args.length;
        int marker[]=new int[100];

        Stacklinkedlist<String> operator_stack = new Stacklinkedlist<String>();
        QueueAsLinkedList<String> output_queue = new QueueAsLinkedList<String>();
        QueueAsLinkedList<String> input_queue = new QueueAsLinkedList<String>();
        precendence_check p = new precendence_check();
        for (int i = 0; i < input_length; i++) {
            input_queue.enqueue(args[i]);
        }

        // shuanting yard algorithm
        for (int i = 0; i < input_length;i++){
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
                    output_queue.enqueue(operator_stack.pop());
                    operator_stack.push(args[i]);
                    marker[pos]=val;

                }else if(val>marker[pos]){
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
        System.out.println();    }}



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
            case '^': marker=7; break;
          
            default: marker=0;
        }}else if(c.length==2){
            marker=0;
        }else return marker;
        return marker;
    }
}
