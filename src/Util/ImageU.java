package Util;

import Util.PixelCalc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ImageU {

    double x0;
    double x1;
    double y0;
    double y1;

    double x;
    double oix;
    double ofx;
    double dix;
    double dfx;
    double pxr;

    double y;
    double oiy;
    double ofy;
    double diy;
    double dfy;
    double pyr;

    int eixoX;
    int eixoY;

    String xLabel;
    double w = 1;

    String yLabel;
    int z = 1;

    double porcentagem;

    BufferedImage report;
    Graphics2D render;
    PixelCalc pixelcalc;

    List<Double> auxList;
    List<Point> pontos;

    public ImageU(PixelCalc pixelcalc) {
        this.pixelcalc = pixelcalc;
    }

    public double getMaxValue() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < auxList.size(); i++) {
            if (auxList.get(i) > max) {
                max = auxList.get(i);
            }
        }
        return max;
    }

    public double getMinValue() {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < auxList.size(); i++) {
            if (auxList.get(i) < min) {
                min = auxList.get(i);
            }
        }
        return min;
    }

    public void background() {
        if (pixelcalc.getPixels_width() == null && pixelcalc.getPixels_height() == null) {
            pixelcalc = new PixelCalc();
            pixelcalc.defaultImage();
        }
        report = new BufferedImage(pixelcalc.getPixels_width(), pixelcalc.getPixels_height(), BufferedImage.TYPE_INT_RGB);
        render = (Graphics2D) report.getGraphics();
        render.setColor(Color.lightGray);
        render.fillRect(0, 0, report.getWidth(), report.getHeight());
    }

    public double margem() {
        return report.getWidth() * this.porcentagem;
    }

    public void whiteBrackground() {


        this.porcentagem = 0.05;
        x0 = margem();
        x1 = report.getWidth()  - margem() - 1;
        y0 = margem() ;
        y1 = report.getHeight() - 1 ;


        render.setColor(Color.WHITE);
        render.fillRect((int) x0, (int) y0 - 15, (int) x1 - (int) x0 + 15, (int) y1 - (int) y0);
        setTitulo();

    }

    public void setTitulo(){
        render.setColor(Color.BLACK);
        render.drawString("Gráfico Rede Neural: TESTE" , (int) x0,  (int) y0 - 20);
    }

    public List<Double> valoresSaida() {
        auxList = new ArrayList<>();
        Random random = new Random();

        eixoX = 20;
        eixoY = 10;
        double v = 3;
        for (int i = 0; i < eixoX; i++) {
            v+=Math.abs(random.nextDouble())/10.0;
//            auxList.add((random.nextDouble() * eixoY));
            auxList.add(v);
        }
        return auxList;
    }

    public void mapeamentoX() {
        pxr = ((x - oix) * (dfx - dix) / (ofx - oix)) + dix;
    }

    public void mapeamentoY() {
        pyr = ((y - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;
    }

    public void eixoXY() {
        porcentagem = 0.1;

        render.setColor(Color.BLACK);
        render.drawLine((int) x0 + 75, (int) y1 - (int) margem(), (int) x1, (int) y1 - (int) margem()); // eixo x
        render.drawLine((int) x0 + (int) margem(), (int) y0, (int) x0 + (int) margem(), (int) y1 - 75); // eixo Y

    }

    public void criaGrade() {

        Stroke oldStroke = render.getStroke();
        float[] dash = { 2f, 0f, 2f };
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        render.setStroke(bs);

        porcentagem = 0.1;

        oix = 0;
        ofx = 10;
        dix = (int) (x0 + margem());
        dfx = x1;

        oiy = 10;
        ofy = 0;
        diy = y0;
        dfy = (int) (y1 - margem());




        double auxFixa = (double) auxList.size() / ofx;
        w = auxFixa;

        //X
        x = 1;
        for (int i = 0; i < auxList.size(); i++) {
            mapeamentoX();
            for (int a = 0; a < auxList.size() ; a++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) pxr, (int) (y1 - margem()), (int) pxr, (int) y0);

                if ((double) auxList.size() % ofx == 0) {
                    xLabel = Math.round(w) + "";
                } else {
                    xLabel = Math.round(w * 100.0) / 100.0 + "";
                }

                render.setColor(Color.BLACK);
                render.drawString(xLabel, (int) pxr - 5, (int) y1 - (int) margem() + 15);
            }
            w =  w + auxFixa;
            x++;
        }

        //Y
        y = 1;
        for (int j = 0; j < 10; j++) {
            mapeamentoY();
            for (int i = 0; i < 10; i++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) (x0 + margem()), (int) pyr, (int) x1, (int) pyr);
                yLabel = z + "";
                render.setColor(Color.BLACK);
                render.drawString(yLabel, (int) x0 + (int) margem() - 20,(int) pyr + 5);
            }
            z++;
            y++;
        }
        render.setStroke(oldStroke);
    }

    public void criaLinhasPontos() {
        pontos = new ArrayList<>();
        valoresSaida();
        System.out.println(valoresSaida());

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size() - 1;
        dix = (int) (x0 + margem() + 1);
        dfx = x1;

        oiy = getMaxValue();
        ofy = getMinValue();
        diy = y0 ;
        dfy = (int) (y1 - margem());

        for (int i = 0; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();

            // System.out.println(Prx);
            for (int j = 0; j < auxList.size(); j++) {
                y = auxList.get(i);
                mapeamentoY();
                pontos.add(new Point((int) pxr, (int) pyr));
            }
        }

        for (int a = 0; a < pontos.size() - 1; a++) {
            render.setColor(Color.blue);
            render.drawLine(pontos.get(a).x, pontos.get(a).y, pontos.get(a + 1).x, pontos.get(a + 1).y);
        }
    }

    public void criaPontos() {

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size() - 1;
        dix = (int) (x0 + margem() );
        dfx = x1 ;

        oiy = getMaxValue();
        ofy = getMinValue();
        diy = y0;
        dfy = (int) (y1  - margem());

        for (int i = 0; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();


            for (int j = 0; j < auxList.size(); j++) {
                y = auxList.get(i);
                mapeamentoY();

                render.setColor(Color.RED);
                render.fillRect((int) pxr, (int) pyr, 5, 5);
            }
        }
    }



    public void saveImage() {
        try {
            ImageIO.write(report, "PNG", new File("test.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void criaImagem() {

        background();
        whiteBrackground();
        criaLinhasPontos();
//        criaPontos();
        criaGrade();

        eixoXY();
        saveImage();

    }

}