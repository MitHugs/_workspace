import java.util.*;
public class blackjack_main 
{

	public static void main(String[] args) 
	{
		Scanner scnr = new Scanner(System.in);
		System.out.println("\n---BLACKJACK---");
		System.out.println("\nConstructing the deck...\n");
		
		boolean newGame = false;
		do 
		{
		ArrayList <String> deckArray = new ArrayList <String>(0);
		ArrayList <String> playerHand = new ArrayList <String>(0);
		ArrayList <String> dealerHand = new ArrayList <String>(0);
		
		//construct the deck
		buildDeck(deckArray);

		int playerHandValue = 0;
		int dealerHandValue = 0;
		int playerAcesReduced = 0;
		int dealerAcesReduced = 0;
		
		final int STARTING_CARDS = 2;
		
		//Give cards to the dealer
		addCardToHand(deckArray, dealerHand, STARTING_CARDS);
		int dealerAceCount = 0;
			for (int i = 0; i < STARTING_CARDS; i++)
			{
				//IMPORTANT! This will recognize the first ace and only the first ace as an 11. 
				if(dealerHand.get(i).contains("Ace"))
				{
					dealerAceCount++;
				}
				dealerHandValue += getCardValue(dealerHand.get(i));
				
				if(dealerAceCount > 1)
				{
					dealerHandValue -= 10; //If there is more than one ace, reduce it by 10 to make sure it doesnt go over. 
					dealerAcesReduced++;  //Add this to the reduced aces tally to keep track of how many aces have already been reduced. 
				}
			}

			System.out.println("Dealer's face up card is: [" + dealerHand.get(0) + "]" + " [" + getCardValue(dealerHand.get(0)) + "]");
		
		
		//Give cards to the player
		addCardToHand(deckArray, playerHand, STARTING_CARDS);
		int playerAceCount = 0; //Keeps track of all the aces in the hand
		
		for (int i = 0; i < STARTING_CARDS; i++)
		{
			//IMPORTANT! This will recognize the first ace and only the first ace as an 11. 
			if(playerHand.get(i).contains("Ace"))
			{
				playerAceCount++;
			}
			playerHandValue += getCardValue(playerHand.get(i));
		}
		
		if(playerAceCount > 1)
		{
			playerHandValue -= 10; //If there is more than one ace, reduce it by 10 to make sure it doesnt go over. 
			playerAcesReduced++; //Add this to the reduced aces tally to keep track of how many aces have already been reduced. 
		}
		System.out.println("\nYour hand is: " + playerHand + " [" + playerHandValue + "]");
		
		boolean addCards = false;
		boolean dealerBust = false;
		boolean playerBust = false;
	
		do 
		{
			System.out.println("\n-----------------PLAY OPTIONS----------------------");
			System.out.println("To add another card (hit):\t\tPress H");
			System.out.println("To keep your current cards (check):\tPress C");
			System.out.println("To view the cards in play:\t\tPress V");
			System.out.print("\nEnter your choice here: ");
			
			String optionSelection = scnr.next().toUpperCase();
			
			switch(optionSelection)
			{
				case "H":
					addCardToHand(deckArray, playerHand, 1);
					playerAceCount = 0;
					
					playerHandValue += getCardValue(playerHand.get(0));
					//Check to see if the value is under 10 to change the ace
					if(playerHandValue > 10 && playerHand.get(0).contains("Ace")) //if the player draws another ace
					{
						playerAcesReduced += 1; //add a tally to the aces reduced. 
						playerHandValue -= 10; //reduce the value of the ace by 10, from 11 to 1, to make sure that the player does not go over. 
					}//end if
					
					//If the next card drawn will put the user over.
					//This check is important, as it will reduce any 11 Ace to a 1 Ace if it hasn't been already. 
					if(playerHandValue > 21)
					{
						playerAceCount = 0;
						for(int i = 0; i < playerHand.size(); i++)
						{
							if(playerHand.get(i).contains("Ace"))
							{
								playerAceCount++;
							}
						}//end for
						
						if(playerAceCount > 0 && playerAcesReduced < playerAceCount) //compares the amount of aces to the amount of times that the aces have been reduced
						{
							playerHandValue -= 10;
							playerAcesReduced += 1;
						}//end if
					}//end if
		
					System.out.println("\nCard Drawn: \t[" + playerHand.get(0) + "]");
					System.out.println("Hand: \t\t" + playerHand + " [" + playerHandValue + "]");
					
					addCards = true;
				break;

				case "C":
					addCards = false;
				break;
				
				case "V":
					System.out.println("Your Hand: \t\t" + playerHand + " [" + playerHandValue + "]");
					System.out.println("Dealer's card: \t\t[" + dealerHand.get(0) + "]" + " [" + getCardValue(dealerHand.get(0)) + "]");
					addCards = true;
				break;
					
				default:
					System.out.println("Please input only a H or C");
					addCards = true;
				break;
			}
			
			//Final check for a bust or blackjack
			if(playerHandValue > 21)
			{
				System.out.println("Bust!");
				playerBust = true;
				addCards = false;
			}
			
			else if(playerHandValue == 21)
			{
				System.out.println("Blackjack!");
				playerBust = false;
				addCards = false;
			}
			
		}while(addCards);

		//DEALER ALGORITHIM 
		while(dealerHandValue < 17 && playerBust == false)
		{
			addCardToHand(deckArray, dealerHand, 1);
			dealerAceCount = 0;
			if(dealerHandValue > 10 && dealerHand.get(0).contains("Ace")) //if the dealer draws another ace
			{
				dealerAcesReduced += 1; //add a tally to the aces reduced. 
				dealerHandValue -= 10; //reduce the value of the ace by 10, from 11 to 1, to make sure that the player does not go over. 
			}//end if
			
			dealerHandValue += getCardValue(dealerHand.get(0));
			if(dealerHandValue > 21)
			{
				dealerAceCount = 0;
				for(int i = 0; i < dealerHand.size(); i++)
				{
					if(dealerHand.get(i).contains("Ace"))
					{
						dealerAceCount++;
					}
				}//end for
				
				if(dealerAceCount > 0 && dealerAcesReduced < dealerAceCount) //compares the amount of aces to the amount of times that the aces have been reduced
				{
					dealerHandValue -= 10;
					dealerAcesReduced += 1;
				}//end if
			}
			
			if(dealerHandValue > 21)
			{
				dealerBust = true;
			}
			
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("\nYour final hand: " + playerHand + " [" + playerHandValue + "]");
		System.out.println("\nDealer final hand: " + dealerHand + " [" + dealerHandValue + "]");
		
		//win conditionals
		winConditions(dealerBust, playerBust, dealerHandValue, playerHandValue);

		System.out.println("\nTo start a new game, press any key.");
		System.out.println("To quit the game, press Q.");
		System.out.print("Enter your choice here: ");
		String playAgain = scnr.next().toUpperCase();
		
		switch(playAgain)
		{
		case "Q":
			newGame = false;
		break;
		
		default:
			newGame = true;
		break;
		}
	System.out.println("----------------------------------------------------\n");
	}while(newGame);
	
	System.out.println("Thanks for playing, goodbye!");
	}//end class

	public static void winConditions(boolean dealerResult, boolean playerResult, int dealerHandValue, int playerHandValue)
	{
		if(dealerResult && playerResult == false) //if the dealer busts, but the player has not
		{
			System.out.println("\nDealer has busted!");
			System.out.println("Player Win!");
		}
		else if(playerResult && dealerResult == false) //if the player busts, but the dealer has not
		{
			System.out.println("\nPlayer has busted!");
			System.out.println("House Win!");
		}
		
		else if(dealerHandValue >= playerHandValue && playerResult == false) //if neither have busted, but the dealer has a bigger hand
		{
			System.out.println("\nHouse Win!");
		}
		
		else if(dealerHandValue < playerHandValue && playerResult == false) //if neither have busted, but the player has a bigger hand
		{
			System.out.println("\nPlayer Win!");
		}
	}
	
	//This method will build a deck of cards from two arrays of suit and rank
	public static void buildDeck(ArrayList<String> deck)
	{
		String [] suitArray = {"Spades", "Clubs", "Hearts", "Diamonds"};
		String [] rankArray = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
		
		for(int i = 0; i < suitArray.length; i++)
		{
			for(int j = 0; j < rankArray.length; j++)
			{
				deck.add(rankArray[j] + " of " + suitArray[i]);
			}
		}
		
	}//end class
	
	//This method will retrieve a card from the deck, and fill the empty space with a no value token
	public static String getCardFromDeck(ArrayList<String> deck)

	{
		Random rdm = new Random();
		String card = "";
		
		int cardIndex = rdm.nextInt(52);
		card = deck.get(cardIndex);
		
		//IMPORTANT! Set this to null, which represents an empty space for the card, ensuring it can't get drawn again. 
		deck.set(cardIndex, "null");
		return card;
	}//end class
	
	//This method will add a card from the deck to a hand to the first element in the ArrayList
	public static void addCardToHand(ArrayList<String> deck, ArrayList<String> hand, int numCards)
	{
		boolean duplicate;
		String cardToken = "";
		for(int i = 0; i < numCards; i++)
		{
			do
			{
				duplicate = false;
				cardToken = getCardFromDeck(deck);
				if(cardToken.equals("null"))
				{
					//duplicate card found, loop it to find another
					duplicate = true;
				}
				
			}while(duplicate);
			
			//add the non-duplicate card to the hand
			hand.add(0, cardToken);
		}
	}//end class
	
	//This method will attach a value to a card in the deck based on the rank.
	
	//This method will attach a numerical value to the card drawn
	public static int getCardValue(String card)
	{
		int cardValue = 0;
		if(card.contains("Ace"))
		{
			cardValue = 11;
		}
		else if(card.contains("Two"))
		{
			cardValue = 2;
		}
		else if(card.contains("Three"))
		{
			cardValue = 3;
		}
		else if(card.contains("Four"))
		{
			cardValue = 4;
		}
		else if(card.contains("Five"))
		{
			cardValue = 5;
		}
		else if(card.contains("Six"))
		{
			cardValue = 6;
		}
		else if(card.contains("Seven"))
		{
			cardValue = 7;
		}
		else if(card.contains("Eight"))
		{
			cardValue = 8;
		}
		else if(card.contains("Nine"))
		{
			cardValue = 9;
		}
		else if(card.contains("Ten") || card.contains("Jack") || card.contains("Queen") || card.contains("King"))
		{
			cardValue = 10;
		}
		return cardValue;
	}//end class
}

