package com.austindorff.programs.poker.cards;

import java.util.ArrayList;
import java.util.Random;

public class DeckOfCards
{
	public static ArrayList<Card> deckOfCards = new ArrayList<Card>();
	private static Card tempCard;
	private static Card tempCard2;
	private static Card finalCard;
	
	private static int tempInt = 0;
	private static int tempInt2 = 0;
	private static int counter = 0;
	private static int placeHolder = 0;
	
	private static String deckString = "";
	private static String tempSuitString = "";
	private static String tempSuitString2 = "";
	private static String tempTypeString = "";
	private static String tempTypeString2 = "";
	
	private static String[] suits = Card.getSuits();
	private static String[] types = Card.getTypes();
	
	public DeckOfCards()
	{
		generateDeck();
		shuffleDeck();
	}
	
	public void generateDeck()
	{
		for (int index = 0; index < 13; index++)
		{
			deckOfCards.add(new Card(index, 0));
		}
		for (int index = 0; index < 13; index++)
		{
			deckOfCards.add(new Card(index, 1));
		}
		for (int index = 0; index < 13; index++)
		{
			deckOfCards.add(new Card(index, 2));
		}
		for (int index = 0; index < 13; index++)
		{
			deckOfCards.add(new Card(index, 3));
		}
	}
	
	public void shuffleDeck()
	{
		Random generator = new Random();
		for (int index = 0; index < 52; index++)
		{
			tempInt = (generator.nextInt(51) + 1);
			tempCard = deckOfCards.get(tempInt);
			tempInt2 = (generator.nextInt(51) + 1);
			tempCard2 = deckOfCards.get(tempInt2);
			deckOfCards.set(tempInt2, tempCard);
			deckOfCards.set(tempInt, tempCard2);
		}
	}
	
	public void printDeck()
	{
		deckString = "";
		for (int index = 1; index < deckOfCards.size() + 1; index++)
		{
			tempCard = deckOfCards.get(index - 1);
			deckString += (Card.printCard(tempCard) + "\t");
			if (index % 3 == 0)
			{
				deckString += "\n" + "\n";
			}
		}
		
		System.out.println(deckString);
	}
	
	public ArrayList<Card> getDeck()
	{
		return deckOfCards;
	}
	
	public static void printHand(ArrayList<Card> hand)
	{
		deckString = "";
		if (hand != null)
		{
			for (int index = 0; index < hand.size(); index++)
			{
				tempCard = hand.get(index);
				deckString += ("\t" + "\t" + Card.printCard(tempCard) + "\n");
			}
		}
		else
		{
			deckString = "Hand Does Not Exist.";
		}
		
		System.out.println(deckString);
	}
	
	public static ArrayList<Card> handContainsFlush(ArrayList<Card> hand)
	{
		ArrayList<Card> newHand = getAllCardsOfTheMostFrequentSuit(hand);
		if (newHand.size() == 5)
		{
			return newHand;
		}
		else 
		{
			return null;
		}
	}
	
	public static ArrayList<Card> handContainsStraightFlush(ArrayList<Card> hand)
	{
		if ((handContainsStraight(hand) != null) && (handContainsFlush(hand) != null))
		{
			return hand;
		}
		else
		{
			return null;
		}
	}
	
	public static ArrayList<Card> handContainsRoyalFlush(ArrayList<Card> hand) 
	{
		if (handContainsStraightFlush(hand) != null)
		{
			boolean containsAce = arrayListContainsCardTypeAndReturnsIndex(hand, 12) == -1 ? false : true;
			boolean containsKing = arrayListContainsCardTypeAndReturnsIndex(hand, 4) == -1 ? false : true;
			if ((containsAce == true) && (containsKing == true))
			{
				return hand;
			}
			else
			{
				return null;
			}
		}
		return null;
	}
	
