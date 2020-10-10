function simpleFormValidation(formId) {
  $(`#${formId}`).submit(function (event) {
    if (this.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }
    $(this).addClass('was-validated');
  })
}