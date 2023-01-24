import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

public class Board {

    public static class Pile extends Stack< Card > {

        Object pilePanel = null;

        public Card top ( ) {
            try {
                return this.elementAt ( this.size ( ) - 1 );
            }
            catch ( ArrayIndexOutOfBoundsException e ) {
                throw new EmptyStackException ( );
            }
        }

        public Card CardAt ( int index ) {
            return this.elementAt ( this.size ( ) - 1 - index );
        }

        private void turnAll ( ) {
            int i = 0;
            while ( true ) {
                try {
                    this.elementAt ( i ).faceDown ( );
                    i++;
                }
                catch ( ArrayIndexOutOfBoundsException e ) {
                    break;
                }
            }
        }

        private void unTurnAll ( ) {
            int i = 0;
            while ( true ) {
                try {
                    this.elementAt ( i ).faceUp ( );
                    i++;
                }
                catch ( ArrayIndexOutOfBoundsException e ) {
                    break;
                }
            }
        }

        /*private void printPile ( int nrOfCards ) {
            if ( nrOfCards == 0 )
                nrOfCards = size ( );

            for ( int i = Math.max ( size ( ) - nrOfCards, 0 ); i < size ( ); i++ ) {
                if ( !get ( i ).isFaceUp ( ) ) {
                    System.out.print ( TextColor.GREEN + "▮" + TextColor.RESET );
                    continue;
                }

                String name = "";

                switch ( get ( i ).getNumber ( ) ) {
                    case ACE -> name += "A";
                    case TWO -> name += "2";
                    case THREE -> name += "3";
                    case FOUR -> name += "4";
                    case FIVE -> name += "5";
                    case SIX -> name += "6";
                    case SEVEN -> name += "7";
                    case EIGHT -> name += "8";
                    case NINE -> name += "9";
                    case TEN -> name += "10";
                    case JACK -> name += "J";
                    case QUEEN -> name += "Q";
                    case KING -> name += "K";
                }

                switch ( get ( i ).getSuite ( ) ) {
                    case SPADES -> name += "♠";
                    case DIAMONDS -> name += "♦";
                    case CLUBS -> name += "♣";
                    case HEARTS -> name += "♥";


                }

                switch ( get ( i ).getColor ( ) ) {
                    case RED -> System.out.print ( TextColor.RED );
                    case BLACK -> System.out.print ( TextColor.BLUE );
                }

                System.out.print ( name + TextColor.RESET + " " );
            }

            System.out.println ( );
        }*/

        private void fixStock ( ) {
            for ( int i = 0; i < size ( ); i++ ) {
                CardAt ( i ).faceDown ( );
                if ( i != size ( ) - 1 )
                    CardAt ( i ).setState ( CardCoverState.COVERED );
                else
                    CardAt ( i ).setState ( CardCoverState.UNCOVERED );
            }
        }

        private void fixWaste ( ) {
            for ( int i = 0; i < size ( ); i++ ) {
                CardAt ( i ).faceUp ( );
                if ( i == 0 )
                    CardAt ( i ).setState ( CardCoverState.UNCOVERED );
                else if ( i == 1 || i == 2 )
                    CardAt ( i ).setState ( CardCoverState.PARTIALLY_COVERED );
                else
                    CardAt ( i ).setState ( CardCoverState.COVERED );

            }
        }

        private void fixPile ( ) {
            for ( int i = 0; i < size ( ); i++ ) {
                if ( i != 0 )
                    CardAt ( i ).setState ( CardCoverState.PARTIALLY_COVERED );
                else
                    CardAt ( i ).setState ( CardCoverState.UNCOVERED );
            }

            if ( !empty ( ) )
                top ( ).faceUp ( );
        }

        private void fixFoundation ( ) {
            if ( size ( ) > 1 ) {
                CardAt ( 1 ).setState ( CardCoverState.COVERED );
            }

            if ( !empty ( ) ) {
                top ( ).setState ( CardCoverState.UNCOVERED );
                top ( ).faceUp ( );
            }
        }

        public int indexOf ( Card card ) {
            int i = 0;

            while ( i < size ( ) && CardAt ( i ) != card )
                i++;

            return i;
        }
    }

    private static class Move {
        boolean browseMove;

        boolean revealedCard;
        Pile source;
        Pile destination;
        int nrOfCards;