	public static ArrayList<Card> handContainsStraight(ArrayList<Card> hand)
	{
		boolean containsAce = arrayListContainsCardTypeAndReturnsIndex(hand, 12) == -1 ? false : true;
		if (containsAce == true)
		{
			boolean containsTwo = arrayListContainsCardTypeAndReturnsIndex(hand, 0) == -1 ? false : true;
			boolean containsThree = arrayListContainsCardTypeAndReturnsIndex(hand, 1) == -1 ? false : true;
			boolean containsFour = arrayListContainsCardTypeAndReturnsIndex(hand, 2) == -1 ? false : true;
			boolean containsFive = arrayListContainsCardTypeAndReturnsIndex(hand, 3) == -1 ? false : true;
			boolean containsTen = arrayListContainsCardTypeAndReturnsIndex(hand, 8) == -1 ? false : true;
			boolean containsJack = arrayListContainsCardTypeAndReturnsIndex(hand, 9) == -1 ? false : true;
			boolean containsQueen = arrayListContainsCardTypeAndReturnsIndex(hand, 10) == -1 ? false : true;
			boolean containsKing = arrayListContainsCardTypeAndReturnsIndex(hand, 11) == -1 ? false : true;
			if (((containsTwo == true) && (containsThree == true) && (containsFour == true) && (containsFive == true)) || ((containsTen == true) && (containsJack == true) && (containsQueen == true) && (containsKing == true)))
			{
				return hand;
			}
			else
			{
				return null;
			}
		}
		else
		{
			hand = orderHandByType(hand);
			int startIndex = findIndexOfCardTypeInTypesList(hand.get(hand.size() - 1).getType());
			if (startIndex + 4 <= 13)
			{
				boolean containsNext = arrayListContainsCardTypeAndReturnsIndex(hand, startIndex + 1) == -1 ? false : true;
				boolean containsNext2 = arrayListContainsCardTypeAndReturnsIndex(hand, startIndex + 2) == -1 ? false : true;
				boolean containsNext3 = arrayListContainsCardTypeAndReturnsIndex(hand, startIndex + 3) == -1 ? false : true;
				boolean containsNext4 = arrayListContainsCardTypeAndReturnsIndex(hand, startIndex + 4) == -1 ? false : true;
				if ((containsNext == true) && (containsNext2 == true) && (containsNext3 == true) && (containsNext4 == true))
				{
					return hand;
				}
			}
			else 
			{
				return null;
			}
		}
		return null;
	}
	
	public static ArrayList<Card> handContainsTwoOfAKind(ArrayList<Card> hand)
	{
		ArrayList<Card> newHand;
		for (int index = 0; index < hand.size(); index++)
		{
			newHand = new ArrayList<Card>();
			tempTypeString = hand.get(index).getType();
			newHand.add(hand.get(index));
			counter = 1;
			for (int index2 = index + 1; index2 < hand.size(); index2++)
			{
				tempTypeString2 = hand.get(index2).getType();
				if (tempTypeString2.equals(tempTypeString))
				{
					counter++;
					newHand.add(hand.get(index2));
				}
				if (counter == 2)
				{	
					return newHand;
				}
			}
			if (counter != 2)
			{
				newHand = null;
			}
		}
		return null;
	}

	public static ArrayList<Card> handContainsOtherTwoOfAKind(ArrayList<Card> hand, int cardType)
	{
		ArrayList<Card> newHand = getAllOtherCardTypes(hand, cardType);
		if (handContainsTwoOfAKind(newHand) != null)
		{
			return hand;
		}
		else
		{
			return null;
		}
	}
	
	public static ArrayList<Card> handContainsThreeOfAKind(ArrayList<Card> hand)
	{
		tempInt = 0;
		tempInt2 = 0;
		ArrayList<Card> newHand;
		for (int index = 0; index < hand.size(); index++)
		{
			newHand = new ArrayList<Card>();
			tempTypeString = hand.get(index).getType();
			newHand.add(hand.get(index));
			counter = 1;
			for (int index2 = index + 1; index2 < hand.size(); index2++)
			{
				tempTypeString2 = hand.get(index2).getType();
				if (tempTypeString2.equals(tempTypeString))
				{
					counter++;
					newHand.add(hand.get(index2));
				}
				if (counter == 3)
				{
					return newHand;
				}
			}
			if (counter != 3)
			{
				newHand = null;
			}
		}
		return null;
	}
	
