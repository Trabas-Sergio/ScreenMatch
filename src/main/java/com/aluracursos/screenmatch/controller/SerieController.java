package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.repository.ISerieRepository;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
        return serieService.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return serieService.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    List<SerieDTO> getLanzamientosMasRecientes() {
        return serieService.getLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return serieService.getById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> getTemporadas(@PathVariable Long id) {
        return serieService.getTemporadas(id);
    }
    @GetMapping("/{id}/temporadas/{numberTemporada}")
    public List<EpisodioDTO> getTemporadasByNumber(@PathVariable Long id, @PathVariable Long numberTemporada) {
        return serieService.getTemporadasByNumber(id, numberTemporada);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> getByCategory(@PathVariable String genero) {
        return serieService.getSeriesByCategory(genero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> getTop5Episodios(@PathVariable Long id) {
        return serieService.getTop5Episodios(id);
    }

}
