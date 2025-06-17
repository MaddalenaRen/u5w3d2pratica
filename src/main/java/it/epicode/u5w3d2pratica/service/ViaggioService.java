package it.epicode.u5w3d2pratica.service;



import it.epicode.u5w3d2pratica.dto.ViaggioDto;
import it.epicode.u5w3d2pratica.enumaration.StatoViaggio;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.model.Viaggio;
import it.epicode.u5w3d2pratica.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;



    public Viaggio saveViaggio(ViaggioDto viaggioDto){
        Viaggio viaggio = new Viaggio();

        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setData(viaggioDto.getData());
        viaggio.setStatoViaggio(viaggioDto.getStatoViaggio());

        return viaggioRepository.save(viaggio);


    }


    public Page<Viaggio> getAllViaggi(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }


    public Viaggio getViaggio(int id )throws NotFoundException{
        return viaggioRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Il viaggio con id: " + id + " non trovato"));
    }


    public Viaggio updateViaggio(int id, ViaggioDto viaggioDto)throws NotFoundException{
        Viaggio viaggioDaAggiornare = getViaggio(id);

        viaggioDaAggiornare.setDestinazione(viaggioDto.getDestinazione());
        viaggioDaAggiornare.setData(viaggioDto.getData());
        viaggioDaAggiornare.setStatoViaggio(viaggioDto.getStatoViaggio());

        return viaggioRepository.save(viaggioDaAggiornare);
    }


    public void deleteViaggio(int id)throws NotFoundException{
        Viaggio viaggioDaRimuovere = getViaggio(id);

        viaggioRepository.delete(viaggioDaRimuovere);
    }

    public Viaggio changeStatus(int id, StatoViaggio nuovoStato) throws NotFoundException {
        Viaggio viaggio = viaggioRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Viaggio con id " + id + " non trovato"));
        viaggio.setStatoViaggio(nuovoStato);
        return viaggioRepository.save(viaggio);

    }




}
