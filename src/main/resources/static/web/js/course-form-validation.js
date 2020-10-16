function courseFormValidation(event) {
  submitForm = true;
  //Validate thumbnail
  validateImage(true);

  let form = $('#editCourseForm');
  submitForm = form[0].checkValidity();
  form.addClass('was-validated');
  //prevent submit and return
  if (!submitForm) {
    event.preventDefault();
    event.stopPropagation();
  }
  return submitForm;
}

function useValidation() {
  $('input[type="file"]').change(function () {
    validateImage(true);
  });
  $('#editCourseForm').submit(courseFormValidation);
}