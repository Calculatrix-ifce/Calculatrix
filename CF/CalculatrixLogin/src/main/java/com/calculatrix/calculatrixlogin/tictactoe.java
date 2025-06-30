package com.calculatrix.calculatrixlogin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class tictactoe extends JFrame implements ActionListener {
    private JButton[][] botoes = new JButton[3][3];
    private char jogadorAtual = 'X';
    private JFrame telaAnterior;
    private JPanel painelPrincipal;
    private Image backgroundImage;

    public tictactoe(JFrame telaAnterior) {
        this.telaAnterior = telaAnterior;
        setTitle("Jogo da Velha");
        setSize(400, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/com/calculatrix/calculatrixlogin/images/calculaTRIX (1).png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Se não encontrar o ícone, não faz nada
        }
        getContentPane().setBackground(new Color(70, 130, 180));
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/com/calculatrix/calculatrixlogin/images/fundo.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }
        painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    int imgWidth = backgroundImage.getWidth(this);
                    int imgHeight = backgroundImage.getHeight(this);
                    if (imgWidth > 0 && imgHeight > 0) {
                        double scale = Math.max((double)panelWidth / imgWidth, (double)panelHeight / imgHeight);
                        int newImgWidth = (int)(imgWidth * scale);
                        int newImgHeight = (int)(imgHeight * scale);
                        int x = (panelWidth - newImgWidth) / 2;
                        int y = (panelHeight - newImgHeight) / 2;
                        g.drawImage(backgroundImage, x, y, newImgWidth, newImgHeight, this);
                    }
                }
            }
        };
        painelPrincipal.setOpaque(false);
        painelPrincipal.setLayout(new java.awt.BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.setOpaque(false);
        inicializarBotoes(gridPanel);
        painelPrincipal.add(gridPanel, java.awt.BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnVoltar.setBackground(new Color(25, 25, 35));
        btnVoltar.setForeground(new Color(70, 130, 180));
        btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 35), 0));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> {
            if (this.telaAnterior != null) {
                this.telaAnterior.setVisible(true);
            }
            this.dispose();
        });
        JPanel panelSul = new JPanel();
        panelSul.setOpaque(false);
        panelSul.add(btnVoltar);
        painelPrincipal.add(panelSul, java.awt.BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        if (this.telaAnterior != null) {
            this.telaAnterior.setVisible(false);
        }
    }

    private void inicializarBotoes(JPanel gridPanel) {
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                botoes[linha][coluna] = new JButton("");
                botoes[linha][coluna].setFont(new Font("Segoe UI", Font.BOLD, 60));
                botoes[linha][coluna].setFocusPainted(false);
                botoes[linha][coluna].setBackground(new Color(100, 150, 200));
                botoes[linha][coluna].setForeground(new Color(25, 25, 35));
                botoes[linha][coluna].setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
                botoes[linha][coluna].addActionListener(this);
                botoes[linha][coluna].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        ((JButton)evt.getSource()).setBackground(new Color(70, 130, 180));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        ((JButton)evt.getSource()).setBackground(new Color(100, 150, 200));
                    }
                });
                gridPanel.add(botoes[linha][coluna]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botaoClicado = (JButton) e.getSource();

        if (!botaoClicado.getText().equals("")) return;

        botaoClicado.setText(String.valueOf(jogadorAtual));
        if (Util.verificarVitoria(botoes)) {
            JOptionPane.showMessageDialog(this, "Jogador " + jogadorAtual + " venceu!");
            resetarJogo();
            return;
        }

        if (Util.empate(botoes)) {
            JOptionPane.showMessageDialog(this, "Empate!");
            resetarJogo();
            return;
        }

        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }

    private void resetarJogo() {
        for (JButton[] linha : botoes) {
            for (JButton botao : linha) {
                botao.setText("");
            }
        }
        jogadorAtual = 'X';
    }
}