        private Move ( int nrOfCards ) {
            browseMove = true;
            this.nrOfCards = nrOfCards;
        }

        private Move ( boolean revealedCard, Pile source, Pile destination, int nrOfCards ) {
            this.revealedCard = revealedCard;
            this.source = source;
            this.destination = destination;
            this.nrOfCards = nrOfCards;
        }
    }

    public static class InvalidMoveException extends Exception {
    }

    public Stack< Move > Moves = new Stack< Move > ( );
    private Instant start;
    private Instant end;

    Pile Stock = new Pile ( );
    Pile Waste = new Pile ( );

    Pile Pile1 = new Pile ( );
    Pile Pile2 = new Pile ( );
    Pile Pile3 = new Pile ( );
    Pile Pile4 = new Pile ( );
    Pile Pile5 = new Pile ( );
    Pile Pile6 = new Pile ( );
    Pile Pile7 = new Pile ( );

    Pile SpadesFoundation = new Pile ( );
    Pile DiamondsFoundation = new Pile ( );
    Pile ClubsFoundation = new Pile ( );
    Pile HeartsFoundation = new Pile ( );

    private void fixUp ( Pile pile ) {
        if ( pile.equals ( Stock ) )
            Stock.fixStock ( );
        else if ( pile.equals ( Waste ) )
            pile.fixWaste ( );
        else if ( pile.equals ( SpadesFoundation ) || pile.equals ( DiamondsFoundation ) || pile.equals ( ClubsFoundation ) || pile.equals ( HeartsFoundation ) )
            pile.fixFoundation ( );
        else
            pile.fixPile ( );
    }

    private boolean validMove ( Pile source, Pile destination, int quantity ) {
        if ( destination == this.SpadesFoundation )
            return quantity == 1 && source.top ( ).getSuite ( ) == CardSuite.SPADES && ( ( destination.empty ( ) && source.top ( ).getNumber ( ) == CardNumber.ACE ) || ( !destination.empty ( ) && source.top ( ).getNumber ( ).ordinal ( ) == destination.top ( ).getNumber ( ).ordinal ( ) + 1 ) );
        else if ( destination == this.DiamondsFoundation )
            return quantity == 1 && source.top ( ).getSuite ( ) == CardSuite.DIAMONDS && ( ( destination.empty ( ) && source.top ( ).getNumber ( ) == CardNumber.ACE ) || ( !destination.empty ( ) && source.top ( ).getNumber ( ).ordinal ( ) == destination.top ( ).getNumber ( ).ordinal ( ) + 1 ) );
        else if ( destination == this.ClubsFoundation )
            return quantity == 1 && source.top ( ).getSuite ( ) == CardSuite.CLUBS && ( ( destination.empty ( ) && source.top ( ).getNumber ( ) == CardNumber.ACE ) || ( !destination.empty ( ) && source.top ( ).getNumber ( ).ordinal ( ) == destination.top ( ).getNumber ( ).ordinal ( ) + 1 ) );
        else if ( destination == this.HeartsFoundation )
            return quantity == 1 && source.top ( ).getSuite ( ) == CardSuite.HEARTS && ( ( destination.empty ( ) && source.top ( ).getNumber ( ) == CardNumber.ACE ) || ( !destination.empty ( ) && source.top ( ).getNumber ( ).ordinal ( ) == destination.top ( ).getNumber ( ).ordinal ( ) + 1 ) );
        else if ( destination.empty ( ) )
            return quantity <= source.size ( ) && source.CardAt ( quantity - 1 ).isFaceUp ( ) && source.CardAt ( quantity - 1 ).getNumber ( ) == CardNumber.KING;
        else
            return quantity <= source.size ( ) && source.CardAt ( quantity - 1 ).isFaceUp ( ) && source.CardAt ( quantity - 1 ).getColor ( ) != destination.top ( ).getColor ( ) && source.CardAt ( quantity - 1 ).getNumber ( ).ordinal ( ) + 1 == destination.top ( ).getNumber ( ).ordinal ( );
    }

    public Board ( ) {

        for ( CardSuite cardSuit : CardSuite.values ( ) )
            for ( CardNumber cardNumber : CardNumber.values ( ) )
                this.Stock.push ( new Card ( cardNumber, cardSuit ) );

    }

