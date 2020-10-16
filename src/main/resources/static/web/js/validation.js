var submitForm = true;
function showInvalid(inputTag, message){
  inputTag.removeClass('is-valid');
  inputTag.addClass('is-invalid');
  inputTag.siblings('.invalid-feedback').html(message);
  submitForm = false;
}

function showValid(inputTag, message) {
  inputTag.removeClass('is-invalid');
  inputTag.addClass('is-valid');
  if (message !== undefined) inputTag.siblings('.valid-feedback').html(message);
}

function checkImageExtension(filePath) {
  let extension = filePath.split('.').pop();
  let supportedExtension = ['jpeg', 'jpg', 'png'];
  return supportedExtension.includes(extension);
}

function validateImage(required = false) {
  let fileInp = $('input[type="file"]');
  let filePath = fileInp.val();
  if (required) {
    if (filePath.length === 0) {
      showInvalid(fileInp, 'Vui lòng chọn hình ảnh');
    } else if (!checkImageExtension(filePath)) {
      showInvalid(fileInp, 'Chỉ hỗ trợ định dạng ảnh JPEG và PNG');
    } else showValid(fileInp);
  }
  else if (filePath.length !== 0) {
    if (!checkImageExtension(filePath)) {
      showInvalid(fileInp, 'Chỉ hỗ trợ định dạng ảnh JPEG và PNG');
    } else showValid(fileInp);
  } else removeValidation(fileInp);
}