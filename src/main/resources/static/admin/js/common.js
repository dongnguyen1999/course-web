function useBtnCheckbox() {
  let checkBoxs = $('.checkbox');
  checkBoxs.unbind('click');
  checkBoxs.click(function (event) {
    event.preventDefault();
    if ($(this).hasClass('checked')) $(this).removeClass('checked');
    else $(this).addClass('checked');
  })
}