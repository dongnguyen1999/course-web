function deleteCourse(courseId) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: `/web/api/course/${courseId}`,
    type: "DELETE",
    success: (response) => {
      window.location = '/course'
    },
    error: (error) => {
      console.log(error)
    },
  });
}

function deleteLatestLesson(courseId) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: `/web/api/course/${courseId}/`,
    type: "DELETE",
    success: (response) => {
      window.location = `/course/${courseCode}`
    },
    error: (error) => {
      console.log(error)
    },
  });
}