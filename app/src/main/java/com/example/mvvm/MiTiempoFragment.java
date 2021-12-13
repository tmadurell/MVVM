package com.example.mvvm;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.databinding.FragmentMiTiempoBinding;

public class MiTiempoFragment extends Fragment {
    private FragmentMiTiempoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiTiempoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MiTiempoViewModel miTiempoViewModel = new ViewModelProvider(this).get(MiTiempoViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;

                double duracion = 0;
                int capitulos = 0;

                try {
                    duracion = Double.parseDouble(binding.duracion.getText().toString());
                } catch (Exception e){
                    binding.duracion.setError("Error: Introduzca un valor. Por favor");
                    error = true;
                }

                try {
                    capitulos = Integer.parseInt(binding.capitulos.getText().toString());
                } catch (Exception e){
                    binding.capitulos.setError("Error: Introduzca un valor. Por favor");
                    error = true;
                }

                if (!error) {
                    miTiempoViewModel.calcular(duracion, capitulos);
                }
            }
        });

        miTiempoViewModel.tiempo.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double tiempo) {
                binding.tiempo.setText(String.format("%.2f",tiempo)+ " horas en total");
            }
        });

        miTiempoViewModel.errorDuracion.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer duracionMinima) {
                if (duracionMinima != null) {
                    binding.duracion.setError("La duración minima del capitulo-episodio debe de ser de " + duracionMinima + " minutos, en esta app");
                } else {
                    binding.duracion.setError(null);
                }
            }
        });

        miTiempoViewModel.errorCapitulos.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer capitulosMinimo) {
                if (capitulosMinimo != null) {
                    binding.capitulos.setError("Debe tener minimo un " + capitulosMinimo + " capitulo en total");
                } else {
                    binding.capitulos.setError(null);
                }
            }
        });

        miTiempoViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.tiempo.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.tiempo.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}