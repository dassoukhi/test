package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Livre;
import com.mycompany.myapp.repository.LivreRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Livre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LivreResource {

    private final Logger log = LoggerFactory.getLogger(LivreResource.class);

    private static final String ENTITY_NAME = "livre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivreRepository livreRepository;

    public LivreResource(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    /**
     * {@code POST  /livres} : Create a new livre.
     *
     * @param livre the livre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livre, or with status {@code 400 (Bad Request)} if the livre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livres")
    public ResponseEntity<Livre> createLivre(@Valid @RequestBody Livre livre) throws URISyntaxException {
        log.debug("REST request to save Livre : {}", livre);
        if (livre.getId() != null) {
            throw new BadRequestAlertException("A new livre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livre result = livreRepository.save(livre);
        return ResponseEntity.created(new URI("/api/livres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livres} : Updates an existing livre.
     *
     * @param livre the livre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livre,
     * or with status {@code 400 (Bad Request)} if the livre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livres")
    public ResponseEntity<Livre> updateLivre(@Valid @RequestBody Livre livre) throws URISyntaxException {
        log.debug("REST request to update Livre : {}", livre);
        if (livre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livre result = livreRepository.save(livre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, livre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livres} : get all the livres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livres in body.
     */
    @GetMapping("/livres")
    public List<Livre> getAllLivres() {
        log.debug("REST request to get all Livres");
        return livreRepository.findAll();
    }

    /**
     * {@code GET  /livres/:id} : get the "id" livre.
     *
     * @param id the id of the livre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livres/{id}")
    public ResponseEntity<Livre> getLivre(@PathVariable Long id) {
        log.debug("REST request to get Livre : {}", id);
        Optional<Livre> livre = livreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(livre);
    }

    /**
     * {@code DELETE  /livres/:id} : delete the "id" livre.
     *
     * @param id the id of the livre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livres/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        log.debug("REST request to delete Livre : {}", id);
        livreRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/livre_byTheme")
    public List<Livre> getAllTLivreByTheme(@RequestParam(required = false, defaultValue = "") String theme) {
        log.debug("REST request to get all Livres by theme");
        return livreRepository.findAllByTheme(theme);
    }
    
    
    @GetMapping("/livre_by_auteur")
    public List<Livre> getAllTLivreByAuteur(@RequestParam(required = false, defaultValue = "") String auteur) {
        log.debug("REST request to get all Livres by auteur");
        return livreRepository.findAllByAuteur(auteur);
    }
    
    @GetMapping("/livres_by_titre/{titre}")
    public ResponseEntity<Livre> getLivreByTitre(@PathVariable String titre) {
        log.debug("REST request to get Livre : {}", titre);
        Optional<Livre> livre = livreRepository.findByTitre(titre);
        return ResponseUtil.wrapOrNotFound(livre);
    }
}
