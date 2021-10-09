package main;

import Util.ImageU;
import Util.PixelCalc;

public class main {

        public static void main (String []args) {

        PixelCalc pixelcalc = new PixelCalc();
        ImageU imageu = new ImageU(pixelcalc);

//        pc.setPixelsWidth(800);
//        pc.setPixelsHeight(600);
//        pc.setPixelsWidth(600);
//        pc.setPixelsHeight(900);
//        pc.Dados();
//                pc.setRatio(16,9);
//                pc.setPixelsHeight(800);
//        pc.proporcao();
                pixelcalc.Dados();
                imageu.criaImagem();
    }
}

