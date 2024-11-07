package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.ISerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    ISerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return convertirDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convertirDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> getLanzamientosMasRecientes() {
        return convertirDatos(repository.lanzamientosMasRecientes());
    }

    public List<SerieDTO> convertirDatos(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(),
                        s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public SerieDTO getById(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(),
                    s.getGenero(), s.getActores(), s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDTO> getTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> getTemporadasByNumber(Long id, Long numberTemporada) {
        return repository.getTemporadaByNumber(id, numberTemporada).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> getSeriesByCategory(String genero) {
        Categoria categoria = Categoria.fromEspañol(genero);
        return convertirDatos(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> getTop5Episodios(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return repository.top5Episodios(s).stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
