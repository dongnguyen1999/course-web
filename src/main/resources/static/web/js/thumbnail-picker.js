function setThumbnailPicker() {
  let cameraIcon = $('#camera-icon');
  $('#imgThumbnail')
      .mouseenter(function () {
        cameraIcon.removeClass('d-none');
        cameraIcon.addClass('d-block');
        $('#imgThumbnail').css({
          "opacity": "0.5"
        });
        cameraIcon.css({
          "position": "absolute",
          "font-size": "6rem",
          "left": "50%",
          "top": "50%",
          "transform": "translate(-50%, -50%)",
        });
      })
      .mouseleave(function () {
        cameraIcon.removeClass('d-block');
        cameraIcon.addClass('d-none');
        $('#imgThumbnail').css({
          "opacity": "1"
        });
      })
      .click(showPicker)
}

function showPicker() {
  console.log('showPicker');
}