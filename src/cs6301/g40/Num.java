
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g40;


import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Num implements Comparable<Num> {
	
	static long defaultBase = 10;  // This can be changed to what you want it to be.
	long base = defaultBase;  // Change as needed
	boolean negative;
	LinkedList<Long> Digits;
	
	/* Start of Level 1 */
	Num(String s) {
		long q, r, tempSize = 0;
		Digits = new LinkedList<>();
		LinkedList<Long> TempDigits = new LinkedList<>();
		for (char c : s.toCharArray()) {
			if (c == '1')
				Digits.add(1L);
			else if (c == '2')
				Digits.add(2L);
			else if (c == '3')
				Digits.add(3L);
			else if (c == '4')
				Digits.add(4L);
			else if (c == '5')
				Digits.add(5L);
			else if (c == '6')
				Digits.add(6L);
			else if (c == '7')
				Digits.add(7L);
			else if (c == '8')
				Digits.add(8L);
			else if (c == '9')
				Digits.add(9L);
			else if (c == '0')
				Digits.add(0L);
			else if (c == '-')
				negative = true;
		}
        this.Digits=convertBase_Input(this.Digits,10,this.base);
    }

    /*
    Num(long x)
    {
        Digits = new LinkedList<>();
        if(x<0)
            negative=true;
        while(x!=0)
        {
            Digits.add(x % base);
            x=x/base;
        }
    }
    */
    //overloaded constructor - if init is 0: only allocates memory to the linked list 'Digits'
    // and doesnt initialise with the value
    Num(long x,boolean init)
    {
        Digits = new LinkedList<>();
        if(init)
        {
            if (x < 0)
                negative = true;
            if(x == 0)
            {
                Digits.add(0L);
            }
            while (x != 0) {
                Digits.add(x % base);
                x = x / base;
            }
            //convertBaseFromLong(x,10,this.base());
        }
        this.Digits=convertBase_Input(this.Digits,10,this.base());
    }

    Num(Num obj)
    {
        this.Digits= new LinkedList<>(obj.Digits);
        this.negative=obj.negative;
        this.base = obj.base;
    }

    //Method to convert Base from one base to another, when the number is given as long;
    // and store the number in new base is 'Digits' linkedList

    void convertBaseFromLong(long x, long from, long to)
    {
        if(x==0)
        {
            this.Digits.add(x % this.base);
            return;
        }
        while(x!=0)
        {
            this.Digits.add(x % this.base);
            x=x/this.base;
        }
    }

    //Method to convert Base from one base to another, when the number is stored in linked list;
    // and store the number in new base is 'Digits' linkedList
    public static LinkedList<Long> convertBase_Input( LinkedList<Long> dig,long from,long to)
    {
        LinkedList<Long> D = new LinkedList<>(dig);
        long r,tempSize=0;
        LinkedList<Long> TempDigits = new LinkedList<>();
        //
        /*eg 11|121|
             11| 11|0
             11|  1|0
             11|  0|1

          This is being replicated in the code.
          Steps:
          1.    Take one digit say D1 at a time from MSB to LSB
          2.    Multiply remainder (r) with the base (in which it is in) and add D1 to it, call this term T
                and divide the whole thing by the base we need to convert into. Call it Q
          3.    Add Q into the temporary LinkedList D
          4.    Take modulo of T with the "to" base. Call this remainder r
          5.    When all digits are exhausted, we get the Quotient in LinkedList D.
          6.    Repeat this until the quotient becomes 0
        */
        while(D.size() != 0)
        {
            r=0;
            tempSize = D.size();
            for (int i = 0; i < tempSize; i++)
            {
                D.add((r*from + D.peek())/to);
                r = (r * from + D.peek())%to;
                D.pop();
            }
            TempDigits.add(r);
            // To remove 0s ahead of the number in LinkedList D
            while(!D.isEmpty()&&D.peek()==0)
                D.pop();
        }
        D=TempDigits;
        TempDigits=null;
        return D;
    }

    /*
        This original Linked List 'Digits' is stored from LSB to MSB
        and in order to output it correctly we need to reverse it
     */
    public LinkedList<Long> convertBase_Output()
    {
        LinkedList<Long> Reverse = new LinkedList<>();
        for (Long digit:this.Digits)
        {
            Reverse.push(digit);
        }
        return convertBase_Input(Reverse,this.base,10);
    }

    /*
        Code to add 2 numbers without considering their signs
     */

    static Num UnsignedAdd(Num a, Num b)
    {
        Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long sum=0L,carry=0L, element_a,element_b;
        

        while(ita.hasNext() && itb.hasNext())
        {
           element_a = (Long)ita.next();
           element_b = (Long)itb.next();
           sum = (element_a + element_b + carry)%c.base;
           carry = (element_a + element_b + carry)/c.base;

           c.Digits.add(sum);
        }
        while(ita.hasNext() && !itb.hasNext())
        {
           element_a = (Long)ita.next();
           sum = (element_a + carry)%c.base;
           carry = (element_a + carry)/c.base;

           c.Digits.add(sum);
        }
        while(!ita.hasNext() && itb.hasNext())
        {
            element_b = (Long)itb.next();
            sum = (element_b + carry)%c.base;
            carry = (element_b + carry)/c.base;

            c.Digits.add(sum);
        }
        if(!ita.hasNext() && !itb.hasNext() && carry!=0)
            c.Digits.add(carry);
        return c;
    }

    /*

    Code to add 2 numbers considering their signs.
    if both the numbers are positive, we simpy add them.
    if one of them is negative, we subtract one from the other
    if both are negative, we add them but keep the sign negative

    */

    static Num add(Num a, Num b)
    {
        Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long sum=0L,carry=0L, element_a,element_b;

        if(!a.negative && !b.negative)   // a and b both are positive
        {
            c = UnsignedAdd(a,b);
        }
        else if(a.negative && !b.negative)             // a is negative
        {
            if(a.compareTo(b)>=0)       //absolute value of a is bigger than b and a is negative
            {
                c=UnsignedSubtract(a,b);
                c.negative=true;
            }
            else                        //absolute value of b is bigger than a and a is negative
            {
                c=UnsignedSubtract(b,a);
            }
        }
        else if(b.negative && !a.negative)             // b is negative
        {
            if(a.compareTo(b)>=0)          //absolute value of a is bigger than b and b is negative
                c=UnsignedSubtract(a,b);
            else                            //absolute value of b is bigger than a and b is negative
            {
                c=UnsignedSubtract(b,a);
                c.negative=true;
            }
        }
        else                            // a and b both are negative
        {
            c=UnsignedAdd(a,b);
            c.negative = true;
        }
        return c;
    }

     /*
        Code to subtract 2 numbers without considering their signs
        and considering abs(a) is greater than abs(b)
     */

    static Num UnsignedSubtract(Num a, Num b)
    {
         Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long diff=0L, element_a,element_b;
        boolean didBorrow=false,borrowDone=false;
        c.negative=false;

        while(ita.hasNext() && itb.hasNext())
        {
            element_a = (Long)ita.next();
            element_b = (Long)itb.next();
            if(didBorrow)                   // if borrowed from previous digit
            {
                element_a--;                // reduce the value of current digit by 1 and then proceed
                /*if(element_a == -1)
                    element_a = 0L;*/
                didBorrow=false;
            }

            diff = element_a - element_b;
            if(diff<0)
            {
                diff = a.base + element_a - element_b;
                didBorrow = true;
            }

            c.Digits.add(diff);
        }
        /*if a still has digits to process and b has exhausted all digits
        * check if there is a pending borrow*/
        if(!didBorrow)
            while(ita.hasNext() && !itb.hasNext())
            {
                element_a = (Long)ita.next();
                c.Digits.add(element_a);
            }
        else
            while(ita.hasNext() && !itb.hasNext())
            {
                /*Takes care of the following case: 5000
                                                   -   1
                                                   ------
                                                    4999
                * */
                element_a = (Long)ita.next();
                if(!borrowDone)
                {
                    --element_a;
                    if(element_a<0)
                    {
                        element_a = a.base + element_a;
                        didBorrow = true;
                    }
                    else
                        borrowDone = true;
                }
                c.Digits.add(element_a);
            }


        return c;
    }

    /*

    Code to subtract 2 numbers considering their signs.
    if both the numbers are positive, we simpy subtract them.
    if one of them is negative, we add one from the other and put the corresponding sign
    if both are negative, we subtract them and keep the appropriate sign

    */


    static Num subtract(Num a, Num b)
    {
        Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long element_a,element_b;
        LinkedList<Long> copy_a = new LinkedList<>(a.Digits);
        LinkedList<Long> copy_b = new LinkedList<>(b.Digits);

        //copy_a = a.Digits;
        //copy_b = b.Digits;

        if(!a.negative && !b.negative)
        {
           if(a.compareTo(b)>=0)
               c=UnsignedSubtract(a,b);
           else
           {
              c=UnsignedSubtract(b,a);
              c.negative=true;
           }
        }
        else if(a.negative && !b.negative)
        {
            c = UnsignedAdd(a,b);
            c.negative = true;
        }
        else if(b.negative && !a.negative)
        {
            c = UnsignedAdd(a,b);
        }
        else
        {
            if(b.compareTo(a)>=0)
                c=UnsignedSubtract(b,a);
            else
            {
                c=UnsignedSubtract(a,b);
                c.negative=true;
            }
        }
        c.truncate();
        a.truncate();
        b.truncate();
        return c;
    }

    /*
        Method to do a right shift of the number by pushing 0s.
        Used for Multiplication
    * */

    static Num rightShift(Num X, long n)
    {
        while(n-- > 0)
        {
            X.Digits.push(0L);
        }
        return X;
    }

    // Implements Karatsuba algorithm for excellence credit
    static Num unsignedProduct(Num X, Num Y)
    {
        Num c = new Num(0,false);
        Num X2_Y2 = new Num(0,false);
        Num X1_Y1 = new Num(0,false);
        Num X1 = new Num(0,false);
        Num X2 = new Num(0,false);
        Num Y1 = new Num(0,false);
        Num Y2 = new Num(0,false);
        long prod=1,index = 0, n = 0;

        /*first check if both numbers are of equal length- if not then append 0s to make them equal*/
        if(X.Digits.size() != Y.Digits.size())
        {
            if(X.Digits.size() < Y.Digits.size())
            {
                while(X.Digits.size() != Y.Digits.size())
                {
                    X.Digits.add(0L);
                }
            }
            else
            {
                while(Y.Digits.size() != X.Digits.size())
                {
                    Y.Digits.add(0L);
                }
            }
        }
        /*if after making the size equal, the size is odd then make the size even by adding one 0*/
        if(X.Digits.size()%2 != 0 && X.Digits.size()!=1)
        {
            X.Digits.add(0L);
            Y.Digits.add(0L);
        }

        n = X.Digits.size();        // size of X is same as Y now

        //if size is 1: base case reached: do simple multiplication
        if(X.Digits.size() == 1 && Y.Digits.size() == 1)
        {
            prod = X.Digits.peek() * Y.Digits.peek();
            c.convertBaseFromLong(prod,10,11);
            return c;
        }
        else
        {
            //creates X1, X2, Y1, Y2
            for (long digit:X.Digits)
            {
                if(index < X.Digits.size()/2)
                {
                    X2.Digits.add(digit);
                }
                else if(index < X.Digits.size())
                {
                    X1.Digits.add(digit);
                }
                index++;
            }
            index = 0;
            for (long digit:Y.Digits)
            {
                if(index < Y.Digits.size()/2)
                {
                    Y2.Digits.add(digit);
                }
                else if(index < Y.Digits.size())
                {
                    Y1.Digits.add(digit);
                }
                index++;
            }
            X1_Y1 = unsignedProduct(X1,Y1);
            X2_Y2 = unsignedProduct(X2,Y2);
            c = add(add(rightShift(subtract(subtract(unsignedProduct(add(X1,X2),add(Y1,Y2)),X1_Y1),X2_Y2),n/2),rightShift(X1_Y1,n)),X2_Y2);
        }
        return c;
    }

    /*
        Does product considering the sign and truncate the leading zeroes
    * */

    static Num product(Num a, Num b)
    {
        Num c = new Num(0,false);
        Num partA=new Num(a);
        Num partB=new Num(b);
        c.negative=a.negative^b.negative;
        c = unsignedProduct(partA,partB);
        //truncates extra 0s which were added while doing product
        c.truncate();
        a.truncate();
        b.truncate();
        return c;
    }
    // Use divide and conquer to find power of
    //  divide the base to half until you reach base case
    //  return product of both parts
    static Num power(Num a, long n) {
        if(n==0)
        {
            Num result = new Num(1,true);
            return result;
        }
        else if (n==1)
        {
            return a;
        }
    
        Long first = n/2;
        Long second = n-n/2;
        Num partA = new Num(a);
        Num partB = new Num(a);
        Num result = product(power(partA,first),power(partB,second));
        // result.printList();
        return result;
    
    }
    /* End of Level 1 */
	
	/* Start of Level 2 */
	/*
	    
	* */
	//Use binar ysearch (implemented by binarydivision() method) to implement division.
    // Num a : It is a number to be divided by Num b (dividend)
    // Num b : It is a number which divides Num a (divisor)
    //while calling binarydivision;  Dividend, Divisor, Highest value of range i.e Dividend
    // and Lowest value of range i.e 0 is given as parameter
    static Num divide(Num a, Num b)
    {
        Num low = new Num("0");
        Num result = binarydivision(a,b,a,low);
        if(a.negative!=b.negative)
            result.negative=true;
        return result;
    }
    
    //Implements binary search for division of two numbers
    // Num a : It is a number to be divided by Num b (dividend)
    // Num b : It is a number which divides Num a (divisor)
    // Num high: Highest value of range in which quotient will lie
    // Num low: Lowest value of range in which quotient will lie
    
    static Num binarydivision(Num a, Num b, Num high, Num low){
        Num zero = new Num("0");
        if(b.compareTo(zero)==0) throw  new java.lang.RuntimeException("Division by Zero");
        Num mid = divideByTwo(add(high,low));
        Num one = new Num ("1");
        Num prod = product(mid,b);
        if(low.compareTo(high)>=0)
        {
            if(prod.compareTo(a)>0) return subtract(mid,one);
            else return mid;
        }
        
        else if(prod.compareTo(a)==0) return mid;
        
        else if (prod.compareTo(a)>0) return binarydivision(a,b,subtract(mid,one),low);
        
        else return binarydivision(a,b,high,add(mid,one));
        
    }
    
    //Implement mod function
    static Num mod(Num a, Num b)
    {
        Num c = divide(a,b);
        Num d = product(c,b);
        Num e = subtract(a,d);
        return e;
    }
    
    
    
    // Use divide and conquer to calculate the power with "Num a" as base and "Num n" as exponent
    //Num a : base
    //Num n : exponent
    static Num power(Num a, Num n)
    {
        Num zero = new Num(0L,true);
        Num one = new Num(1L,true);
        if(n.compareTo(zero)==0)
        {
            Num result = new Num(1,true);
            return result;
        }
    
        else if (n.compareTo(one)==0)
        {
            return a;
        }
        else if(n.Digits.size()==1){return power(a,n.Digits.remove());}
        Long temp = n.Digits.remove();
        Num n0 = new Num (temp,true);
        Num A = power(a,n);
        Num B = power(A,a.base);
        Num C = power(a,n0);
        Num D = product(B,C);
        return D;
    }
    
    //Use divide and conquer to implement Squareroot of a Number
    //Num a: Number whose Squareroot needs to be calculated
    //This function calls another function findRoot
    static Num squareRoot(Num a )
    {
        Num zero = new Num("0");
        Num sqroot = findRoot(a,a,zero);
        
        return sqroot;
    }
    
    //Use divide and conquer technique to implement Squareroot of a number
    //Num a: Number whose Squareroot needs to be calculated
    // Num high: Highest value of range in which squareroot of Num a will lie
    // Num low: Lowest value of range in which squareroot of Num a will lie
    static Num findRoot(Num a,Num high, Num low)
    {
        Num one = new Num("1");
        Num two = new Num("2");
        Num zero = new Num("0");
        Num mid = divideByTwo(add(high,low));
        Num val1 = subtract(a,product(mid,mid));
        Num val2 =  add(product(two,mid),one);
        
        if(low.compareTo(high)>=0)
        {
            if(val1.negative)
                return subtract(mid,one);
            else
                return mid;
        }
        
        if(val1.compareTo(zero)==0) return mid;
        else if (val1.negative) return findRoot(a,subtract(mid,one),low);
        else return findRoot(a,high,add(mid,one));
    }    /* End of Level 2 */
    
    // This function divides Num a by 2
    //This function is called from division function which uses binary search
    static Num divideByTwo(Num a)
    {
        Long rem = 0L;
        Long quotient;
        int size=a.Digits.size();
        Long sum;
        Long num;
        while(size>0)
        {
            num = a.Digits.removeLast();
            sum = rem*a.base + num;
            quotient = sum/2 ;
            rem = sum%2 ;
            a.Digits.push(quotient);
            size--;
        }
        a.truncate();
        return a;
    }
    
    
    // This function truncates leading zeroes from the number
    public void truncate(){
        while(this.Digits.peekLast()==0L && this.Digits.size()>1){
            this.Digits.removeLast();
        }
    }
    
    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
        public int compareTo(Num other)
    {
        this.truncate();
        other.truncate();
        Iterator ita= this.Digits.iterator();
        Iterator itb= other.Digits.iterator();
        Long element_a,element_b;
        LinkedList<Long> copy_this = new LinkedList<>(this.Digits);
        LinkedList<Long> copy_other = new LinkedList<>(other.Digits);

        //copy_this = this.Digits;
        //copy_other = other.Digits;

        if(this.Digits.size()!=other.Digits.size())
        {
            if(other.Digits.size() > this.Digits.size())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            while(!copy_other.isEmpty() && !copy_this.isEmpty() && (copy_other.peekLast() - copy_this.peekLast() == 0 ))
            {
                copy_this.removeLast();
                copy_other.removeLast();
            }
            if(copy_other.isEmpty() && copy_this.isEmpty())
                return 0;
            if(copy_other.peekLast() - copy_this.peekLast() > 0)
                return -1;
            else
            {
                return 1;
            }
        }
    }
    
    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList()
    {
        System.out.print(this.base()+": ");
        for (long num:Digits)
        {
            System.out.print(num+" ");
        }
    }
    
    // Return number to a string in base 10
    public String toString()
    {
        LinkedList<Long> ConvertedDigits=new LinkedList<>(this.convertBase_Output());
        String output=new String();
        //String output=new String(this.convertBase_Output(this.base));
        StringBuilder finalOp = new StringBuilder();
        for (Long digit:ConvertedDigits)
        {
            output=output+Long.toString(digit);
        }
        finalOp.append(output);
        finalOp=finalOp.reverse();
        if(this.negative)
            finalOp.insert(0,"-");
        output = finalOp.toString();
     return output;
    }


    public long base() { return base; }

    public static void main(String [] args)
    {
        String input = new String();
        String Output = new String();
        Scanner in = new Scanner(System.in);
        int big=2;
        //System.out.println("Enter 1st number as a string");
        //input=in.next();
        Num bigNumber1 = new Num("256");
   //     bigNumber1.printList();

        //System.out.println("Enter 2nd number as a string");
        //input=in.next();
        Num bigNumber2 = new Num("256");
    //    bigNumber2.printList();

        Num result = new Num(0L,false);
        //result = add(bigNumber1,bigNumber2);
        //result = subtract(bigNumber1,bigNumber2);
        //result = squareRoot(bigNumber1);
        //result = product(bigNumber1,bigNumber2);
        result = product(bigNumber1,bigNumber2);
        System.out.print(result.toString());
     //   System.out.println("bigumber1 * bigNumber2 "+Output);


    }
}
