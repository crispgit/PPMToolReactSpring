package com.example.ppmtool.web;

import com.example.ppmtool.domain.Project;
import com.example.ppmtool.services.ProjectService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProjectControllerTest {

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

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
    void getProjectById() {
        when(projectService.findProjectByIdentifier(anyString(), anyObject())).thenReturn(project);
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "me";
            }
        };
        ResponseEntity<?> res = projectController.getProjectById("test", principal);
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }



}