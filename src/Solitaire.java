import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Solitaire extends JFrame implements MouseListener {

    @Override
    public void mouseReleased ( MouseEvent event ) {

        if ( event.getSource ( ) == controlPanels[0] ) {
            board.undo ( );
            updateAll ( );
        }
        else if ( event.getSource ( ) == controlPanels[1] ) {
            board.newGame ( );
            updateAll ( );
        }
        else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == stockPanel || event.getSource ( ) == stockPanel ) {
            board.browse ( );
            source = null;
            updateAll ( );
        }
        else if ( source != null ) {
            if ( event.getSource ( ) == mainPanels[2] || event.getSource ( ) == mainPanels[1] || event.getSource ( ) == mainPanels[3] || event.getSource ( ) == mainPanels[0] )
                source = null;
            else {
                try {
                    Board.Pile destination;

                    if ( event.getSource ( ).getClass ( ).getName ( ).equals ( "CardPanel" ) ) {
                        if ( ( ( CardPanel ) event.getSource ( ) ).getParent ( ).getParent ( ) == mainPanels[2] || ( ( CardPanel ) event.getSource ( ) ).getParent ( ).getParent ( ) == mainPanels[1] )
                            destination = ( ( PilePanel ) ( ( CardPanel ) event.getSource ( ) ).getParent ( ) ).correspondingPile;
                        else
                            destination = ( ( StockPanel ) ( ( CardPanel ) event.getSource ( ) ).getParent ( ) ).correspondingPile;
                    }
                    else {
                        if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) != mainPanels[3] )
                            destination = ( ( PilePanel ) event.getSource ( ) ).correspondingPile;
                        else
                            destination = ( ( StockPanel ) event.getSource ( ) ).correspondingPile;
                    }

                    if ( source == destination )
                        source = null;
                    else {
                        board.move ( source, destination, quantity );
                        if ( source.pilePanel == foundationPanels[0] || source.pilePanel == foundationPanels[1] || source.pilePanel == foundationPanels[2] || source.pilePanel == foundationPanels[3] )
                            ( ( FoundationPanel ) source.pilePanel ).updateFoundation ( );
                        else if ( source.pilePanel == wastePanel )
                            ( ( WastePanel ) source.pilePanel ).updateWaste ( );

                        if ( destination.pilePanel == foundationPanels[0] || destination.pilePanel == foundationPanels[1] || destination.pilePanel == foundationPanels[2] || destination.pilePanel == foundationPanels[3] )
                            ( ( StockPanel ) destination.pilePanel ).updateStock ( );
                        else
                            ( ( PilePanel ) destination.pilePanel ).updatePile ( );
                    }
                }
                catch ( Board.InvalidMoveException exception ) {
                    System.out.println ( "Invalid move" );
                }
                finally {
                    source = null;
                }
            }

            if ( board.checkForWin ( ) ) {
                board.stopTime ( );
                winPopUp ( );
            }
            updateAll ( );
        }
        else if ( event.getSource ( ) != mainPanels[2] && event.getSource ( ) != mainPanels[1] && event.getSource ( ) != mainPanels[3] && event.getSource ( ) != mainPanels[0] ) {
            if ( board.Moves.empty ( ) )
                board.startTime ( );

            if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == wastePanel )
                source = wastePanel.correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[0] )
                source = pilePanels[0].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[1] )
                source = pilePanels[1].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[2] )
                source = pilePanels[2].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[3] )
                source = pilePanels[3].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[4] )
                source = pilePanels[4].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[5] )
                source = pilePanels[5].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == pilePanels[6] )
                source = pilePanels[6].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == foundationPanels[0] )
                source = foundationPanels[0].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == foundationPanels[1] )
                source = foundationPanels[1].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == foundationPanels[2] )
                source = foundationPanels[2].correspondingPile;
            else if ( ( ( JPanel ) event.getSource ( ) ).getParent ( ) == foundationPanels[3] )
                source = foundationPanels[3].correspondingPile;

            if ( source != null )
                quantity = source.indexOf ( ( ( CardPanel ) event.getSource ( ) ).correspondingCard ) + 1;
        }
    }

    @Override
    public void mouseEntered ( MouseEvent e ) {
        assert true;
    }

    @Override
    public void mouseExited ( MouseEvent e ) {
        assert true;
    }

    @Override
    public void mousePressed ( MouseEvent e ) {
        assert true;
    }

    @Override
    public void mouseClicked ( MouseEvent e ) {
        assert true;
    }

    public final Board board = new Board ( );

    final JPanel[] mainPanels = SolitaireFactory.mainPanelsBuilder ( );
    final JPanel[] controlPanels = SolitaireFactory.controlPanelsBuilder ( mainPanels[0] );

    final StockPanel stockPanel = new StockPanel ( board.Stock );
    final WastePanel wastePanel = new WastePanel ( board.Waste );
    final PilePanel[] pilePanels = new PilePanel[]
            {
                    new PilePanel ( board.Pile1 ),
                    new PilePanel ( board.Pile2 ),
                    new PilePanel ( board.Pile3 ),
                    new PilePanel ( board.Pile4 ),
                    new PilePanel ( board.Pile5 ),
                    new PilePanel ( board.Pile6 ),
                    new PilePanel ( board.Pile7 )
            };
    final FoundationPanel[] foundationPanels = new FoundationPanel[]
            {
                    new FoundationPanel ( board.SpadesFoundation, board ),
                    new FoundationPanel ( board.DiamondsFoundation, board ),
                    new FoundationPanel ( board.ClubsFoundation, board ),
                    new FoundationPanel ( board.HeartsFoundation, board )
            };
    final CardPanel[] cardPanels = SolitaireFactory.cardPanelsBuilder ( board );

    private Board.Pile source = null;
    private int quantity;

    public Solitaire ( ) {

        SolitaireFactory.frameSetUp ( this );

        board.newGame ( );

        for ( int i = 0; i < 4; i++ )
            mainPanels[i].addMouseListener ( this );

        for ( int i = 0; i < 2; i++ )
            controlPanels[i].addMouseListener ( this );

        stockPanel.addMouseListener ( this );
        wastePanel.addMouseListener ( this );

        for ( int i = 0; i < 7; i++ )
            pilePanels[i].addMouseListener ( this );

        for ( int i = 0; i < 4; i++ )
            foundationPanels[i].addMouseListener ( this );

        for ( int i = 0; i < 52; i++ )
            cardPanels[i].addMouseListener ( this );


        add ( mainPanels[0], BorderLayout.NORTH );
        add ( mainPanels[1], BorderLayout.WEST );
        add ( mainPanels[2], BorderLayout.CENTER );
        add ( mainPanels[3], BorderLayout.EAST );

        mainPanels[1].add ( stockPanel );
        mainPanels[1].add ( wastePanel );

        for ( int i = 0; i < 7; i++ )
            mainPanels[2].add ( pilePanels[i] );

        SolitaireFactory.middlePanelLayoutSetUp ( mainPanels[2], pilePanels );

        for ( int i = 0; i < 4; i++ )
            mainPanels[3].add ( foundationPanels[i] );

        updateAll ( );

        pack ( );
        setVisible ( true );
    }

    private void winPopUp ( ) {
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

        JFrame winPopUp = new JFrame ( );

        winPopUp.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
        winPopUp.setResizable ( false );
        winPopUp.setLocation ( ( screenSize.width - 320 ) / 2, ( screenSize.height - 180 ) / 2 );

        SpringLayout layout = new SpringLayout ( );

        JPanel mainPanel = new JPanel ( layout );
        mainPanel.setBackground ( new Color ( 0x34a249 ) );
        mainPanel.setPreferredSize ( new Dimension ( 320, 180 ) );

        JLabel youWinText = new JLabel ( "You win!" );
        JLabel timeText = new JLabel ( "Time: " + board.getTime ( ) );
        JLabel movesText = new JLabel ( "Moves: " + board.getMoves ( ) );

        youWinText.setForeground ( Color.WHITE );
        youWinText.setHorizontalAlignment ( JLabel.CENTER );
        youWinText.setVerticalAlignment ( JLabel.CENTER );
        youWinText.setFont ( new Font ( null, Font.BOLD, 15 ) );
        youWinText.setPreferredSize ( new Dimension ( 80, 40 ) );

        timeText.setForeground ( Color.WHITE );
        timeText.setHorizontalAlignment ( JLabel.CENTER );
        timeText.setVerticalAlignment ( JLabel.CENTER );
        timeText.setFont ( new Font ( null, Font.BOLD, 15 ) );
        timeText.setPreferredSize ( new Dimension ( 80, 40 ) );

        movesText.setForeground ( Color.WHITE );
        movesText.setHorizontalAlignment ( JLabel.CENTER );
        movesText.setVerticalAlignment ( JLabel.CENTER );
        movesText.setFont ( new Font ( null, Font.BOLD, 15 ) );
        movesText.setPreferredSize ( new Dimension ( 80, 40 ) );

        mainPanel.add ( youWinText );
        mainPanel.add ( timeText );
        mainPanel.add ( movesText );

        layout.putConstraint ( SpringLayout.NORTH, youWinText, 50, SpringLayout.NORTH, mainPanel );
        layout.putConstraint ( SpringLayout.WEST, youWinText, 120, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.NORTH, timeText, 100, SpringLayout.NORTH, mainPanel );
        layout.putConstraint ( SpringLayout.WEST, timeText, 70, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.NORTH, movesText, 100, SpringLayout.NORTH, mainPanel );
        layout.putConstraint ( SpringLayout.WEST, movesText, 170, SpringLayout.WEST, mainPanel );

        winPopUp.add ( mainPanel );

        winPopUp.pack ( );
        winPopUp.setVisible ( true );

        board.newGame ( );

        updateAll ( );
    }

    private void updateAll ( ) {
        for ( int i = 0; i < 52; i++ )
            cardPanels[i].updateCard ( );

        stockPanel.updateStock ( );
        wastePanel.updateWaste ( );

        for ( int i = 0; i < 7; i++ )
            pilePanels[i].updatePile ( );

        for ( int i = 0; i < 4; i++ )
            foundationPanels[i].updateFoundation ( );
    }
}
