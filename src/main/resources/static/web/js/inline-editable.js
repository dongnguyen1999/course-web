function turnInlineEditMode(scopeSelector, status) {
  if (status) {
    let span = $(`${scopeSelector} .editable span`);
    let input = $(`${scopeSelector} .editable input`);
    span.hide();
    input.removeClass('hide').show();
  } else {
    $(`${scopeSelector} .editable span`).show();
    $(`${scopeSelector} .editable input`).hide();
  }
}

function recoverItem(trId) {
  if (trId === 'new-item') $('#new-item').remove();
  else {
    $(`#${trId} input.name`).val($(`#${trId} span.name`).html());
    $(`#${trId} input.code`).val($(`#${trId} span.code`).html()).removeClass('is-invalid');
    $(`#${trId} input.extension`).val($(`#${trId} span.extension`).html());
  }
}

function editButtonClickHandler(editBtn, event, itemType, enableSubmit=true) {
    event.preventDefault();
    let trId = editBtn.parents('tr').attr('id');
    if (editBtn.hasClass('editing')) {
      editBtn.removeClass('editing');
      turnInlineEditMode(`#${trId}`, false);
      if (enableSubmit) {
        if (itemType === 'CATEGORY') updateCategory(trId);
        else if (itemType === 'MEDIA_TYPE') updateMediaType(trId);
      } else recoverItem(trId);
    } else {
      editBtn.addClass('editing');
      turnInlineEditMode(`#${trId}`, true);
    }
}



function setEditButtons(itemType) {
  $('.editable input.code').keyup(function (event) {
    checkCodeExist($(this).parents('tr').find('.edit-btn'), $(this).val(), itemType)
  })
  $('.edit-btn').click(function (event) {
    editButtonClickHandler($(this), event, itemType);
  });
}

function updateCategory(trId) {
  let data = {
    name: $(`#${trId} input.name`).val(),
    code: $(`#${trId} input.code`).val(),
  }
  if (trId !== 'new-item') data.id = trId;
  $.ajax({
    url: '/admin/api/category',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(data),
    dataType: 'json',
    success: function (response) {
      $(`#${trId} span.id`).html(response.id);
      $(`#${trId} span.name`).html(response.name);
      $(`#${trId} span.code`).html(response.code);
      if (trId === 'new-item') {
        $(`#${trId}`).attr('id', response.id);
        $(`#${response.id} button.delete-btn`).click(function () {
          showConfirmModal(`Bạn có chắc muốn xóa thể loại ${response.name}`, function () {
            deleteCurrent(response.id, 'CATEGORY');
          })
        })
        let tbody = $('tbody');
        tbody.find('tr').sort((a, b) =>
          parseInt($(a).attr('id')) > parseInt($(b).attr('id'))
        ).appendTo(tbody);
      }

    },
    error: function (error) {
      console.log(error)
      if (trId === 'new-item') $('#new-item').remove();
      else {
        $(`#${trId} input.name`).val($(`#${trId} span.name`).html());
        $(`#${trId} input.code`).val($(`#${trId} span.code`).html()).removeClass('is-invalid');
      }
    },
  });
}

function updateMediaType(trId) {
  let data = {
    name: $(`#${trId} input.name`).val(),
    code: $(`#${trId} input.code`).val(),
    extension: $(`#${trId} input.extension`).val(),
  }
  if (trId !== 'new-item') data.id = trId;
  $.ajax({
    url: '/admin/api/media-type',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(data),
    dataType: 'json',
    success: function (response) {
      $(`#${trId} span.id`).html(response.id);
      $(`#${trId} span.name`).html(response.name);
      $(`#${trId} span.code`).html(response.code);
      $(`#${trId} span.extension`).html(response.extension);
      if (trId === 'new-item') {
        $(`#${trId}`).attr('id', response.id);
        $(`#${response.id} button.delete-btn`).click(function () {
          showConfirmModal(`Bạn có chắc muốn xóa loại tệp ${response.name}`, function () {
            deleteCurrent(response.id, 'MEDIA_TYPE');
          })
        })
        let tbody = $('tbody');
        tbody.find('tr').sort((a, b) =>
          parseInt($(a).attr('id')) > parseInt($(b).attr('id'))
        ).appendTo(tbody);
      }

    },
    error: function (error) {
      console.log(error);
      recoverItem(trId);
    },
  });
}

