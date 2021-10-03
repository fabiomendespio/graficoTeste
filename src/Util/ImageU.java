package Util;

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

    double porcentagem;

    BufferedImage report;
    Graphics2D render;
    PixelCalc pixelcalc;

    List<Double> auxList;
    List<Point> pontos;



    public ImageU(PixelCalc pixelcalc) {
        this.pixelcalc = pixelcalc;
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

    public void universoPlotavel() {
        this.porcentagem = 0.05;
        x0 = margem();
        x1 = report.getWidth() - 1 - margem();
        y0 = margem();
        y1 = report.getHeight() - 1 - margem();

        render.setColor(Color.WHITE);
        render.fillRect((int) x0, (int) y0, (int) x1 - (int) x0, (int) y1 - (int) y0);
    }

    public List<Double> valoresSaida() {
        auxList = new ArrayList<>();
        Random random = new Random();
        auxList.add(0.0);
        int eixoX = 10;
        int eixoY = 10;
        for (int i = 1; i < eixoX + 1; i++) {
            auxList.add((random.nextDouble() * eixoY));
        }
        return auxList;
    }

    public void criaGrade() {
        
        Stroke oldStroke = render.getStroke();
        float[] dash = { 2f, 0f, 2f };
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        render.setStroke(bs);

        auxList = new ArrayList<>();
        double count = 0;
        for(int aux = 0; aux < 10; aux++){
            count++;
            auxList.add(count);
                    }

        porcentagem = 0.1;

        oix = 0;
        ofx = 10;
        dix = (int) (x0 + margem());
        dfx = x1;

        oiy = 10;
        ofy = 0;
        diy = y0;
        dfy = (int) (y1 - margem());

        for (int i = 0; i < auxList.size(); i++) {
            x = auxList.get(i);
            mapeamentoX();

            for (int a = 0; a < auxList.size() + 1; a++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) pxr, (int) (y1 - margem()), (int) pxr, (int) y0);
            }
        }

        for (int j = 0; j < auxList.size(); j++) {
            y = auxList.get(j);
            mapeamentoY();


            for (int i = 0; i < auxList.size() + 1; i++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) (x0 + margem()), (int) pyr, (int) x1, (int) pyr);
            }
        }
        render.setStroke(oldStroke);
    }

    public double getMaxValue() {
        double max = 0.0;
        for (int i = 1; i < auxList.size(); i++) {
            if (auxList.get(i) > max) {
                max = auxList.get(i);
            }
        }
        return max;
    }

    public void mapeamentoX (){
            pxr = ((x - oix) * (dfx - dix) / (ofx - oix)) + dix;
    }

    public void mapeamentoY (){
        pyr = ((y - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;
    }

    public void linhasEntrePontos () {
        pontos = new ArrayList<>();
        valoresSaida();
        System.out.println(valoresSaida());

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size() - 1;
        dix = (int) (x0 + margem() + 1);
        dfx = x1 ;

        oiy = (int) getMaxValue();
        ofy = 0;
        diy = y0 + margem();
        dfy = (int) (y1 - margem());


        for (int i = 0; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();

            // System.out.println(Prx);
            for (int j = 0; j < auxList.size(); j++) {
                y =  auxList.get(i);
                mapeamentoY();
                pontos.add(new Point((int) pxr, (int) pyr));
            }
        }

        for (int a = 0; a < pontos.size() - 1; a++) {
            render.setColor(Color.blue);
            render.drawLine(pontos.get(a).x, pontos.get(a).y, pontos.get(a + 1).x, pontos.get(a + 1).y);
        }
    }

    public void criaPontos(){

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size() - 1;
        dix = (int) (x0 + margem() -2);
        dfx = x1 - 5;

        oiy = (int) getMaxValue();
        ofy = 0;
        diy = y0 + margem();
        dfy = (int) (y1 - 4 - margem());


        for (int i = 1; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();

            // System.out.println(Prx);
            for (int j = 0; j < auxList.size(); j++) {
                y = auxList.get(i);
                mapeamentoY();

                render.setColor(Color.RED);
                render.fillOval((int) pxr, (int) pyr, 6, 6);
            }
        }
    }

    public void eixoXY(){
        porcentagem = 0.1;

        render.setColor(Color.BLACK);
        render.drawLine((int) x0, (int) y1 - (int) margem(), (int)x1, (int) y1 - (int) margem()); //eixo Y
        render.drawLine((int) x0 + (int)margem(), (int)y0, (int) x0 + (int) margem(), (int) y1); //eixo X
    }

    public void saveImage(){
        try {
            ImageIO.write(report, "PNG", new File("test.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // TODO: 30/09/2021 getmimvalue


    public void criaImagem() {
        background();
        universoPlotavel();


        criaGrade();


        linhasEntrePontos();
        criaPontos();
        eixoXY();
        saveImage();
    }

}