	public static ArrayList<Card> handContainsFourOfAKind(ArrayList<Card> hand)
	{
		tempInt = 0;
		tempInt2 = 0;
		ArrayList<Card> newHand = new ArrayList<Card>();
		for (int index = 0; index < hand.size(); index++)
		{
			tempTypeString = hand.get(index).getType();
			newHand.add(hand.get(index));
			counter = 0;
			for (int index2 = index; index2 < hand.size(); index2++)
			{
				tempTypeString2 = hand.get(index2).getType();
				if (tempTypeString2.equals(tempTypeString))
				{
					counter++;
					newHand.add(hand.get(index2));
				}
				if (counter == 5)
				{
					return newHand;
				}
			}
			if (counter != 5)
			{
				for (int index2 = 0; index2 < newHand.size(); index2++)
				{
					newHand.remove(index2);
				}
			}
		}
		return null;
	}
	
	public static ArrayList<Card> handContainsTwoPair(ArrayList<Card> hand)
	{
		ArrayList<Card> newHand = handContainsTwoOfAKind(hand);
		if (newHand != null)
		{
			if (handContainsOtherTwoOfAKind(hand, findIndexOfCardTypeInTypesList(newHand.get(0).getType())) != null)
			{
				return hand;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static ArrayList<Card> handContainsFullHouse(ArrayList<Card> hand)
	{
		if ((handContainsThreeOfAKind(hand) != null))
		{
			ArrayList<Card> newHand = handContainsThreeOfAKind(hand);
			if (handContainsOtherTwoOfAKind(hand, findIndexOfCardTypeInTypesList(newHand.get(0).getType())) != null)
			{
				return hand;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static int findCardIndexByTypeAndSuitInArrayList(ArrayList<Card> hand, int suitsIndex, int typesIndex)
	{
		for (int index = 0; index < hand.size(); index++)
		{
			tempInt = findIndexOfCardTypeInTypesList(hand.get(index).getType());
			tempInt2 = findIndexOfCardSuitInSuitsList(hand.get(index).getSuit());
			
			if((tempInt == typesIndex) && (tempInt2 == suitsIndex))
			{
				return index;
			}
		}
		return -1;
	}
	
	public static int arrayListContainsCardTypeAndReturnsIndex(ArrayList<Card> hand, int typesIndex)
	{
		for (int index = 0; index < hand.size(); index++)
		{
			tempInt = findIndexOfCardTypeInTypesList(hand.get(index).getType());
			if(tempInt == typesIndex)
			{
				return index;
			}
		}
		return -1;
	}
	
	public static int findIndexOfCardTypeInTypesList(String cardType)
	{
		for (int index = 0; index < 13; index++)
		{
			tempTypeString = types[index];
			if (tempTypeString.equals(cardType))
			{
				return index;
			}
		}
		return -1;
	}
	
	public static int findIndexOfCardSuitInSuitsList(String cardSuit)
	{
		for (int index = 0; index < 4; index++)
		{
			tempSuitString = suits[index];
			if (tempSuitString.equals(cardSuit))
			{
				return index;
			}
		}
		return -1;
	}
	
	public static ArrayList<Card> orderHandBySuit(ArrayList<Card> hand)
	{
		tempInt = 3;
		ArrayList<Card> newHand = new ArrayList<Card>();
		ArrayList<Card> tempHand = new ArrayList<Card>();
		for (int index = 0; index < 4; index++)
		{
			tempHand = getAllCardsOfThisSuit(hand, index);
			for (int index2 = 0; index2 < tempHand.size(); index2++)
			{
				newHand.add(tempHand.get(index2));
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> orderHandByType(ArrayList<Card> hand)
	{
		tempInt = 3;
		ArrayList<Card> newHand = new ArrayList<Card>();
		ArrayList<Card> tempHand = new ArrayList<Card>();
		for (int index = 12; index > -1; index--)
		{
			tempHand = getAllCardsOfThisType(hand, index);
			for (int index2 = 0; index2 < tempHand.size(); index2++)
			{
				newHand.add(tempHand.get(index2));
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> getAllOtherCardTypes(ArrayList<Card> hand, int cardTypeIndex)
	{
		ArrayList<Card> newHand = new ArrayList<Card>();
		for (int index = 0; index < hand.size(); index++)
		{
			tempCard = hand.get(index);
			if (findIndexOfCardTypeInTypesList(tempCard.getType()) != cardTypeIndex)
			{
				newHand.add(tempCard);
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> getAllCardsOfThisSuit(ArrayList<Card> hand, int suitIndex)
	{
		tempSuitString = suits[suitIndex];
		ArrayList<Card> newHand = new ArrayList<Card>();
		for (int index = 0; index < hand.size(); index++)
		{
			tempSuitString2 = hand.get(index).getSuit();
			
			if (tempSuitString2.equals(tempSuitString))
			{
				newHand.add(hand.get(index));
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> getAllCardsOfThisType(ArrayList<Card> hand, int typeIndex)
	{
		tempTypeString = types[typeIndex];
		ArrayList<Card> newHand = new ArrayList<Card>();
		for (int index = 0; index < hand.size(); index++)
		{
			tempTypeString2 = hand.get(index).getType();
			
			if (tempTypeString2.equals(tempTypeString))
			{
				newHand.add(hand.get(index));
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> getAllCardsOfTheMostFrequentSuit(ArrayList<Card> hand)
	{
		ArrayList<Card> newHand = new ArrayList<Card>();
		tempInt2 = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempCard = hand.get(index);
			counter = 0;
			for (int index2 = 0; index2 < hand.size(); index2++)
			{
				tempCard2 = hand.get(index2);
				if (Card.areCardSuitsTheSame(tempCard, tempCard2))
				{
					counter++;
				}
			}
			if (counter > tempInt2)
			{
				tempInt2 = counter;
				placeHolder = index;
			}
		}
		finalCard = hand.get(placeHolder);
		counter = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempCard = hand.get(index);
			if (Card.areCardSuitsTheSame(finalCard, tempCard))
			{
				newHand.add(hand.get(index));
			}
		}
		return newHand;
	}
	
	public static ArrayList<Card> getAllCardsOfTheMostFrequentType(ArrayList<Card> hand)
	{
		ArrayList<Card> newHand = new ArrayList<Card>();
		tempInt2 = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempCard = hand.get(index);
			counter = 0;
			for (int index2 = 0; index2 < hand.size(); index2++)
			{
				tempCard2 = hand.get(index2);
				if (Card.areCardTypesTheSame(tempCard, tempCard2))
				{
					counter++;
				}
			}
			if (counter > tempInt2)
			{
				tempInt2 = counter;
				placeHolder = index;
			}
		}
		finalCard = hand.get(placeHolder);
		counter = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempCard = hand.get(index);
			if (Card.areCardTypesTheSame(finalCard, tempCard))
			{
				newHand.add(hand.get(index));
			}
		}
		return newHand;
	}
	
	public static Card getHandHighCard(ArrayList<Card> hand)
	{
		int highestSuitIndex = 4;
		int tempSuitIndex = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempSuitIndex = findIndexOfCardSuitInSuitsList(hand.get(index).getSuit());
			if (tempSuitIndex <= highestSuitIndex)
			{
				highestSuitIndex = tempSuitIndex;
			}
		}
		ArrayList<Card> newHand = getAllCardsOfThisSuit(hand, highestSuitIndex);
		newHand = orderHandByType(newHand);
		return newHand.get(newHand.size() - 1);
	}
	
	public static Card getHandLowCard(ArrayList<Card> hand)
	{
		int lowestSuitIndex = 0;
		int tempSuitIndex = 0;
		for (int index = 0; index < hand.size(); index++)
		{
			tempSuitIndex = findIndexOfCardSuitInSuitsList(hand.get(index).getSuit());
			if (tempSuitIndex >= lowestSuitIndex)
			{
				lowestSuitIndex = tempSuitIndex;
			}
		}
		ArrayList<Card> newHand = getAllCardsOfThisSuit(hand, lowestSuitIndex);
		newHand = orderHandByType(newHand);
		return newHand.get(newHand.size() - 1);
	}
	
	public static int rankHand(ArrayList<Card> hand)
	{
		if (handContainsRoyalFlush(hand) != null)
		{
			tempSuitString = hand.get(0).getSuit();
			return findIndexOfCardSuitInSuitsList(tempSuitString);
		}
		else if (handContainsStraightFlush(hand) != null)
		{
			tempSuitString = hand.get(0).getSuit();
			hand = orderHandByType(hand);
			tempTypeString = hand.get(0).getType();
			return (((findIndexOfCardSuitInSuitsList(tempSuitString)) * (13 - (findIndexOfCardTypeInTypesList(tempTypeString)))) + 4);
		}
		else if (handContainsFourOfAKind(hand) != null)
		{
			ArrayList<Card> newHand = hand;
			newHand = handContainsFourOfAKind(newHand);
			tempTypeString = newHand.get(0).getType();
			return (13 - findIndexOfCardTypeInTypesList(tempTypeString) + 31);
		}
		else if (handContainsFullHouse(hand) != null)
		{
			ArrayList<Card> newHand = handContainsThreeOfAKind(hand);
			tempTypeString = newHand.get(0).getType();
			return (13 - findIndexOfCardTypeInTypesList(tempTypeString) + 276);
		}
		else if (handContainsFlush(hand) != null)
		{
			ArrayList<Card> newHand = hand;
			newHand = handContainsFlush(newHand);
			newHand = orderHandByType(newHand);
			tempSuitString = newHand.get(0).getSuit();
			tempTypeString = newHand.get(0).getType();
			return ((13 - (findIndexOfCardTypeInTypesList(tempTypeString))) * findIndexOfCardSuitInSuitsList(tempSuitString) + 289);
		}
		else if (handContainsStraight(hand) != null)
		{
			ArrayList<Card> newHand;
			newHand = handContainsStraight(hand);
			newHand = orderHandByType(newHand);
			tempTypeString = newHand.get(0).getType();
			tempSuitString = newHand.get(0).getSuit();
			return (findIndexOfCardSuitInSuitsList(tempSuitString) * (13 - (findIndexOfCardTypeInTypesList(tempTypeString))) + 322);
		}
		else if (handContainsThreeOfAKind(hand) != null)
		{
			ArrayList<Card> newHand = hand;
			newHand = handContainsThreeOfAKind(newHand);
			newHand = orderHandBySuit(newHand);
			tempTypeString = newHand.get(0).getType();
			tempSuitString = newHand.get(0).getSuit();
			return (13 - (findIndexOfCardTypeInTypesList(tempTypeString)) + 349);
		}
		else if (handContainsTwoPair(hand) != null)
		{
			ArrayList<Card> newHand = hand;
			newHand = handContainsTwoPair(newHand);
			newHand = orderHandByType(newHand);
			tempTypeString = newHand.get(0).getType();
			tempSuitString = newHand.get(0).getSuit();
			return (((findIndexOfCardSuitInSuitsList(tempSuitString)) * (13 - (findIndexOfCardTypeInTypesList(tempTypeString)))) + 362);
		}
		else if (handContainsTwoOfAKind(hand) != null)
		{
			ArrayList<Card> newHand = hand;
			newHand = handContainsTwoOfAKind(newHand);
			newHand = orderHandBySuit(newHand);
			tempTypeString = newHand.get(0).getType();
			tempSuitString = newHand.get(0).getSuit();
			return (((findIndexOfCardSuitInSuitsList(tempSuitString)) * ((13 - (findIndexOfCardTypeInTypesList(tempTypeString)))) + 389));
		}
		else
		{
			ArrayList<Card> newHand = hand;
			newHand = orderHandByType(newHand);
			tempTypeString = newHand.get(0).getType();
			return ((13 - (findIndexOfCardTypeInTypesList(tempTypeString))) + 415);
		}
	}
	
}
