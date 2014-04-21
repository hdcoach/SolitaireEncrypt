package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {		
		
		//FIRST FIND THE POSITION OF JOKER A
		
		CardNode prev = deckRear;
		CardNode curr = deckRear.next;
		CardNode ptr = deckRear.next.next;
		
		do{
			
			if(curr.cardValue == 27)
			{
				break;
			}
			prev = curr;
			curr = ptr;
			ptr = curr.next;
		}
		while(curr!=deckRear.next);
		
		//now I know where Joker A is
		
		if(deckRear.cardValue == 27) //if joker A is the last card
		{	
			CardNode front = deckRear.next;
			prev.next = front;
			deckRear.next = front.next;
			front.next = deckRear;
			deckRear = front;
			return;
		}
		
		if(curr.next == deckRear) //if joker A is the second to last card
		{
			prev.next = ptr;
			curr.next = ptr.next;
			ptr.next = curr;
			deckRear = curr;
			return;
		}
		
		//otherwise joker A is in the regular position
		
		prev.next = ptr;
		curr.next = ptr.next;
		ptr.next = curr;
		return;
		
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	
	void jokerB() {

		//find the location of Joker B first
		
		CardNode temp = deckRear;
		CardNode prev = null;
		CardNode curr = null;
		CardNode ptr = null;
		do{
			if(temp.next.cardValue == 28)
			{
				prev = temp;
				curr = temp.next; //curr is now Joker B
				ptr = curr.next;
				break;
			}
			
			temp = temp.next;
		}
		
		while(temp != deckRear);
		
		if(curr == deckRear) //if Joker B is at the end of the list
		{
			prev.next = ptr;
			curr.next = ptr.next.next;
			ptr.next.next = curr;
			deckRear = ptr; //set deckRear = front of list
			return;
		}
		
		if(ptr.next == deckRear) //IF JOKER B IS SECOND-TO-SECOND TO LAST
		{
			prev.next = ptr;
			curr.next = ptr.next.next;
			ptr.next.next = curr;
			deckRear = curr;
			return;
		}
		
		if(ptr == deckRear) //if Joker B is the second-to-last element in the list
		{
			prev.next = ptr;
			curr.next = ptr.next.next;
			ptr.next.next = curr;
			deckRear = ptr.next;
			return;
		}
		
		//otherwise this is the regular case
		
		prev.next = ptr;
		curr.next = ptr.next.next;
		ptr.next.next = curr;
		return;		
	}
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode beforeJ1 = null;
		CardNode J1 = null;
		CardNode J2 = null;
		CardNode afterJ2 = null;
		CardNode temp = deckRear;
		int valOfFirst;
		int valOfSecond;
		
		if(deckRear.cardValue == 27 || deckRear.cardValue == 28)
		{
			if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
				return;
		}
		
		//if the first card is a joker, i set deckRear to the second joker
		if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
		{
			
			J1 = deckRear.next;
			
			valOfFirst = J1.cardValue; //to see whether the first joker is 27 or 28
			
			if(valOfFirst == 27)
			{
				valOfSecond = 28;
			}
			
			else
			{
				valOfSecond = 27;
			}
			
			
			while(temp.cardValue != valOfSecond)
			{
				temp = temp.next;
			}
			
			deckRear = temp;
			return;
		}
		
		//if the last card is a joker, i set deckRear equal to the card right before the
		//first joker.
		
		if(deckRear.cardValue == 27 || deckRear.cardValue == 28)
		{
			int J1Val;
			int J2Val = deckRear.cardValue;
			if(J2Val == 28)
			{
				J1Val = 27;
			}
			else
				J1Val = 28;
			
			temp = deckRear;
			while(temp.next.cardValue != J1Val)
			{
				temp = temp.next;
			}
			
			deckRear = temp;
			return;
		}
		
		temp = deckRear; //reset temp to deckRear
		
		//search for joker 1 and the card right before it
		do{
			if(temp.next.cardValue == 28 || temp.next.cardValue == 27)
			{
				beforeJ1 = temp;
				J1 = temp.next;
				break;
			}
			temp = temp.next;
			
		}
		while(temp != deckRear);
		
		//search for joker 2 and the card right after it
		temp = J1.next;
		
		do{
			if(temp.cardValue == 28 || temp.cardValue == 27)
			{
				J2 = temp;
				afterJ2 = temp.next;
				break;
			}
			
			temp = temp.next;
			
		}
		while(temp != J1);
		
		beforeJ1.next = afterJ2;
		J2.next = deckRear.next;
		deckRear.next = J1;
		deckRear = beforeJ1;
		
		return;
		
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	
	void countCut() {		
		
		int numToMove = deckRear.cardValue;
		if(numToMove == 28|| numToMove==27)
		{
			return;
		}
		
		CardNode temp = deckRear.next; //set temp = front of list
		CardNode prev = null;
		
		while(temp.next != deckRear) //search for the last node before rear
		{
			temp = temp.next;
		}
		
		prev = temp;
		
		temp = deckRear.next; //set temp to the front again
		
		while(numToMove > 1)
		{
			temp = temp.next;
			numToMove--;
		}
		
		CardNode endCut = temp; //the node where i stop cutting
		
		prev.next = deckRear.next;
		deckRear.next = endCut.next;
		endCut.next = deckRear;
		
		return;
		
		
	}
	
        /**
         * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
         * counts down based on the value of the first card and extracts the next card value 
         * as key, but if that value is 27 or 28, repeats the whole process until a value
         * less than or equal to 26 is found, which is then returned.
         * 
         * @return Key between 1 and 26
         */

	int getKey() {
		
		int key;
		CardNode temp;
		int numToGo;
		
	do{
				jokerA();
				jokerB();
				tripleCut();
				countCut();				
				
				temp = deckRear.next; //set temp to the front
				numToGo = temp.cardValue;
				if(numToGo == 28)
					numToGo = 27;
				for(int i = 0; i<numToGo; i++)
				{
					temp = temp.next;
				}
				
				key = temp.cardValue;
				if(key != 27 || key != 28)
					break;
			}
		while (key == 27 || key == 28);
	
	return key;

	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		
		
		String s = "";
		String encrypted = "";
		
		for(int i = 0; i<message.length(); i++)
		{
			if(Character.isLetter(message.charAt(i)))
			{
				s = s + message.charAt(i); //only the letters will be included in encrypted message
			}
		}
		
		s = s.toUpperCase(); // the string is all uppercase now
				
		for(int i = 0; i<s.length(); i++)
		{
			int alpha;
			
			int key;
			//find the corresponding alphabet position, find a key
			
			char c = s.charAt(i);
			alpha = c - 'A' + 1;
			key = getKey();
			int sum = alpha + key;
			if(sum>26)
			{
				sum = sum - 26;
			}
			c = (char)(sum - 1 + 'A');
			encrypted = encrypted + c;
		}
		
		return encrypted;

	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		String decrypted = "";
		int alpha;
		int key;
		char c;
		int finalLoc;
		
		//first find the alphabet position of the encrypted chars
		
		for(int i = 0; i<message.length(); i++)
		{
			c = message.charAt(i);
			alpha = c - 'A' + 1; //alphabetical location of encrypted char
			key = getKey();
			
			if(alpha <= key)
			{
				alpha = alpha + 26;
			}
			
			finalLoc = alpha - key;
			c = (char)(finalLoc - 1 + 'A');
			
			decrypted = decrypted + c;

		}
		
		return decrypted;
		
	}
}
