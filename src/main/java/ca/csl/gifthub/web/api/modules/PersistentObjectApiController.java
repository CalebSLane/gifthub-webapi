package ca.csl.gifthub.web.api.modules;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import ca.csl.gifthub.core.persistence.PersistentObject;
import ca.csl.gifthub.core.persistence.service.PersistentObjectService;

public abstract class PersistentObjectApiController<T extends PersistentObject<K>, K extends Serializable>
        extends ApiController {

    private final PersistentObjectService<T, K> persistentObjectService;

    public PersistentObjectApiController(PersistentObjectService<T, K> persistentObjectService) {
        this.persistentObjectService = persistentObjectService;
    }

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<T> listAll() {
        return this.persistentObjectService.getAll();
    }

    @PutMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public K createNew(@Valid T object, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        return this.persistentObjectService.insert(object);
    }

    @DeleteMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<K> deleteAll(List<K> ids) {
        return this.persistentObjectService.deleteAllById(ids, false);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public T showUser(@PathVariable K id) {
        return this.persistentObjectService.getById(id);
    }

    @PostMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public T editUser(@PathVariable K id, @Valid T object, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        object.setId(id);
        return this.persistentObjectService.update(object);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public K deleteUser(@PathVariable K id) {
        this.persistentObjectService.deleteById(id);
        return id;
    }

}
