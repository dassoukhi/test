package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Theme;
import com.mycompany.myapp.repository.ThemeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Theme}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);

    private static final String ENTITY_NAME = "theme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeRepository themeRepository;

    public ThemeResource(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * {@code POST  /themes} : Create a new theme.
     *
     * @param theme the theme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new theme, or with status {@code 400 (Bad Request)} if the theme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/themes")
    public ResponseEntity<Theme> createTheme(@Valid @RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", theme);
        if (theme.getId() != null) {
            throw new BadRequestAlertException("A new theme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /themes} : Updates an existing theme.
     *
     * @param theme the theme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated theme,
     * or with status {@code 400 (Bad Request)} if the theme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the theme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/themes")
    public ResponseEntity<Theme> updateTheme(@Valid @RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to update Theme : {}", theme);
        if (theme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, theme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /themes} : get all the themes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themes in body.
     */
    @GetMapping("/themes")
    public List<Theme> getAllThemes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Themes");
        return themeRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /themes/:id} : get the "id" theme.
     *
     * @param id the id of the theme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the theme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/themes/{id}")
    public ResponseEntity<Theme> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        Optional<Theme> theme = themeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(theme);
    }

    /**
     * {@code DELETE  /themes/:id} : delete the "id" theme.
     *
     * @param id the id of the theme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
