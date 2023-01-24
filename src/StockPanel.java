import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {
    private static final int cardWidth;
    private static final int cardHeight;

    final Board.Pile correspondingPile;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        cardWidth = Math.round ( screenSize.width * 0.0296875f );
        cardHeight = Math.round ( screenSize.height * 0.0722222f );
    }

    StockPanel ( Board.Pile correspondingPile ) {
        super ( new FlowLayout ( FlowLayout.CENTER, 0, 0 ) );

        this.correspondingPile = correspondingPile;
        correspondingPile.pilePanel = this;

        setPreferredSize ( new Dimension ( cardWidth, cardHeight ) );
        setBackground ( new Color ( 0x2b7b3b ) );
    }

    private void addAllCards ( Board.Pile pile ) {
        for ( int i = pile.size ( ) - 1; i >= 0; i-- )
            add ( pile.CardAt ( i ).getCorrespondingPanel ( ) );
    }

    public void updateStock ( ) {
        removeAll ( );
        addAllCards ( correspondingPile );
        if ( getComponentCount ( ) == 0 )
            setBorder ( BorderFactory.createLineBorder ( new Color ( 0x34a249 ), 3 ) );
        else
            setBorder ( null );
        revalidate ( );
        repaint ( );
    }
}