function setAddNewButtons(itemType) {
  $('#new-btn').click(function () {
    // noinspection JSJQueryEfficiency
    if ($('#new-item').length === 0) {
      if (itemType === 'CATEGORY') $('tbody').prepend(newCategoryElement);
      else if (itemType === 'MEDIA_TYPE') $('tbody').prepend(newMediaTypeElement);
      let codeInput = $('#new-item input.code');
      let editBtn = $('#new-item button.edit-btn');
      let deleteBtn = $('#new-item button.delete-btn');
      useBtnCheckbox();
      editBtn.click(function (event) {
        editButtonClickHandler($(this), event, itemType, false);
      })
      codeInput.keyup(function (event) {
        checkCodeExist(editBtn, $(this).val(), itemType)
      })
      deleteBtn.click(function (){
        $('#new-item').remove();
      })
      editBtn.addClass('editing');
      turnInlineEditMode('#new-item', true);
    }
  })
}

function showConfirmModal(message, confirmCallback) {
  $('#confirmDialog button.confirm').click(confirmCallback);
  $('#confirmDialog .modal-body').html(message);
  $('#confirmDialog').modal('show');
}

function deleteSelected(itemType){
  let data = [];
  $('tbody').find('.checkbox.checked').each(function () {
    let id = $(this).parents('tr').attr('id');
    data.push(id);
  })
  let url = '/admin/api/';
  if (itemType === 'CATEGORY') url += 'category';
  else if (itemType === 'MEDIA_TYPE') url += 'media-type';
  $.ajax({
    url: url,
    type: 'DELETE',
    contentType: 'application/json',
    data: JSON.stringify(data),
    dataType: 'json',
    success: function (response) {
      location.reload();
    },
    error: function (error) {
      console.log(error)
      $('#messageDialog').modal('show')
    },
  });
}

function deleteCurrent(trId, itemType) {
  console.log(trId, itemType)
  let url = '/admin/api/';
  if (itemType === 'CATEGORY') url += 'category';
  else if (itemType === 'MEDIA_TYPE') url += 'media-type';
  $.ajax({
    url: url,
    type: 'DELETE',
    contentType: 'application/json',
    data: JSON.stringify([trId]),
    dataType: 'json',
    success: function (response) {
      location.reload();
    },
    error: function (error) {
      console.log(error)
      $('#messageDialog').modal('show')
    },
  });
}

function setDeleteButtons(itemType) {
  $('#delete-all-btn').click(function () {
    showConfirmModal($(this).attr('message'), function () {
      deleteSelected(itemType);
    });
  })
  $('.delete-btn').click(function () {
    let trId = $(this).parents('tr').attr('id');
    showConfirmModal($(this).attr('message'), function () {
      deleteCurrent(trId, itemType);
    })
  })
}

function checkCodeExist(okBtn, code, itemType) {
  let url = '/admin/api/';
  if (itemType === 'CATEGORY') url += 'category';
  else if (itemType === 'MEDIA_TYPE') url += 'media-type';
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: url,
    type: "GET",
    data: {
      code: code,
    },
    success: (response) => {
      okBtn.parents('tr').find('input.code').addClass('is-invalid');
      okBtn.unbind('click');
      okBtn.click(function (event) {
        editButtonClickHandler($(this), event, itemType, false);
      });
    },
    error: (error) => {
      okBtn.parents('tr').find('input.code').removeClass('is-invalid');
      okBtn.unbind('click');
      okBtn.click(function (event) {
        editButtonClickHandler($(this), event, itemType);
      });
      okBtn.find('span').html('OK');
    },
  });
}


