function checkExtension(filePath, supportedExtensions) {
  let extension = filePath.split('.').pop();
  return supportedExtensions.includes(extension);
}

function validateMedia() {
  let fileInp = $('#uploadMediaForm input[type="file"]');
  let filePath = fileInp.val();
  let filename = getFileName(filePath)
  if (filename !== undefined) {
    $('#uploadMediaForm .custom-file-label').html(filename)
  }
  supportedExtensions = []
  $('#uploadMediaForm .media-type-extensions').each((index, span) => {
    let extension = span.getAttribute("extension");
    supportedExtensions = supportedExtensions.concat(extension.split('|'));
  })
  if (filePath.length === 0) {
    showInvalid(fileInp, 'Vui lòng chọn hình ảnh thuộc các định dạng được hỗ trợ' + exStr);
  } else if (!checkExtension(filePath, supportedExtensions)) {
    showInvalid(fileInp, 'Định dạng tệp không hỗ trợ: ' + filePath.split('.').pop());
  } else showValid(fileInp);
}

function uploadMediaFormValidation(event) {
  submitForm = true;
  validateMedia();

  let form = $('#uploadMediaForm');
  let isOk = form[0].checkValidity();
  form.addClass('was-validated');
  if (submitForm) submitForm = isOk;

  // console.log(submitForm)
  if (!submitForm) {
    event.preventDefault();
    event.stopPropagation();
  }
  return submitForm;
}

function resetUploadModal(event) {
  let form = $('#uploadMediaForm');
  form.removeClass('was-validated');
  $('#uploadMediaForm input').removeClass('is-valid').removeClass('is-invalid')
  form[0].reset();
  $('#uploadMediaForm .custom-file-label').html('Chọn tệp')
}

function useValidation() {
  $('#uploadMediaModal').on('hide.bs.modal', resetUploadModal);
  $('#uploadMediaForm input[type="file"]').change(function () {
    validateMedia();  
  });
  $('#uploadMediaForm .upload-confirm-btn').click(function(event) {
    event.preventDefault();
    event.stopPropagation();
    if (uploadMediaFormValidation(event)) {
      uploadMedia();
    }
  });
}