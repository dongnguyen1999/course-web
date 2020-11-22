function setAvatarPicker() {
  $('#user-image')
    .mouseenter(function () {
      $('#camera-icon')
        .css({
          "font-size": "70px",
          "top": "50%",
          "left": "50%",
          "transform": "translate(-50%, -50%)"
        })
        .removeClass('d-none');
      $('#user-image').css({
        "opacity": "0.5",
        "transition": "opacity 0.3s",
      });
    })
    .mouseleave(function () {
      $('#camera-icon').addClass('d-none');
      $('#user-image').css({
        "opacity": "1",
        "transition": "opacity 0.3s"
      });
    });

  $('#avatarInput').change(function () {
    clientLoadImage(this, $('.image-preview').attr('defSrc'));
  })
}

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

  $('#thumbnailInput').change(function () {
    clientLoadImage(this, $('.image-preview').attr('defSrc'));
  })
}

function clientLoadImage(fileInput, defaultSrc) {
  let browser = fileInput;
  if (browser.files && browser.files[0]) {
    let file = browser.files[0];
    if (file) {
      let reader = new FileReader();
      reader.onload = (event) => {
        let url = event.target.result;
        let fileType = url.split(';').shift();
        if (fileType.includes('image')) $('.image-preview').attr("src", url);
        else $('.image-preview').attr("src", defaultSrc);
      }
      reader.readAsDataURL(file);
    }
  }
}