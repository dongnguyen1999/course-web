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

function setEditButtons() {
  $('.edit-btn').click(function (event) {
    event.preventDefault();
    let trId = $(this).parents('tr').attr('id');
    if ($(this).hasClass('editing')) {
      $(this).removeClass('editing');
      turnInlineEditMode(`#${trId}`, false);
      updateCategory(trId);
    } else {
      $(this).addClass('editing');
      turnInlineEditMode(`#${trId}`, true);
    }
  })
}

function updateCategory(trId) {
  let data = {
    name: $(`#${trId} input.name`).val(),
    code: $(`#${trId} input.code`).val(),
  }
  if (trId !== 'new-category') data.id = trId;
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
      $(`#${trId}`).attr('id', response.id);
      let tbody = $('tbody');
      tbody.find('tr').sort((a, b) =>
        parseInt($(a).attr('id')) > parseInt($(b).attr('id'))
      ).appendTo(tbody);
    },
    error: function (error) {
      console.log(error)
    },
  });
}

function setAddNewButtons() {
  $('#new-btn').click(function () {
    // noinspection JSJQueryEfficiency
    if ($('#new-category').length === 0) {
      $('tbody').prepend(newCategoryElement);
      $('#new-category button.edit-btn').addClass('editing');
      setEditButtons();
      turnInlineEditMode('#new-category', true);
    }
  })
}

function showConfirmModal(message, confirmCallback) {
  $('#confirmDialog button.confirm').click(confirmCallback);
  $('#confirmDialog .modal-body').html(message);
  $('#confirmDialog').modal('show');
}

function deleteSelected(){
  let data = [];
  $('tbody').find('.checkbox.checked').each(function () {
    let id = $(this).parents('tr').attr('id');
    data.push(id);
  })
  $.ajax({
    url: '/admin/api/category',
    type: 'DELETE',
    contentType: 'application/json',
    data: JSON.stringify(data),
    dataType: 'json',
    success: function (response) {
      if (response === true) {
        data.forEach(function (id) {
          $(`#${id}`).remove();
        })
      }
    },
    error: function (error) {
      console.log(error)
    },
  });
}

function deleteCurrent(trId) {
  $.ajax({
    url: '/admin/api/category',
    type: 'DELETE',
    contentType: 'application/json',
    data: JSON.stringify([trId]),
    dataType: 'json',
    success: function (response) {
      if (response === true){
        $(`#${trId}`).remove();
      }
    },
    error: function (error) {
      console.log(error)
    },
  });
}

function setDeleteButtons() {
  $('#delete-all-btn').click(function () {
    showConfirmModal($(this).attr('message'), function () {
      deleteSelected();
    });
  })
  $('.delete-btn').click(function () {
    let trId = $(this).parents('tr').attr('id');
    showConfirmModal($(this).attr('message'), function () {
      deleteCurrent(trId);
    })
  })
}


const newCategoryElement = "<tr id=\"new-category\">\n" +
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
  "        </td>\n" +
  "        <td class=\"align-middle text-right\" style=\"width: 15%;\">\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn edit-btn\" aria-label=\"Chỉnh sửa thể loại\"\n" +
  "                  title=\"Chỉnh sửa thể loại\">\n" +
  "            <i class=\"fas fa-edit\"></i>\n" +
  "            <span class=\"font-weight-bold\">OK</span>\n" +
  "          </button>\n" +
  "          <button type=\"button\" class=\"btn btn-outline-dark table-btn\" aria-label=\"Xóa thể loại\"\n" +
  "                  title=\"Xóa thể loại\">\n" +
  "            <i class=\"fas fa-times\"></i>\n" +
  "          </button>\n" +
  "        </td>\n" +
  "      </tr>";