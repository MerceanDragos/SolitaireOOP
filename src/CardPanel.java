import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    public static final int cardWidth;
    public static final int cardHeight;
    public static final int partiallyCoveredCardHeight;

    public final Card correspondingCard;

    private final JPanel upperPanel;
    private final JPanel lowerPanel;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        cardWidth = Math.round ( screenSize.width * 0.0296875f );
        cardHeight = Math.round ( screenSize.height * 0.0722222f );
        partiallyCoveredCardHeight = Math.round ( screenSize.height * 0.0722222f / 4f );
    }

    CardPanel ( Card correspondingCard ) {
        super ( new FlowLayout ( FlowLayout.CENTER, 0, 0 ) );
        this.correspondingCard = correspondingCard;
        upperPanel = new JPanel ( new FlowLayout ( FlowLayout.CENTER, 0, 0 ) );
        lowerPanel = new JPanel ( new FlowLayout ( FlowLayout.CENTER, 0, 0 ) );
        correspondingCard.setCorrespondingPanel ( this );

        setVisible ( false );

        upperPanel.setPreferredSize ( new Dimension ( cardWidth - 2, partiallyCoveredCardHeight - 1 ) );
        lowerPanel.setPreferredSize ( new Dimension ( cardWidth - 2, partiallyCoveredCardHeight * 3 - 1 ) );

        upperPanel.setBackground ( Color.WHITE );
        lowerPanel.setBackground ( Color.WHITE );

        JLabel smallSuite = new JLabel ( );
        JLabel bigSuite = new JLabel ( );
        JLabel number = new JLabel ( );

        smallSuite.setHorizontalAlignment ( JLabel.CENTER );
        smallSuite.setVerticalAlignment ( JLabel.CENTER );
        bigSuite.setHorizontalAlignment ( JLabel.CENTER );
        bigSuite.setVerticalAlignment ( JLabel.BOTTOM );
        number.setHorizontalAlignment ( JLabel.CENTER );
        number.setVerticalAlignment ( JLabel.CENTER );

        smallSuite.setFont ( new Font ( null, Font.BOLD, 25 ) );
        bigSuite.setFont ( new Font ( null, Font.BOLD, 70 ) );
        number.setFont ( new Font ( null, Font.BOLD, 20 ) );

        smallSuite.setPreferredSize ( new Dimension ( cardWidth / 2 - 2, partiallyCoveredCardHeight - 1 ) );
        number.setPreferredSize ( new Dimension ( cardWidth / 2 - 2, partiallyCoveredCardHeight - 1 ) );
        bigSuite.setPreferredSize ( new Dimension ( cardWidth - 2, partiallyCoveredCardHeight * 3 - 1 ) );

        switch ( correspondingCard.getNumber ( ) ) {
            case ACE -> number.setText ( "A" );
            case TWO -> number.setText ( "2" );
            case THREE -> number.setText ( "3" );
            case FOUR -> number.setText ( "4" );
            case FIVE -> number.setText ( "5" );
            case SIX -> number.setText ( "6" );
            case SEVEN -> number.setText ( "7" );
            case EIGHT -> number.setText ( "8" );
            case NINE -> number.setText ( "9" );
            case TEN -> number.setText ( "10" );
            case JACK -> number.setText ( "J" );
            case QUEEN -> number.setText ( "Q" );
            case KING -> number.setText ( "K" );
        }

        switch ( correspondingCard.getSuite ( ) ) {
            case SPADES -> {
                smallSuite.setText ( "♠" );
                bigSuite.setText ( "♠" );
            }
            case DIAMONDS -> {
                smallSuite.setText ( "♦" );
                bigSuite.setText ( "♦" );
            }
            case CLUBS -> {
                smallSuite.setText ( "♣" );
                bigSuite.setText ( "♣" );
            }
            case HEARTS -> {
                smallSuite.setText ( "♥" );
                bigSuite.setText ( "♥" );
            }
        }

        if ( correspondingCard.getColor ( ) == CardColor.RED ) {
            smallSuite.setForeground ( Color.RED );
            bigSuite.setForeground ( Color.RED );
            number.setForeground ( Color.RED );
        }

        upperPanel.add ( number );
        upperPanel.add ( smallSuite );
        lowerPanel.add ( bigSuite );

        add ( upperPanel );
        add ( lowerPanel );
    }

    public void updateCard ( ) {
        if ( correspondingCard.isFaceUp ( ) ) {
            if ( correspondingCard.getState ( ) == CardCoverState.COVERED )
                setVisible ( false );
            else if ( correspondingCard.getState ( ) == CardCoverState.PARTIALLY_COVERED ) {
                setVisible ( true );
                setBackground ( Color.WHITE );
                setBorder ( BorderFactory.createMatteBorder ( 1, 1, 0, 1, Color.LIGHT_GRAY ) );
                setPreferredSize ( new Dimension ( cardWidth, partiallyCoveredCardHeight ) );
                upperPanel.setVisible ( true );
                lowerPanel.setVisible ( false );
            }
            else {
                setVisible ( true );
                setBackground ( Color.WHITE );
                setBorder ( BorderFactory.createLineBorder ( Color.LIGHT_GRAY, 1 ) );
                setPreferredSize ( new Dimension ( cardWidth, cardHeight ) );
                upperPanel.setVisible ( true );
                lowerPanel.setVisible ( true );
            }
        }
        else {
            if ( correspondingCard.getState ( ) == CardCoverState.COVERED ) {
                setVisible ( false );
            }
            else if ( correspondingCard.getState ( ) == CardCoverState.PARTIALLY_COVERED ) {
                setVisible ( true );
                setBackground ( Color.BLUE );
                setBorder ( BorderFactory.createMatteBorder ( 3, 3, 0, 3, Color.WHITE ) );
                setPreferredSize ( new Dimension ( cardWidth, partiallyCoveredCardHeight ) );
                upperPanel.setVisible ( false );
                lowerPanel.setVisible ( false );
            }
            else {
                setVisible ( true );
                setBackground ( Color.BLUE );
                setBorder ( BorderFactory.createLineBorder ( Color.WHITE, 3 ) );
                setPreferredSize ( new Dimension ( cardWidth, cardHeight ) );
                upperPanel.setVisible ( false );
                lowerPanel.setVisible ( false );
            }
        }
    }
}