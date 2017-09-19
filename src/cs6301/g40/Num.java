
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
		/*
        //TempDigits = Digits;
        while(Digits.size() != 0)
        {
            r=0;
            tempSize = Digits.size();
            for (int i = 0; i < tempSize; i++)
            {
                Digits.add((r*10 + Digits.peek())/base);
                r = (r * 10 + Digits.peek())%base;
                Digits.pop();
            }
            TempDigits.add(r);
            while(!Digits.isEmpty()&&Digits.peek()==0)
                Digits.pop();
        }
        Digits=TempDigits;
        TempDigits=null;
        */
        this.Digits=convertBase_Input(this.Digits,10,this.base);
    }

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

    Num(long x,boolean init)
    {
        Digits = new LinkedList<>();
        if(init)
        {
            if (x < 0)
                negative = true;
            while (x != 0) {
                Digits.add(x % base);
                x = x / base;
            }
        }
    }

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

    public static LinkedList<Long> convertBase_Input( LinkedList<Long> dig,long from,long to)
    {
        LinkedList<Long> D = new LinkedList<>(dig);
        long r,tempSize=0;
        LinkedList<Long> TempDigits = new LinkedList<>();
        //D=this.Digits;
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
            while(!D.isEmpty()&&D.peek()==0)
                D.pop();
        }
        D=TempDigits;
        TempDigits=null;
        return D;
    }


    public LinkedList<Long> convertBase_Output()
    {
        LinkedList<Long> Reverse = new LinkedList<>();
        for (Long digit:this.Digits)
        {
            Reverse.push(digit);
        }
        return convertBase_Input(Reverse,this.base,10);
    }



    static Num add(Num a, Num b,boolean neg)
    {
        Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long sum=0L,carry=0L, element_a,element_b;


        c.negative=neg;

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

    static Num add(Num a, Num b)
    {
        Num c=new Num(0,false);
        Iterator ita= a.Digits.iterator();
        Iterator itb= b.Digits.iterator();
        Long sum=0L,carry=0L, element_a,element_b;

        if(!a.negative && !b.negative)   // a and b both are positive
        {
            c = add(a,b,false);
        }
        else if(a.negative && !b.negative)             // a is negative
        {
            if(a.compareTo(b)>=0)       //absolute value of a is bigger than b and a is negative
            {
                c=subtract(a,b,false);
                c.negative=true;
            }
            else                        //absolute value of b is bigger than a and a is negative
            {
                c=subtract(b,a,false);
            }
        }
        else if(b.negative && !b.negative)             // b is negative
        {
            if(a.compareTo(b)>=0)          //absolute value of a is bigger than b and b is negative
                c=subtract(a,b,false);
            else                            //absolute value of b is bigger than a and b is negative
            {
                c=subtract(b,a,false);
                c.negative=true;
            }
        }
        else                            // a and b both are negative
        {
            c=add(a,b,false);
            c.negative = true;
        }
        return c;
    }

    static Num subtract(Num a, Num b,boolean neg)
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
        if(!didBorrow)
            while(ita.hasNext() && !itb.hasNext())
            {
                element_a = (Long)ita.next();
                c.Digits.add(element_a);
            }
        else
            while(ita.hasNext() && !itb.hasNext())
            {
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
               c=subtract(a,b,false);
           else
           {
              c=subtract(b,a,false);
              c.negative=true;
           }
        }
        else if(a.negative && !b.negative)
        {
            c = add(a,b,false);
            c.negative = true;
        }
        else if(b.negative && !a.negative)
        {
            c = add(a,b,false);
        }
        else
        {
            if(b.compareTo(a)>=0)
                c=subtract(b,a,false);
            else
            {
                c=subtract(a,b,false);
                c.negative=true;
            }
        }
        return c;
    }

    static Num rightShift(Num X, long n)
    {
        while(n-- > 0)
        {
            X.Digits.push(0L);
        }
        return X;
    }

    // Implement Karatsuba algorithm for excellence credit
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
        if(X.Digits.size()%2 != 0 && X.Digits.size()!=1)
        {
            X.Digits.add(0L);
            Y.Digits.add(0L);
        }

        n = X.Digits.size();        // size of X is same as Y now

        if(X.Digits.size() == 1 && Y.Digits.size() == 1)
        {
            prod = X.Digits.peek() * Y.Digits.peek();
            c.convertBaseFromLong(prod,10,11);
            return c;
        }
        else
        {
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

    static Num product(Num a, Num b)
    {
        Num c = new Num(0,false);
        c.negative=a.negative^b.negative;
        c = unsignedProduct(a,b);
        return c;
    }
    // Use divide and conquer
    static Num power(Num a, long n) {
	return null;
    }
    /* End of Level 1 */
	
	/* Start of Level 2 */
	static Num divide(Num a, Num b) {
		return null;
	}
	
	static Num mod(Num a, Num b) {
		return null;
	}
	
	// Use divide and conquer
	static Num power(Num a, Num n) {
		return null;
	}
	
	static Num squareRoot(Num a) {
		return null;
	}
    /* End of Level 2 */

    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other)
    {
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
        System.out.println("----------------Printing---------------");
        for (long num:Digits)
        {
            System.out.println(num);
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
        Num bigNumber1 = new Num("5000");
        bigNumber1.printList();

        //System.out.println("Enter 2nd number as a string");
        //input=in.next();
        Num bigNumber2 = new Num("1");
        bigNumber2.printList();

        Num result = new Num(0L);
        //result = add(bigNumber1,bigNumber2);
        result = subtract(bigNumber1,bigNumber2);
        //result = product(bigNumber1,bigNumber2);
        Output=result.toString();
        System.out.println("bigumber1 * bigNumber2 "+Output);


    }
}
