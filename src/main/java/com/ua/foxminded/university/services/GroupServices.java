package com.ua.foxminded.university.services;

import com.ua.foxminded.university.dao.impl.FacultyRepositoryImpl;
import com.ua.foxminded.university.dao.impl.GroupRepositoryImpl;
import com.ua.foxminded.university.dto.GroupDto;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
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
public class GroupServices {

    @Autowired
    private GroupRepositoryImpl groupDao;

    @Autowired
    private FacultyRepositoryImpl facultyDao;

    @Autowired
    private ValidatorEntity<Group> validator;

    private static final Logger logger = LoggerFactory.getLogger(GroupServices.class);

    private static final String MISSING_ID_ERROR_MESSAGE = "Missing id group.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such group";


    private GroupDto getDtoById(Long id) {

        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchEntityException("Invalid groupd ID"));
        Faculty faculty = facultyDao.findById(group.getFaculty().getFacultyId()).orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));

        GroupDto groupDto = new GroupDto();
        groupDto.setGroupId(group.getGroupId());
        groupDto.setFaculty(faculty);
        groupDto.setName(group.getName());

        return groupDto;
    }

    private List<GroupDto> getAllDto() {
        List<Group> groups = groupDao.findAll();
        List<GroupDto> groupDtos = new ArrayList<>();

        GroupDto groupDto;
        Faculty faculty;

        for (Group group : groups) {

            group = groupDao.findById(group.getGroupId()).orElseThrow(() -> new NoSuchEntityException("Invalid groupd ID"));;;
            faculty = facultyDao.findById(group.getFaculty().getFacultyId()).orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));

            groupDto = new GroupDto();
            groupDto.setGroupId(group.getGroupId());
            groupDto.setFaculty(faculty);
            groupDto.setName(group.getName());

            groupDtos.add(groupDto);
        }

        return groupDtos;
    }

    public List<GroupDto> getAll() {
        logger.debug("Trying to get all groups");

        try {
            return getAllDto();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Groups is not exist");
            throw new NoSuchEntityException("Doesn't exist such groups");
        } catch (DataAccessException e) {
            logger.error("Failed to get all groups", e);
            throw new ServiceException("Failed to get list of groups", e);
        }
    }

    public GroupDto getById(long id) {
        logger.debug("Trying to get group with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        GroupDto group;
        try {
            group = getDtoById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve group with id={}", id, e);
            throw new ServiceException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public Group getByIdLight(long id) {
        logger.debug("Trying to get group with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        Group group;
        try {
            group = groupDao.findById(id).orElseThrow(() -> new NoSuchEntityException("Invalid group ID"));
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve group with id={}", id, e);
            throw new ServiceException("Failed to retrieve group by id", e);
        }
        return group;
    }

    public List<Group> getAllLight() {
        logger.debug("Trying to get all groups");

        try {
            return groupDao.findAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Groups is not exist");
            throw new NoSuchEntityException("Doesn't exist such groups");
        } catch (DataAccessException e) {
            logger.error("Failed to get all groups", e);
            throw new ServiceException("Failed to get list of groups", e);
        }
    }

    public void create(Group group, long facultyId) {
        logger.debug("Trying to create group: {}", group);
        logger.debug("Trying to get faculty By Id with id: {}", facultyId);

        Faculty faculty = facultyDao.findById(facultyId).orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));
        group.setFaculty(faculty);

        validator.validate(group);
        try {
            groupDao.save(group);
        } catch (DataAccessException e) {
            logger.error("Failed to create group: {}", group, e);
            throw new ServiceException("Failed to create group", e);
        }
    }

    public void deleteById(long id) {
        logger.debug("Trying to delete group with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            groupDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete group with id={}", id, e);
            throw new ServiceException("Failed to delete group by id", e);
        }
    }

    public void delete(Group group) {
        logger.debug("Trying to delete group {}", group);

        if (group == null) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            groupDao.delete(group);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group = {}", group);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete group ={}", group, e);
            throw new ServiceException("Failed to delete group", e);
        }
    }


    public void update(Group group, long facultyId) {
        logger.debug("Trying to update group: {}", group);
        logger.debug("Trying to get faculty By Id with id: {}", facultyId);



        if (group.getGroupId() == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }

        Faculty faculty = facultyDao.findById(facultyId)
                .orElseThrow(() -> new NoSuchEntityException("Invalid faculty ID"));
        group.setFaculty(faculty);

        validator.validate(group);
        try {
            groupDao.findById(group.getGroupId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing group: {}", group);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve group: {}", group);
            throw new ServiceException("Failed to retrieve group from such id: ", e);
        }

        try {
            groupDao.save(group);
        } catch (DataAccessException e) {
            logger.error("Failed to update group: {}", group);
            throw new ServiceException("Problem with updating group");
        }
    }

//    public int getLessonsForGroup(Long id) {
//        logger.debug("Trying to get lessons for group with id={}", id);
//        if (id == 0) {
//            logger.warn(MISSING_ID_ERROR_MESSAGE);
//            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
//        }
//        try {
//            return groupDao.getLessonsById(id);
//        } catch (EmptyResultDataAccessException e) {
//            logger.warn("Not existing group with id={}", id);
//            throw new NoSuchEntityException("Doesn't exist such lessons for group");
//        } catch (DataAccessException e) {
//            logger.error("Failed to get lessons group by such id={}", id);
//            throw new ServiceException("Failed to get lessons group by such id", e);
//        }

}
