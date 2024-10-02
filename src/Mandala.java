import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandala extends JFrame {
    private double centroX, centroY, radio;
    private int figuraSeleccionada = 0; // 1 - Circulo, 2 - Cuadrado, 3 - Linea, 4 - Ovalo
    private BufferedImage buffer;
    private Color red = Color.BLACK;
    private Color spaceBlue = new Color(28,31,117);
    private Color orange = new Color(245,94,34);
    private Color yellow = new Color(131, 135, 22);
    private DrawPanel drawPanel;

    public Mandala() {
        drawPanel = new DrawPanel();
        drawPanel.setPreferredSize(new Dimension(500, 500));
        add(drawPanel, BorderLayout.CENTER);


        // Dibujar mandala
        centroX = 200;
        centroY = 100;
        radio = 50;
        ovalo(220,190,200, 150);
        ovalo(220,190,150, 200);
        linea(220, 90, 220, 290, orange);
        linea(120, 190, 320, 190, orange);
        linea(220, 90, 120, 190, orange);
        linea(220, 90, 320, 190, orange);
        linea(220, 290, 120, 190, orange);
        linea(220, 290, 320, 190, orange);
        linea(120, 190, 220, 90, orange);
        linea(120, 190, 220, 290, orange);
        linea(0, 190, 440, 190, yellow);
        linea(220, 0, 220, 380, yellow);
        linea(220, 0, 440, 190, yellow);
        linea(220, 0, 0, 190, yellow);
        linea(220, 380, 440, 190,yellow);
        linea(220, 380, 0, 190, yellow);
        linea(0, 190, 220, 0, yellow);
        linea(440, 190, 220, 0, yellow);
        linea(0, 190, 220, 380, yellow);
        linea(440, 190, 220, 380, yellow);
        int lado = 50;
        for (int i = lado; i < 300; i+= 150) {
            cuadrado(220, 190, i);
        }
        linea(220, 190, 220, 240, spaceBlue);
        linea(170, 140, 270, 240, spaceBlue);


        for(int i = 0; i < 12; i++) {

            centroX += 50 * Math.cos(Math.toRadians(30 * i));
            centroY += 50 * Math.sin(Math.toRadians(30 * i));
            circulo((int) centroX, (int) centroY, (int) radio);
            linea(220, 190, (int) centroX, (int) centroY, spaceBlue);


        }
        centroX = 175;
        centroY = 10;
        for(int i = 0; i < 12; i++) {

            centroX += 100 * Math.cos(Math.toRadians(30 * i));
            centroY += 100 * Math.sin(Math.toRadians(30 * i));
            circulo((int) centroX, (int) centroY, (int) radio);

        }

        setTitle("Dibujar Mandala");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //Panel para que no se muera el menu
    private class DrawPanel extends JPanel {
        public DrawPanel() {
            buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            Graphics graPixel = buffer.getGraphics();
            graPixel.setColor(Color.WHITE); // Fondo blanco
            graPixel.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(buffer, 0, 0, this);
        }
    }

    public void putPixel(int x, int y, Color color) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, color.getRGB());
        }
    }

    public void circulo(int centerX, int centerY, int radius) {
        int x = 0;
        int y = radius;
        int d = 1 - radius;

        while (x <= y) {
            putPixel(centerX + x, centerY + y, red);
            putPixel(centerX - x, centerY + y, red);
            putPixel(centerX + x, centerY - y, red);
            putPixel(centerX - x, centerY - y, red);
            putPixel(centerX + y, centerY + x, red);
            putPixel(centerX - y, centerY + x, red);
            putPixel(centerX + y, centerY - x, red);
            putPixel(centerX - y, centerY - x, red);
            x++;

            if (d < 0) {
                d = d + 2 * x + 1;
            } else {
                y--;
                d = d + 2 * x - 2 * y + 1;
            }
        }
    }

    public void cuadrado(int centerX, int centerY, int sideLength) {
        int halfSide = sideLength / 2;

        int x1 = centerX - halfSide;
        int y1 = centerY - halfSide;
        int x2 = centerX + halfSide;
        int y2 = centerY + halfSide;

        linea(x1, y1, x2, y1, spaceBlue);
        linea(x2, y1, x2, y2, spaceBlue);
        linea(x2, y2, x1, y2, spaceBlue);
        linea(x1, y2, x1, y1, spaceBlue);
    }

    public void rectangulo(int startX, int startY, int width, int height) {
        linea(startX, startY, startX + width, startY, Color.BLACK);
        linea(startX, startY, startX, startY + height, Color.BLACK);
        linea(startX + width, startY, startX + width, startY + height, Color.BLACK);
        linea(startX, startY + height, startX + width, startY + height, Color.BLACK);
    }


    public void linea(int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, color);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void ovalo(int centerX, int centerY, int radiusX, int radiusY) {
        int x = 0;
        int y = radiusY;

        double dx = 2 * radiusY * radiusY * x;
        double dy = 2 * radiusX * radiusX * y;

        double d1 = (radiusY * radiusY) - (radiusX * radiusX * radiusY) + (0.25 * radiusX * radiusX);

        while (dx < dy) {
            putPixel(centerX + x, centerY + y, Color.MAGENTA);
            putPixel(centerX - x, centerY + y, Color.MAGENTA);
            putPixel(centerX + x, centerY - y, Color.MAGENTA);
            putPixel(centerX - x, centerY - y, Color.MAGENTA);

            if (d1 < 0) {
                x++;
                dx += 2 * radiusY * radiusY;
                d1 += dx + (radiusY * radiusY);
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                d1 += dx - dy + (radiusY * radiusY);
            }
        }
        double d2 = ((radiusY * radiusY) * (x + 0.5) * (x + 0.5)) + ((radiusX * radiusX) * (y - 1) * (y - 1)) - (radiusX * radiusX * radiusY * radiusY);

        while (y >= 0) {
            putPixel(centerX + x, centerY + y, Color.MAGENTA);
            putPixel(centerX - x, centerY + y, Color.MAGENTA);
            putPixel(centerX + x, centerY - y, Color.MAGENTA);
            putPixel(centerX - x, centerY - y, Color.MAGENTA);

            if (d2 > 0) {
                y--;
                dy -= 2 * radiusX * radiusX;
                d2 += (radiusX * radiusX) - dy;
            } else {
                x++;
                y--;
                dx += 2 * radiusY * radiusY;
                dy -= 2 * radiusX * radiusX;
                d2 += dx - dy + (radiusX * radiusX);
            }
        }
    }


    public static void main(String[] args) {
        Mandala mandala = new Mandala();
        mandala.setVisible(true);
    }

}
