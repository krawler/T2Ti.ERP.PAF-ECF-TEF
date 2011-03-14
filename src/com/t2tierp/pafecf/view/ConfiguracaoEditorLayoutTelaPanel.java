package com.t2tierp.pafecf.view;

import com.t2tierp.pafecf.controller.ConfiguracaoController;
import com.t2tierp.pafecf.infra.ConfiguracaoComponenteModel;
import com.t2tierp.pafecf.infra.ConfiguracaoComponenteModelListener;
import com.t2tierp.pafecf.infra.ConfiguracaoEditorLayoutTelaModel;
import com.t2tierp.pafecf.infra.ConfiguracaoEditorLayoutTelaModelListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.poi.hssf.model.Model;

/**
 * Painel principal para o Editor de Layout de Tela.
 *
 * @author Leonardo Ono
 * @version 1.0 (20/11/2010)
 */
public class ConfiguracaoEditorLayoutTelaPanel extends javax.swing.JPanel
    implements ConfiguracaoEditorLayoutTelaModelListener, ComponenteListener {

    private ConfiguracaoEditorLayoutTelaModel model;
    private Map<ConfiguracaoComponenteModel, Componente> componentes
            = new HashMap<ConfiguracaoComponenteModel, Componente>();

    /** Creates new form ConfiguracaoEditorLayoutTelaPanel */
    @SuppressWarnings("LeakingThisInConstructor")
    public ConfiguracaoEditorLayoutTelaPanel(
            ConfiguracaoEditorLayoutTelaModel model) {

        initComponents();
        this.model = model;
        model.addConfiguracaoEditorLayoutTelaModelListener(this);
        criarTodosComponentes();
        setVisible(true);
    }

    public Map<ConfiguracaoComponenteModel, Componente> getComponentes() {
        return componentes;
    }

    private void criarTodosComponentes() {
        for (ConfiguracaoComponenteModel ccm
                : model.getListaDePosicaoComponentesVO()) {

            if (ccm.getNomeComponente().startsWith("panel")) continue;
            Componente componente = new Componente(ccm, this);
            add(componente);
            componentes.put(ccm, componente);
        }
 
        // Imagem de fundo
        ConfiguracaoController configuracaoController
                = new ConfiguracaoController();
        JLabel label = new JLabel(new javax.swing.ImageIcon(
                getClass().getResource(configuracaoController
                .pegaConfiguracao().getCaminhoImagensLayout()
                + configuracaoController.pegaConfiguracao()
                .getResolucaoVO().getImagemTela())));
        label.setBounds(0, 0, 800, 600);
        label.setVisible(true);
        add(label);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void tamanhoDoConfiguracaoComponenteModelFoiAlterado(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void posicaoDoConfiguracaoComponenteModelFoiAlterada(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void configuracaoComponenteModelFoiSelecionado(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void configuracaoComponenteModelFoiDesselecionado(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void textoDoConfiguracaoComponenteModelFoiAlterado(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void fonteDoConfiguracaoComponenteModelFoiAlterada(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void flagDeslocavelDoConfiguracaoComponenteModelFoiAlterada(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void flagRedimensionavelDoConfiguracaoComponenteModelFoiAlterada(
            ConfiguracaoComponenteModel componenteModel) {
        
        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
    }

    @Override
    public void flagSelecionadoDoConfiguracaoComponenteModelFoiAlterada(
            ConfiguracaoComponenteModel componenteModel) {

        Componente componente = componentes.get(componenteModel);
        componente.atualizarEsteComponenteConformeModel();
        // traz componente para frente
        componente.getParent().setComponentZOrder(componente, 0);
    }
 
    // ComponenteListener - Acoes do usuario

    @Override
    public void usuarioSelecionouComponenteNaView(Componente componente) {
        model.setComponenteModelSelecionado(
                componente.getConfiguracaoComponenteModel());
    }

    @Override
    public void usuarioDesselecionouComponenteNaView(Componente componente) {
        // nao faz nada
    }

    @Override
    public void usuarioAlterouPosicaoDoComponenteNaView(
            Componente componente, int x, int y) {

        model.alterarPosicaoEsquerda(x);
        model.alterarPosicaoTopo(y);
        model.salvarHistorico("Posicao do componente "
            + componente.getConfiguracaoComponenteModel().getNomeComponente());
    }

    @Override
    public void usuarioRedimensionouTamanhoDoComponenteNaView(
            Componente componente, int width, int height) {
        
        model.alterarTamanhoLargura(width);
        model.alterarTamanhoAltura(height);
        model.salvarHistorico("Tamanho do componente "
            + componente.getConfiguracaoComponenteModel().getNomeComponente());
    }

    @Override
    public void usuarioAlterouPosicaoRedimensionouTamanhoDoComponenteNaView(
            Componente componente, int x, int y, int width, int height) {

        model.alterarPosicaoEsquerda(x);
        model.alterarPosicaoTopo(y);
        model.alterarTamanhoLargura(width);
        model.alterarTamanhoAltura(height);
        model.salvarHistorico("Posicao/Tamanho do componente "
            + componente.getConfiguracaoComponenteModel().getNomeComponente());
    }

    // ConfiguracaoEditorLayoutTelaModelListener

    @Override
    public void historicoFoiSalvo(int indice, String descricao) {
        //System.out.println("historicoFoiSalvo " + indice + " - " + descricao);
    }

    @Override
    public void historicoFoiAvancado(int indice, String descricao) {
        //System.out.println("historicoFoiAvancado " + indice + " - " + descricao);
    }

    @Override
    public void historicoFoiVoltado(int indice, String descricao) {
        //System.out.println("historicoFoiVoltado " + indice + " - " + descricao);
    }

    @Override
    public void visibilidadeDaGrideAlterada() {
        repaint();
        System.out.println("visibilidadeDaGrideAlterada");
    }

    @Override
    public void tamanhoDaGrideAlterada() {
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (model.isGrideVisivel()) {
            g.setColor(new Color(0, 0, 0, 127));
            for (int x=0; x<=1024; x+=10) {
                for (int y=0; y<=768; y+=10) {
                    g.drawLine(x, y, x, y);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        //super.paintComponents(g);
    }


}

interface ComponenteListener {

    public abstract void usuarioSelecionouComponenteNaView(
            Componente componente);

    public abstract void usuarioDesselecionouComponenteNaView(
            Componente componente);
    
    public abstract void usuarioAlterouPosicaoDoComponenteNaView(
            Componente componente, int x, int y);

    public abstract void usuarioRedimensionouTamanhoDoComponenteNaView(
            Componente componente, int width, int height);

    public abstract void 
            usuarioAlterouPosicaoRedimensionouTamanhoDoComponenteNaView(

        Componente componente, int x, int y, int width, int height);

}

final class Componente extends JPanel 
        implements FocusListener, MouseListener, MouseMotionListener
        , KeyListener {

    private ConfiguracaoComponenteModel configuracaoComponenteModel;
    private JLabel componente;
    private JLabel redimensionadorNW = new JLabel();
    private JLabel redimensionadorN = new JLabel();
    private JLabel redimensionadorNE = new JLabel();
    private JLabel redimensionadorE = new JLabel();
    private JLabel redimensionadorSE = new JLabel();
    private JLabel redimensionadorS = new JLabel();
    private JLabel redimensionadorSW = new JLabel();
    private JLabel redimensionadorW = new JLabel();
    private ComponenteListener listener;

    private int xInicialTela;
    private int yInicialTela;
    private int xInicialComp;
    private int yInicialComp;
    private int widthInicialComp;
    private int heightInicialComp;

    @SuppressWarnings("LeakingThisInConstructor")
    public Componente(ConfiguracaoComponenteModel configuracaoComponenteModel
            , ComponenteListener listener) {
        this.configuracaoComponenteModel = configuracaoComponenteModel;
        this.listener = listener;
        configurarEsteComponente();
        inicializarTodosComponentes();
        configurarTodosComponentes();
        adicionarTodosComponentes();
        atualizarEsteComponenteConformeModel();
    }

    public ConfiguracaoComponenteModel getConfiguracaoComponenteModel() {
        return configuracaoComponenteModel;
    }

    public void setConfiguracaoComponenteModel(
            ConfiguracaoComponenteModel configuracaoComponenteModel) {
        
        this.configuracaoComponenteModel = configuracaoComponenteModel;
    }

    public void configurarEsteComponente() {
        setFocusable(false);
        setLayout(null);
        setOpaque(false);
        setVisible(true);
    }

    public void atualizarEsteComponenteConformeModel() {
        setLocation(configuracaoComponenteModel.getEsquerda()
                , configuracaoComponenteModel.getTopo());

        setSize(configuracaoComponenteModel.getLargura()
                , configuracaoComponenteModel.getAltura());

        exibirRedimensionador(configuracaoComponenteModel.isSelecionado());

        if (configuracaoComponenteModel.isDeslocavel())
            componente.setCursor(
                    Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        else
            componente.setCursor(
                    Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private void inicializarTodosComponentes() {
        redimensionadorNW = new JLabel();
        redimensionadorN = new JLabel();
        redimensionadorNE = new JLabel();
        redimensionadorE = new JLabel();
        redimensionadorSE = new JLabel();
        redimensionadorS = new JLabel();
        redimensionadorSW = new JLabel();
        redimensionadorW = new JLabel();
        componente = new JLabel(
                configuracaoComponenteModel.getNomeComponente(), JLabel.CENTER);
    }
    private void configurarTodosComponentes() {
        configurarRedimensionador(redimensionadorNW);
        configurarRedimensionador(redimensionadorN);
        configurarRedimensionador(redimensionadorNE);
        configurarRedimensionador(redimensionadorE);
        configurarRedimensionador(redimensionadorSE);
        configurarRedimensionador(redimensionadorS);
        configurarRedimensionador(redimensionadorSW);
        configurarRedimensionador(redimensionadorW);
        // Redimensionador Listener
        redimensionadorNW.addMouseListener(this);
        redimensionadorNW.addMouseMotionListener(this);
        redimensionadorN.addMouseListener(this);
        redimensionadorN.addMouseMotionListener(this);
        redimensionadorNE.addMouseListener(this);
        redimensionadorNE.addMouseMotionListener(this);
        redimensionadorE.addMouseListener(this);
        redimensionadorE.addMouseMotionListener(this);
        redimensionadorSE.addMouseListener(this);
        redimensionadorSE.addMouseMotionListener(this);
        redimensionadorS.addMouseListener(this);
        redimensionadorS.addMouseMotionListener(this);
        redimensionadorSW.addMouseListener(this);
        redimensionadorSW.addMouseMotionListener(this);
        redimensionadorW.addMouseListener(this);
        redimensionadorW.addMouseMotionListener(this);
        // Redimensionador Cursor
        redimensionadorNW.setCursor(
                Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        redimensionadorN.setCursor(
                Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        redimensionadorNE.setCursor(
                Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        redimensionadorE.setCursor(
                Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        redimensionadorSE.setCursor(
                Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        redimensionadorS.setCursor(
                Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        redimensionadorSW.setCursor(
                Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
        redimensionadorW.setCursor(
                Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        // configura componente
        componente.setOpaque(true);
        componente.setFocusable(true);
        componente.addFocusListener(this);
        componente.addMouseListener(this);
        componente.addMouseMotionListener(this);
        componente.addKeyListener(this);
        componente.setBorder(BorderFactory.createLineBorder(Color.black));
        componente.setBackground(new Color(255, 255, 255, 127));
    }

    private void configurarRedimensionador(JLabel redimensionador) {
        redimensionador.setSize(6, 6);
        redimensionador.setBackground(Color.black);
        redimensionador.setOpaque(true);
        redimensionador.setCursor(
                Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
    }

    private void adicionarTodosComponentes() {
        add(redimensionadorNW);
        add(redimensionadorN);
        add(redimensionadorNE);
        add(redimensionadorE);
        add(redimensionadorSE);
        add(redimensionadorS);
        add(redimensionadorSW);
        add(redimensionadorW);
        add(componente);
    }

    @Override
    public void setSize(int width, int height) {
        if (!configuracaoComponenteModel.isRedimensionavel()) return;
        super.setSize(width + 12, height + 12);
        reposicionarRedimensionador();
    }

    @Override
    public void setLocation(int x, int y) {
        if (!configuracaoComponenteModel.isDeslocavel()) return;
        super.setLocation(x - 6, y - 6);
        reposicionarRedimensionador();
    }

    private void reposicionarRedimensionador() {
        redimensionadorNW.setLocation(0, 0);
        redimensionadorN.setLocation(getWidth() / 2 - 3, 0);
        redimensionadorNE.setLocation(getWidth() - 6, 0);
        redimensionadorE.setLocation(getWidth() - 6, getHeight() / 2 - 3);
        redimensionadorSE.setLocation(getWidth() - 6, getHeight() - 6);
        redimensionadorS.setLocation(getWidth() / 2 - 3, getHeight() - 6);
        redimensionadorSW.setLocation(0, getHeight() - 6);
        redimensionadorW.setLocation(0, getHeight() / 2 - 3);
        componente.setBounds(6, 6, getWidth() - 12, getHeight() - 12);
    }

    private void exibirRedimensionador(boolean visivel) {
        redimensionadorNW.setVisible(visivel);
        redimensionadorN.setVisible(visivel);
        redimensionadorNE.setVisible(visivel);
        redimensionadorE.setVisible(visivel);
        redimensionadorSE.setVisible(visivel);
        redimensionadorS.setVisible(visivel);
        redimensionadorSW.setVisible(visivel);
        redimensionadorW.setVisible(visivel);
    }

    // FocusListener

    @Override
    public void focusGained(FocusEvent e) {
        componente.setBackground(new Color(255, 255, 0, 127));
        if (listener != null)
            listener.usuarioSelecionouComponenteNaView(this);
    }

    @Override
    public void focusLost(FocusEvent e) {
        componente.setBackground(new Color(255, 255, 255, 127));
        if (listener != null)
            listener.usuarioDesselecionouComponenteNaView(this);
    }

    // MouseListener

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == componente)
                componente.requestFocus();
        xInicialTela = e.getXOnScreen();
        yInicialTela = e.getYOnScreen();
        xInicialComp = getX() + 6;
        yInicialComp = getY() + 6;
        widthInicialComp = getWidth() - 12;
        heightInicialComp = getHeight() - 12;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = getX() + 6;
        int y = getY() + 6;
        int width = getWidth() - 12;
        int height = getHeight() - 12;

        boolean alterouPosicao = (x != xInicialComp || y != yInicialComp);
        boolean alterouTamanho = 
                (width != widthInicialComp || height != heightInicialComp);

        if (alterouPosicao && !alterouTamanho)
            listener.usuarioAlterouPosicaoDoComponenteNaView(this, x, y);

        if(alterouTamanho && !alterouPosicao)
            listener.usuarioRedimensionouTamanhoDoComponenteNaView(
                    this, width, height);

        if (alterouPosicao && alterouTamanho)
           listener.usuarioAlterouPosicaoRedimensionouTamanhoDoComponenteNaView(
                    this, x, y, width, height);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // MouseMotionListener

    @Override
    public void mouseDragged(MouseEvent e) {
        int getXOnScreen = e.getXOnScreen();
        int getYOnScreen = e.getYOnScreen();
        
        if (e.getSource() == componente) {
            int novoX = xInicialComp - (xInicialTela - getXOnScreen);
            int novoY = yInicialComp - (yInicialTela - getYOnScreen);
            novoX = novoX - (novoX % 10);
            novoY = novoY - (novoY % 10);
            setLocation(novoX, novoY);
        }
        // Se for redimensionador
        else {
            int novoWidth = widthInicialComp;
            int novoHeight = heightInicialComp;

            if (e.getSource() == redimensionadorNE 
                    || e.getSource() == redimensionadorN
                    || e.getSource() == redimensionadorNW)
                    novoHeight -= (getYOnScreen - yInicialTela);

            if (e.getSource() == redimensionadorSE
                    || e.getSource() == redimensionadorS
                    || e.getSource() == redimensionadorSW)
                    novoHeight += (getYOnScreen - yInicialTela);

            if (e.getSource() == redimensionadorNW
                    || e.getSource() == redimensionadorW
                    || e.getSource() == redimensionadorSW)
                    novoWidth -= (getXOnScreen - xInicialTela);
            
            if (e.getSource() == redimensionadorNE
                    || e.getSource() == redimensionadorE
                    || e.getSource() == redimensionadorSE)
                    novoWidth += (getXOnScreen - xInicialTela);

            int novoX = xInicialComp;
            int novoY = yInicialComp;

            if (e.getSource() == redimensionadorNE
                    || e.getSource() == redimensionadorN
                    || e.getSource() == redimensionadorNW)
                    novoY += (getYOnScreen - yInicialTela);

            if (e.getSource() == redimensionadorNW
                    || e.getSource() == redimensionadorW
                    || e.getSource() == redimensionadorSW)
                    novoX += (getXOnScreen - xInicialTela);

            if (novoWidth < 0) {
                novoWidth = -novoWidth;
                novoX -= novoWidth;
            }
            if (novoHeight < 0) {
                novoHeight = - novoHeight;
                novoY -= novoHeight;
            }

            // Se grade ativado
            novoX = novoX - (novoX % 10);
            novoY = novoY - (novoY % 10);
            novoWidth = novoWidth - (novoWidth % 10) + 1;
            novoHeight = novoHeight - (novoHeight % 10) + 1;

            setLocation(novoX, novoY);
            setSize(novoWidth, novoHeight);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    // KeyListener

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed=" + e + "/modifiers=" + e.getModifiers());
        if (e.getModifiers()==1) {
            if (e.getKeyCode()==37) {
                listener.usuarioRedimensionouTamanhoDoComponenteNaView(this
                        , configuracaoComponenteModel.getLargura()-1
                        , configuracaoComponenteModel.getAltura());
            }
            if (e.getKeyCode()==39) {
                listener.usuarioRedimensionouTamanhoDoComponenteNaView(this
                        , configuracaoComponenteModel.getLargura()+1
                        , configuracaoComponenteModel.getAltura());
            }
            if (e.getKeyCode()==38) {
                listener.usuarioRedimensionouTamanhoDoComponenteNaView(this
                        , configuracaoComponenteModel.getLargura()
                        , configuracaoComponenteModel.getAltura()-1);
            }
            if (e.getKeyCode()==40) {
                listener.usuarioRedimensionouTamanhoDoComponenteNaView(this
                        , configuracaoComponenteModel.getLargura()
                        , configuracaoComponenteModel.getAltura()+1);
            }
        }
        // Sem Shift altera posicao
        else {
            if (e.getKeyCode()==37) {
                listener.usuarioAlterouPosicaoDoComponenteNaView(this
                        , configuracaoComponenteModel.getEsquerda()-1
                        , configuracaoComponenteModel.getTopo());
            }
            if (e.getKeyCode()==39) {
                listener.usuarioAlterouPosicaoDoComponenteNaView(this
                        , configuracaoComponenteModel.getEsquerda()+1
                        , configuracaoComponenteModel.getTopo());
            }
            if (e.getKeyCode()==38) {
                listener.usuarioAlterouPosicaoDoComponenteNaView(this
                        , configuracaoComponenteModel.getEsquerda()
                        , configuracaoComponenteModel.getTopo()-1);
            }
            if (e.getKeyCode()==40) {
                listener.usuarioAlterouPosicaoDoComponenteNaView(this
                        , configuracaoComponenteModel.getEsquerda()
                        , configuracaoComponenteModel.getTopo()+1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }




}