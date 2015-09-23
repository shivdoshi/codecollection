import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class LaurenInversion {

	
	
	public static HashMap<Integer, Integer> inversionCounts = new HashMap<>(); 
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] ar = new int[n];
        for(int i=0;i<n;i++){
        	ar[i] = in.nextInt();
        	inversionCounts.put(ar[i], 0);
        }
        long oldInvVal = invCount(ar.clone());
        int indexWithMaxInv = -1;
        int maxInv = -1;
        ArrayList<Integer> indexes = new ArrayList<>();
        
        for(int i=1;i<ar.length;i++){
        	int currVal = inversionCounts.get(ar[i]);
        	if(currVal > 0){
        		if(currVal > maxInv){
            		maxInv = currVal;
            		indexes.clear();
            		indexes.add(i);
            	} else if(currVal == maxInv){
            		indexes.add(i);
            	}
        	}
        	
        }
        if(indexes.size() <= 0)
        	System.out.println("Cool Array");
        else{
        	int indexWithHighNumb = -1;
        	long newInvVal = oldInvVal;
        	for(int index : indexes){
        		System.err.println("*"+index);
	        	for(int i=0;i<index;i++){
	        		if(ar[i]>ar[index]){
	        			int[] temp = ar.clone();
	        			temp[i] = ar[index];
	        			temp[index] = ar[i];
	        			long midInvVal = invCount(temp);
	        			if(midInvVal < newInvVal){
	        				newInvVal = midInvVal;
	        				indexWithHighNumb = i;
	        				indexWithMaxInv = index;
	        			}else if(midInvVal == newInvVal){
	        				if(indexWithHighNumb>i && indexWithMaxInv>=index){
	        					indexWithHighNumb = i;
	        					indexWithMaxInv = index;
	        				}
	        			}
	        		}
	            }
        	}
        	System.out.println((indexWithHighNumb+1)+ " " + (indexWithMaxInv+1));
        }        
    }
	
	static long merge(int[] arr, int[] left, int[] right) {
	    int i = 0, j = 0, count = 0;
	    while (i < left.length || j < right.length) {
	        if (i == left.length) {
	            arr[i+j] = right[j];
	            j++;
	        } else if (j == right.length) {
	            arr[i+j] = left[i];
	            i++;
	        } else if (left[i] <= right[j]) {
	            arr[i+j] = left[i];
	            i++;                
	        } else {
	            arr[i+j] = right[j];
	            int shift = left.length-i;
	            count += shift;
	            Integer tcount = inversionCounts.get(right[j]);
	            inversionCounts.put(right[j], tcount + shift);
	            j++;
	        }
	    }
	    return count;
	}

	static long invCount(int[] arr) {
	    if (arr.length < 2)
	        return 0;

	    int m = (arr.length + 1) / 2;
	    int left[] = Arrays.copyOfRange(arr, 0, m);
	    int right[] = Arrays.copyOfRange(arr, m, arr.length);

	    return invCount(left) + invCount(right) + merge(arr, left, right);
	}

}