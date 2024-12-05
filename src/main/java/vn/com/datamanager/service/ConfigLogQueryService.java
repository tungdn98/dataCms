package vn.com.datamanager.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import vn.com.datamanager.domain.*; // for static metamodels
import vn.com.datamanager.domain.ConfigLog;
import vn.com.datamanager.repository.ConfigLogRepository;
import vn.com.datamanager.service.criteria.ConfigLogCriteria;

/**
 * Service for executing complex queries for {@link ConfigLog} entities in the database.
 * The main input is a {@link ConfigLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConfigLog} or a {@link Page} of {@link ConfigLog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigLogQueryService extends QueryService<ConfigLog> {

    private final Logger log = LoggerFactory.getLogger(ConfigLogQueryService.class);

    private final ConfigLogRepository configLogRepository;

    public ConfigLogQueryService(ConfigLogRepository configLogRepository) {
        this.configLogRepository = configLogRepository;
    }

    /**
     * Return a {@link List} of {@link ConfigLog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigLog> findByCriteria(ConfigLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConfigLog> specification = createSpecification(criteria);
        return configLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConfigLog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigLog> findByCriteria(ConfigLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigLog> specification = createSpecification(criteria);
        return configLogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConfigLog> specification = createSpecification(criteria);
        return configLogRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigLog> createSpecification(ConfigLogCriteria criteria) {
        Specification<ConfigLog> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConfigLog_.id));
            }
            if (criteria.getConfigName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigName(), ConfigLog_.configName));
            }
            if (criteria.getValueBefore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueBefore(), ConfigLog_.valueBefore));
            }
            if (criteria.getValueAfter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueAfter(), ConfigLog_.valueAfter));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), ConfigLog_.modifiedDate));
            }
            if (criteria.getModifiedUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedUsername(), ConfigLog_.modifiedUsername));
            }
            if (criteria.getModifiedFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedFullname(), ConfigLog_.modifiedFullname));
            }
        }
        return specification;
    }
}
