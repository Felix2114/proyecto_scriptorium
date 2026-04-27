package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scriptorium.scriptorium.dto.MultaRequestDTO;
import com.scriptorium.scriptorium.dto.MultaResponseDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;

@Component
public class AutomaticaMultaService implements ApplicationRunner {

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    MultaService multaService;
    LocalDate fechaActual;
    float coeficienteMulta = 200;

    /*
     * @Scheduled(cron = "0 0 0 * * *")
     * public void creadorMultas(){
     * 
     * List<PrestamoResponseDTO> prestamoList= prestamoService.listar();
     * List<MultaResponseDTO> multaList = multaService.listar();
     * fechaActual= LocalDate.now();
     * MultaRequestDTO multaDTO= new MultaRequestDTO();
     * 
     * 
     * Set<Long> prestamosConMulta = multaList.stream()
     * .map(MultaResponseDTO::getPrestamoId)
     * .collect(Collectors.toSet());
     * 
     * 
     * 
     * for (PrestamoResponseDTO prestamo : prestamoList) {
     * 
     * 
     * if (prestamosConMulta.contains(prestamo.getIdPrestamo())) {
     * continue;
     * }
     * 
     * if (prestamo.getFechaFin().isAfter(fechaActual)) {
     * long dias=ChronoUnit.DAYS.between(prestamo.getFechaFin(), fechaActual);
     * 
     * multaDTO.setTipoMultaId(1);
     * multaDTO.setPrestamoId(prestamo.getIdPrestamo());
     * multaDTO.setMonto(dias*coeficienteMulta);
     * multaDTO.setFechaMulta(fechaActual);
     * 
     * multaService.guardar(multaDTO);
     * }
     * 
     * }
     * }
     */

    /*
     * @Scheduled(cron = "0 0 0 * * *")
     * public void creadorMultas(){
     * 
     * List<PrestamoResponseDTO> prestamoList= prestamoService.listar();
     * List<MultaResponseDTO> multaList = multaService.listar();
     * fechaActual= LocalDate.now();
     * MultaRequestDTO multaDTO= new MultaRequestDTO();
     * 
     * 
     * Set<Long> prestamosConMulta = multaList.stream()
     * .map(MultaResponseDTO::getPrestamoId)
     * .collect(Collectors.toSet());
     * 
     * 
     * 
     * for (PrestamoResponseDTO prestamo : prestamoList) {
     * 
     * 
     * if (prestamosConMulta.contains(prestamo.getIdPrestamo())) {
     * continue;
     * }
     * 
     * if (prestamo.getFechaFin().isAfter(fechaActual)) {
     * long dias=ChronoUnit.DAYS.between(prestamo.getFechaFin(), fechaActual);
     * 
     * multaDTO.setTipoMultaId(1);
     * multaDTO.setPrestamoId(prestamo.getIdPrestamo());
     * multaDTO.setMonto(dias*coeficienteMulta);
     * multaDTO.setFechaMulta(fechaActual);
     * 
     * multaService.guardar(multaDTO);
     * 
     * }
     * 
     * }
     * }
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<PrestamoResponseDTO> prestamoList = prestamoService.listar();
        List<MultaResponseDTO> multaList = multaService.listar();
        fechaActual = LocalDate.now();
        MultaRequestDTO multaDTO = new MultaRequestDTO();

        Set<Long> prestamosConMulta = multaList.stream()
                .map(MultaResponseDTO::getPrestamoId)
                .collect(Collectors.toSet());

        for (PrestamoResponseDTO prestamo : prestamoList) {

            if (prestamosConMulta.contains(prestamo.getIdPrestamo())) {
                continue;
            }

            if (prestamo.getFechaFin().isAfter(fechaActual)) {
                long dias = ChronoUnit.DAYS.between(fechaActual, prestamo.getFechaFin());

                multaDTO.setTipoMultaId(1);
                multaDTO.setPrestamoId(prestamo.getIdPrestamo());
                multaDTO.setMonto(dias * coeficienteMulta);
                multaDTO.setFechaMulta(fechaActual);

                multaService.guardar(multaDTO);

                // Aquí marcamos el préstamo como multado
                prestamoService.marcarComoMultado(prestamo.getIdPrestamo());
            }
        }
    }

}
