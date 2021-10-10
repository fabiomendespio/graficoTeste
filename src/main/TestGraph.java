package main;

import Util.ImageU;
import Util.PixelCalc;

public class TestGraph {

        public static void main (String []args) {

        PixelCalc pixelcalc = new PixelCalc();
        ImageU imageu = new ImageU(pixelcalc);

//        pixelcalc.setPixelsWidth(800);
//        pixelcalc.setPixelsHeight(600);
//        pixelcalc.setPixelsWidth(600);
//        pixelcalc.setPixelsHeight(900);
//        pixelcalc.setDate();
//                pixelcalc.setRatio(16,9);
//                pixelcalc.setPixelsHeight(800);
//        pixelcalc.setProportion();
                pixelcalc.setDate();
                imageu.showImage();


    }
}

