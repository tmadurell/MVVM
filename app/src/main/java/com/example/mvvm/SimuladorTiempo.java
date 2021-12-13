package com.example.mvvm;

public class SimuladorTiempo {

    public static class Calculo {
        public double duracion;
        public int capitulos;

        public Calculo(double duracion, int capitulos) {
            this.duracion = duracion;
            this.capitulos = capitulos;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaEltiempo(double tiempo);
        void cuandoHayaErrorDeDuracionInferiorAlMinimo(double duracionMinima);
        void cuandoHayaErrorDeCapituloInferiorAlMinimo(int capituloMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Calculo calculo, Callback callback) {
        int capituloMinimo = 0;
        double duracionMinima = 0;
        

        callback.cuandoEmpieceElCalculo();


        try {
            // simular operacion de larga duracion (3s)
            Thread.sleep(3000);
            //El total de episodios que vas a ver en total
            capituloMinimo = 1;
            //El tiempo que dura un capitulo promedio no incluye especiales, en minutos
            duracionMinima = 10;

        } catch (InterruptedException e) {}
        boolean error = false;
        if (calculo.duracion < duracionMinima) {
            callback.cuandoHayaErrorDeDuracionInferiorAlMinimo(duracionMinima);
            error = true;
        }

        if (calculo.capitulos < capituloMinimo) {
            callback.cuandoHayaErrorDeCapituloInferiorAlMinimo(capituloMinimo);
            error = true;
        }

        if(!error) {
            //Formula matematica IMPORTANTE
            callback.cuandoEsteCalculadaEltiempo( (calculo.capitulos * calculo.duracion) / 60 );
        }

        callback.cuandoFinaliceElCalculo();
    }
}
