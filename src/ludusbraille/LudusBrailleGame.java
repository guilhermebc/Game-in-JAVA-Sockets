/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludusbraille;

import Game.Board;
import Game.Piece;
import Game.Player;
import Socket.Client;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Socket.Server;

/**
 *
 * @author guilhermecosta
 */
public class LudusBrailleGame extends javax.swing.JFrame {

    /**
     * Creates new form LudusBrailleGame
     */
    static private Client clientsConfigs;
    private Board board = new Board();
    private Piece pieceType = new Piece(9, 9, true);
    private Player player;
    public int circlePiece, crownPiece;
    public String playerName = "";
    public boolean isDisable = false;
    public boolean isTurn = false;
    Server serverConfigs;

    public LudusBrailleGame() {
        initComponents();
        board.initBoard();
        disableToPlay(false);
        initPieces();
        initPlayer();
        this.logArea.append("Olá, " + playerName + ".\n" + "Bem vindo ao jogo LudusBraile!\nInicie a partida...\n");
        isYourTurn();
    }

    public void initPieces() {
        labelCrownPiece.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Elipse.png")));
        labelCirclePiece.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Coroa.png")));
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
    }

    public void updatePieceSlot(int row, int column, int type) {
        JLabel labelPos = setLabelPosition(row, column);
        if (type == 0) {
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CelulaVazia.png")));
        }
        if (type == 1) {
            circlePiece--;
            piece1.setText(String.valueOf(circlePiece));
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Coroa.png")));
        }
        if (type == 2) {
            crownPiece--;
            piece2.setText(String.valueOf(crownPiece));
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Elipse.png")));
        }
        if (type == 3) {
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CoroaElipse.png")));
        }

    }

    public void initPlayer() {
        playerName = JOptionPane.showInputDialog(null, "Digite o seu nome:");
        super.setTitle(playerName);
    }

    public JLabel setLabelPosition(int labelRow, int labelColumn) {
        JLabel labelPosition = new JLabel();

        if (labelRow == 0 && labelColumn == 0) {
            labelPosition = labelCell00;
        }
        if (labelRow == 0 && labelColumn == 1) {
            labelPosition = labelCell01;
        }
        if (labelRow == 0 && labelColumn == 2) {
            labelPosition = labelCell02;
        }
        if (labelRow == 0 && labelColumn == 3) {
            labelPosition = labelCell03;
        }
        if (labelRow == 1 && labelColumn == 0) {
            labelPosition = labelCell10;
        }
        if (labelRow == 1 && labelColumn == 1) {
            labelPosition = labelCell11;
        }
        if (labelRow == 1 && labelColumn == 2) {
            labelPosition = labelCell12;
        }
        if (labelRow == 1 && labelColumn == 3) {
            labelPosition = labelCell13;
        }
        if (labelRow == 2 && labelColumn == 0) {
            labelPosition = labelCell20;
        }
        if (labelRow == 2 && labelColumn == 1) {
            labelPosition = labelCell21;
        }
        if (labelRow == 2 && labelColumn == 2) {
            labelPosition = labelCell22;
        }
        if (labelRow == 2 && labelColumn == 3) {
            labelPosition = labelCell23;
        }
        return labelPosition;
    }

