

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Graph2 {

    public static void main(String[] args) {
        BufferedImage report = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D render = (Graphics2D) report.getGraphics();

        render.setColor(Color.lightGray);
        render.fillRect(0, 0, report.getWidth(), report.getHeight());

        //calculo de proporcao
        int Ratio_width = 4;
        int Ratio_height = 3;
        int Pixels_width = 600;
        int Pixels_height;

        Pixels_height = (Pixels_width / Ratio_width) * Ratio_height;
        System.out.println("Larguera em 4/3: " + Pixels_width);
        System.out.println("Altura em 4/3: " + Pixels_height);

        double porcentagem = 0.1;
        double margem = report.getWidth() * porcentagem;
        //   System.out.println("Valor da margem: " +margem);

        double porcentagemL = 0.05;
        double margemL = Pixels_width * porcentagemL;
        int mLinha = (int) margemL;


        int x0 = (int) margem;
        int x1 = report.getWidth() - 1 - (int) margem;
        int y0 = (int) margem;
        ;
        int y1 = report.getHeight() - 1 - (int) margem;
        //  System.out.println("margem em X: " +x0);
        // System.out.println("Imagem de fundo - 1 - margem em X: " +x1);
        // System.out.println("margem em Y: " +y0);
        //  System.out.println("Imagem de fundo:" + report.getWidth()+ " - 1 " + "- margem em Y:" +y0+ " = " +y1);


        render.setColor(Color.WHITE);
        render.fillRect(x0, y0, x1 - x0, y1 - y0);

        List<Double> result = new ArrayList<>();
        Random random = new Random();
        result.add(0.0);
        int eixoX = 10;
        int eixoY = 10;
        for (int i = 1; i < eixoX + 1; i++) {
            result.add((random.nextDouble() * eixoY));
            System.out.println(result);

        }

        int linhaY[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        int oix = 0;
        int ofx = 10;
        int dix = x0 + mLinha;
        int dfx = x1;

        int oiy = 10;
        int ofy = 0;
        int diy = y0;
        int dfy = y1 - mLinha;

        for (int i = 0; i < linhaY.length; i++) {
            int x2 = linhaY[i];
            int pxr = ((x2 - oix) * (dfx - dix) / (ofx - oix)) + dix;

            for (int a = 0; a < linhaY.length + 1; a++) {
                render.setColor(Color.lightGray);
                render.drawLine(pxr, y1 - mLinha, pxr, y0);
            }
        }

        for (int j = 0; j < linhaY.length; j++) {
            int y2 = linhaY[j];
            int pyr = ((y2 - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;


            for (int i = 0; i < linhaY.length + 1; i++) {
                render.setColor(Color.lightGray);
                render.drawLine(x0 + mLinha, pyr, x1, pyr);
            }
        }
        double max = 0.0;
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i) > max) {
                max = result.get(i);
            }
        }


        List<Point> linhasP = new ArrayList<>();


        int Oix1 = 0;
        int Ofx1 = result.size() - 1;
        int Dix1 = x0 + mLinha + 1;
        int Dfx1 = x1 - 5;


        double Oiy1 = max;
        int Ofy1 = 0;
        int Diy1 = y0;
        int Dfy1 = y1 - mLinha;


        for (int i = 0; i < result.size(); i++) {
            int x = i;
            int Pxr = ((x - Oix1) * (Dfx1 - Dix1) / (Ofx1 - Oix1)) + Dix1;

            // System.out.println(Prx);
            for (int j = 0; j < result.size(); j++) {
                double y = result.get(i);
                double Pyr = (int) (((y - Ofy1) * (Diy1 - Dfy1) / (Oiy1 - Ofy1)) + Dfy1);

                linhasP.add(new Point(Pxr, (int) Pyr));
            }

        }

        for (int a = 0; a < linhasP.size() - 1; a++) {
            render.setColor(Color.blue);
            render.drawLine(linhasP.get(a).x, linhasP.get(a).y, linhasP.get(a + 1).x, linhasP.get(a + 1).y);

        }


        int Oix = 0;
        int Ofx = result.size() - 1;
        int Dix = x0 + mLinha - 2;
        int Dfx = x1 - 5;


        double Oiy = max;
        int Ofy = 0;
        int Diy = y0;
        int Dfy = y1 - 4 - mLinha;


        for (int i = 1; i < result.size(); i++) {
            int x = i;
            int Pxr = ((x - Oix) * (Dfx - Dix) / (Ofx - Oix)) + Dix;

            // System.out.println(Prx);
            for (int j = 0; j < result.size(); j++) {
                double y = result.get(i);
                double Pyr = (int) (((y - Ofy) * (Diy - Dfy) / (Oiy - Ofy)) + Dfy);
                //System.out.println(Pry);

                render.setColor(Color.RED);
                render.fillOval(Pxr, (int) Pyr, 6, 6);

            }

        }


        render.setColor(Color.BLACK);
        render.drawLine(x0, y1 - mLinha, x1, y1 - mLinha); //eixo Y
        render.drawLine(x0 + mLinha, y0, x0 + mLinha, y1); //eixo X
//
//        System.out.println("Maior elemento é " + max);


        try {
            ImageIO.write(report, "PNG", new File("testPx.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }
}
