function courseFormValidation(event) {
  submitForm = true;
  //Validate thumbnail
  validateImage(true);

  let form = $('#editCourseForm');
  let isOk = form[0].checkValidity();
  form.addClass('was-validated');
  if (submitForm) submitForm = isOk;
  
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