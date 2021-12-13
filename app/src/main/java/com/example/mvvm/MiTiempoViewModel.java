package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MiTiempoViewModel extends AndroidViewModel {

    Executor executor;

    SimuladorTiempo simulador;

    MutableLiveData<Double> tiempo = new MutableLiveData<>();
    MutableLiveData<Integer> errorDuracion = new MutableLiveData<>();
    MutableLiveData<Integer> errorCapitulos = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MiTiempoViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorTiempo();
    }

    public void calcular(double capital, int plazo) {

        final SimuladorTiempo.Calculo calculo = new SimuladorTiempo.Calculo(capital, plazo);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(calculo, new SimuladorTiempo.Callback() {

                    @Override
                    public void cuandoEsteCalculadaEltiempo(double tiempo) {
                        errorDuracion.postValue(null);
                        errorCapitulos.postValue(null);
                        MiTiempoViewModel.this.tiempo.postValue(tiempo);
                    }

                    @Override
                    public void cuandoHayaErrorDeDuracionInferiorAlMinimo(double duracionMinima) {
                        errorDuracion.postValue((int) duracionMinima);
                    }

                    @Override
                    public void cuandoHayaErrorDeCapituloInferiorAlMinimo(int capituloMinimo) {
                        errorCapitulos.postValue(capituloMinimo);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            }
        });
    }
}
