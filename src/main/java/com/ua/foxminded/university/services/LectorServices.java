package com.ua.foxminded.university.services;

import com.ua.foxminded.university.dao.impl.FacultyDaoImpl;
import com.ua.foxminded.university.dao.impl.LectorDaoImpl;
import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.NoSuchEntityException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LectorServices {

    @Autowired
    private LectorDaoImpl lectorDao;

    @Autowired
    private FacultyDaoImpl facultyDao;
    @Autowired
    private ValidatorEntity<Lector> validator;

    private static final Logger logger = LoggerFactory.getLogger(LectorServices.class);

    private static final String MISSING_ID_ERROR_MESSAGE = "Missing id lector.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such lector";


    private LectorDto getDtoById(Long id) {

        Lector lector = lectorDao.getById(id);
        Faculty faculty = facultyDao.getById(lector.getFaculty().getFacultyId());

        LectorDto lectorDto = new LectorDto();
        lectorDto.setLectorId(lector.getLectorId());
        lectorDto.setFaculty(faculty);
        lectorDto.setFirstName(lector.getFirstName());
        lectorDto.setLastName(lector.getLastName());

        return lectorDto;
    }

    private List<LectorDto> getAllDto() {
        List<Lector> lectors = lectorDao.getAll();
        List<LectorDto> lectorDtos = new ArrayList<>();

        LectorDto lectorDto;
        Faculty faculty;

        for (Lector lector : lectors) {

            lector = lectorDao.getById(lector.getLectorId());
            faculty = facultyDao.getById(lector.getFaculty().getFacultyId());

            lectorDto = new LectorDto();
            lectorDto.setLectorId(lector.getLectorId());
            lectorDto.setFaculty(faculty);
            lectorDto.setFirstName(lector.getFirstName());
            lectorDto.setLastName(lector.getLastName());

            lectorDtos.add(lectorDto);
        }

        return lectorDtos;
    }

    public List<LectorDto> getAll() {
        logger.debug("Trying to get all lectors");
        try {
            return getAllDto();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Lectors is not exist");
            throw new NoSuchEntityException("Doesn't exist such lectors");
        } catch (DataAccessException e) {
            logger.error("Failed to get all lectors", e);
            throw new ServiceException("Failed to get list of lector", e);
        }
    }

    public LectorDto getById(long id) {
        logger.debug("Trying to get lector with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        LectorDto lector;
        try {
            lector = getDtoById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lector with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lector with id={}", id, e);
            throw new ServiceException("Failed to retrieve lector by such id: ", e);
        }
        return lector;
    }

    public Lector getByIdLight(long id) {
        logger.debug("Trying to get lector with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        Lector lector;
        try {
            lector = lectorDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lector with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lector with id={}", id, e);
            throw new ServiceException("Failed to retrieve lector by such id: ", e);
        }
        return lector;
    }

    public List<Lector> getAllLight() {
        logger.debug("Trying to get all lectors");
        try {
            return lectorDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Lectors is not exist");
            throw new NoSuchEntityException("Doesn't exist such lectors");
        } catch (DataAccessException e) {
            logger.error("Failed to get all lectors", e);
            throw new ServiceException("Failed to get list of lector", e);
        }
    }

    public void create(Lector lector,long facultyId) {
        logger.debug("Trying to create lector: {}", lector);
        logger.debug("Trying to get facultyById with id: {}", facultyId);

        Faculty faculty = facultyDao.getById(facultyId);
        lector.setFaculty(faculty);

        validator.validate(lector);
        try {
            lectorDao.create(lector);
        } catch (
                DataAccessException e) {
            logger.error("failed to create lector: {}", lector, e);
            throw new ServiceException("Failed to create lector", e);
        }

    }

    public void deleteById(long id) {
        logger.debug("Trying to delete lector with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            lectorDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lector with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to delete lector with id={}", id, e);
            throw new ServiceException("Failed to delete lector by such id", e);
        }
    }

    public void delete(Lector lector) {
        logger.debug("Trying to delete lector ={}", lector);

        if (lector == null) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            lectorDao.delete(lector);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lector ={}", lector);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to delete lector ={}", lector, e);
            throw new ServiceException("Failed to delete lector", e);
        }
    }

    public void update(Lector lector,long facultyId) {
        logger.debug("Trying to update lector: {}", lector);
        logger.debug("Trying to get faculty By Id with id: {}", facultyId);

        if (lector.getLectorId() == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        Faculty faculty = facultyDao.getById(facultyId);
        lector.setFaculty(faculty);

        validator.validate(lector);
        try {
            lectorDao.getById(lector.getLectorId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lector: {}", lector);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve lector: {}", lector, e);
            throw new ServiceException("Failed to retrieve lector by id" + e);
        }

        try {
            lectorDao.update(lector);
        } catch (DataAccessException e) {
            logger.error("Failed to update lector: {}", lector, e);
            throw new ServiceException("Problem with updating lector");
        }
    }

//    public int getLessonsForLector(LocalDateTime start, LocalDateTime end) {
//        logger.debug("Trying to get lessons for lector with start time={} and end time={}", start, end);
//
//        try {
//            return lectorDao.getLessonsByTime(start, end);
//        } catch (EmptyResultDataAccessException e) {
//            logger.warn("Not existing lessons with such: start time={} and end time={}", start, end);
//            throw new NoSuchEntityException("Doesn't exist such lessons for lector");
//        } catch (DataAccessException e) {
//            logger.error("Failed to get lessons for lector with start time={} and end time={}", start, end, e);
//            throw new ServiceException("Failed to get lessons for lector by id", e);
//        }
//}
}

