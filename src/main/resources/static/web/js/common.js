function simpleFormValidation(formId) {
  $(`#${formId}`).submit(function (event) {
    if (this.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }
    $(this).addClass('was-validated');
  })
}

let scrollTopBtn = $('#back-to-top')
scrollTopBtn.css('display', 'none');
function setScrollTopHandler(){
  $(window).scroll(function () {
    if ($(this).scrollTop() > 200) {
      scrollTopBtn.fadeIn("slow");
      scrollTopBtn.css('display', 'flex');
      scrollTopBtn.css('justify-content', 'center');
      scrollTopBtn.css('align-items', 'center');
      scrollTopBtn.css('font-size', '2rem');
    } else {
      scrollTopBtn.fadeOut("slow");
    }
  });
  // scroll body to 0px on click

  scrollTopBtn.click(function () {
    $('body,html').animate({
      scrollTop: 0
    }, 1000);
    return false;
  });
}
$(function () {
  setScrollTopHandler();
})

function setScrollToViewHandler(jqBtn, jqElement) {
  jqBtn.click(function(event) {
    event.preventDefault();
    $([document.documentElement, document.body]).animate({
      scrollTop: jqElement.offset().top
    }, 800);
    $('button.navbar-toggler').addClass('collapsed').attr('aria-expanded','false');
    $('#navbarResponsive').removeClass('show');
  });
}

function useBtnCheckbox() {
  let checkBoxs = $('.checkbox');
  checkBoxs.unbind('click');
  checkBoxs.click(function (event) {
    event.preventDefault();
    if ($(this).hasClass('checked')) {
      $(this).removeClass('checked');
      $('#enableFreeTrial').val(false);
    }
    else {
      $(this).addClass('checked');
      $('#enableFreeTrial').val(true);
    }
  })
}

function showConfirmModal(title, message, confirmCallback, confirmBtnText) {
  let confirmBtn = $('#confirmDialog button.confirm');
  confirmBtn.click(confirmCallback);
  if (confirmBtnText) confirmBtn.html(confirmBtnText);
  $('#confirmDialog .modal-title').html(title);
  $('#confirmDialog .modal-body').html(message);
  $('#confirmDialog').modal('show');
}

function showInformationModal(title, message) {
  $('#messageDialog .modal-title').html(title);
  $('#messageDialog .modal-body').html(message);
  $('#messageDialog').modal('show');
}

function getFileName(filePath){
  if (filePath) {
    let startIndex = (filePath.indexOf('\\') >= 0 ? filePath.lastIndexOf('\\') : filePath.lastIndexOf('/'));
    let filename = filePath.substring(startIndex);
    if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
        filename = filename.substring(1);
    }
    return filename;
  }
  return undefined;
}
