function deleteCourse(courseId) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: `/web/api/course/${courseId}`,
    type: "DELETE",
    success: (response) => {
      window.location = '/account/manage-courses'
    },
    error: (error) => {
      showInformationModal('Xóa bài học thất bại', 'Có gì đó sai sai, có lẽ hệ thống gặp trục trặc vui lòng thử lại sau')
    },
  });
}

function completeCourse(courseId) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: `/web/api/course/complete/${courseId}`,
    type: "PUT",
    success: (response) => {
      location.reload();
    },
    error: (error) => {
      showInformationModal('Xóa bài học thất bại', 'Có gì đó sai sai, có lẽ hệ thống gặp trục trặc vui lòng thử lại sau')
    },
  });
}

function deleteLatestLesson(courseId) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: `/web/api/course/pop-lesson/${courseId}/`,
    type: "DELETE",
    success: (response) => {
      location.reload();
    },
    error: (error) => {
      showInformationModal('Xóa bài học thất bại', 'Có gì đó sai sai, có lẽ hệ thống gặp trục trặc vui lòng thử lại sau')
    },
  });
}