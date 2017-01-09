// this program uses java generic. it will only work with JDK 5+
// this program implements RPN and Shaunting yard
// It can handle operands and parenthesis as well as exponents
// the class In2Pj will be iplemented as default class

public class calc {

    public static void main(String[] args) {
        float a,b,c;
        final int input_length = args.length;
        for (int i=0;i<input_length;i++){
            System.out.print(args[i]);
        }
        System.out.print("=");
        Stacklinkedlist<String> operator_stack = new Stacklinkedlist<String>();
        Stacklinkedlist<Float> result=new Stacklinkedlist<Float>();
        QueueAsLinkedList<String> calc=new QueueAsLinkedList<>();
        QueueAsLinkedList<String> output_queue = new QueueAsLinkedList<String>();
        QueueAsLinkedList<String> input_queue = new QueueAsLinkedList<String>();
        precendence_check p = new precendence_check();

        int marker[]=new int[100];
        int pos=0;
        
        for (int i = 0; i < input_length; i++) {
            input_queue.enqueue(args[i]);
        }

        // shuanting yard algorithm
        for (int i = 0; i < input_length;i++){
            int val=p.precedence_check(args[i]);

            if (val==0){
                output_queue.enqueue(args[i]);
            }else{
                if(operator_stack.isEmpty()){
                    operator_stack.push(args[i]);

                }else if(val==2){
                    operator_stack.push(args[i]);
                    marker[pos]=val;
                    pos++;
                }else if(val==3){
                    String str;
                    while(p.precedence_check(operator_stack.peek())!=2){
                        str = operator_stack.pop();
                        output_queue.enqueue(str);
                        pos--;
                }
                        operator_stack.pop();

                }else if(val<=p.precedence_check(operator_stack.peek())){
        
                    while(!operator_stack.isEmpty()&& val<=p.precedence_check(operator_stack.peek())) {
                            output_queue.enqueue(operator_stack.pop());


                    }
                    operator_stack.push(args[i]);
                }else if(val>p.precedence_check(operator_stack.peek())){
                    operator_stack.push(args[i]);

                }
            }
        }
        while(!operator_stack.isEmpty()){
            output_queue.enqueue(operator_stack.pop());
        }
    
        while(!output_queue.isEmpty()){
            String str1=output_queue.dequeue();
            if(p.precedence_check(str1)==0){
               result.push(Float.parseFloat(str1));
            }else {
                a=(result.pop());
                b=(result.pop());
                c=p.evaluate(str1,a,b);
                result.push(c);
            }
        }
        System.out.print(result.pop());
        System.out.println();

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
    public T peek(){
        T item;
        if (first.item!=null){
        item=first.item;
        }else {
            item=null;
        }

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
    public float evaluate(String str,Float a, Float b){
        float result=1;
        char c[]=str.toCharArray();
        if(c.length==1){
            switch(c[0]){
                case '/': result=b/a; break;
                case 'x': result=b*a; break;
                case '+': result=b+a; break;
                case '-': result=b-a; break;
                // deal with exponential operator
                case '^': {
                    for (int i=0;i<a;i++){
                        result=result*b;
                    }
                }break;
                default: result=0;
            }
        }else{
            result=0;
        }
        return result;
    }

}
