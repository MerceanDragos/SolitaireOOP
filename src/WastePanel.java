import javax.swing.*;
import java.awt.*;

public class WastePanel extends PilePanel {

    WastePanel ( Board.Pile correspondingPile ) {
        super ( correspondingPile );
        setBackground ( new Color ( 0x2b7b3b ) );
        setPreferredSize ( new Dimension ( CardPanel.cardWidth, CardPanel.cardHeight ) );
    }

    public void updateWaste ( ) {
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
