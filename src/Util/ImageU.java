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

    int x0 = 0;
    int x1 = 0;
    int y0 = 0;
    int y1 = 0;

    int oix;
    int ofx;
    int dix;
    int dfx;

    int oiy;
    int ofy;
    int diy;
    int dfy;

    List<Double> result;
    List<Point> linhasP;

    BufferedImage report;
    Graphics2D render;
    PixelCalc pc;
    double porcentagem = 0;

    public ImageU(PixelCalc pc) {
        this.pc = pc;
    }

    public void background() {
        if (pc.getPixels_width() == null && pc.getPixels_height() == null) {
            pc = new PixelCalc();
            pc.defaultImage();
        }
        report = new BufferedImage(pc.getPixels_width(), pc.getPixels_height(), BufferedImage.TYPE_INT_RGB);
        render = (Graphics2D) report.getGraphics();
        render.setColor(Color.lightGray);
        render.fillRect(0, 0, report.getWidth(), report.getHeight());
    }

    public double margem() {
        return report.getWidth() * this.porcentagem;
    }

    public void universoPlotavel() {
        this.porcentagem = 0.05;
        x0 = (int) margem();
        x1 = report.getWidth() - 1 - (int) margem();
        y0 = (int) margem();
        y1 = report.getHeight() - 1 - (int) margem();

        render.setColor(Color.WHITE);
        render.fillRect(x0, y0, x1 - x0, y1 - y0);
    }

    public void valoresSaida() {
        result = new ArrayList<>();
        Random random = new Random();
        result.add(0.0);
        int eixoX = 10;
        int eixoY = 10;
        for (int i = 1; i < eixoX + 1; i++) {
            result.add((random.nextDouble() * eixoY));
        }
    }

    public void criaGrade() {

        int linhaY[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        porcentagem = 0.1;

        oix = 0;
        ofx = 10;
        dix = (int) (x0 + margem());
        dfx = x1;

        oiy = 10;
        ofy = 0;
        diy = y0;
        dfy = (int) (y1 - margem());

        for (int i = 0; i < linhaY.length; i++) {
            int x2 = linhaY[i];
            int pxr = ((x2 - oix) * (dfx - dix) / (ofx - oix)) + dix;

            for (int a = 0; a < linhaY.length + 1; a++) {
                render.setColor(Color.lightGray);
                render.drawLine(pxr, (int) (y1 - margem()), pxr, y0);
            }
        }

        for (int j = 0; j < linhaY.length; j++) {
            int y2 = linhaY[j];
            int pyr = ((y2 - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;


            for (int i = 0; i < linhaY.length + 1; i++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) (x0 + margem()), pyr, x1, pyr);
            }
        }
    }

    public double getMaxValue() {
        double max = 0.0;
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i) > max) {
                max = result.get(i);
            }
        }
        return max;
    }

    public void mapeamento (){

    }

    // TODO: 30/09/2021 getmimvalue



    public void criaImagem() {
        background();
        universoPlotavel();
        criaGrade();


        try {
            ImageIO.write(report, "PNG", new File("test.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
