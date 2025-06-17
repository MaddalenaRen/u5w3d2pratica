package it.epicode.u5w3d2pratica.service;



import it.epicode.u5w3d1pratica.dto.PrenotazioneDto;
import it.epicode.u5w3d1pratica.exception.NotFoundException;
import it.epicode.u5w3d1pratica.exception.ValidationException;
import it.epicode.u5w3d1pratica.model.Dipendente;
import it.epicode.u5w3d1pratica.model.Prenotazione;
import it.epicode.u5w3d1pratica.model.Viaggio;
import it.epicode.u5w3d1pratica.repository.DipendenteRepository;
import it.epicode.u5w3d1pratica.repository.PrenotazioneRepository;
import it.epicode.u5w3d1pratica.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;  // inietta anche questi
    @Autowired
    private ViaggioRepository viaggioRepository;



    public Prenotazione savePrenotazione (PrenotazioneDto prenotazioneDto) throws ValidationException, NotFoundException {

        if (prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(prenotazioneDto.getDipendenteId(), prenotazioneDto.getDataRichiesta())) {
            throw new ValidationException("Diepndente già prenotato in quella data");
        }

        Dipendente dipendente = dipendenteRepository.findById(prenotazioneDto.getDipendenteId()).
                orElseThrow(() -> new NotFoundException("Dipendente non trovato"));

        Viaggio viaggio = viaggioRepository.findById(prenotazioneDto.getViaggioId()).
                orElseThrow(() -> new NotFoundException("viaggio non trovato"));

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazione.setNote(prenotazioneDto.getNote());
        prenotazione.setPreferenze(prenotazioneDto.getPreferenze());
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);

        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> getAllPrenotazioni(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione getPrenotazione(int id)throws NotFoundException {
        return prenotazioneRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Prenotazione con id: " + id + " non trovata"));


    }

    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto)throws NotFoundException{
        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        Dipendente dipendente = dipendenteRepository.findById(prenotazioneDto.getDipendenteId()).
                orElseThrow(() -> new NotFoundException("Dipendente non trovato"));

        Viaggio viaggio = viaggioRepository.findById(prenotazioneDto.getViaggioId()).
                orElseThrow(() -> new NotFoundException("viaggio non trovato"));

        prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazioneDaAggiornare.setNote(prenotazioneDto.getNote());
        prenotazioneDaAggiornare.setPreferenze(prenotazioneDto.getPreferenze());
        prenotazioneDaAggiornare.setDipendente(dipendente);
        prenotazioneDaAggiornare.setViaggio(viaggio);

        return (prenotazioneRepository.save(prenotazioneDaAggiornare));
    }

    public void deletePrenotazione(int id)throws NotFoundException {
        Prenotazione prenotazioneDaRimuovere = getPrenotazione(id);

        prenotazioneRepository.delete(prenotazioneDaRimuovere);
    }


}