    public void setLabelData(int row, int column, int typeCell) {
       
        if (circlePiece < 1 || crownPiece < 1) {
            this.logArea.append(playerName + ", voce não tem mais dessa peça para jogar!\n");
            this.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        } else {
            if (typeCell == 0) {
                this.logArea.append(playerName + ", selecione uma peça para jogar!\n");
                this.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
                board.boardTable[row][column] = 0;
            }
            if (typeCell == 1) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 1;
            }
            if (typeCell == 2) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 2;
            }
            if (typeCell == 3) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 3;
            }
        }
        pieceType.setTypePiece(0);
    }
    
    public void checkAvaibleCell() {
        //check if this cell is avaible to set pieces...
    }
    
    public void startGame(String action){
        if(action.equals("1")){
            disableToPlay(true);
        }else{
            action = "0";
            disableToPlay(false);
        }
        this.logArea.append("Partida iniciada, boa sorte aos jogadores!\n");
        this.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
    }
    
    public void disableToPlay(boolean answer){
        
        
        this.isDisable = answer;
        if (isDisable){
            circlePieceBlock.setBackground(Color.GREEN);
            crownPieceBlock.setBackground(Color.GREEN);
            
            circlePieceBlock.setVisible(answer);
            crownPieceBlock.setVisible(answer);
            
            labelCirclePiece.setVisible(answer);
            labelCrownPiece.setVisible(answer);
            
            piece1.setVisible(answer);
            piece2.setVisible(answer);
        }else{
            circlePieceBlock.setBackground(Color.RED);
            crownPieceBlock.setBackground(Color.RED);
            
            circlePieceBlock.setVisible(answer);
            crownPieceBlock.setVisible(answer);
            
            labelCirclePiece.setVisible(answer);
            labelCrownPiece.setVisible(answer);
            
            piece1.setVisible(answer);
            piece2.setVisible(answer);
        }
    }
    
    public void restartGame(String action){
        
        int type = 0;
        for(int row=0; row<3; row++){
            for(int column=0 ; column<4 ; column++){
                updatePieceSlot(row, column, type);
            }
        }
        pieceType.resetPieces();
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
        piece1.setText(String.valueOf(circlePiece));
        piece2.setText(String.valueOf(crownPiece));
        
        if(action.equals("1")){
            disableToPlay(false);    
            startGame(action);
            this.logArea.append("Partida reiniciada! Boa sorte!\n\n");
            this.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        }
        
    }
    
    public void surrenderGame(String action){
        JOptionPane.showConfirmDialog(null, "Você tem certeza que quer desistir?");
        int type = 0;
        for(int row=0; row<3; row++){
            for(int column=0 ; column<4 ; column++){
                updatePieceSlot(row, column, type);
            }
        }
        pieceType.resetPieces();
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
        piece1.setText(String.valueOf(circlePiece));
        piece2.setText(String.valueOf(crownPiece));
        
        if(action.equals("1")){
            disableToPlay(false);    
            this.logArea.setText("\n");
            this.logArea.setText("Você desistiu do jogo! Oponente venceu! Inicie uma nova partida! Não desista!\n"); 
            this.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        }
    }
    
    public void isYourTurn(){
        /*
        FALTOU IMPLEMENTAR
        setar o turno: numero impar para o primeiro cliente e numero par para o segundo cliente,
        variavel countTurnos divisivel == 0 enableToPlay;
        se nao for sua vez: bloco de pecas vermelho
        sua vez: verde.
        */
        
//        this.isTurn = turn;

//        if (turn){
//            circlePieceBlock.setBackground(Color.GREEN);
//            crownPieceBlock.setBackground(Color.GREEN);
//        }else{
//            circlePieceBlock.setBackground(Color.RED);
//            crownPieceBlock.setBackground(Color.RED);
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel = new javax.swing.JPanel();
        sendText = new javax.swing.JTextField();
        scrollChatArea = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        sendBtn = new javax.swing.JButton();
        restartBtn = new javax.swing.JButton();
        quitBtn = new javax.swing.JButton();
        block1 = new javax.swing.JPanel();
        cell00 = new javax.swing.JPanel();
        labelCell00 = new javax.swing.JLabel();
        cell01 = new javax.swing.JPanel();
        labelCell01 = new javax.swing.JLabel();
        cel10 = new javax.swing.JPanel();
        labelCell10 = new javax.swing.JLabel();
        cell11 = new javax.swing.JPanel();
        labelCell11 = new javax.swing.JLabel();
        cell20 = new javax.swing.JPanel();
        labelCell20 = new javax.swing.JLabel();
        cell21 = new javax.swing.JPanel();
        labelCell21 = new javax.swing.JLabel();
        block2 = new javax.swing.JPanel();
        cell23 = new javax.swing.JPanel();
        labelCell23 = new javax.swing.JLabel();
        cell22 = new javax.swing.JPanel();
        labelCell22 = new javax.swing.JLabel();
        cell12 = new javax.swing.JPanel();
        labelCell12 = new javax.swing.JLabel();
        cell13 = new javax.swing.JPanel();
        labelCell13 = new javax.swing.JLabel();
        cell03 = new javax.swing.JPanel();
        labelCell03 = new javax.swing.JLabel();
        cell02 = new javax.swing.JPanel();
        labelCell02 = new javax.swing.JLabel();
        asideBlock = new javax.swing.JPanel();
        piece1 = new javax.swing.JLabel();
        piece2 = new javax.swing.JLabel();
        crownPieceBlock = new javax.swing.JPanel();
        labelCrownPiece = new javax.swing.JLabel();
        circlePieceBlock = new javax.swing.JPanel();
        labelCirclePiece = new javax.swing.JLabel();
        startBtn = new javax.swing.JButton();
        scrollChatArea1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gamePanel.setBackground(new java.awt.Color(56, 63, 81));
        gamePanel.setPreferredSize(new java.awt.Dimension(740, 467));

        sendText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sendTextKeyPressed(evt);
            }
        });

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chatArea.setFocusable(false);
        chatArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                chatAreaCaretUpdate(evt);
            }
        });
        scrollChatArea.setViewportView(chatArea);

        sendBtn.setText("Send message");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        restartBtn.setText("Restart");
        restartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartBtnActionPerformed(evt);
            }
        });

        quitBtn.setText("Surrender");
        quitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitBtnActionPerformed(evt);
            }
        });

        block1.setBackground(new java.awt.Color(177, 183, 189));

        cell00.setBackground(new java.awt.Color(105, 69, 56));
        cell00.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell00.setPreferredSize(new java.awt.Dimension(80, 80));
        cell00.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell00MouseClicked(evt);
            }
        });

        labelCell00.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell00Layout = new javax.swing.GroupLayout(cell00);
        cell00.setLayout(cell00Layout);
        cell00Layout.setHorizontalGroup(
            cell00Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell00, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell00Layout.setVerticalGroup(
            cell00Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell00, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell01.setBackground(new java.awt.Color(105, 69, 56));
        cell01.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell01.setPreferredSize(new java.awt.Dimension(80, 80));
        cell01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell01MouseClicked(evt);
            }
        });

        labelCell01.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell01Layout = new javax.swing.GroupLayout(cell01);
        cell01.setLayout(cell01Layout);
        cell01Layout.setHorizontalGroup(
            cell01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell01, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell01Layout.setVerticalGroup(
            cell01Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell01, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cel10.setBackground(new java.awt.Color(105, 69, 56));
        cel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cel10.setPreferredSize(new java.awt.Dimension(80, 80));
        cel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cel10MouseClicked(evt);
            }
        });

        labelCell10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cel10Layout = new javax.swing.GroupLayout(cel10);
        cel10.setLayout(cel10Layout);
        cel10Layout.setHorizontalGroup(
            cel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell10, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cel10Layout.setVerticalGroup(
            cel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell10, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell11.setBackground(new java.awt.Color(105, 69, 56));
        cell11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell11.setPreferredSize(new java.awt.Dimension(80, 80));
        cell11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell11MouseClicked(evt);
            }
        });

        labelCell11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell11Layout = new javax.swing.GroupLayout(cell11);
        cell11.setLayout(cell11Layout);
        cell11Layout.setHorizontalGroup(
            cell11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell11, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell11Layout.setVerticalGroup(
            cell11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell11, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell20.setBackground(new java.awt.Color(105, 69, 56));
        cell20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell20.setPreferredSize(new java.awt.Dimension(80, 80));
        cell20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell20MouseClicked(evt);
            }
        });

        labelCell20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell20Layout = new javax.swing.GroupLayout(cell20);
        cell20.setLayout(cell20Layout);
        cell20Layout.setHorizontalGroup(
            cell20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell20, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell20Layout.setVerticalGroup(
            cell20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell20, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell21.setBackground(new java.awt.Color(105, 69, 56));
        cell21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell21.setPreferredSize(new java.awt.Dimension(80, 80));
        cell21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell21MouseClicked(evt);
            }
        });

        labelCell21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell21Layout = new javax.swing.GroupLayout(cell21);
        cell21.setLayout(cell21Layout);
        cell21Layout.setHorizontalGroup(
            cell21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell21, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell21Layout.setVerticalGroup(
            cell21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell21, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout block1Layout = new javax.swing.GroupLayout(block1);
        block1.setLayout(block1Layout);
        block1Layout.setHorizontalGroup(
            block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, block1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(block1Layout.createSequentialGroup()
                        .addComponent(cell20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cell21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(block1Layout.createSequentialGroup()
                        .addComponent(cel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cell11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(block1Layout.createSequentialGroup()
                        .addComponent(cell00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(cell01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        block1Layout.setVerticalGroup(
            block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(block1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cell00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(block1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cell20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        block2.setBackground(new java.awt.Color(177, 183, 189));

        cell23.setBackground(new java.awt.Color(105, 69, 56));
        cell23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell23.setPreferredSize(new java.awt.Dimension(80, 80));
        cell23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell23MouseClicked(evt);
            }
        });

        labelCell23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell23Layout = new javax.swing.GroupLayout(cell23);
        cell23.setLayout(cell23Layout);
        cell23Layout.setHorizontalGroup(
            cell23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell23, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell23Layout.setVerticalGroup(
            cell23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell23, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell22.setBackground(new java.awt.Color(105, 69, 56));
        cell22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell22.setPreferredSize(new java.awt.Dimension(80, 80));
        cell22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell22MouseClicked(evt);
            }
        });

        labelCell22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell22Layout = new javax.swing.GroupLayout(cell22);
        cell22.setLayout(cell22Layout);
        cell22Layout.setHorizontalGroup(
            cell22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell22, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell22Layout.setVerticalGroup(
            cell22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell22, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell12.setBackground(new java.awt.Color(105, 69, 56));
        cell12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell12.setPreferredSize(new java.awt.Dimension(80, 80));
        cell12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell12MouseClicked(evt);
            }
        });

        labelCell12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell12Layout = new javax.swing.GroupLayout(cell12);
        cell12.setLayout(cell12Layout);
        cell12Layout.setHorizontalGroup(
            cell12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell12, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell12Layout.setVerticalGroup(
            cell12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell12, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell13.setBackground(new java.awt.Color(105, 69, 56));
        cell13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell13.setPreferredSize(new java.awt.Dimension(80, 80));
        cell13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell13MouseClicked(evt);
            }
        });

        labelCell13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell13Layout = new javax.swing.GroupLayout(cell13);
        cell13.setLayout(cell13Layout);
        cell13Layout.setHorizontalGroup(
            cell13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell13, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell13Layout.setVerticalGroup(
            cell13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell13, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell03.setBackground(new java.awt.Color(105, 69, 56));
        cell03.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell03.setPreferredSize(new java.awt.Dimension(80, 80));
        cell03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell03MouseClicked(evt);
            }
        });

        labelCell03.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell03Layout = new javax.swing.GroupLayout(cell03);
        cell03.setLayout(cell03Layout);
        cell03Layout.setHorizontalGroup(
            cell03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell03, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell03Layout.setVerticalGroup(
            cell03Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell03, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        cell02.setBackground(new java.awt.Color(105, 69, 56));
        cell02.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cell02.setPreferredSize(new java.awt.Dimension(80, 80));
        cell02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cell02MouseClicked(evt);
            }
        });

        labelCell02.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout cell02Layout = new javax.swing.GroupLayout(cell02);
        cell02.setLayout(cell02Layout);
        cell02Layout.setHorizontalGroup(
            cell02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell02, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        cell02Layout.setVerticalGroup(
            cell02Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCell02, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout block2Layout = new javax.swing.GroupLayout(block2);
        block2.setLayout(block2Layout);
        block2Layout.setHorizontalGroup(
            block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(block2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(block2Layout.createSequentialGroup()
                        .addComponent(cell22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cell23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(block2Layout.createSequentialGroup()
                        .addComponent(cell12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cell13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(block2Layout.createSequentialGroup()
                        .addComponent(cell02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(cell03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        block2Layout.setVerticalGroup(
            block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(block2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cell02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cell12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(block2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cell22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cell23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        asideBlock.setBackground(new java.awt.Color(66, 75, 84));

        piece1.setFont(new java.awt.Font("Menlo", 1, 14)); // NOI18N
        piece1.setForeground(new java.awt.Color(255, 255, 255));
        piece1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        piece1.setText("9");
        piece1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        piece2.setFont(new java.awt.Font("Menlo", 1, 14)); // NOI18N
        piece2.setForeground(new java.awt.Color(255, 255, 255));
        piece2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        piece2.setText("9");
        piece2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        crownPieceBlock.setBackground(new java.awt.Color(105, 69, 56));
        crownPieceBlock.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        crownPieceBlock.setPreferredSize(new java.awt.Dimension(80, 80));
        crownPieceBlock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                crownPieceBlockMouseClicked(evt);
            }
        });

        labelCrownPiece.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout crownPieceBlockLayout = new javax.swing.GroupLayout(crownPieceBlock);
        crownPieceBlock.setLayout(crownPieceBlockLayout);
        crownPieceBlockLayout.setHorizontalGroup(
            crownPieceBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCrownPiece, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        crownPieceBlockLayout.setVerticalGroup(
            crownPieceBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCrownPiece, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        circlePieceBlock.setBackground(new java.awt.Color(105, 69, 56));
        circlePieceBlock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        circlePieceBlock.setPreferredSize(new java.awt.Dimension(80, 80));
        circlePieceBlock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                circlePieceBlockMouseClicked(evt);
            }
        });

        labelCirclePiece.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout circlePieceBlockLayout = new javax.swing.GroupLayout(circlePieceBlock);
        circlePieceBlock.setLayout(circlePieceBlockLayout);
        circlePieceBlockLayout.setHorizontalGroup(
            circlePieceBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCirclePiece, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        circlePieceBlockLayout.setVerticalGroup(
            circlePieceBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCirclePiece, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        startBtn.setText("Start");
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });

        logArea.setEditable(false);
        logArea.setColumns(10);
        logArea.setRows(5);
        logArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        logArea.setFocusable(false);
        logArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                logAreaCaretUpdate(evt);
            }
        });
        scrollChatArea1.setViewportView(logArea);

        javax.swing.GroupLayout asideBlockLayout = new javax.swing.GroupLayout(asideBlock);
        asideBlock.setLayout(asideBlockLayout);
        asideBlockLayout.setHorizontalGroup(
            asideBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(asideBlockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(asideBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollChatArea1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(asideBlockLayout.createSequentialGroup()
                        .addComponent(crownPieceBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(asideBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(asideBlockLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(piece2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, asideBlockLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(piece1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(circlePieceBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(asideBlockLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        asideBlockLayout.setVerticalGroup(
            asideBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, asideBlockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollChatArea1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(asideBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(asideBlockLayout.createSequentialGroup()
                        .addComponent(piece2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(piece1))
                    .addComponent(circlePieceBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crownPieceBlock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(block1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(block2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(asideBlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollChatArea, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                            .addComponent(sendText))
                        .addGap(18, 18, 18)
                        .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(restartBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(asideBlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(block2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(block1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                        .addComponent(restartBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quitBtn))
                    .addComponent(scrollChatArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        String message = sendText.getText().trim();
        String sender = this.playerName;
        clientsConfigs.sendToServer("c", message, sender, "0");
        sendText.setText("");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }//GEN-LAST:event_sendBtnActionPerformed

    private void quitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitBtnActionPerformed

        clientsConfigs.sendToServer("sr", "1", "0", "0");
    }//GEN-LAST:event_quitBtnActionPerformed

    private void cell00MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell00MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "0", "0", type);
    }//GEN-LAST:event_cell00MouseClicked

    private void cell01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell01MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "0", "1", type);
    }//GEN-LAST:event_cell01MouseClicked

    private void cell02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell02MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "0", "2", type);
    }//GEN-LAST:event_cell02MouseClicked

    private void cel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cel10MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "1", "0", type);
    }//GEN-LAST:event_cel10MouseClicked

    private void cell11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell11MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "1", "1", type);
    }//GEN-LAST:event_cell11MouseClicked

    private void cell12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell12MouseClicked
        // TODO add your handling code here:
//        setLabelData(1, 2, Piece.getTypePiece());
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "1", "2", type);
    }//GEN-LAST:event_cell12MouseClicked

    private void cell20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell20MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "2", "0", type);
    }//GEN-LAST:event_cell20MouseClicked

    private void cell21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell21MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "2", "1", type);
    }//GEN-LAST:event_cell21MouseClicked

    private void cell22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell22MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "2", "2", type);
    }//GEN-LAST:event_cell22MouseClicked

    private void cell13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell13MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "1", "3", type);
    }//GEN-LAST:event_cell13MouseClicked

    private void cell03MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell03MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "0", "3", type);
    }//GEN-LAST:event_cell03MouseClicked

    private void cell23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cell23MouseClicked
        // TODO add your handling code here:
        String type = Integer.toString(pieceType.getTypePiece());
        clientsConfigs.sendToServer("m", "2", "3", type);
    }//GEN-LAST:event_cell23MouseClicked

    private void circlePieceBlockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_circlePieceBlockMouseClicked
        // TODO add your handling code here:
        pieceType.setTypePiece(1);
        chatArea.append("Você selecionou a peça circular. " + "TypePiece: " + pieceType.getTypePiece() + "\n");
        chatArea.setCaretPosition(LudusBrailleGame.chatArea.getDocument().getLength());

    }//GEN-LAST:event_circlePieceBlockMouseClicked

    private void crownPieceBlockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crownPieceBlockMouseClicked
        // TODO add your handling code here:
        pieceType.setTypePiece(2);
        chatArea.append("Você selecionou a peça coroa. " + "TypePiece: " + pieceType.getTypePiece() + "\n");
        chatArea.setCaretPosition(LudusBrailleGame.chatArea.getDocument().getLength());
    }//GEN-LAST:event_crownPieceBlockMouseClicked

    private void sendTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendTextKeyPressed
        // TODO add your handling code here:
        String message = sendText.getText().trim();
        String sender = this.playerName;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            clientsConfigs.sendToServer("c", message, sender, "0");
            sendText.setText("");
        }
    }//GEN-LAST:event_sendTextKeyPressed

    private void restartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartBtnActionPerformed
        // TODO add your handling code here:
        //enable select pieces button...
        clientsConfigs.sendToServer("r", "1", "null", "0");
//        restartGame();
        
    }//GEN-LAST:event_restartBtnActionPerformed

    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
        // TODO add your handling code here:
//        disableToPlay(true);
        clientsConfigs.sendToServer("i", "1", "0", "0");
    }//GEN-LAST:event_startBtnActionPerformed

    private void chatAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_chatAreaCaretUpdate
        // TODO add your handling code here:
//        chatArea.setCaretPosition(textArea.getDocument().getLength()‌​);
        
    }//GEN-LAST:event_chatAreaCaretUpdate

    private void logAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_logAreaCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_logAreaCaretUpdate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LudusBrailleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LudusBrailleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LudusBrailleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LudusBrailleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LudusBrailleGame g = new LudusBrailleGame();
                g.setVisible(true);
                clientsConfigs = new Client("localhost", 8000, g);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel asideBlock;
    private javax.swing.JPanel block1;
    private javax.swing.JPanel block2;
    private javax.swing.JPanel cel10;
    private javax.swing.JPanel cell00;
    private javax.swing.JPanel cell01;
    private javax.swing.JPanel cell02;
    private javax.swing.JPanel cell03;
    private javax.swing.JPanel cell11;
    private javax.swing.JPanel cell12;
    private javax.swing.JPanel cell13;
    private javax.swing.JPanel cell20;
    private javax.swing.JPanel cell21;
    private javax.swing.JPanel cell22;
    private javax.swing.JPanel cell23;
    public static javax.swing.JTextArea chatArea;
    private javax.swing.JPanel circlePieceBlock;
    private javax.swing.JPanel crownPieceBlock;
    private javax.swing.JPanel gamePanel;
    public javax.swing.JLabel labelCell00;
    public javax.swing.JLabel labelCell01;
    public javax.swing.JLabel labelCell02;
    public javax.swing.JLabel labelCell03;
    public javax.swing.JLabel labelCell10;
    public javax.swing.JLabel labelCell11;
    public javax.swing.JLabel labelCell12;
    public javax.swing.JLabel labelCell13;
    public javax.swing.JLabel labelCell20;
    public javax.swing.JLabel labelCell21;
    public javax.swing.JLabel labelCell22;
    public javax.swing.JLabel labelCell23;
    private javax.swing.JLabel labelCirclePiece;
    private javax.swing.JLabel labelCrownPiece;
    public static javax.swing.JTextArea logArea;
    private javax.swing.JLabel piece1;
    private javax.swing.JLabel piece2;
    private javax.swing.JButton quitBtn;
    private javax.swing.JButton restartBtn;
    private javax.swing.JScrollPane scrollChatArea;
    private javax.swing.JScrollPane scrollChatArea1;
    private javax.swing.JButton sendBtn;
    public javax.swing.JTextField sendText;
    public javax.swing.JButton startBtn;
    // End of variables declaration//GEN-END:variables
}
