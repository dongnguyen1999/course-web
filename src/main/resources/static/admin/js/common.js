function useBtnCheckbox() {
  $('.checkbox').click(function (event) {
    event.preventDefault();
    if ($(this).hasClass('checked')) $(this).removeClass('checked');
    else $(this).addClass('checked');
  })
}