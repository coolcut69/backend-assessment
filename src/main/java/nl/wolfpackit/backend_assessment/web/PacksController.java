package nl.wolfpackit.backend_assessment.web;

import java.util.List;
import java.util.stream.Collectors;

import nl.wolfpackit.backend_assessment.model.PackEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PacksController implements PacksSwagger {

    private final WolvesService service;

    public PacksController(WolvesService service) {
        this.service = service;
    }

    @GetMapping("/api/packs")
    ResponseEntity<List<PackResource>> searchPacks(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "name") String sort) {
        log.info("GET /api/pack?size=" + size + "&page=" + page + "&sort=" + sort);

        List<PackEntity> entities = service.getPacks(size, page, sort);
        List<PackResource> resources = entities.stream().map(PackResource::new).collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @PostMapping("api/packs/{pack-id}/wolves/{wolf-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addWolfToPack(@PathVariable("pack-id") Long packId, @PathVariable("wolf-id") Long wolfId) {
        log.info("POST /api/packs/" + packId + "/wolves/" + wolfId);
        service.addWolfToPack(packId, wolfId);
    }

    @DeleteMapping("api/packs/{pack-id}/wolves/{wolf-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWolfToPack(@PathVariable("pack-id") Long packId, @PathVariable("wolf-id") Long wolfId) {
        log.info("DELETE /api/packs/" + packId + "/wolves/" + wolfId);
        service.deleteWolfFromPack(packId, wolfId);
    }


}
