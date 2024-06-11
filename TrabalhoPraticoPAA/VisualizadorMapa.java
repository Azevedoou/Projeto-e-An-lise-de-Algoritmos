import javax.swing.*;
import java.awt.*;
import java.util.List;

class MapEntry {
    // Variáveis relacionadas ao número da franqua e suas coordenadas X e Y
    int numFranquia, coordenadaX, coordenadaY;

    // Construtor
    public MapEntry(int numFranquia, int coordenadaX, int coordenaday) {
        this.numFranquia = numFranquia;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenaday;
    }
}

public class VisualizadorMapa extends JPanel {
    List<MapEntry> entries;
    int larguraMapa;
    int alturaMapa;

    // Construtor
    public VisualizadorMapa(List<MapEntry> entries, int larguraMapa, int alturaMapa) {
        this.entries = entries;
        this.larguraMapa = larguraMapa;
        this.alturaMapa = alturaMapa;
        setPreferredSize(new Dimension(larguraMapa, alturaMapa));
    }

    // Desenha o que está dentro da janela
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenhar a grade do mapa
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= larguraMapa; i += 10) {
            g.drawLine(i, 0, i, alturaMapa);
        }
        for (int i = 0; i <= alturaMapa; i += 10) {
            g.drawLine(0, i, larguraMapa, i);
        }

        // Desenhar as entradas do mapa
        g.setColor(Color.BLACK);
        for (MapEntry entry : entries) {
            g.drawString(String.valueOf(entry.numFranquia), entry.coordenadaX, entry.coordenadaY);
        }
    }
}