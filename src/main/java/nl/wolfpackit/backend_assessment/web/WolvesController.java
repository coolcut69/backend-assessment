package nl.wolfpackit.backend_assessment.web;

import java.util.List;
import java.util.stream.Collectors;

import nl.wolfpackit.backend_assessment.model.WolfEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WolvesController implements WolvesSwagger{

    private final WolvesService service;

    public WolvesController(WolvesService service) {
        this.service = service;
    }

    @GetMapping("/api/wolves")
    ResponseEntity<List<WolfResource>> searchWolves(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "name") String sort) {
        log.info("GET /api/wolves?size=" + size + "&page=" + page + "&sort=" + sort);

        List<WolfEntity> entities = service.getWolves(size, page, sort);
        List<WolfResource> resources = entities.stream().map(WolfResource::new).collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @PostMapping("/api/wolves")
    @Override
    public ResponseEntity<WolfResource> createWolf(@RequestBody WolfResource resource) {
        log.info("POST /api/wolves/" + resource);

        WolfEntity entity = service.createWolf(resource);
        return ResponseEntity.ok(new WolfResource(entity));
    }

    @PutMapping("/api/wolves/{id}")
    public ResponseEntity<WolfResource> updateWolf(@RequestBody WolfResource resource, @PathVariable Long id) {
        log.info("PUT /api/wolves/" + id + "with body " + resource);

        WolfEntity entity = service.updateWolf(id, resource);
        return ResponseEntity.ok(new WolfResource(entity));
    }

    @DeleteMapping("/api/wolves/{id}")
    public void deleteWolf(@PathVariable Long id) {
        log.info("DELETE /api/wolves/" + id);

        service.deleteWolf(id);
    }


}
