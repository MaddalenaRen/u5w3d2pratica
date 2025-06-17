package it.epicode.u5w3d2pratica.controller;


import it.epicode.u5w3d2pratica.dto.ViaggioDto;
import it.epicode.u5w3d2pratica.enumaration.StatoViaggio;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.Viaggio;
import it.epicode.u5w3d2pratica.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/viaggi")
public class ViaggioController{

    @Autowired
    private ViaggioService viaggioService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio saveViaggio(@RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }

        return viaggioService.saveViaggio(viaggioDto);
    }


    @GetMapping("")
    public Page<Viaggio> getAllViaggi(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy){

        return viaggioService.getAllViaggi(page,size, sortBy);
    }

    @GetMapping("/{id}")
    public Viaggio getViaggio(@PathVariable int id)throws NotFoundException {
        return viaggioService.getViaggio(id);
    }

    @PutMapping("/{id}")
    public Viaggio updateViaggio(@PathVariable int id,@RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return viaggioService.updateViaggio(id, viaggioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViaggio(@PathVariable int id) throws NotFoundException {
        viaggioService.deleteViaggio(id);
    }

    @PatchMapping("/{id}/status")
    public Viaggio changeStatus(
            @PathVariable int id,
            @RequestParam("stato") StatoViaggio stato
    ) throws NotFoundException {
        return viaggioService.changeStatus(id, stato);
    }




}