package com.bilgeadam.service;

import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.dto.response.StatusDto;
import com.bilgeadam.dto.response.TeacherDetailResponseDto;
import com.bilgeadam.dto.response.TeacherResponseDto;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.ITeacherRepository;
import com.bilgeadam.repository.ITopicRepository;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Teacher;
import com.bilgeadam.repository.entity.Topic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.bilgeadam.util.TeacherServiceUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private ITeacherRepository teacherRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private ITopicRepository topicRepository;

    @Spy
    private UserServiceMapper mapper = Mappers.getMapper(UserServiceMapper.class);

    @Test
    void listAllTeacher() {
        when(teacherRepository.findAll()).thenReturn(List.of(getTeacherWithId()));
        List<TeacherResponseDto> responseDtoList = teacherService.listAllTeacher();
        assertEquals(responseDtoList.size(), 1);
        assertEquals(responseDtoList.get(0).getFirstname(), getTeacher().getFirstname());
    }

    @Test
    void save() {
        when(teacherRepository.save(ArgumentMatchers.any(Teacher.class))).thenReturn(getTeacherWithId());
        Teacher teacher = teacherService.save(getTeacherRequestDto());
        assertEquals(teacher.getId(), getTeacherWithId().getId());
    }


    @Test
    void whenSendExistingIdThenGetTeacherDetails() {
        Teacher teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(1L);
        when(teacherRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(teacher));
        when(teacher.getMasterCourses()).thenReturn(Set.of(getTeachersCourse()));
        when(teacher.getAssistanCourses()).thenReturn(Set.of(getTeachersCourse()));
        when(teacher.getTopics()).thenReturn(Set.of(getTeachersTopic()));

        TeacherDetailResponseDto teacherDetailResponseDto = teacherService.getTeacherDetailById(teacher.getId());

        assertEquals(teacherDetailResponseDto.getStatus(), 200);
        assertEquals(teacherDetailResponseDto.getId(), teacher.getId());
        assertEquals(teacherDetailResponseDto.getMasterCourses().get(0).getName(), getTeachersCourse().getName());
        assertEquals(teacherDetailResponseDto.getAssistantCourses().get(0).getName(), getTeachersCourse().getName());
        assertEquals(teacherDetailResponseDto.getTopics().get(0).getName(), getTeachersTopic().getName());
    }

    @Test
    void whenSendNonExistingIdThenGetNotFoundStatus(){
        when(teacherRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        TeacherDetailResponseDto teacherDetailResponseDto = teacherService.getTeacherDetailById(-1L);

        assertEquals(teacherDetailResponseDto.getStatus(), 404);
    }

    @Test
    void getCoursesByDto() {
        when(courseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Course.builder().name("TestCourse").id(1L).build()));

        Set<Course> courses = teacherService.getCoursesByDto(List.of(getCourseDto()));

        assertEquals(courses.size(), 1);
    }

    @Test
    void getTopicsByDto() {
        when(topicRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Topic.builder().name("TestTopic").id(1L).build()));

        Set<Topic> topics = teacherService.getTopicsByDto(List.of(getTopicDto()));

        assertEquals(topics.size(), 1);
    }

    @Test
    void updateCoursesMasterTrainer() {
        Teacher teacher = mock(Teacher.class);
        teacherService.updateCoursesMasterTrainer(Set.of(getTeachersCourse()), teacher);
        verify(courseRepository).save(ArgumentMatchers.any(Course.class));
    }

    @Test
    void updateCoursesAssistantTrainer() {
        Teacher teacher = mock(Teacher.class);
        teacherService.updateCoursesAssistantTrainer(Set.of(getTeachersCourse()), teacher);
        verify(courseRepository).save(ArgumentMatchers.any(Course.class));
    }

    @Test
    void updateTopicsTeacher() {
        Teacher teacher = mock(Teacher.class);
        Topic topic = mock(Topic.class);
        when(topic.getTeachers()).thenReturn(new HashSet<Teacher>());
        teacherService.updateTopicsTeacher(Set.of(topic), teacher);
        verify(topicRepository).save(ArgumentMatchers.any(Topic.class));
    }

    @Test
    void update() {
        Course course = mock(Course.class);
        Topic topic = mock(Topic.class);
        TeacherDetailResponseDto teacherDetailResponseDto = mock(TeacherDetailResponseDto.class);

        when(teacherDetailResponseDto.getMasterCourses()).thenReturn(new ArrayList<>());
        when(teacherDetailResponseDto.getAssistantCourses()).thenReturn(new ArrayList<>());
        when(teacherDetailResponseDto.getTopics()).thenReturn(new ArrayList<>());

        StatusDto result = teacherService.update(teacherDetailResponseDto);

        assertEquals(result.getStatus(), 200);
    }
}