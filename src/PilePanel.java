import javax.swing.*;
import java.awt.*;

public class PilePanel extends JPanel {

    final Board.Pile correspondingPile;

    PilePanel ( Board.Pile newCorrespondingPile ) {
        super ( new FlowLayout ( FlowLayout.CENTER, 0, 0 ) );
        setMaximumSize ( new Dimension ( CardPanel.cardWidth, CardPanel.cardHeight + 18 * CardPanel.partiallyCoveredCardHeight ) );
        setBackground ( new Color ( 0x34a249 ) );
        correspondingPile = newCorrespondingPile;
        correspondingPile.pilePanel = this;
    }

    void pack ( ) {
        setPreferredSize ( new Dimension ( CardPanel.cardWidth, ( Math.max ( getComponentCount ( ) - 1, 0 ) ) * CardPanel.partiallyCoveredCardHeight + CardPanel.cardHeight ) );
    }

    void addAllCards ( Board.Pile pile ) {
        for ( int i = pile.size ( ) - 1; i >= 0; i-- )
            add ( pile.CardAt ( i ).getCorrespondingPanel ( ) );
    }

    public void updatePile ( ) {
        removeAll ( );
        addAllCards ( correspondingPile );
        pack ( );
        if ( getComponentCount ( ) == 0 )
            setBorder ( BorderFactory.createLineBorder ( new Color ( 0x2b7b3b ), 3 ) );
        else
            setBorder ( null );
        revalidate ( );
        repaint ( );
    }

}