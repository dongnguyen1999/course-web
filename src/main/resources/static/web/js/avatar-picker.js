function setAvatarPicker() {
  let avatar = $('.avatar');
  let avatarReplace = $('.avatar-replace');
  avatar.mouseover(function () {
    avatarReplace.removeClass('hide')
  })
  avatarReplace.mouseover(function () {
    avatarReplace.removeClass('hide')
  })
  avatar.mouseout(function () {
    avatarReplace.addClass('hide')
  })
  avatarReplace.mouseout(function () {
    avatarReplace.addClass('hide')
  })
  avatarReplace.click(showPicker)
}

function showPicker() {
  console.log('showPicker');
}