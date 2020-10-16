var submitForm = true;

function checkUsername(username) {
  let usernamePattern = /^[a-z][a-z0-9]{7,31}$/g;
  return usernamePattern.test(username);
}

function checkPassword(password) {
  let passwordPattern = /^(?=.*?[a-zA-Z])(?=.*?[0-9]).{8,32}$/g;
  return passwordPattern.test(password);
}

function checkEmail(email) {
  let emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g;
  return emailPattern.test(email);
}

function checkPhone(phone) {
  let phonePattern = /^0?[89375][0-9]{8}$/g;
  return phonePattern.test(phone);
}

function checkAvatarExtension(filePath) {
  let extension = filePath.split('.').pop();
  let supportedExtension = ['jpeg','jpg','png'];
  return supportedExtension.includes(extension);
}


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

function removeValidation(inputTag) {
  inputTag.removeClass('is-invalid');
  inputTag.removeClass('is-valid');
}

function getUserAvatar(username) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/web/api/user",
    type: "GET",
    data: {
      username: username,
    },
    success: (response, status) => {
      $('#user-image').attr("src", `/resource/${response.avatar}`);
    },
    error: (error) => {
      $('#user-image').attr("src", '/web/img/default-avatar.png');
    },
  });
}

function checkUsernameExist(username) {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/web/api/user",
    type: "GET",
    data: {
      username: username,
    },
    success: (response, status) => {
      let usernameInp = $('input[name="username"]');
      showInvalid(usernameInp, 'Tên đăng nhập đã tồn tại')
    },
    error: (error) => {
      let usernameInp = $('input[name="username"]');
      showValid(usernameInp, 'Tên đăng nhập hợp lệ')
    },
  });
}

function validateAvatar() {
  let avatarInp = $('input[type="file"]');
  let filePath = avatarInp.val();
  if (filePath.length !== 0) {
    if (!checkAvatarExtension(filePath)) {
      showInvalid(avatarInp, 'Chỉ hỗ trợ định dạng ảnh JPEG và PNG');
    } else showValid(avatarInp);
  }
  else removeValidation(avatarInp);
}

function validateUsername() {
  let usernameInp = $('input[name="username"]');
  let username = usernameInp.val();
  if (username.length === 0) {
    showInvalid(usernameInp, 'Tên tài khoản không được bỏ trống');
  }
  else if (!checkUsername(username)) {
    showInvalid(usernameInp, 'Tên tài khoản có độ dài từ 8-32 ký tự, gồm chữ thường, số và phải bắt đầu bằng chữ cái');
  }
  else checkUsernameExist(username);
}

function validateFullName() {
  let fullNameInp = $('input[name="fullName"]');
  let fullName = fullNameInp.val();
  if (fullName.length === 0) {
    showInvalid(fullNameInp, 'Tên đầy đủ không được bỏ trống');
  }
  else showValid(fullNameInp);
}

function validatePassword() {
  let passwordInp = $('input[name="password"]');
  let password = passwordInp.val();
  if (password.length === 0){
    showInvalid(passwordInp, 'Mật khẩu không được bỏ trống');
  }
  else if (!checkPassword(password)) {
    showInvalid(passwordInp, 'Mật khẩu có độ dài từ 8-32 ký tự, phải chứa ít nhất một chữ cái và ít nhất một số');
  } else showValid(passwordInp);
}

function validateConfirmPassword() {
  let passwordInp = $('input[name="password"]');
  let password = passwordInp.val();
  let confirmInp = $('#confirm-password');
  let confirmPass = confirmInp.val();
  if (confirmPass.length === 0) {
    showInvalid(confirmInp, 'Hãy xác nhận mật khẩu');
  }
  else if (password !== confirmPass) {
    showInvalid(confirmInp, 'Mật khẩu xác nhận không trùng khớp');
  } else showValid(confirmInp);
}

function validateEmail(){
  let emailInp = $('input[name="email"]');
  let email = emailInp.val();
  if (email.length !== 0) {
    if (!checkEmail(email)) {
      showInvalid(emailInp, 'Địa chỉ email không đúng định dạng');
    } else showValid(emailInp);
  } else removeValidation(emailInp);
}

function validatePhone() {
  let phoneInp = $('input[name="phone"]');
  let phone = phoneInp.val();
  if (phone.length !== 0) {
    if (!checkPhone(phone)) {
      showInvalid(phoneInp, 'Số điện thoại không đúng định dạng');
    } else showValid(phoneInp);
  } else removeValidation(phoneInp);
}

function registerFormValidation(event) {
  submitForm = true;
  //Validate avatar
  validateAvatar();

  //Validate username
  validateUsername();

  //Validate full name
  validateFullName();

  //Validate password
  validatePassword();

  //Validate password confirm
  validateConfirmPassword()

  //Validate email if it is not empty
  validateEmail()

  //Validate phone if it is not empty
  validatePhone();

  //prevent submit and return
  if (!submitForm) {
    event.preventDefault();
    event.stopPropagation();
  }
  return submitForm;
}

function useValidation() {
  $('input[type="file"]').change(validateAvatar);
  $('input[name="username"]').keyup(validateUsername);
  $('input[name="password"]').keyup(validatePassword);
  $('input[name="fullName"]').keyup(validateFullName);
  $('#confirm-password').keyup(validateConfirmPassword);
  $('input[name="email"]').keyup(validateEmail);
  $('input[name="phone"]').keyup(validatePhone);
  $('#registerForm').submit(registerFormValidation);
}