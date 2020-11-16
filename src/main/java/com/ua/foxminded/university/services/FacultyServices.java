package com.ua.foxminded.university.services;


import com.ua.foxminded.university.dao.impl.FacultyRepositoryImpl;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.NoSuchEntityException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class FacultyServices {

    @Autowired
    private FacultyRepositoryImpl facultyDao;

    @Autowired
    private ValidatorEntity<Faculty> validator;

    private static final Logger logger = LoggerFactory.getLogger(FacultyServices.class);

    private static final String MISSING_ID_ERROR_MESSAGE = "Missing id faculty.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such faculty";

    public List<Faculty> getAll() {
        logger.debug("Trying to get all faculties");
        try {
            return (List<Faculty>) facultyDao.findAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Faculties is not exist");
            throw new NoSuchEntityException("Doesn't exist such faculties");
        } catch (DataAccessException e) {
            logger.error("Failed to get all faculties", e);
            throw new ServiceException("Failed to get list of faculties", e);
        }
    }

    public void create(Faculty faculty) {
        logger.debug("Trying to create faculty: {}", faculty);

        validator.validate(faculty);
        try {
           facultyDao.save(faculty);
        } catch (DataAccessException e) {
            logger.error("Failed to create faculty: {}", faculty, e);
            throw new ServiceException("Failed to create faculty", e);
        }
    }
    public Faculty save(Faculty faculty) {
        logger.debug("Trying to create faculty: {}", faculty);

        validator.validate(faculty);
        try {
            return facultyDao.save(faculty);
        } catch (DataAccessException e) {
            logger.error("Failed to create faculty: {}", faculty, e);
            throw new ServiceException("Failed to create faculty", e);
        }
    }

    public void deleteById(long id) {
        logger.debug("Trying to delete faculty with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
             facultyDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing faculty with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to delete faculty with id={}", id, e);
            throw new ServiceException("Failed to delete faculty by id", e);
        }
    }
    public void delete(Faculty faculty) {
        logger.debug("Trying to delete faculty = {}", faculty);

        if (faculty == null) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            facultyDao.delete(faculty);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing faculty = {}", faculty);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to delete faculty = {}", faculty, e);
            throw new ServiceException("Failed to delete faculty", e);
        }
    }

    public Faculty getById(long id) {
        logger.debug("Trying to get faculty with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        Faculty faculty;
        try {
            faculty = facultyDao.findById(id)
                    .orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing faculty with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve faculty with id={}", id, e);
            throw new ServiceException("Failed to retrieve faculty with such id", e);
        }
        return faculty;
    }
    public Optional<Faculty> findById(long id) {
        logger.debug("Trying to get faculty with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
          return facultyDao.findById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing faculty with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve faculty with id={}", id, e);
            throw new ServiceException("Failed to retrieve faculty with such id", e);
        }
    }

    public void update(Faculty faculty) {
        logger.debug("Trying to update faculty: {}", faculty);

        if (faculty.getFacultyId() == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        validator.validate(faculty);
        try {
            facultyDao.findById(faculty.getFacultyId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing faculty: {}", faculty);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve faculty: {}", faculty);
            throw new ServiceException("Failed to retrieve faculty: ", e);
        }
        try {
             facultyDao.save(faculty);
        } catch (DataAccessException e) {
            logger.error("Failed to update faculty: {}", faculty);
            throw new ServiceException("Problem with updating faculty");
        }
    }
}


