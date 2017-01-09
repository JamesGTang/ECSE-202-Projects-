import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class calcGui {
    String input1="";
    int input_queue_size=0;
    float a,b,c;
    float fin;
    JTextField output = new JTextField();
    Stacklinkedlist<String> operator_stack = new Stacklinkedlist<String>();
    Stacklinkedlist<Float> result=new Stacklinkedlist<Float>();
    QueueAsLinkedList<String> calc=new QueueAsLinkedList<>();
    QueueAsLinkedList<String> output_queue = new QueueAsLinkedList<String>();
    QueueAsLinkedList<String> input_queue = new QueueAsLinkedList<String>();
    precendence_check p = new precendence_check();

    calcGui() {

        JPanel jp = new JPanel(new GridLayout(4, 3));
        JTextField input = new JTextField();

        input.setBackground(Color.lightGray);


        jp.add(input);
        jp.add(output);

        JPanel bp = new JPanel(new GridLayout(7, 4));
        // function buttons
        JButton cButton = new JButton("C");
        JButton negButton = new JButton("+/-");
        JButton remainder = new JButton("%");
        JButton divide = new JButton("/");
        JButton multiply = new JButton("x");
        JButton substract = new JButton("-");
        JButton plus = new JButton("+");
        JButton equal = new JButton("=");
        JButton exponent = new JButton("^");

        // num buttons
        final JButton jb0 = new JButton("0");
        final JButton jb1 = new JButton("1");
        JButton jb2 = new JButton("2");
        JButton jb3 = new JButton("3");
        JButton jb4 = new JButton("4");
        JButton jb5 = new JButton("5");
        JButton jb6 = new JButton("6");
        JButton jb7 = new JButton("7");
        JButton jb8 = new JButton("8");
        JButton jb9 = new JButton("9");
        JButton jbdot = new JButton(".");
        // adding buttons to panel
        bp.add(cButton);
        bp.add(negButton);
        bp.add(remainder);
        bp.add(divide);
        // row 3
        bp.add(jb7);
        bp.add(jb8);
        bp.add(jb9);
        bp.add(multiply);
        // row 4
        bp.add(jb4);
        bp.add(jb5);
        bp.add(jb6);
        bp.add(substract);
        //row 5
        bp.add(jb1);
        bp.add(jb2);
        bp.add(jb3);
        bp.add(plus);
        //row 6
        bp.add(jb0);
        bp.add(jbdot);
        bp.add(exponent);
        bp.add(equal);


        JLabel precLabel = new JLabel("Precision:");
        JLabel precLabel2 = new JLabel("8");
        JSlider prec = new JSlider(0, 8);
        JButton close = new JButton("Close");

        // add the last rows
        bp.add(precLabel);
        bp.add(precLabel2);
        bp.add(prec);
        bp.add(close);

        // add everything to the frame
        JFrame jfr = new JFrame("A simple calculator");
        jfr.setSize(400, 500);
        //set the content to the panel
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.add(bp);


        jfr.setContentPane(jp);
        jfr.setVisible(true);

        jb0.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jb1.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });

        jb2.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jb3.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });

        jb4.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jb5.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });

        jb6.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jb7.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });

        jb8.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jb9.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        jbdot.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        // operand group
        multiply.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        substract.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        divide.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        plus.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        exponent.addActionListener((e) -> {
            passValue(e.getActionCommand());
            input.setText(input1);
        });
        // not yet supported ones
        negButton.addActionListener((e) -> {
            output.setText("Not yet supported, please clear screen and try again");
        });
        remainder.addActionListener((e) -> {
            output.setText("Not yet supported, please clear screen and try again");
        });

        // special case
        cButton.addActionListener((e) -> {
            output.setText("");
            input.setText("");
            input_queue_size=0;
            passValue("");
        });
        equal.addActionListener((e) -> {
            passValue("evaluate");
            output.setBackground(Color.yellow);
            output.setText((Float.toString(fin)));
        });

        // close the program
       close.addActionListener((e) -> {
            System.exit(0);
        });

    }

    private void passValue(String d){
        if (d==""){
            input1="";
            System.out.print("I have been freed");
        }else if (d=="evaluate"){
            // evaluate the equation
            fin=calc(input1);
        }else {
            input1 = input1 + d;
            System.out.println(input1);
        }
    }

    public float calc(String str){
        parser(str);

        for (int i = 0; i < input_queue_size;i++){

            String temp=input_queue.dequeue();

            int val=p.precedence_check(temp);
            
            if (val==0){
                output_queue.enqueue(temp);
            }else{
                if(operator_stack.isEmpty()){
                
                    operator_stack.push(temp);
                }else if(val<=p.precedence_check(operator_stack.peek())){
                    while(!operator_stack.isEmpty()&& val<=p.precedence_check(operator_stack.peek())) {
                        output_queue.enqueue(operator_stack.pop());


                    }
                    operator_stack.push(temp);
                }else if(val>p.precedence_check(operator_stack.peek())){
                    operator_stack.push(temp);

                }

            }
        }
        // enqueue all the left over operators in stack

        while(!operator_stack.isEmpty()){
            output_queue.enqueue(operator_stack.pop());
        }
   
        try{
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
        }}catch (NullPointerException e){
            output.setBackground(Color.red);
            output.setText("Error! Check your input!"+e);
        }
        return result.pop();


    }
    public void parser(String str){
        
        for(int i=0;i<str.length();i++) {

            if ((p.precedence_check(str.charAt(i)))!= 0){
                input_queue.enqueue(Character.toString(str.charAt(i)));
                input_queue_size++;
            }
            else if((p.precedence_check(str.charAt(i)) == 0)){
                
                String temp = "";
                int k=i;
                while (k<str.length() && p.precedence_check(str.charAt(k))==0) {

                    temp = temp + str.charAt(k);
                    k++;
                    i++;
                }
                i--;
                input_queue_size++;
                input_queue.enqueue(temp);
            }
        }
    }
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new calcGui();
            }
        });
    }
}

class QueueAsLinkedList<T> {

    private Node<T> front;
    private Node<T> rear;
    public int size=0;
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
    public int size(){
        return size;
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


class precendence_check{
    int marker=0;
    // function to assgin a value to the operator
    public int precedence_check(char c){

        //System.out.println(c[0]);
            switch(c){
                
                case '/': marker=13; break;
                // command line will not pass asterik
                // therefore x is used
                case 'x': marker=13; break;
                case '+': marker=12; break;
                case '-': marker=12; break;
                // exponent operator
                case '^': marker=7; break;

                default: marker=0;
            }
            // throw exception
            // the operator is not correct
        return marker;
    }
    public int precedence_check(String str){
        char c[]=str.toCharArray();
        //System.out.println(c[0]);
        if(c.length==1){
            switch(c[0]){
            
                case '.': marker=15; break;
                case '/': marker=13; break;
                case 'x': marker=13; break;
                case '+': marker=12; break;
                case '-': marker=12; break;
                case '^': marker=7; break;
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


