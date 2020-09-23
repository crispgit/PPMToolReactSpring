package com.example.ppmtool.services;

import com.example.ppmtool.domain.Project;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        project = new Project();
        project.setId(1L);
        project.setProjectName("testName");
        project.setProjectIdentifier("test");
        project.setDescription("testDesc");
        project.setStart_date(new Date(5000));
        project.setEnd_date(new Date(6000));
        project.setCreated_At(new Date(3000));
        project.setUpdated_At(new Date(4000));
        project.setProjectLeader("me");
    }

    @Test
    final void testFindProjectByIdentifierTrue() {
        when(projectRepository.findByProjectIdentifier(anyString())).thenReturn(project);
        Project projectFound = projectService.findProjectByIdentifier("test", "me");
        assertNotNull(projectFound);
        assertEquals("testName", projectFound.getProjectName());
    }

    @Test
    final void testFindProjectByIdentifierProjectIdException() {
        when(projectRepository.findByProjectIdentifier(anyString())).thenReturn(null);

        assertThrows(ProjectIdException.class,
                () -> {
                    projectService.findProjectByIdentifier("test", "me");
                });
    }

    @Test
    final void testFindProjectByIdentifierProjectNotFoundException() {
        when(projectRepository.findByProjectIdentifier(anyString())).thenReturn(project);

        assertThrows(ProjectNotFoundException.class,
                () -> {
                    projectService.findProjectByIdentifier("test", "he");
                });
    }
}