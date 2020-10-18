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