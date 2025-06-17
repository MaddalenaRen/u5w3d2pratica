package it.epicode.u5w3d2pratica.controller;



import it.epicode.u5w3d2pratica.dto.DipendenteDto;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.Dipendente;
import it.epicode.u5w3d2pratica.service.DipendenteService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult)throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,s)->e+s));
        }
        return dipendenteService.saveDipendenta(dipendenteDto);

    }

    @GetMapping("")
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){

        return dipendenteService.getAllDipendenti(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Dipendente getDipendente(@PathVariable int id) throws NotFoundException {
        return dipendenteService.getDipendente(id);
    }

    @PutMapping ("/{id}")
    public Dipendente updateDipendente(@PathVariable int id,@RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult)throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,s)->e+s));
        }
         return dipendenteService.updateDipendente(id, dipendenteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable int id) throws NotFoundException {
        dipendenteService.deleteDipendente(id);
    }

    @PatchMapping("/{id}/profile-image")
    public String uploadProfileImage(@PathVariable int id,@RequestBody @NotNull MultipartFile file) throws NotFoundException, IOException {
        return dipendenteService.patchDipendente(id, file);


    }


}