    private void gatherCards ( ) {

        while ( !Waste.empty ( ) )
            Stock.push ( Waste.pop ( ) );

        while ( !Pile1.empty ( ) )
            Stock.push ( Pile1.pop ( ) );

        while ( !Pile2.empty ( ) )
            Stock.push ( Pile2.pop ( ) );

        while ( !Pile3.empty ( ) )
            Stock.push ( Pile3.pop ( ) );

        while ( !Pile4.empty ( ) )
            Stock.push ( Pile4.pop ( ) );

        while ( !Pile5.empty ( ) )
            Stock.push ( Pile5.pop ( ) );

        while ( !Pile6.empty ( ) )
            Stock.push ( Pile6.pop ( ) );

        while ( !Pile7.empty ( ) )
            Stock.push ( Pile7.pop ( ) );

        while ( !SpadesFoundation.empty ( ) )
            Stock.push ( SpadesFoundation.pop ( ) );

        while ( !DiamondsFoundation.empty ( ) )
            Stock.push ( DiamondsFoundation.pop ( ) );

        while ( !ClubsFoundation.empty ( ) )
            Stock.push ( ClubsFoundation.pop ( ) );

        while ( !HeartsFoundation.empty ( ) )
            Stock.push ( HeartsFoundation.pop ( ) );

        for ( int i = 0; i < 52; i++ )
            Stock.CardAt ( i ).faceDown ( );
    }

    public void newGame ( ) {
        Moves.clear ( );

        if ( Stock.size ( ) != 52 )
            gatherCards ( );


        Collections.shuffle ( Stock );

        Pile1.push ( Stock.pop ( ) );

        for ( int i = 0; i < 2; i++ )
            Pile2.push ( Stock.pop ( ) );

        for ( int i = 0; i < 3; i++ )
            Pile3.push ( Stock.pop ( ) );

        for ( int i = 0; i < 4; i++ )
            Pile4.push ( Stock.pop ( ) );

        for ( int i = 0; i < 5; i++ )
            Pile5.push ( Stock.pop ( ) );

        for ( int i = 0; i < 6; i++ )
            Pile6.push ( Stock.pop ( ) );

        for ( int i = 0; i < 7; i++ )
            Pile7.push ( Stock.pop ( ) );

        Stock.fixStock ( );

        Pile1.fixPile ( );
        Pile2.fixPile ( );
        Pile3.fixPile ( );
        Pile4.fixPile ( );
        Pile5.fixPile ( );
        Pile6.fixPile ( );
        Pile7.fixPile ( );

        SpadesFoundation.fixFoundation ( );
        DiamondsFoundation.fixFoundation ( );
        ClubsFoundation.fixFoundation ( );
        HeartsFoundation.fixFoundation ( );
    }

    /*public void newWinnableGame ( ) {
        if ( Stock.size ( ) != 52 )
            gatherCards ( );

        for ( int i = 0; i < 13; i++ )
            Pile1.push ( Stock.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            HeartsFoundation.push ( Pile1.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            Pile1.push ( Stock.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            ClubsFoundation.push ( Pile1.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            Pile1.push ( Stock.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            DiamondsFoundation.push ( Pile1.pop ( ) );
        for ( int i = 0; i < 13; i++ )
            Pile1.push ( Stock.pop ( ) );
        for ( int i = 0; i < 12; i++ )
            SpadesFoundation.push ( Pile1.pop ( ) );


        Stock.fixStock ( );

        Pile1.fixPile ( );
        Pile2.fixPile ( );
        Pile3.fixPile ( );
        Pile4.fixPile ( );
        Pile5.fixPile ( );
        Pile6.fixPile ( );
        Pile7.fixPile ( );

        SpadesFoundation.fixFoundation ( );
        DiamondsFoundation.fixFoundation ( );
        ClubsFoundation.fixFoundation ( );
        HeartsFoundation.fixFoundation ( );
    }*/

/*    public void printBoard ( ) {

        System.out.println ( "Stock: " + this.Stock.size ( ) );
        System.out.print ( "0.Waste: " );
        this.Waste.printPile ( 3 );
        System.out.print ( "1.Pile: " );
        this.Pile1.printPile ( 0 );
        System.out.print ( "2.Pile: " );
        this.Pile2.printPile ( 0 );
        System.out.print ( "3.Pile: " );
        this.Pile3.printPile ( 0 );
        System.out.print ( "4.Pile: " );
        this.Pile4.printPile ( 0 );
        System.out.print ( "5.Pile: " );
        this.Pile5.printPile ( 0 );
        System.out.print ( "6.Pile: " );
        this.Pile6.printPile ( 0 );
        System.out.print ( "7.Pile: " );
        this.Pile7.printPile ( 0 );
        System.out.print ( "8.Spades: " );
        this.SpadesFoundation.printPile ( 0 );
        System.out.print ( "9.Diamonds: " );
        this.DiamondsFoundation.printPile ( 0 );
        System.out.print ( "10.Clubs: " );
        this.ClubsFoundation.printPile ( 0 );
        System.out.print ( "11.Hearts: " );
        this.HeartsFoundation.printPile ( 0 );
        System.out.println ( );

    }*/

