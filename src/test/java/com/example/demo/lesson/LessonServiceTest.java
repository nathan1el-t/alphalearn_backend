package com.example.demo.lesson;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.config.SupabaseAuthUser;
import com.example.demo.contributor.Contributor;
import com.example.demo.contributor.ContributorRepository;
import com.example.demo.lesson.dto.request.CreateLessonRequest;
import com.example.demo.lesson.dto.request.UpdateLessonRequest;
import com.example.demo.lesson.dto.response.LessonContributorSummaryDto;
import com.example.demo.lesson.dto.response.LessonDetailDto;
import com.example.demo.lesson.dto.response.LessonDetailView;
import com.example.demo.lesson.dto.response.LessonPublicDetailDto;
import com.example.demo.lesson.dto.response.LessonPublicSummaryDto;
import com.example.demo.lesson.enums.LessonModerationStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ContributorRepository contributorRepository;

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LessonService lessonService;

    private UUID contributorId;
    private Contributor contributor;
    private Lesson approvedLesson;
    private Lesson pendingLesson;
    private Lesson unpublishedLesson;

    @BeforeEach
    void setUp() {
        contributorId = UUID.randomUUID();
        contributor = new Contributor(contributorId, null, OffsetDateTime.now(), null);

        approvedLesson = createTestLesson(1, "Approved Lesson", LessonModerationStatus.APPROVED);
        pendingLesson = createTestLesson(2, "Pending Lesson", LessonModerationStatus.PENDING);
        unpublishedLesson = createTestLesson(3, "Unpublished Lesson", LessonModerationStatus.UNPUBLISHED);
    }

    private Lesson createTestLesson(Integer id, String title, LessonModerationStatus status) {
        Lesson lesson = new Lesson(
                title,
                "objectives",
                mock(JsonNode.class),
                status,
                contributor,
                OffsetDateTime.now()
        );
        // Use reflection to set lessonId since it has no setter
        try {
            var field = Lesson.class.getDeclaredField("lessonId");
            field.setAccessible(true);
            field.set(lesson, id);
        } catch (Exception e) {
            // ID will be null in tests where it's not needed
        }
        return lesson;
    }

    // ===========================================
    // findAllLessons tests
    // ===========================================
    @Nested
    @DisplayName("findAllLessons")
    class FindAllLessonsTests {

        @Test
        @DisplayName("should return all lessons")
        void shouldReturnAllLessons() {
            // Arrange
            when(lessonRepository.findAll())
                    .thenReturn(List.of(approvedLesson, pendingLesson, unpublishedLesson));

            // Act
            List<LessonPublicSummaryDto> result = lessonService.findAllLessons();

            // Assert
            assertEquals(3, result.size());
            verify(lessonRepository).findAll();
        }

        @Test
        @DisplayName("should return empty list when no lessons exist")
        void shouldReturnEmptyListWhenNoLessons() {
            // Arrange
            when(lessonRepository.findAll()).thenReturn(List.of());

            // Act
            List<LessonPublicSummaryDto> result = lessonService.findAllLessons();

            // Assert
            assertTrue(result.isEmpty());
        }
    }

    // ===========================================
    // getLessonsByContributor tests
    // ===========================================
    @Nested
    @DisplayName("getLessonsByContributor")
    class GetLessonsByContributorTests {

        @Test
        @DisplayName("should return all lessons when no concept filter provided")
        void shouldReturnAllLessonsWhenNoConceptFilter() {
            // Arrange
            when(lessonRepository.findByContributor_ContributorId(contributorId))
                    .thenReturn(List.of(approvedLesson, pendingLesson, unpublishedLesson));

            // Act
            List<LessonContributorSummaryDto> result = lessonService.getLessonsByContributor(
                    contributorId, null, null);

            // Assert
            assertEquals(3, result.size());
            verify(lessonRepository).findByContributor_ContributorId(contributorId);
        }

        @Test
        @DisplayName("should return all lessons when concept list is empty")
        void shouldReturnAllLessonsWhenConceptListEmpty() {
            // Arrange
            when(lessonRepository.findByContributor_ContributorId(contributorId))
                    .thenReturn(List.of(approvedLesson));

            // Act
            List<LessonContributorSummaryDto> result = lessonService.getLessonsByContributor(
                    contributorId, List.of(), null);

            // Assert
            assertEquals(1, result.size());
            verify(lessonRepository).findByContributor_ContributorId(contributorId);
        }

        @Test
        @DisplayName("should filter by any concept when conceptsMatch is 'any'")
        void shouldFilterByAnyConceptWhenMatchAny() {
            // Arrange
            List<Integer> conceptIds = List.of(1, 2);
            when(lessonRepository.findByContributorAndConceptIds(contributorId, conceptIds))
                    .thenReturn(List.of(approvedLesson));

            // Act
            List<LessonContributorSummaryDto> result = lessonService.getLessonsByContributor(
                    contributorId, conceptIds, "any");

            // Assert
            assertEquals(1, result.size());
            verify(lessonRepository).findByContributorAndConceptIds(contributorId, conceptIds);
        }

        @Test
        @DisplayName("should filter by all concepts when conceptsMatch is not 'any'")
        void shouldFilterByAllConceptsWhenMatchAll() {
            // Arrange
            List<Integer> conceptIds = List.of(1, 2);
            when(lessonRepository.findByContributorAndAllConceptIds(contributorId, conceptIds, 2))
                    .thenReturn(List.of(approvedLesson));

            // Act
            List<LessonContributorSummaryDto> result = lessonService.getLessonsByContributor(
                    contributorId, conceptIds, "all");

            // Assert
            assertEquals(1, result.size());
            verify(lessonRepository).findByContributorAndAllConceptIds(contributorId, conceptIds, 2);
        }
    }

    // ===========================================
    // getLessonsByConcepts tests
    // ===========================================
    @Nested
    @DisplayName("getLessonsByConcepts")
    class GetLessonsByConceptsTests {

        @Test
        @DisplayName("should return empty list when conceptIds is null")
        void shouldReturnEmptyListWhenConceptIdsNull() {
            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByConcepts(null);

            // Assert
            assertTrue(result.isEmpty());
            verifyNoInteractions(lessonRepository);
        }

        @Test
        @DisplayName("should return empty list when conceptIds is empty")
        void shouldReturnEmptyListWhenConceptIdsEmpty() {
            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByConcepts(List.of());

            // Assert
            assertTrue(result.isEmpty());
            verifyNoInteractions(lessonRepository);
        }

        @Test
        @DisplayName("should return lessons matching any of the concepts")
        void shouldReturnLessonsMatchingConcepts() {
            // Arrange
            List<Integer> conceptIds = List.of(1, 2);
            when(lessonRepository.findByConceptIds(conceptIds))
                    .thenReturn(List.of(approvedLesson));

            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByConcepts(conceptIds);

            // Assert
            assertEquals(1, result.size());
        }
    }

    // ===========================================
    // getLessonsByAllConcepts tests
    // ===========================================
    @Nested
    @DisplayName("getLessonsByAllConcepts")
    class GetLessonsByAllConceptsTests {

        @Test
        @DisplayName("should return empty list when conceptIds is null")
        void shouldReturnEmptyListWhenConceptIdsNull() {
            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByAllConcepts(null);

            // Assert
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should return empty list when conceptIds is empty")
        void shouldReturnEmptyListWhenConceptIdsEmpty() {
            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByAllConcepts(List.of());

            // Assert
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should return lessons matching all concepts")
        void shouldReturnLessonsMatchingAllConcepts() {
            // Arrange
            List<Integer> conceptIds = List.of(1, 2);
            when(lessonRepository.findByAllConceptIds(conceptIds, 2))
                    .thenReturn(List.of(approvedLesson));

            // Act
            List<LessonPublicSummaryDto> result = lessonService.getLessonsByAllConcepts(conceptIds);

            // Assert
            assertEquals(1, result.size());
        }
    }

    // ===========================================
    // getLessonDetailForUser tests
    // ===========================================
    @Nested
    @DisplayName("getLessonDetailForUser")
    class GetLessonDetailForUserTests {

        @Test
        @DisplayName("should throw NOT_FOUND when lesson does not exist")
        void shouldThrowNotFoundWhenLessonNotExists() {
            // Arrange
            when(lessonRepository.findById(999)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.getLessonDetailForUser(999, null));
            assertEquals(404, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should return full detail for contributor who owns the lesson")
        void shouldReturnFullDetailForOwner() {
            // Arrange
            SupabaseAuthUser owner = new SupabaseAuthUser(contributorId, null, contributor);
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailView result = lessonService.getLessonDetailForUser(1, owner);

            // Assert
            assertInstanceOf(LessonDetailDto.class, result);
        }

        @Test
        @DisplayName("should return public detail when user is not owner")
        void shouldReturnPublicDetailForNonOwner() {
            // Arrange
            UUID otherUserId = UUID.randomUUID();
            Contributor otherContributor = new Contributor(otherUserId, null, OffsetDateTime.now(), null);
            SupabaseAuthUser nonOwner = new SupabaseAuthUser(otherUserId, null, otherContributor);
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailView result = lessonService.getLessonDetailForUser(1, nonOwner);

            // Assert
            assertInstanceOf(LessonPublicDetailDto.class, result);
        }

        @Test
        @DisplayName("should return public detail for anonymous user")
        void shouldReturnPublicDetailForAnonymousUser() {
            // Arrange
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailView result = lessonService.getLessonDetailForUser(1, null);

            // Assert
            assertInstanceOf(LessonPublicDetailDto.class, result);
        }
    }

    // ===========================================
    // createLesson tests
    // ===========================================
    @Nested
    @DisplayName("createLesson")
    class CreateLessonTests {

        private SupabaseAuthUser contributorUser;

        @BeforeEach
        void setUp() {
            contributorUser = new SupabaseAuthUser(contributorId, null, contributor);
        }

        private Concept createMockConcept() {
            Concept concept = mock(Concept.class);
            when(concept.getConceptId()).thenReturn(1);
            return concept;
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when request is null")
        void shouldThrowBadRequestWhenRequestNull() {
            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(null, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not a contributor")
        void shouldThrowForbiddenWhenNotContributor() {
            // Arrange
            SupabaseAuthUser nonContributor = new SupabaseAuthUser(contributorId, null, null);
            CreateLessonRequest request = new CreateLessonRequest(
                    "Title", null, "content", 1, contributorId, false);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, nonContributor));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw FORBIDDEN when creating lesson for another contributor")
        void shouldThrowForbiddenWhenCreatingForOtherContributor() {
            // Arrange
            UUID otherContributorId = UUID.randomUUID();
            CreateLessonRequest request = new CreateLessonRequest(
                    "Title", null, "content", 1, otherContributorId, false);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, contributorUser));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when required fields are missing")
        void shouldThrowBadRequestWhenMissingRequiredFields() {
            // Arrange - missing title
            CreateLessonRequest request = new CreateLessonRequest(
                    null, null, "content", 1, contributorId, false);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when title is blank")
        void shouldThrowBadRequestWhenTitleBlank() {
            // Arrange
            CreateLessonRequest request = new CreateLessonRequest(
                    "   ", null, "content", 1, contributorId, false);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should create lesson with UNPUBLISHED status when not submitted")
        void shouldCreateLessonWithUnpublishedStatus() {
            // Arrange
            Concept concept = createMockConcept();
            CreateLessonRequest request = new CreateLessonRequest(
                    "New Lesson", null, "content", 1, contributorId, false);
            when(contributorRepository.findById(contributorId)).thenReturn(Optional.of(contributor));
            when(conceptRepository.findById(1)).thenReturn(Optional.of(concept));
            when(objectMapper.valueToTree(any())).thenReturn(mock(JsonNode.class));
            when(lessonRepository.save(any(Lesson.class))).thenAnswer(inv -> {
                Lesson saved = inv.getArgument(0);
                try {
                    var field = Lesson.class.getDeclaredField("lessonId");
                    field.setAccessible(true);
                    field.set(saved, 100);
                } catch (Exception e) {}
                return saved;
            });
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailDto result = lessonService.createLesson(request, contributorUser);

            // Assert
            assertEquals("UNPUBLISHED", result.moderationStatus());

            ArgumentCaptor<Lesson> lessonCaptor = ArgumentCaptor.forClass(Lesson.class);
            verify(lessonRepository).save(lessonCaptor.capture());
            assertEquals(LessonModerationStatus.UNPUBLISHED, lessonCaptor.getValue().getLessonModerationStatus());
        }

        @Test
        @DisplayName("should create lesson with PENDING status when submitted")
        void shouldCreateLessonWithPendingStatusWhenSubmitted() {
            Concept concept = createMockConcept();
            //Arrange
            CreateLessonRequest request = new CreateLessonRequest(
                    "New Lesson", null, "content", 1, contributorId, true);
            when(contributorRepository.findById(contributorId)).thenReturn(Optional.of(contributor));
            when(conceptRepository.findById(1)).thenReturn(Optional.of(concept));
            when(objectMapper.valueToTree(any())).thenReturn(mock(JsonNode.class));
            when(lessonRepository.save(any(Lesson.class))).thenAnswer(inv -> {
                Lesson saved = inv.getArgument(0);
                try {
                    var field = Lesson.class.getDeclaredField("lessonId");
                    field.setAccessible(true);
                    field.set(saved, 100);
                } catch (Exception e) {}
                return saved;
            });
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailDto result = lessonService.createLesson(request, contributorUser);

            // Assert
            assertEquals("PENDING", result.moderationStatus());
        }

        @Test
        @DisplayName("should throw NOT_FOUND when contributor does not exist")
        void shouldThrowNotFoundWhenContributorNotExists() {
            // Arrange
            CreateLessonRequest request = new CreateLessonRequest(
                    "Title", null, "content", 1, contributorId, false);
            when(contributorRepository.findById(contributorId)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, contributorUser));
            assertEquals(404, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw NOT_FOUND when concept does not exist")
        void shouldThrowNotFoundWhenConceptNotExists() {
            // Arrange
            CreateLessonRequest request = new CreateLessonRequest(
                    "Title", null, "content", 1, contributorId, false);
            when(contributorRepository.findById(contributorId)).thenReturn(Optional.of(contributor));
            when(conceptRepository.findById(1)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.createLesson(request, contributorUser));
            assertEquals(404, ex.getStatusCode().value());
        }
    }

    // ===========================================
    // updateLesson tests
    // ===========================================
    @Nested
    @DisplayName("updateLesson")
    class UpdateLessonTests {

        private SupabaseAuthUser contributorUser;

        @BeforeEach
        void setUp() {
            contributorUser = new SupabaseAuthUser(contributorId, null, contributor);
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when request is null")
        void shouldThrowBadRequestWhenRequestNull() {
            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(1, null, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not a contributor")
        void shouldThrowForbiddenWhenUserNotContributor() {
            // Arrange
            SupabaseAuthUser nonContributor = new SupabaseAuthUser(contributorId, null, null);
            UpdateLessonRequest request = new UpdateLessonRequest("Title", null, "content");

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(1, request, nonContributor));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when title is missing")
        void shouldThrowBadRequestWhenTitleMissing() {
            // Arrange
            UpdateLessonRequest request = new UpdateLessonRequest(null, null, "content");

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(1, request, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw BAD_REQUEST when content is missing")
        void shouldThrowBadRequestWhenContentMissing() {
            // Arrange
            UpdateLessonRequest request = new UpdateLessonRequest("Title", null, null);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(1, request, contributorUser));
            assertEquals(400, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw NOT_FOUND when lesson does not exist")
        void shouldThrowNotFoundWhenLessonNotExists() {
            // Arrange
            UpdateLessonRequest request = new UpdateLessonRequest("Title", null, "content");
            when(lessonRepository.findById(999)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(999, request, contributorUser));
            assertEquals(404, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not owner")
        void shouldThrowForbiddenWhenNotOwner() {
            // Arrange
            UUID otherUserId = UUID.randomUUID();
            Contributor otherContributor = new Contributor(otherUserId, null, OffsetDateTime.now(), null);
            SupabaseAuthUser otherUser = new SupabaseAuthUser(otherUserId, null, otherContributor);
            UpdateLessonRequest request = new UpdateLessonRequest("Title", null, "content");
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.updateLesson(1, request, otherUser));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should update lesson successfully when user is owner")
        void shouldUpdateLessonWhenOwner() {
            // Arrange
            UpdateLessonRequest request = new UpdateLessonRequest("Updated Title", null, "new content");
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));
            when(objectMapper.valueToTree(any())).thenReturn(mock(JsonNode.class));
            when(lessonRepository.save(any(Lesson.class))).thenReturn(approvedLesson);
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            LessonDetailDto result = lessonService.updateLesson(1, request, contributorUser);

            // Assert
            assertNotNull(result);
            verify(lessonRepository).save(approvedLesson);
        }
    }

    // ===========================================
    // submitLesson tests
    // ===========================================
    @Nested
    @DisplayName("submitLesson")
    class SubmitLessonTests {

        private SupabaseAuthUser contributorUser;

        @BeforeEach
        void setUp() {
            contributorUser = new SupabaseAuthUser(contributorId, null, contributor);
        }

        @Test
        @DisplayName("should change status to PENDING")
        void shouldChangeStatusToPending() {
            // Arrange
            when(lessonRepository.findById(3)).thenReturn(Optional.of(unpublishedLesson));
            when(lessonRepository.save(any(Lesson.class))).thenReturn(unpublishedLesson);
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            lessonService.submitLesson(3, contributorUser);

            // Assert
            assertEquals(LessonModerationStatus.PENDING, unpublishedLesson.getLessonModerationStatus());
            verify(lessonRepository).save(unpublishedLesson);
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not contributor")
        void shouldThrowForbiddenWhenNotContributor() {
            // Arrange
            SupabaseAuthUser nonContributor = new SupabaseAuthUser(contributorId, null, null);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.submitLesson(1, nonContributor));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw NOT_FOUND when lesson does not exist")
        void shouldThrowNotFoundWhenLessonNotExists() {
            // Arrange
            when(lessonRepository.findById(999)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.submitLesson(999, contributorUser));
            assertEquals(404, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not owner")
        void shouldThrowForbiddenWhenNotOwner() {
            // Arrange
            UUID otherUserId = UUID.randomUUID();
            Contributor otherContributor = new Contributor(otherUserId, null, OffsetDateTime.now(), null);
            SupabaseAuthUser otherUser = new SupabaseAuthUser(otherUserId, null, otherContributor);
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.submitLesson(1, otherUser));
            assertEquals(403, ex.getStatusCode().value());
        }
    }

    // ===========================================
    // unpublishLesson tests
    // ===========================================
    @Nested
    @DisplayName("unpublishLesson")
    class UnpublishLessonTests {

        private SupabaseAuthUser contributorUser;

        @BeforeEach
        void setUp() {
            contributorUser = new SupabaseAuthUser(contributorId, null, contributor);
        }

        @Test
        @DisplayName("should change status to UNPUBLISHED")
        void shouldChangeStatusToUnpublished() {
            // Arrange
            when(lessonRepository.findById(1)).thenReturn(Optional.of(approvedLesson));
            when(lessonRepository.save(any(Lesson.class))).thenReturn(approvedLesson);
            when(objectMapper.convertValue(any(), eq(Object.class))).thenReturn("content");

            // Act
            lessonService.unpublishLesson(1, contributorUser);

            // Assert
            assertEquals(LessonModerationStatus.UNPUBLISHED, approvedLesson.getLessonModerationStatus());
            verify(lessonRepository).save(approvedLesson);
        }

        @Test
        @DisplayName("should throw FORBIDDEN when user is not contributor")
        void shouldThrowForbiddenWhenNotContributor() {
            // Arrange
            SupabaseAuthUser nonContributor = new SupabaseAuthUser(contributorId, null, null);

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.unpublishLesson(1, nonContributor));
            assertEquals(403, ex.getStatusCode().value());
        }

        @Test
        @DisplayName("should throw NOT_FOUND when lesson does not exist")
        void shouldThrowNotFoundWhenLessonNotExists() {
            // Arrange
            when(lessonRepository.findById(999)).thenReturn(Optional.empty());

            // Act & Assert
            ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                    () -> lessonService.unpublishLesson(999, contributorUser));
            assertEquals(404, ex.getStatusCode().value());
        }
    }
}