const newCategoryElement = "<tr id=\"new-item\">\n" +
  "        <td class=\"align-middle\" style=\"width: 5%;\">\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn checkbox\" aria-label=\"Chọn\"\n" +
  "                  title=\"Chọn\">\n" +
  "            <i class=\"fas fa-check\"></i>\n" +
  "          </button>\n" +
  "        </td>\n" +
  "        <td class=\"align-middle\" style=\"width: 10%;\">\n" +
  "          <span class=\"id\">Id</span>\n" +
  "        </td>\n" +
  "        <td class=\"align-middle editable\" style=\"width: 35%;\">\n" +
  "          <span class=\"name\">Tên thể loại</span>\n" +
  "          <input class=\"form-control name hide\" type=\"text\">\n" +
  "        </td>\n" +
  "        <td class=\"align-middle editable\" style=\"width: 35%;\">\n" +
  "          <span class=\"code\">Mã thể loại</span>\n" +
  "          <input class=\"form-control code hide\" type=\"text\">\n" +
  "          <div class=\"invalid-feedback\">\n" +
  "            Mã thể loại đã tồn tại trên hệ thống\n" +
  "          </div>" +
  "        </td>\n" +
  "        <td class=\"align-middle text-right\" style=\"width: 15%;\">\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn edit-btn\" aria-label=\"Chỉnh sửa thể loại\"\n" +
  "                  title=\"Chỉnh sửa thể loại\">\n" +
  "            <i class=\"fas fa-edit\"></i>\n" +
  "            <span class=\"font-weight-bold\">OK</span>\n" +
  "          </button>\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn delete-btn\" aria-label=\"Xóa thể loại\"\n" +
  "                  title=\"Xóa thể loại\">\n" +
  "            <i class=\"fas fa-times\"></i>\n" +
  "          </button>\n" +
  "        </td>\n" +
  "      </tr>";

const newMediaTypeElement = "<tr id=\"new-item\">\n" +
  "        <td class=\"align-middle\" style=\"width: 5%;\">\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn checkbox\" aria-label=\"Chọn\"\n" +
  "                  title=\"Chọn\">\n" +
  "            <i class=\"fas fa-check\"></i>\n" +
  "          </button>\n" +
  "        </td>\n" +
  "        <td class=\"align-middle\" style=\"width: 10%;\">\n" +
  "          <span class=\"id\">Id</span>\n" +
  "        </td>\n" +
  "        <td class=\"align-middle editable\" style=\"width: 25%;\">\n" +
  "          <span class=\"name\">Tên thể loại</span>\n" +
  "          <input class=\"form-control name hide\" type=\"text\">\n" +
  "        </td>\n" +
  "        <td class=\"align-middle editable\" style=\"width: 25%;\">\n" +
  "          <span class=\"code\">Mã thể loại</span>\n" +
  "          <input class=\"form-control code hide\" type=\"text\">\n" +
  "          <div class=\"invalid-feedback\">\n" +
  "            Mã thể loại đã tồn tại trên hệ thống\n" +
  "          </div>" +
  "        <td class=\"align-middle editable\" style=\"width: 20%;\">\n" +
  "          <span class=\"extension\">Phần mở rộng</span>\n" +
  "          <input class=\"form-control extension hide\" type=\"text\">\n" +
  "        </td>" +
  "        </td>\n" +
  "        <td class=\"align-middle text-right\" style=\"width: 15%;\">\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn edit-btn\" aria-label=\"Chỉnh sửa loại tệp\"\n" +
  "                  title=\"Chỉnh sửa loại tệp\">\n" +
  "            <i class=\"fas fa-edit\"></i>\n" +
  "            <span class=\"font-weight-bold\">OK</span>\n" +
  "          </button>\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn delete-btn\" aria-label=\"Xóa loại tệp\"\n" +
  "                  title=\"Xóa loại tệp\">\n" +
  "            <i class=\"fas fa-times\"></i>\n" +
  "          </button>\n" +
  "        </td>\n" +
  "      </tr>";