    public void move ( Pile source, Pile destination, int quantity ) throws InvalidMoveException {

        if ( destination == Waste )
            throw new InvalidMoveException ( );

        if ( validMove ( source, destination, quantity ) ) {


            Pile aux = new Pile ( );

            for ( int i = 0; i < quantity; i++ )
                aux.push ( ( Card ) source.pop ( ) );

            for ( int i = 0; i < quantity; i++ )
                destination.push ( ( Card ) aux.pop ( ) );

            Move move = new Move ( source.size ( ) != 0 && !source.top ( ).isFaceUp ( ), source, destination, quantity );
            Moves.push ( move );

            fixUp ( source );
            fixUp ( destination );
        }
        else {
            throw new InvalidMoveException ( );
        }
    }

    public void browse ( ) {
        if ( Moves.empty ( ) )
            startTime ( );

        if ( Stock.empty ( ) && Waste.empty ( ) )
            return;

        int i = 0;
        boolean flag = false;
        if ( !Stock.empty ( ) ) {
            while ( !Stock.empty ( ) && i < 3 ) {
                Waste.push ( Stock.pop ( ) );
                i++;
            }
        }
        else if ( Stock.empty ( ) && !Waste.empty ( ) ) {
            flag = true;
            while ( !Waste.empty ( ) ) {
                Stock.push ( Waste.pop ( ) );
            }

            Stock.turnAll ( );
        }

        Moves.push ( new Move ( ( flag ) ? 0 : i ) );

        Stock.fixStock ( );
        Waste.fixWaste ( );
    }

    public void undo ( ) {
        if ( this.Moves.empty ( ) )
            return;

        Move lastMove = this.Moves.pop ( );

        if ( lastMove.browseMove )
            this.undoBrowse ( lastMove );
        else
            this.undoMove ( lastMove );
    }

    private void undoMove ( Move move ) {
        if ( move.revealedCard )
            move.source.top ( ).faceDown ( );

        Pile aux = new Pile ( );

        for ( int i = 0; i < move.nrOfCards; i++ )
            aux.push ( move.destination.pop ( ) );

        for ( int i = 0; i < move.nrOfCards; i++ )
            move.source.push ( aux.pop ( ) );

        fixUp ( move.source );
        fixUp ( move.destination );
    }

    private void undoBrowse ( Move move ) {
        if ( move.nrOfCards == 0 )
            while ( !Stock.empty ( ) )
                Waste.push ( Stock.pop ( ) );
        else
            for ( int i = 0; i < move.nrOfCards; i++ )
                Stock.push ( Waste.pop ( ) );


        fixUp ( Stock );
        fixUp ( Waste );
    }

    public boolean checkForWin ( ) {
        return this.ClubsFoundation.size ( ) == 13 &&
                this.SpadesFoundation.size ( ) == 13 &&
                this.DiamondsFoundation.size ( ) == 13 &&
                this.HeartsFoundation.size ( ) == 13;
    }

    public void startTime ( ) {
        start = Instant.now ( );
    }

    public void stopTime ( ) {
        end = Instant.now ( );
    }

    public String getTime ( ) {
        long milliSeconds = Duration.between ( start, end ).toMillis ( );

        int minutes = ( int ) ( milliSeconds / 1000 ) / 60;

        int seconds = ( int ) ( milliSeconds / 1000 ) % 60;

        String secondsString = ( ( seconds < 10 ) ? "0" : "" ) + String.valueOf ( seconds );

        return minutes + ":" + secondsString;
    }

    public double getTimeDouble ( ) {
        return ( double ) Duration.between ( start, end ).toMillis ( );
    }

    public int getMoves ( ) {
        return Moves.size ( );
    }

}
