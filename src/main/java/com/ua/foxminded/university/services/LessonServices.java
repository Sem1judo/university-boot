package com.ua.foxminded.university.services;


import com.ua.foxminded.university.dao.impl.FacultyRepositoryImpl;
import com.ua.foxminded.university.dao.impl.LectorRepositoryImpl;
import com.ua.foxminded.university.dao.impl.LessonRepositoryImpl;
import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.dto.LessonDto;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.Lesson;
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
import java.util.Optional;


@Service
@Transactional
public class LessonServices {

    @Autowired
    private LessonRepositoryImpl lessonDao;

    @Autowired
    private LectorRepositoryImpl lectorDao;

    @Autowired
    private FacultyRepositoryImpl facultyDao;

    @Autowired
    private ValidatorEntity<Lesson> validator;

    private static final Logger logger = LoggerFactory.getLogger(LessonServices.class);

    private static final String MISSING_ID_ERROR_MESSAGE = "Missing id lesson.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such lesson";


    private LessonDto getDtoById(Long id) {

        Lesson lesson = lessonDao.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Invalid lesson ID"));
        Lector lector = lectorDao.findById(lesson.getLector().getLectorId())
                .orElseThrow(() -> new NoSuchEntityException("Invalid lector ID"));
        Faculty faculty = facultyDao.findById(lector.getFaculty().getFacultyId())
                .orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));

        LectorDto lectorDto = new LectorDto();
        lectorDto.setLectorId(lector.getLectorId());
        lectorDto.setFirstName(lector.getFirstName());
        lectorDto.setLastName(lector.getLastName());
        lectorDto.setFaculty(faculty);

        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setName(lesson.getName());
        lessonDto.setLector(lectorDto);

        return lessonDto;
    }

    private List<LessonDto> getAllDto() {
        List<Lesson> lessons = lessonDao.findAll();
        List<LessonDto> lessonDtos = new ArrayList<>();

        Lector lector;
        Faculty faculty;

        LessonDto lessonDto;
        LectorDto lectorDto;

        for (Lesson lesson : lessons) {

            lector = lectorDao.findById(lesson.getLector().getLectorId())
                    .orElseThrow(() -> new NoSuchEntityException("Invalid lector ID"));
            faculty = facultyDao.findById(lector.getFaculty().getFacultyId())
                    .orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));

            lectorDto = new LectorDto();
            lectorDto.setLectorId(lector.getLectorId());
            lectorDto.setFirstName(lector.getFirstName());
            lectorDto.setLastName(lector.getLastName());
            lectorDto.setFaculty(faculty);

            lessonDto = new LessonDto();
            lessonDto.setLessonId(lesson.getLessonId());
            lessonDto.setName(lesson.getName());
            lessonDto.setLector(lectorDto);

            lessonDtos.add(lessonDto);
        }

        return lessonDtos;
    }

    public LessonDto getById(long id) {
        logger.debug("Trying to get lesson with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        LessonDto lesson;
        try {
            lesson = getDtoById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lesson with id={}", id, e);
            throw new ServiceException("Failed to retrieve lesson by id", e);
        }
        return lesson;
    }

    public List<LessonDto> getAll() {
        logger.debug("Trying to get all lessons");

        try {
            return getAllDto();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Lessons is not exist");
            throw new NoSuchEntityException("Doesn't exist such lessons");
        } catch (DataAccessException e) {
            logger.error("Failed to get all lessons", e);
            throw new ServiceException("Failed to get list of lessons", e);
        }
    }

    public Lesson getByIdLight(long id) {
        logger.debug("Trying to get lesson with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        Lesson lesson;
        try {
            lesson = lessonDao.findById(id)
                    .orElseThrow(() -> new NoSuchEntityException("Invalid lesson ID"));

        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lesson with id={}", id, e);
            throw new ServiceException("Failed to retrieve lesson by id", e);
        }
        return lesson;
    }

    public Optional<Lesson> findById(long id) {
        logger.debug("Trying to get lesson with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        try {
            return lessonDao.findById(id);

        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lesson with id={}", id, e);
            throw new ServiceException("Failed to retrieve lesson by id", e);
        }
    }

    public List<Lesson> getAllLight() {
        logger.debug("Trying to get all lessons");

        try {
            return lessonDao.findAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Lessons is not exist");
            throw new NoSuchEntityException("Doesn't exist such lessons");
        } catch (DataAccessException e) {
            logger.error("Failed to get all lessons", e);
            throw new ServiceException("Failed to get list of lessons", e);
        }
    }


    public void create(Lesson lesson, long lectorId) {
        logger.debug("Trying to create lesson: {}", lesson);
        logger.debug("Trying to get lector By Id with id: {}", lectorId);


        getLector(lesson, lectorId);

        validator.validate(lesson);
        try {
            lessonDao.save(lesson);
        } catch (DataAccessException e) {
            logger.error("Failed to create lesson: {}", lesson, e);
            throw new ServiceException("Failed to create lesson", e);
        }
    }

    public Lesson save(Lesson lesson, long lectorId) {
        logger.debug("Trying to create lesson: {}", lesson);
        logger.debug("Trying to get lector By Id with id: {}", lectorId);

        getLector(lesson, lectorId);

        validator.validate(lesson);
        try {
            return lessonDao.save(lesson);
        } catch (DataAccessException e) {
            logger.error("Failed to create lesson: {}", lesson, e);
            throw new ServiceException("Failed to create lesson", e);
        }
    }

    public void deleteById(long id) {
        logger.debug("Trying to delete lesson with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        try {
            lessonDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete lesson with id={}", id, e);
            throw new ServiceException("Failed to delete lesson by id", e);
        }
    }

    public void delete(Lesson lesson) {
        logger.debug("Trying to delete lesson ={}", lesson);

        if (lesson == null) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        try {
            lessonDao.delete(lesson);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson ={}", lesson);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete lesson ={}", lesson, e);
            throw new ServiceException("Failed to delete lesson ", e);
        }
    }

    public void update(Lesson lesson, long lectorId) {
        logger.debug("Trying to update lesson: {}", lesson);
        logger.debug("Trying to get lector By Id with id: {}", lectorId);

        if (lesson.getLessonId() == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        getLector(lesson, lectorId);

        validator.validate(lesson);
        try {
            lessonDao.findById(lesson.getLessonId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing lesson: {}", lesson);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("failed to retrieve lesson: {}", lesson, e);
            throw new ServiceException("Failed to retrieve lesson by id", e);
        }
        try {
            lessonDao.save(lesson);
        } catch (DataAccessException e) {
            logger.error("failed to update lesson: {}", lesson, e);
            throw new ServiceException("Problem with updating lesson");
        }
    }

    private void getLector(Lesson lesson, long lectorId) {
        try {
            Lector lector = lectorDao.findById(lectorId)
                    .orElseThrow(() -> new NoSuchEntityException("Invalid lector ID"));
            lesson.setLector(lector);
        } catch (NoSuchEntityException e) {
            logger.error("Failed to retrieve cause Invalid lector ID: {}", lectorId);
            throw new ServiceException("Failed to retrieve lector from such id: ", e);
        }
    }
}
