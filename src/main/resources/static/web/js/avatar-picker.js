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
    let browser = this;
    if (browser.files && browser.files[0]){
      let file = browser.files[0];
      if (file) {
        let reader = new FileReader();
        reader.onload = (event) => {
          let url = event.target.result;
          let fileType = url.split(';').shift();
          if (fileType.includes('image')) $('#user-image').attr("src", url);
          else $('#user-image').attr("src", '/web/img/default-avatar.png');
        }
        reader.readAsDataURL(file);
      }
    }
  })
}