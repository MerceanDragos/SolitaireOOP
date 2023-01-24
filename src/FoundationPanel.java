import javax.swing.*;
import java.awt.*;

public class FoundationPanel extends StockPanel {

    private final JLabel suiteLabel;

    FoundationPanel ( Board.Pile correspondingPile, Board board ) {
        super ( correspondingPile );
        correspondingPile.pilePanel = this;

        JLabel newSuiteLabel = new JLabel ( );
        newSuiteLabel.setForeground ( new Color ( 0x34a249 ) );
        newSuiteLabel.setBackground ( new Color ( 0x2b7b3b ) );
        newSuiteLabel.setPreferredSize ( new Dimension ( CardPanel.cardWidth - 6, CardPanel.cardHeight - 6 ) );
        newSuiteLabel.setHorizontalAlignment ( JLabel.CENTER );
        newSuiteLabel.setVerticalAlignment ( JLabel.CENTER );
        newSuiteLabel.setFont ( new Font ( null, Font.BOLD, 70 ) );

        if ( correspondingPile == board.SpadesFoundation )
            newSuiteLabel.setText ( "♠" );
        else if ( correspondingPile == board.DiamondsFoundation )
            newSuiteLabel.setText ( "♦" );
        else if ( correspondingPile == board.ClubsFoundation )
            newSuiteLabel.setText ( "♣" );
        else if ( correspondingPile == board.HeartsFoundation )
            newSuiteLabel.setText ( "♥" );

        suiteLabel = newSuiteLabel;
        add ( newSuiteLabel );

        setBackground ( new Color ( 0x2b7b3b ) );
        setPreferredSize ( new Dimension ( CardPanel.cardWidth, CardPanel.cardHeight ) );
        setBorder ( BorderFactory.createLineBorder ( new Color ( 0x34a249 ), 3 ) );
    }

    void addAllCards ( Board.Pile pile ) {
        for ( int i = pile.size ( ) - 1; i >= 0; i-- )
            add ( pile.CardAt ( i ).getCorrespondingPanel ( ) );
    }

    public void updateFoundation ( ) {
        removeAll ( );
        addAllCards ( correspondingPile );
        if ( getComponentCount ( ) == 0 ) {
            setBorder ( BorderFactory.createLineBorder ( new Color ( 0x34a249 ), 3 ) );
            add ( suiteLabel );
        }
        else
            setBorder ( null );
        revalidate ( );
        repaint ( );
    }

}