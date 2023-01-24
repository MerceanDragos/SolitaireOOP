public class Card {
    private final CardNumber number;
    private final CardSuite suite;
    private final CardColor color;
    private boolean faceUp;

    private CardCoverState state;

    private CardPanel correspondingPanel;

    public Card ( CardNumber number, CardSuite suite ) {
        this.suite = suite;
        this.number = number;
        if ( this.suite == CardSuite.HEARTS || this.suite == CardSuite.DIAMONDS )
            this.color = CardColor.RED;
        else
            this.color = CardColor.BLACK;
        state = CardCoverState.COVERED;
    }

    public CardNumber getNumber ( ) {
        return number;
    }

    public CardSuite getSuite ( ) {
        return suite;
    }

    public CardColor getColor ( ) {
        return color;
    }

    public boolean isFaceUp ( ) {
        return faceUp;
    }

    public void faceUp ( ) {
        faceUp = true;
    }

    public void faceDown ( ) {
        this.faceUp = false;
    }

    public void setState ( CardCoverState newState ) {
        state = newState;
    }

    public CardCoverState getState ( ) {
        return state;
    }

    public void setCorrespondingPanel ( CardPanel correspondingCardPanel ) {
        correspondingPanel = correspondingCardPanel;
    }

    public CardPanel getCorrespondingPanel ( ) {
        return correspondingPanel;
    }
}