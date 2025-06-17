package it.epicode.u5w3d2pratica.service;


import com.cloudinary.Cloudinary;
import it.epicode.u5w3d1pratica.dto.DipendenteDto;
import it.epicode.u5w3d1pratica.exception.NotFoundException;
import it.epicode.u5w3d1pratica.model.Dipendente;
import it.epicode.u5w3d1pratica.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinary;


    public Dipendente saveDipendenta(DipendenteDto dipendenteDto){
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setEmail(dipendenteDto.getEmail());
        dipendente.setUsername(dipendenteDto.getUsername());
        return dipendenteRepository.save(dipendente);
    }

    public Page<Dipendente> getAllDipendenti(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente getDipendente(int id)throws NotFoundException {
        return dipendenteRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Dipendente con id: " + id + " non trovato"));

    }

    public Dipendente updateDipendente(int id, DipendenteDto dipendenteDto)throws NotFoundException {
        Dipendente dipendenteDaAggiornare = getDipendente(id);

        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());;
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());
        dipendenteDaAggiornare.setUsername(dipendenteDto.getUsername());
        return (dipendenteRepository.save(dipendenteDaAggiornare));
    }

    public void deleteDipendente(int id) throws NotFoundException {
        Dipendente dipendenteDaRimuovere = getDipendente(id);

        dipendenteRepository.delete(dipendenteDaRimuovere);
    }

    public String patchDipendente(int id, MultipartFile file) throws IOException, NotFoundException {
        Dipendente dipendenteDaPatchare = getDipendente(id);

        String url = (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        dipendenteDaPatchare.setUrlImmagine(url);
        dipendenteRepository.save(dipendenteDaPatchare);

        return url;


    }

}
